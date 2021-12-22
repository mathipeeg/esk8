import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {EndRouteData, GeoLocation, Route, StartRouteData} from "../../models";
import {map} from "rxjs/operators";
import {MapService} from "../../services/map-service";
import {Coordinate, toStringXY} from "ol/coordinate";
import {lineStringCoordinateAtM} from "ol/geom/flat/interpolate";
import {LineString} from "ol/geom";
import {RouteService} from "../../services/route.service";


@Component({
  selector: 'nt-create-route',
  templateUrl: './create-route.component.html',
  styleUrls: ['./create-route.component.scss']
})
export class CreateRouteComponent implements OnInit {
  power: number | undefined;
  degrees: number | undefined;
  newRoute: boolean = false;
  // test: LineString = Position;
  newRouteCoords: [[number, number]] = [[0,0]];

  constructor(public dialog: MatDialog,
              private mapService: MapService,
              private routeService: RouteService) {
  }

  ngOnInit(): void {
    console.log(this.newRouteCoords)
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
        console.log('jsfkl')
      }
    });
  }

  stopRoute() {
    this.newRoute = false;
    this.newRouteCoords.shift();
    console.log(this.newRouteCoords)
    this.endRoute();
  }

  endRoute() {
    const dialogRef = this.dialog.open(EndRouteDialogComponent, {
      width: '250px',
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result)
      console.log('the route has been ended somehow');
      let route = {} as Route;
      route.name = result.name;
      route.rating = result.rating;
      route.category = result.category;
      route.length = 5; // todo
      route.note = result.note;
      route.roadType = result.terrain;
      route.picture = [0];
      const ls = new LineString(this.newRouteCoords);
      console.log(ls)
      route.geometry = ls;
      this.routeService.addRoute(route).subscribe((e)=> console.log(e));
    });
  }

  coordinates$ =
    this.mapService.geoLocationTimer$
      .pipe(
        map( (coords: GeoLocation) => {
          if (this.newRoute) {
            // save location to db.
            let coord: Coordinate = [coords.lon, coords.lat];
            // console.log(this.test)
            // this.test.appendCoordinate(coord);
            this.newRouteCoords.push([coords.lon, coords.lat]);
            // set center in service.
            this.mapService.setCenterOnMap([coords.lon, coords.lat]);
            console.log(this.newRouteCoords) // todo check why 0,0 isn't there?
          }
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

