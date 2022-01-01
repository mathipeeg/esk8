import { Injectable } from '@angular/core';
import { epsg } from '../openlayers-tools/epsg';
import proj4 from 'proj4';
import { register } from 'ol/proj/proj4';
import { createWmtsLayer } from '../openlayers-tools/wmts-builder';
import { defaults } from 'ol/interaction';
import {Feature, Map, MapEvent, View} from 'ol';
import { transform, transformExtent } from 'ol/proj';
import {MouseEvents } from '../openlayers-tools/mouse-events';
import {map, mergeMap, take} from 'rxjs/operators';
import { TileWMS } from 'ol/source';
import { FeatureEvents } from '../openlayers-tools/feature-events';
import {Observable, ReplaySubject, timer, combineLatest, interval, startWith, shareReplay} from 'rxjs';
import { Zoom } from 'ol/control';
import TileLayer from 'ol/layer/Tile';
import { Extent } from 'ol/extent';
import VectorSource from "ol/source/Vector";
import VectorLayer from "ol/layer/Vector";
import {Coordinate} from "ol/coordinate";
import {GeoLocation, LonLat} from "../models";
import {LineString, Point} from "ol/geom";
import {Fill, Icon, Stroke, Style} from "ol/style";
import IconAnchorUnits from "ol/style/IconAnchorUnits";
import {CreateRouteComponent} from "../components/create-route/create-route.component";

@Injectable({providedIn: 'root'})
export class MapService {
  readonly mouseEvents = new MouseEvents();
  readonly featureEvents = new FeatureEvents();
  continuousCoordCheck: boolean = false;
  readonly geoLocationTimer$: Observable<GeoLocation>;

  private readonly dashboardWidthSubject = new ReplaySubject<number[]>(1);
  dashboardWidth$: Observable<number[]> = this.dashboardWidthSubject;

  private readonly geoLocationSubject = new ReplaySubject<GeoLocation>(1);
  geoLocation$: Observable<GeoLocation> = this.geoLocationSubject;

  test$: Observable<any>; // todo
  private readonly onMoveEndSubject = new ReplaySubject<MapEvent>(1);

  private wmsSource: TileWMS;
  private olMap: Map;

  private url: string = "https://gs.esk8.grand-cloud.dk/geoserver/esk8/wms?"

  iconSource = new VectorSource();
  iconLayer = new VectorLayer({source: this.iconSource});

  previewRouteSource = new VectorSource();
  previewRouteLayer = new VectorLayer({source: this.previewRouteSource});

  drawCoords: boolean = false;
  lastCoords: Coordinate | undefined = undefined;

  constructor() {
    epsg.forEach(def => proj4.defs(def.srid, def.defs));
    register(proj4);

    this.dashboardWidthSubject.next([0, 0, 0, 0]);

    this.wmsSource = new TileWMS({
      params: {
        LAYERS: 'esk8:routes'
      },
      projection: 'EPSG:900913',
      url: this.url
    });

    this.onMoveEndSubject.subscribe(event => {
      const extentString = event.map.getView().calculateExtent().map(c => c.toString()).join(';');
      sessionStorage.setItem('mapExtent', extentString);
    });

    timer(0, 1000)
      .subscribe(_ => {
        this.getGeolocation()
      })

    this.geoLocationTimer$ =
      combineLatest([this.geoLocation$, this.geoLocationSubject])
        .pipe(
          map(([coords, _]) => {
            return coords;
          })
        );
  }

  createMap(): void {
    createWmtsLayer(
      'https://tile.geoteamwork.com/service/wmts?REQUEST=getcapabilities',
      {layer: 'OSM_europe', format: 'image/png'}
    )
      .subscribe(wmtsLayer => {
        this.olMap = new Map({
          target: 'map',
          controls: [],
          interactions: defaults({
            altShiftDragRotate: false,
            pinchRotate: true
          }),
          layers: [wmtsLayer],
          view: new View({
            projection: wmtsLayer.getSource().getProjection(),
            resolutions: wmtsLayer.getSource().getTileGrid().getResolutions(),
            maxZoom: 40,
            minZoom: 0,
          })
        });

        const zoom = new Zoom();
        this.olMap.addControl(zoom);
        // olMap.getView().setCenter(transform([12.2534753, 55.6469006], 'EPSG:4326', olMap.getView().getProjection()));
        const mapExtent = sessionStorage.getItem('mapExtent')?.split(';').map(s => Number(s));
        if (mapExtent?.length === 4) {
          this.olMap.getView().fit(mapExtent as Extent, {size: this.olMap.getSize()});
        } else {
          this.dashboardWidth$.pipe(take(1)).subscribe(o => {
            if (o) {
              this.olMap.getView()
                .fit(transformExtent([402713, 6020728, 941483, 6432239], 'EPSG:25832', this.olMap.getView().getProjection()), {padding: o});
            }
          });
        }

        this.olMap.getView().setZoom(7);
        this.olMap.on('moveend', event => this.onMoveEndSubject.next(event));

        this.olMap.addLayer(this.iconLayer);
        this.olMap.addLayer(this.previewRouteLayer);
        this.olMap.addLayer(new TileLayer({source: this.wmsSource, preload: 4}));
        this.mouseEvents.setMap(this.olMap);
        this.featureEvents.setSource(this.wmsSource, 'esk8:routes').setMouseEvents(this.mouseEvents);

        this.olMap.getViewport().addEventListener('contextmenu', (e)=> {
          e.preventDefault();
          //transform(olMap.getEventCoordinate(e), 'EPSG:900913', 'CRS:84')
          //this.drawIcon(olMap.getEventCoordinate(e));
        })

      });
  }

  setPadding(padding: number[]): void {
    this.dashboardWidthSubject.next(padding);
  }

  drawIcon(coordinate: Coordinate) {
    // if(this.drawCoords) {
    const iconFeature = new Feature(new Point(coordinate));
    const iconStyle = new Style({
      image: new Icon({
        anchor: [0.5, 0.5],
        // anchorXUnits: IconAnchorUnits.FRACTION,
        // anchorYUnits: IconAnchorUnits.FRACTION,
        src: `assets/my_location.png`,
        scale: 0.6
      })
    });
    iconFeature.setStyle(iconStyle);
    this.iconSource.clear();
    this.iconSource.addFeature(iconFeature);
    // }
  }

  drawPreviewRoute(coordinates: [Coordinate]) {
    console.log('draw')
    var featureLineCoords = []

    for (var i = 0; i < coordinates.length; i++) {
      console.log(coordinates[i])
      featureLineCoords.push(transform(coordinates[i]
      , 'EPSG:4326'
      , this.olMap.getView().getProjection()))
    }

    var featureLine = new Feature({
      geometry: new LineString(featureLineCoords)
    });

    const lineStyle = new Style({
        fill: new Fill({ color: '#23acde'}),
        stroke: new Stroke({ color: '#23acde', width: 7 })
    });
    featureLine.setStyle(lineStyle);
    //this.previewRouteSource.clear();
    this.previewRouteSource.addFeature(featureLine);
  }

  sleep(milliseconds: number) {
    const date = Date.now();
    let currentDate = null;
    do {
      currentDate = Date.now();
    } while (currentDate - date < milliseconds);
  }

  getGeolocation() {
    const positionOptions: PositionOptions = {} as PositionOptions;
    positionOptions.enableHighAccuracy = true;
    // Navigator fås fra "Window" objekt der repræsentere browsing contexten, "Navigatoren" repræsenterer bruger oplysninger.
    // getCurrentPosition() tager i mod en succes, funktion, en erorr funktion og option objekt. query'er selv Permission.
    // Retrunere position objekt.
    navigator.geolocation.getCurrentPosition(
      (position) => {
        this.geoLocationSubject.next({lon: position.coords.longitude, lat: position.coords.latitude});

        this.drawIcon(transform([position.coords.longitude, position.coords.latitude]
                                          , 'EPSG:4326'
                                          , this.olMap.getView().getProjection()))
      },
      () => {
        console.log('error')
        // this.geoLocationSubject.next({lon: 0, lat: 0});
      },
      positionOptions
    );
    // this.geoLocationSubject.next({lon: 0, lat: 0});
  }

  drawRouteIcons(coordinate: Coordinate) {
    // hvis ruten er startet, skal den tegne et ikon på den lokation
  }

  setCenterOnMap(coordinates: Coordinate) {
    this.olMap.getView().setCenter(transform(coordinates, 'EPSG:4326', this.olMap.getView().getProjection()));
  }

  clearPreview() {
    this.previewRouteSource.clear();
  }
}



