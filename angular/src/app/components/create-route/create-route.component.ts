import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {EndRouteData, GeoLocation, Route, StartRouteData} from "../../models";
import {map} from "rxjs/operators";
import {MapService} from "../../services/map-service";
import {Coordinate, toStringXY} from "ol/coordinate";
import {lineStringCoordinateAtM} from "ol/geom/flat/interpolate";
import {LineString} from "ol/geom";
import {RouteService} from "../../services/route.service";
import {Observable} from "rxjs";


@Component({
  selector: 'nt-create-route',
  templateUrl: './create-route.component.html',
  styleUrls: ['./create-route.component.scss']
})
export class CreateRouteComponent implements OnInit {
  power: number | undefined;
  degrees: number | undefined;
  newRoute: boolean = false;
  newRouteWKT: string = '';
  newRouteCoords: [Coordinate] | undefined;

  constructor(public dialog: MatDialog,
              private mapService: MapService,
              private routeService: RouteService) {
  }

  ngOnInit(): void {
    console.log(this.newRouteWKT)
  }

  createRoute(): void {
    const dialogRef = this.dialog.open(CreateRouteDialogComponent, {
      width: '250px',
      data: {power: this.power}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      if (result) {
        this.newRoute = true;
        this.power = result.power;
        this.degrees = result.degrees;
      }
      else {
        // console.log('ongoing')
      }
    });
  }

  stopRoute() {
    this.newRoute = false;
    // this.newRouteCoords.shift();
    console.log(this.newRouteWKT)
    this.endRoute();
  }

  endRoute() {
    const dialogRef = this.dialog.open(EndRouteDialogComponent, {
      width: '250px',
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result)
      let route = {} as Route;
      route.name = result.name;
      route.rating = result.rating;
      route.category = result.category;
      route.length = 5; // todo
      route.note = result.note;
      route.roadType = result.terrain;
      route.picture = [0];
      route.userId = 1;
      console.log(this.newRouteWKT)
      let coords = this.newRouteWKT.substring(0, this.newRouteWKT.length - 2)
      const wkt = 'LINESTRING(' + coords + ')'
      this.routeService.addRoute(route, wkt).subscribe((e)=> console.log(e));
      this.newRouteWKT = ''; // todo find better way to do this
      this.mapService.clearPreview();
    });
  }

  coordinates$: Observable<String> =
    this.mapService.geoLocation$
      .pipe(
        map( (coords: GeoLocation) => {
          if (this.newRoute) {
            this.newRouteWKT = this.newRouteWKT + coords.lon.toString() + ' ' + coords.lat.toString() + ', ';
            if (this.newRouteCoords) {
              this.newRouteCoords.push([coords.lon, coords.lat])
              this.mapService.drawPreviewRoute(this.newRouteCoords)
            } else {
              this.newRouteCoords = [[coords.lon, coords.lat]]
            }
            console.log(this.newRouteWKT)
          }
          return this.newRouteWKT;
        })
      )
}

@Component({
  selector: 'create-route-dialog-component',
  templateUrl: 'create-route-dialog.html',
})
export class CreateRouteDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<CreateRouteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: StartRouteData,
  ) {}

  onSkip(): void {
    this.dialogRef.close();
  }
}

@Component({
  selector: 'end-route-dialog-component',
  templateUrl: 'end-route-dialog.html',
})
export class EndRouteDialogComponent {
  category = ['SCENIC', 'SPEED', 'CHILL', 'EXPLORE', 'OFFROAD'];
  roadType = ['GRAVEL', 'DIRT', 'GRASS', 'CONCRETE', 'PATH', 'TILES', 'WOOD', 'MIX'];

  constructor(
    public dialogRef: MatDialogRef<EndRouteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: EndRouteData,
  ) {}

  onSkip(): void {
    this.dialogRef.close();
  }
}

