import {ChangeDetectorRef, Component} from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "./service/user.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  constructor(private router: Router, private userService: UserService, private detector: ChangeDetectorRef) {
  }

}
