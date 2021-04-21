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
  tabName: string = 'text';
  image: string = null;
  // bac = 'https://shop.piana.ir:8443/assets/header/4Tb9tC59dg1Wd0…tJJHiPn10uDUml5jz2G424JWWUyjoLbjtwiG3C84TvB9.jpeg';

  constructor(public authService: AuthenticationService,
              public shareStateService: ShareStateService) { }

  ngOnInit(): void {
    this.authService.authSubject.subscribe(appInfo => {
      this.siteInfo = appInfo.siteInfo;
      console.log(this.siteInfo.headerImage)
      this.siteInfo.headerImage = this.siteInfo.headerImage.replace(/\s/g, '')
      console.log(this.siteInfo.headerImage)
    });
  }

  updateSiteInfo() {
    this.authService.updateSiteInfo(this.siteInfo);
  }

  tabChange(tabName) {
    this.tabName = tabName;
  }

  selectImage(image) {
    this.image = image;
    console.log(this.image)
    this.siteInfo.headerImage = this.image;
    this.authService.updateSiteInfo(this.siteInfo)
  }
}
