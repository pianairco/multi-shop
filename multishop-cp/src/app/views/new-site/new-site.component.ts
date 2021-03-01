import {Component, Directive, ElementRef, HostListener, Input, OnInit} from '@angular/core';
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
  public mask = [/[a-zA-z]{4,}/];

  pattern="[A-Za-z]{4,}";

  nameChange(t) {
    console.log(t)
  }

  constructor(private constantService: ConstantService,
              private loadingService: LoadingService,
              private notificationService: NotificationService) { }

  ngOnInit(): void {
  }

  addSite() {
    if(this.tenantId.length < 5) {
      this.notificationService.changeMessage("error", "نام باید حداقل دارای پنج کاراکتر باشد")
    }

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
