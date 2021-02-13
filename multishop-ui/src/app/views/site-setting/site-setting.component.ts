import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-site-setting',
  templateUrl: './site-setting.component.html',
  styleUrls: ['./site-setting.component.css']
})
export class SiteSettingComponent implements OnInit {

  showSidebar = true;

  constructor() { }

  ngOnInit(): void {
  }

  toggleSidebar() {
    this.showSidebar = !this.showSidebar;
  }
}
