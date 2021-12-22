import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {MapService} from "../../services/map-service";

@Component({
  selector: 'nt-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {
  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  home() {
    this.router.navigateByUrl('');
  }

  toUser() {
    this.router.navigateByUrl('/user')
  }
}
