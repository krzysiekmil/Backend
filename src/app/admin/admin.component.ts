import {Component, OnInit} from '@angular/core';
import {City} from '../model/city';
import {DataService} from '../service/data.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  cityList: City[];
  city: City;


  constructor(private dataService: DataService) {
  }

  getCityList() {
    this.dataService.getCityList().subscribe(data => this.cityList = data)
  }

  addCity(cityName: string) {
    this.dataService.addCityS(cityName).subscribe(this.getCityList);
    this.cityList.push(this.city);
  }

  deleteCity(cityName: string) {
    let index = this.cityList.findIndex(c => c.name === cityName);
    this.cityList.splice(index, 1);
    this.dataService.deleteCity(cityName).subscribe(this.getCityList);

  }

  ngOnInit() {
    this.getCityList();
  }
}
