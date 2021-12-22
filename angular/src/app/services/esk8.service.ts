import { Injectable } from '@angular/core';
import {
  catchError,
  combineLatest,
  EMPTY,
  Observable,
  of,
  ReplaySubject,
  retry,
  shareReplay,
  startWith,
  switchMap,
  timer
} from "rxjs";
import {Board, GeoLocation, LonLat, Route, User} from "../models";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class Esk8Service {
  private readonly baseUrl: string = '/rest';
  readonly users$: Observable<User[]>;
  readonly userRoutes$: Observable<Route[]>;

  private readonly geoLocationSubject = new ReplaySubject<GeoLocation>(1);
  geoLocation$: Observable<GeoLocation> = this.geoLocationSubject;

  // readonly lonlat$: Observable<LonLat>;

  public readonly userIdSubject = new ReplaySubject<number>(1); // triggers selectedLocation$, selectedRoute$, routes$

  constructor(private readonly http: HttpClient) {
    this.userIdSubject.next(1);

    this.users$ = timer(0, 5000)
      .pipe(
        switchMap(() => this.http.get<User[]>(`${this.baseUrl}/users/all`) // todo also med id
          .pipe(catchError((error) => {
            return EMPTY;
          }))),
        retry(1),
        shareReplay(1)
      );

    this.userRoutes$ = combineLatest([this.userIdSubject])
      .pipe(
        switchMap(([userId]) => this.http.get<Route[]>(`${this.baseUrl}/routes/all/${userId}`) // todo med id
          .pipe(catchError((error) => {
            console.log(error)
            return EMPTY;
          }))),
        retry(1),
        shareReplay(1)
      );
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
      },
      () => {
        this.geoLocationSubject.next({lon: 0, lat: 0});
      },
      positionOptions
    );
  }

  setUserSubjectId(userId: number) {
    let isTheSame = true;
    this.userIdSubject.subscribe(
      value => {
        if (value !== userId) {
          isTheSame = false;
        }
      }
    );
    if (!isTheSame) {
      this.userIdSubject.next(userId);
    }
  }
}
