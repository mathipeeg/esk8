import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {UserComponent} from "./components/user/user.component";
import {SettingsComponent} from "./components/settings/settings.component";

const routes: Routes = [
  {path: '', component: DashboardComponent},
  {path: 'user', component: UserComponent},
  {path: 'settings', component: SettingsComponent},
  {path: '**', redirectTo: '', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
