import { Component, OnInit } from '@angular/core';
import axios from 'axios';
import {ConstantService} from "../../services/constant.service";
import {AuthenticationService, SiteInfo} from "../../services/authentication-service.service";
import {ShareStateService} from "../../services/share-state.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-home-view',
  templateUrl: './home-view.component.html',
  styleUrls: ['./home-view.component.css']
})
export class HomeViewComponent implements OnInit {
  siteInfo: SiteInfo = new SiteInfo();

  constructor(private route: ActivatedRoute,
              public router: Router,
              public constantService: ConstantService,
              public shareStateService: ShareStateService,
              public authService: AuthenticationService) { }

  ngOnInit(): void {
    console.log("d")
    this.authService.authSubject.subscribe(appInfo => {
      this.siteInfo = appInfo.siteInfo;
    });
    // axios.get('/api/message').then(res => {
    //   console.log(res);
    // }, err => {
    //   console.log(err);
    // })
  }

  windowRef=null;
  uuid = null;
  interval = 0;

  openLoginWindow(){
    axios.post('api/sign-in/sub-domain', {}, {headers: {"content-type": "application/json" }})
      .then(res => {
        this.uuid = res.data['uuid'];
        this.windowRef= window.open(res.data['redirect'],"child", "toolbar=no,location=no,directories=no,status=no,menubar=no,titlebar=no,fullscreen=no,scrollbars=1,resizable=no,width=430,height=220,left=500,top=100");
        this.interval = setInterval(() => { this.loginWindowClosedCheck(); }, 1000);
      }, err => {
        console.log(err)
      });
    // this.windowRef= window.open("https://shop.piana.ir:8443/#/child-view","child", "toolbar=no,location=no,directories=no,status=no,menubar=no,titlebar=no,fullscreen=no,scrollbars=1,resizable=no,width=430,height=220,left=500,top=100");

    // if (this.windowRef.addEventListener) {
    //   console.log("addEventListener")
    //   this.windowRef.addEventListener("message", this.receivemessage.bind(this), true);
    // } else {
    //   console.log("attachEvent")
    //   this.windowRef.attachEvent("onmessage", this.receivemessage.bind(this));
    // }
  }

  loginWindowClosedCheck(){
    if(this.windowRef.closed) {
      console.log("closed")
      clearInterval(this.interval);

      axios.post(this.constantService.getRemoteServer() + '/api/sign-in',
        { uuid: this.uuid },
        { headers: { 'Content-Type': 'APPLICATION/JSON', 'auth-type': 'g-oauth2' } });
    }
  }

}
