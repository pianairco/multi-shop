import { Component, OnInit } from '@angular/core';
import {AuthenticationService, SiteInfo} from "../../services/authentication-service.service";
import {ShareStateService} from "../../services/share-state.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  siteInfo: SiteInfo = new SiteInfo();
  isEditedMode: boolean = false;
  tabName: string = 'image';

  constructor(public authService: AuthenticationService,
              public shareStateService: ShareStateService) { }

  ngOnInit(): void {
    this.authService.authSubject.subscribe(appInfo => {
      this.siteInfo = appInfo.siteInfo;
    });
  }

  updateSiteInfo() {
    this.authService.updateSiteInfo(this.siteInfo);
  }

  tabChange(tabName) {
    this.tabName = tabName;
  }
}
