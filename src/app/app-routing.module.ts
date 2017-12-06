import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {WeatherComponent} from './weather/weather.component';
import {AdminComponent} from './admin/admin.component';
import {UserComponent} from './user/user.component';
import {ChartComponent} from './chart/chart.component';
import {LoginComponent} from "./login/login.component";
import {RegistrationComponent} from "./registration/registration.component";

const routes: Routes = [
  {
    path: ' ', redirectTo: 'chart', pathMatch: 'full'
  },
  {
    path: 'weather',
    component: WeatherComponent
  },
  {
    path: 'admin_role',
    component: AdminComponent
  },
  {
    path: 'user',
    component: UserComponent
  },
  {
    path: 'chart',
    component: ChartComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'registration',
    component: RegistrationComponent
  }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule],
  declarations: []
})
export class AppRoutingModule {
}
