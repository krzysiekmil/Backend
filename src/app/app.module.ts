import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {WeatherComponent} from './weather/weather.component';
import {AppRoutingModule} from './app-routing.module';
import {AdminComponent} from './admin/admin.component';
import {UserComponent} from './user/user.component';
import {DataService} from './service/data.service';
import {HttpModule} from '@angular/http';
import {ChartComponent} from './chart/chart.component';
import {ChartsModule} from 'ng2-charts';
import {HashLocationStrategy, LocationStrategy} from "@angular/common";
import {LoginComponent} from './login/login.component';
import {FormsModule} from "@angular/forms";
import {RegistrationComponent} from './registration/registration.component';
import {AuthGuard} from "./guards/auth.guard";

@NgModule({
  declarations: [
    AppComponent,
    WeatherComponent,
    AdminComponent,
    UserComponent,
    ChartComponent,
    LoginComponent,
    RegistrationComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpModule,
    ChartsModule,
    FormsModule
  ],
  providers: [
    DataService,
    {provide: LocationStrategy, useClass: HashLocationStrategy},
    AuthGuard


  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
