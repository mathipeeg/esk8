import { Component, OnInit } from '@angular/core';
import { MapService } from '../services/map-service';
import { Router } from '@angular/router';
import {Observable, pipe, ReplaySubject} from "rxjs";
import {GeoLocation} from "../models";

@Component({
  selector: 'app-map-view',
  templateUrl: './map-view.component.html',
  styleUrls: ['./map-view.component.scss']
})
export class MapViewComponent implements OnInit {

  private readonly geoLocationSubject = new ReplaySubject<GeoLocation>(1);
  geoLocation$: Observable<GeoLocation> = this.geoLocationSubject;

  constructor(
    private mapService: MapService,
    private router: Router
  ) {

  }

  ngOnInit(): void {
    sessionStorage.setItem('lastPage', this.router.url);
    this.mapService.createMap();
    // this.getGeolocation()
    // this.geoLocation$.subscribe( location => console.log(location))
  }

  getGeolocation() {
    const positionOptions: PositionOptions = {} as PositionOptions;
    positionOptions.enableHighAccuracy = true;
    // Navigator fås fra "Window" objekt der repræsentere browsing contexten, "Navigatoren" repræsenterer bruger oplysninger.
    // getCurrentPosition() tager i mod en succes, funktion, en erorr funktion og option objekt. query'er selv Permission.
    // Returnerer position objekt.
    navigator.geolocation.getCurrentPosition(
      (position) => {
        this.geoLocationSubject.next({lon: position.coords.longitude, lat: position.coords.latitude});
      },
      () => {
        this.geoLocationSubject.next({lon: 0, lat: 0});
      },
      positionOptions
    );
  }

}
