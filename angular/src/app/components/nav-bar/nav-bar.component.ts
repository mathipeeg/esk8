import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {DialogData} from "../../models";
import {Router} from "@angular/router";

@Component({
  selector: 'nt-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {
  power: number | undefined;
  degrees: number | undefined;

  constructor(public dialog: MatDialog,
              private router: Router) { }

  ngOnInit(): void {
  }

  createRoute(): void {
    const dialogRef = this.dialog.open(CreateRouteDialogComponent, {
      width: '250px',
      data: {power: this.power},
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.power = result.power;
      this.degrees = result.degrees;
      console.log(this.power, this.degrees)
    });
  }

  home() {
    this.router.navigateByUrl('');
  }

  toUser() {
    this.router.navigateByUrl('/user')
  }
}

@Component({
  selector: 'create-route-dialog-component',
  templateUrl: 'create-route-dialog-component.html',
})
export class CreateRouteDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<CreateRouteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
  ) {}

  onSkip(): void {
    this.dialogRef.close();
  }
}
