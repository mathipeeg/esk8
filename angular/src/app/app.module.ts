import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MapViewComponent} from "./map-view/map-view.component";
import { DashboardComponent } from './components/dashboard/dashboard.component';
import {CreateRouteDialogComponent, NavBarComponent} from "./components/nav-bar/nav-bar.component";
import {HeaderComponent} from "./components/header/header.component";
import {MatButtonModule} from "@angular/material/button";
import {MatDialogModule} from "@angular/material/dialog";
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import { UserComponent } from './components/user/user.component';
import { SettingsComponent } from './components/settings/settings.component';
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {MatSelectModule} from "@angular/material/select";

@NgModule({
  declarations: [
    AppComponent,
    MapViewComponent,
    DashboardComponent,
    NavBarComponent,
    HeaderComponent,
    CreateRouteDialogComponent,
    UserComponent,
    SettingsComponent
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
    MatSelectModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
