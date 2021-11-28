import { Injectable } from '@angular/core';
import { epsg } from '../openlayers-tools/epsg';
import proj4 from 'proj4';
import { register } from 'ol/proj/proj4';
import { createWmtsLayer } from '../openlayers-tools/wmts-builder';
import { defaults } from 'ol/interaction';
import {Feature, Map, MapEvent, View} from 'ol';
import { transform, transformExtent } from 'ol/proj';
import { mouseCoordinateConverter, MouseEvents } from '../openlayers-tools/mouse-events';
import {map, take} from 'rxjs/operators';
import { TileWMS } from 'ol/source';
import { FeatureEvents } from '../openlayers-tools/feature-events';
import {Observable, ReplaySubject} from 'rxjs';
import { Zoom } from 'ol/control';
//import { checkNotUndefined } from '@northtech/ginnungagap';
import TileLayer from 'ol/layer/Tile';
import { Extent } from 'ol/extent';
import VectorSource from "ol/source/Vector";
import VectorLayer from "ol/layer/Vector";
import {Coordinate} from "ol/coordinate";

@Injectable({providedIn: 'root'})
export class MapService {
  readonly mouseEvents = new MouseEvents();
  readonly featureEvents = new FeatureEvents();

  private readonly dashboardWidthSubject = new ReplaySubject<number[]>(1);
  dashboardWidth$: Observable<number[]> = this.dashboardWidthSubject;

  private readonly onMoveEndSubject = new ReplaySubject<MapEvent>(1);

  private searchString: string | undefined;

  private wmsSource: TileWMS;

  private url: string = "http://192.168.1.221:8081/geoserver/esk8/wms?"
  /*private url: string = checkNotUndefined((
    window as any
  ).frontendProperties?.geoserverUrl);*/

  iconSource = new VectorSource();
  iconLayer = new VectorLayer({source: this.iconSource});

  drawCoords: boolean = false;
  lastCoords: Coordinate | undefined = undefined;
  lastCoordsCrs84: Coordinate | undefined = undefined;

  constructor() {
    // this.url = (window as any).frontendProperties.geoServerUrl
    // console.log("url", (window as any).frontendProperties.geoServerUrl)
    epsg.forEach(def => proj4.defs(def.srid, def.defs));
    register(proj4);
    this.searchString = "state ilike 'ACTIVE'";

    this.dashboardWidthSubject.next([0, 0, 0, 0]);

    // Demo output of the clicked coordinates. Notice that the MouseEvents object and its observables are available immediately,
    // even though it has not yet been hooked up to a map creating actual click events:
    this.mouseEvents.clicks
      .pipe(map(mouseCoordinateConverter('CRS:84')))
      .subscribe(value => {
        this.lastCoordsCrs84 = value
      });
    this.mouseEvents.clicks
      .pipe(map(mouseCoordinateConverter('EPSG:25832')))
      .subscribe();

    this.wmsSource = new TileWMS({
      params: {
        LAYERS: 'esk8:routes'
      },
      projection: 'EPSG:900913',
      url: this.url
      // url: 'https://gs.climbalong.com/geoserver/northtech/wms?'
    });
    this.onMoveEndSubject.subscribe(event => {
      const extentString = event.map.getView().calculateExtent().map(c => c.toString()).join(';');
      sessionStorage.setItem('mapExtent', extentString);
    });

  }

  createMap(): void {
    createWmtsLayer(
      'https://tile.geoteamwork.com/service/wmts?REQUEST=getcapabilities',
      {layer: 'OSM_europe', format: 'image/png'}
    )
      .subscribe(wmtsLayer => {
        const olMap = new Map({
          target: 'map',
          controls: [],
          interactions: defaults({
            altShiftDragRotate: false,
            pinchRotate: false
          }),
          layers: [wmtsLayer],
          view: new View({
            projection: wmtsLayer.getSource().getProjection(),
            resolutions: wmtsLayer.getSource().getTileGrid().getResolutions(),
            zoom: 8,
            maxZoom: 40,
            minZoom: 0
          })
        });

        const zoom = new Zoom();
        olMap.addControl(zoom);
        olMap.getView().setCenter(transform([721371, 6174352], 'EPSG:25832', olMap.getView().getProjection()));

        const mapExtent = sessionStorage.getItem('mapExtent')?.split(';').map(s => Number(s));
        if (mapExtent?.length === 4) {
          olMap.getView().fit(mapExtent as Extent, {size: olMap.getSize()});
        } else {
          this.dashboardWidth$.pipe(take(1)).subscribe(o => {
            if (o) {
              olMap.getView()
                .fit(transformExtent([402713, 6020728, 941483, 6432239], 'EPSG:25832', olMap.getView().getProjection()), {padding: o});
            }
          });
        }
        olMap.on('moveend', event => this.onMoveEndSubject.next(event));

        //olMap.on('click', event => console.log(event));

        olMap.addLayer(this.iconLayer);
        olMap.addLayer(new TileLayer({source: this.wmsSource, preload: 4}));
        this.mouseEvents.setMap(olMap);
        this.featureEvents.setSource(this.wmsSource, 'climbalong-locations-feature').setMouseEvents(this.mouseEvents);

        olMap.getViewport().addEventListener('contextmenu', (e)=> {
          e.preventDefault();
          //transform(olMap.getEventCoordinate(e), 'EPSG:900913', 'CRS:84')
          //this.drawIcon(olMap.getEventCoordinate(e));
        })

      });
  }

  setPadding(padding: number[]): void {
    this.dashboardWidthSubject.next(padding);
  }

  setFilter(value: string) {
    if (value === '') {
      this.searchString = "state ilike 'ACTIVE'";
    } else {
      this.searchString
        = `(name ilike '%${value}%' or address ilike '%${value}%' or postal_code ilike '%${value}%' or city ilike '%${value}%') and state ilike 'ACTIVE'`;
    }
    this.wmsSource.updateParams({CQL_FILTER: this.searchString});
  }

  sleep(milliseconds: number) {
    const date = Date.now();
    let currentDate = null;
    do {
      currentDate = Date.now();
    } while (currentDate - date < milliseconds);
  }
}


