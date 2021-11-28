import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MapViewComponent} from "./map-view/map-view.component";

const routes: Routes = [
  {path: '', component: MapViewComponent},
  {path: '**', redirectTo: '', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
