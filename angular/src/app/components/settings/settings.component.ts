import { Component, OnInit } from '@angular/core';
import {Location} from "@angular/common";
import {AuthenticationService} from "../../services/authentication.service";
import {BoardService} from "../../services/board.service";

@Component({
  selector: 'nt-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {

  constructor(private location: Location
              , private readonly authenticationService: AuthenticationService
              , readonly boardService: BoardService) { }

  ngOnInit() {
    this.authenticationService.authorization$.subscribe((a) => {
      console.log('app authenticated', a);
      console.log(`Current access token is '${a}'`);
    });

    this.authenticationService.currentUser$.subscribe((a) => {
      console.log('User', a);
    });
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
