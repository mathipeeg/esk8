import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {Location} from "@angular/common";

@Component({
  selector: 'nt-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  constructor(private router: Router,
              private location: Location) { }

  ngOnInit(): void {
  }

  toSettings() {
    this.router.navigateByUrl('settings');
  }

  back() {
    this.location.back();
  }

}
