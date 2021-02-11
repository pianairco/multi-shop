import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.css']
})
export class TopbarComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  loginClick() {
    console.log(localStorage.getItem('currentUser'));
    console.log(localStorage.getItem('currentUser')['username']);
  }
}
