import { Injectable } from '@angular/core';
import {catchError, EMPTY, Observable, retry, shareReplay, switchMap, timer} from "rxjs";
import {Board, User} from "../models";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class Esk8Service {
  private readonly baseUrl: string = '/rest';
  readonly users$: Observable<User[]>;
  readonly boards$: Observable<Board[]>;

  constructor(private readonly http: HttpClient) {
    this.users$ = timer(0, 5000)
      .pipe(
        switchMap(() => this.http.get<User[]>(`${this.baseUrl}/users/all`)
          .pipe(catchError((error) => {
            return EMPTY;
          }))),
        retry(1),
        shareReplay(1)
      );

    this.boards$ = timer(0, 5000)
      .pipe(
        switchMap(() => this.http.get<Board[]>(`${this.baseUrl}/boards/all`)
          .pipe(catchError((error) => {
            return EMPTY;
          }))),
        retry(1),
        shareReplay(1)
      );
  }
}
