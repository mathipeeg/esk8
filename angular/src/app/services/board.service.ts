import { Injectable } from '@angular/core';
import {catchError, combineLatest, EMPTY, Observable, of, retry, shareReplay, switchMap, timer} from "rxjs";
import {Board} from "../models";
import {HttpClient} from "@angular/common/http";
import {Esk8Service} from "./esk8.service";
import {AuthenticationService} from "./authentication.service";

@Injectable({
  providedIn: 'root'
})
export class BoardService {
  private readonly baseUrl: string = '/rest';
  readonly boards$: Observable<Board[]>;
  readonly userBoards$: Observable<Board[]>;

  constructor(private readonly http: HttpClient,
              private esk8Service: Esk8Service,
              private authenticationService: AuthenticationService) {
    this.boards$ = timer(0, 5000)
      .pipe(
        switchMap(() => this.http.get<Board[]>(`${this.baseUrl}/boards/all`, {headers: this.authenticationService.getAccessToken()}) // todo med id
          .pipe(catchError((error) => {
            console.log(error)
            return EMPTY;
          }))),
        retry(1),
        shareReplay(1)
      );

    this.userBoards$ = combineLatest([this.esk8Service.userIdSubject])
      .pipe(
        switchMap(([userId]) => this.http.get<Board[]>(`${this.baseUrl}/boards/all/${userId}`) // todo med id
          .pipe(catchError((error) => {
            console.log(error)
            return EMPTY;
          }))),
        retry(1),
        shareReplay(1)
      );
  }

  addBoard(board: Board): Observable<Board | undefined> {
    return this.http.post<Board>(`${this.baseUrl}/boards/create`, board)
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
