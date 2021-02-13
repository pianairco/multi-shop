import { Component, OnInit } from '@angular/core';
import axios from "axios";
import {ConstantService} from "../../services/constant.service";
import {NotificationService} from "../../services/notification.service";
import {LoadingService} from "../../services/loading.service";

@Component({
  selector: 'app-new-site',
  templateUrl: './new-site.component.html',
  styleUrls: ['./new-site.component.css']
})
export class NewSiteComponent implements OnInit {

  tenantId: string = '';

  constructor(private constantService: ConstantService,
              private loadingService: LoadingService,
              private notificationService: NotificationService) { }

  ngOnInit(): void {
  }

  addSite() {
    if(this.tenantId != null && this.tenantId != '') {
      this.loadingService.changeState(true);
      axios.post(this.constantService.getRemoteServer() + '/api/site/add',
        { 'tenantId': this.tenantId },
        { headers: { 'Content-Type': 'APPLICATION/JSON' } }).then(
        res => {
          this.loadingService.changeState(false);
          this.notificationService.changeMessage("success", "موفق")
          this.tenantId = '';
        }, err => {
          this.loadingService.changeState(false);
          this.notificationService.changeMessage("error", "ناموفق")
        }
      )
    }

  }
}
