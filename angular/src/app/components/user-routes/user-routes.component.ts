import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {Esk8Service} from "../../services/esk8.service";
import {Route} from "../../models";
import {RouteService} from "../../services/route.service";

@Component({
  selector: 'nt-user-routes',
  templateUrl: './user-routes.component.html',
  styleUrls: ['./user-routes.component.scss']
})
export class UserRoutesComponent implements OnInit {
  userId: number;
  userId$: Observable<number | undefined>;

  constructor(private route: ActivatedRoute,
              private esk8Service: Esk8Service,
              private routeService: RouteService) { }

  ngOnInit(): void {
    this.userId$ = this.route.paramMap.pipe(
      map(params => {
        let uId = Number(params.get('userId'))
        console.log(uId);
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

  deleteRoute(id: number) {
    console.log(id)
    this.routeService.deleteRoute(id).subscribe(e => {
      console.log(e);
    })
    console.log('deleting')
  }

  editRoute() {
    console.log('editing')
  }
}
