import { Component, OnInit } from '@angular/core';
import { MapService } from '../../services/map-service';
import { Router } from '@angular/router';
import {combineLatest, Observable, timer} from "rxjs";
import {map} from "rxjs/operators";

@Component({
  selector: 'app-map-view',
  templateUrl: './map-view.component.html',
  styleUrls: ['./map-view.component.scss']
})
export class MapViewComponent implements OnInit {


  constructor(
    private mapService: MapService,
    private router: Router) {

  }

  ngOnInit(): void {
    sessionStorage.setItem('lastPage', this.router.url);
    this.mapService.createMap();
    this.mapService.getGeolocation();

    // this.mapService.test$.subscribe( location => {
    //   console.log(location)
    // })
  }

  startRoute() {
    // hvis rute er startet, skal den tjekke coords hvert x sekund og gemme det som LineString i databasen
  }
}
