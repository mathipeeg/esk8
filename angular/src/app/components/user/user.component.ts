import {ChangeDetectionStrategy, Component, Inject, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Location} from "@angular/common";
import {Observable, tap} from "rxjs";
import {Board, LoginUser, User, UserDb} from "../../models";
import {combineLatest, map} from "rxjs";
import {Esk8Service} from "../../services/esk8.service";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {BoardService} from "../../services/board.service";
import {AuthenticationService} from "../../services/authentication.service";
import {defaultTargetBuilders} from "@angular/cdk/schematics";

@Component({
  selector: 'nt-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UserComponent implements OnInit {
  newBoard = {} as Board;
  userId = 0;

  boards$: Observable<Board[]> =
    this.boardService.userBoards$
      .pipe(
        map((data) => {
          console.log(data);
          return data;
        }) // test
      )

  user$: Observable<User> =
    combineLatest([this.authenticationService.currentUser$, this.esk8Service.currentUser$])
      .pipe(
        map(([authUser, dbUser]) => ({
          id: dbUser.id,
          routeNotificationId: dbUser.routeNotificationId,
          email: authUser.email,
          firstName: authUser.name,
          lastName: authUser.lastname,
          username: authUser.username,
          gender: dbUser.gender,
          height: dbUser.height,
          weight: dbUser.weight,
        })), tap(console.log)
      )

  constructor(private router: Router,
              private location: Location,
              private esk8Service: Esk8Service,
              private boardService: BoardService,
              public dialog: MatDialog,
              private readonly authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.authenticationService.authorization$.subscribe((a) => {
      console.log('app authenticated', a);
      console.log(`Current access token is '${a}'`);
    });

    this.user$.subscribe(u => {
      console.log(u)
      this.userId = u.id;
    })

    // this.authenticationService.currentUser$.subscribe((a) => {
    //   console.log('User', a);
    // });
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
      if (result) {
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
        console.log(this.newBoard);
        this.boardService.addBoard(this.newBoard).subscribe();
      }
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
