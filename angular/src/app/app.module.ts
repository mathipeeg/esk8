import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MapViewComponent} from "./components/map-view/map-view.component";
import { DashboardComponent } from './components/dashboard/dashboard.component';
import {CreateRouteDialogComponent, EndRouteDialogComponent} from "./components/create-route/create-route.component";
import {HeaderComponent} from "./components/header/header.component";
import {MatButtonModule} from "@angular/material/button";
import {MatDialogModule} from "@angular/material/dialog";
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {CreateBoardDialogComponent, UserComponent} from './components/user/user.component';
import { SettingsComponent } from './components/settings/settings.component';
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {MatSelectModule} from "@angular/material/select";
import {HttpClientModule} from "@angular/common/http";
import { UserRoutesComponent } from './components/user-routes/user-routes.component';
import { CreateRouteComponent } from './components/create-route/create-route.component';
import {NavBarComponent} from "./components/nav-bar/nav-bar.component";
import {AuthConfigModule} from "./auth/auth-config.module";

@NgModule({
  declarations: [
    AppComponent,
    MapViewComponent,
    DashboardComponent,
    NavBarComponent,
    HeaderComponent,
    CreateRouteDialogComponent,
    UserComponent,
    SettingsComponent,
    CreateBoardDialogComponent,
    UserRoutesComponent,
    CreateRouteComponent,
    EndRouteDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatDialogModule,
    MatInputModule,
    FormsModule,
    MatSlideToggleModule,
    MatSelectModule,
    AuthConfigModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
