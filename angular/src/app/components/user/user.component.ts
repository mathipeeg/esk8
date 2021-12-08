import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Location} from "@angular/common";
import {Observable} from "rxjs";
import {Board, User} from "../../models";
import {map} from "rxjs/operators";
import {Esk8Service} from "../../services/esk8.service";

@Component({
  selector: 'nt-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UserComponent implements OnInit {

  user$: Observable<User> =
    this.esk8Service.users$
      .pipe(
        map(
          (users) =>
            ({
              id: users[0].id,
              routeNotificationId: users[0].routeNotificationId,
              email: users[0].email,
              password: users[0].password,
              name: users[0].name,
              gender: users[0].gender,
              height: users[0].height,
              weight: users[0].weight,
            })
        )
      )

  boards$: Observable<Board[]> =
    this.esk8Service.boards$
      .pipe(
        map(data => {
          // todo sort boards via userID
        }) // test
      )

  constructor(private router: Router,
              private location: Location,
              private esk8Service: Esk8Service) { }

  ngOnInit(): void {
  }

  toSettings() {
    this.router.navigateByUrl('settings');
  }

  back() {
    this.location.back();
  }
}
