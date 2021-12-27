import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {MapService} from "../../services/map-service";
import {combineLatest, map} from "rxjs/operators";

@Component({
  selector: 'nt-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {
  test: boolean = false;

  constructor(private router: Router,
              private mapService: MapService) {
  }

  center$ =
    this.mapService.geoLocation$
      .pipe(
        map((coords) => {
          this.mapService.setCenterOnMap([coords.lon, coords.lat]);
          return coords;
        })
      )

  ngOnInit(): void {
  }

  home() {
    this.center$.subscribe();
    this.router.navigateByUrl('');
  }

  toUser() {
    this.router.navigateByUrl('/user')
  }
}
