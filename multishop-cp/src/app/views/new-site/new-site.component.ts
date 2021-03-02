import {Component, Directive, ElementRef, HostListener, Input, OnInit, ViewChild} from '@angular/core';
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

  siteInfo: SiteInfo = new SiteInfo();
  tenantId: string = '';
  public mask = [/[a-zA-z]{4,}/];
  @ViewChild('siteName') siteNameElement: ElementRef;
  pattern="[A-Za-z]{4,}";

  nameChange(t) {
    console.log(t)
  }

  constructor(private constantService: ConstantService,
              private loadingService: LoadingService,
              private notificationService: NotificationService) { }

  ngOnInit(): void {
  }

  isLoading: boolean = false;
  isAccept = false;

  onModelChange(event) {
    if(event.length >= 5) {
      console.log(event);
      this.isLoading = true;
      axios.get(this.constantService.getRemoteServer() + '/api/site/check-name/' + this.siteInfo.siteName)
        .then(res => {
          if(res.data.code === 0) {
            this.isLoading = false;
            this.isAccept = true;
            this.setFocus();
          } else {
            this.isLoading = false;
            this.isAccept = false;
            this.setFocus();
          }
        }, err => {
          this.isLoading = false;
          this.isAccept = false;
          this.setFocus();
        });
    }
  }

  setFocus() {
    setTimeout(()=>{ // this will make the execution after the above boolean has changed
      this.siteNameElement.nativeElement.focus();
    },0);
  }

  addSite() {
    if(this.siteInfo.siteName.length < 5) {
      this.notificationService.changeMessage("error", "نام باید حداقل دارای پنج کاراکتر باشد")
    } else if(!this.siteInfo.title || this.siteInfo.title.length < 1) {
      this.notificationService.changeMessage("error", "عنوان باید مقدار داشته باشد")
    }

    this.loadingService.changeState(true);
    axios.post(this.constantService.getRemoteServer() + '/api/site/add',
      this.siteInfo,
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

export class SiteInfo {
  siteName: string = '';
  title: string = '';
  instagramLink: string = '';
  whatsappLink: string = '';
  facebookLink: string = '';
  telNumber: string = '';
  isActive: boolean = true;

  constructor() {
  }
}
