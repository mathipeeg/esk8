import { Injectable } from '@angular/core';
import {Board, Route} from "../models";
import {catchError, Observable, of} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RouteService {
  private readonly baseUrl: string = '/rest';

  constructor(private readonly http: HttpClient) { }

  addRoute(route: Route): Observable<Route | undefined> {
    return this.http.post<Route>(`${this.baseUrl}/routes/createRoute`, route)
      .pipe(
        catchError(this.handleError(`post ${this.baseUrl}.json`, undefined))
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      console.error(operation + ' - ' + JSON.stringify(error));
      return of(result as T);
    };
  }
}
