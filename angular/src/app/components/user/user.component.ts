import {ChangeDetectionStrategy, Component, Inject, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Location} from "@angular/common";
import {Observable} from "rxjs";
import {Board, User} from "../../models";
import {map} from "rxjs/operators";
import {Esk8Service} from "../../services/esk8.service";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {BoardService} from "../../services/board.service";

@Component({
  selector: 'nt-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UserComponent implements OnInit {
  newBoard = {} as Board;
  userId = 1;

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
    this.boardService.userBoards$
      .pipe(
        map((data) => data) // test
      )

  constructor(private router: Router,
              private location: Location,
              private esk8Service: Esk8Service,
              private boardService: BoardService,
              public dialog: MatDialog) { }

  ngOnInit(): void {
  }

  back() {
    this.location.back();
  }

  addBoard() {
    const dialogRef = this.dialog.open(CreateBoardDialogComponent, {
      width: '95vw',
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.newBoard.nickname = result.nickname;
      this.newBoard.weight = result.weight;
      this.newBoard.length = result.length;
      this.newBoard.boardType = result.boardType;
      this.newBoard.boardBrand = result.boardBrand;
      this.newBoard.motorType = result.motorType;
      this.newBoard.battery = result.battery;
      this.newBoard.note = result.note;
      this.newBoard.picture = [0];
      this.newBoard.userId = this.userId; // todo
      this.boardService.addBoard(this.newBoard).subscribe();
    });
  }

  viewRoutesList() {
    this.router.navigate(['userRoutes', {userId: this.userId}])
  }
}

@Component({
  selector: 'create-board-dialog-component',
  templateUrl: 'create-board-dialog.html',
})
export class CreateBoardDialogComponent {
  types = ['LONGBOARD', 'SKATEBOARD', 'SCOOTER', 'ELONGBOARD', 'ESKATEBOARD', 'ESCOOTER', 'ONEWHEELER', 'SEGWAY'];

  constructor(
    public dialogRef: MatDialogRef<CreateBoardDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Board,
  ) {}

  onSkip(): void {
    this.dialogRef.close();
  }
}
