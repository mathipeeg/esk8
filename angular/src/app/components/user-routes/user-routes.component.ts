import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {Esk8Service} from "../../services/esk8.service";
import {Route} from "../../models";

@Component({
  selector: 'nt-user-routes',
  templateUrl: './user-routes.component.html',
  styleUrls: ['./user-routes.component.scss']
})
export class UserRoutesComponent implements OnInit {
  userId: number;
  userId$: Observable<number | undefined>;

  constructor(private route: ActivatedRoute,
              private esk8Service: Esk8Service) { }

  ngOnInit(): void {
    this.userId$ = this.route.paramMap.pipe(
      map(params => {
        let uId = Number(params.get('userId'))
        if(uId !== undefined && uId > 0){
          this.esk8Service.setUserSubjectId(uId)
        }
        else{
          // redirect
        }
        return uId
      })
    );
  }

  routes$: Observable<Route[]> =
    this.esk8Service.userRoutes$
      .pipe(
        map((routes) => {
          console.log(routes)
          return routes;
        }) // test
      )
}
