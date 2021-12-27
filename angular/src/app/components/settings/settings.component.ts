import { Component, OnInit } from '@angular/core';
import {Location} from "@angular/common";
import {AuthenticationService} from "../../services/authentication.service";
import {BoardService} from "../../services/board.service";
import {Observable, tap} from "rxjs";
import {RouteNotification} from "../../models";
import {Esk8Service} from "../../services/esk8.service";
import {map} from "rxjs/operators";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatSlideToggleChange} from "@angular/material/slide-toggle";

@Component({
  selector: 'nt-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {
  // onOff = false;
  // speed = false;
  // km = false;
  // time = false;
  // public test = 20;
  // voice = '';
  public useDefault = false
  // notifications = false;
  // kmMiles = false; // todo
  formGroup: FormGroup;

  notificationSettings$: Observable<RouteNotification> =
    this.esk8Service.userNotificationSettings$
      .pipe(
        map((data) => ({
          id: data.id,
          isOn: data.isOn,
          interval: data.interval,
          avgSpeed: data.avgSpeed,
          distance: data.distance,
          time: data.time,
          voiceActor: data.voiceActor
        })), tap(console.log)
      )

  constructor(private location: Location
              , private readonly authenticationService: AuthenticationService
              , readonly boardService: BoardService,
              private esk8Service: Esk8Service) {
  }

  ngOnInit() {
    this.authenticationService.authorization$.subscribe((a) => {
      console.log('app authenticated', a);
      console.log(`Current access token is '${a}'`);
    });

    this.authenticationService.currentUser$.subscribe((a) => {
      console.log('User', a);
    });
  }

  toggle(event: MatSlideToggleChange) {
    console.log('toggle', event.checked);
    console.log(event.source.id)
    this.useDefault = event.checked; // todo
  }

  back() {
    this.location.back();
  }

  login() {
    console.log('start login');
    this.authenticationService.login();
  }

  logout() {
    console.log('start logout');
    this.authenticationService.logout();
  }

  getRoutes() {
    console.log('getting routes');
    // this.boardService.boards$;
  }

}
