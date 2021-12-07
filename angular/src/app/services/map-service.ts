import { Injectable } from '@angular/core';
import { epsg } from '../openlayers-tools/epsg';
import proj4 from 'proj4';
import { register } from 'ol/proj/proj4';
import { createWmtsLayer } from '../openlayers-tools/wmts-builder';
import { defaults } from 'ol/interaction';
import {Geolocation, Map, MapEvent, View} from 'ol';
import { transform, transformExtent } from 'ol/proj';
import { mouseCoordinateConverter, MouseEvents } from '../openlayers-tools/mouse-events';
import {map, take} from 'rxjs/operators';
import { TileWMS } from 'ol/source';
import { FeatureEvents } from '../openlayers-tools/feature-events';
import {Observable, ReplaySubject} from 'rxjs';
import { Zoom } from 'ol/control';
import TileLayer from 'ol/layer/Tile';
import { Extent } from 'ol/extent';
import VectorSource from "ol/source/Vector";
import VectorLayer from "ol/layer/Vector";
import {Coordinate} from "ol/coordinate";
import {GeoLocation} from "../models";

@Injectable({providedIn: 'root'})
export class MapService {
  readonly mouseEvents = new MouseEvents();
  readonly featureEvents = new FeatureEvents();

  private readonly dashboardWidthSubject = new ReplaySubject<number[]>(1);
  dashboardWidth$: Observable<number[]> = this.dashboardWidthSubject;

  private readonly onMoveEndSubject = new ReplaySubject<MapEvent>(1);

  private wmsSource: TileWMS;

  private url: string = "http://10.0.4.119:8081/geoserver/esk8/wms?"
  /*private url: string = checkNotUndefined((
    window as any
  ).frontendProperties?.geoserverUrl);*/

  iconSource = new VectorSource();
  iconLayer = new VectorLayer({source: this.iconSource});

  drawCoords: boolean = false;
  lastCoords: Coordinate | undefined = undefined;
  lastCoordsCrs84: Coordinate | undefined = undefined;

  constructor() {
    // this.getGeolocation();
    // this.geoLocation$.subscribe( location => {
    //   this.lon = location.lon;
    //   this.lat = location.lat;
    //   console.log(location)
    // })

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
        olMap.addControl(zoom); //below: change coords
        olMap.getView().setCenter(transform([12.2534753, 55.6469006], 'EPSG:4326', olMap.getView().getProjection()));
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
        olMap.getView().setZoom(6);
        olMap.on('moveend', event => this.onMoveEndSubject.next(event));

        olMap.addLayer(this.iconLayer);
        olMap.addLayer(new TileLayer({source: this.wmsSource, preload: 4}));
        this.mouseEvents.setMap(olMap);
        this.featureEvents.setSource(this.wmsSource, 'esk8:routes').setMouseEvents(this.mouseEvents);

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

  sleep(milliseconds: number) {
    const date = Date.now();
    let currentDate = null;
    do {
      currentDate = Date.now();
    } while (currentDate - date < milliseconds);
  }

}


