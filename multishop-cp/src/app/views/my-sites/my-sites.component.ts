import { Component, OnInit } from '@angular/core';
import axios from "axios";
import {SiteInfo} from "../new-site/new-site.component";
import {ConstantService} from "../../services/constant.service";

@Component({
  selector: 'app-my-sites',
  templateUrl: './my-sites.component.html',
  styleUrls: ['./my-sites.component.css']
})
export class MySitesComponent implements OnInit {
  sites: SiteInfo[] = [];

  constructor(
    private constantService: ConstantService
  ) { }

  ngOnInit(): void {
    axios.get(this.constantService.getRemoteServer() + '/api/site/my-site')
      .then(res => {
        if(res.data.code === 0) {
          this.sites = res.data.data
        } else {
        }
      }, err => {
      });
  }

}
