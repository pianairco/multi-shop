import { Component, OnInit } from '@angular/core';
import axios from "axios";
import {AuthenticationService} from "../../services/authentication-service.service";
import {LoadingService} from "../../services/loading.service";
import {NotificationService} from "../../services/notification.service";

@Component({
  selector: 'app-password-setting',
  templateUrl: './password-setting.component.html',
  styleUrls: ['./password-setting.component.css']
})
export class PasswordSettingComponent implements OnInit {
  password = '';
  passwordRepeat = '';

  constructor(
    private notificationService: NotificationService,
    private loadingService: LoadingService,
    private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
  }

  register() {
    if(this.passwordRepeat !== this.password) {
      this.notificationService.changeMessage("error", "تکرار کلمه عبور با کلمه عبور مغایرت دارد")
      return;
    }
    this.loadingService.changeState(true);
    this.authenticationService.changePassword(this.password, this.passwordRepeat)
      .then(res => {
        this.loadingService.changeState(false);
        this.notificationService.changeMessage("success", "با موفقیت انجام شد");
        this.passwordRepeat = '';
        this.password = '';
        }, err => {
        this.loadingService.changeState(false);
        this.notificationService.changeMessage("error", "نیازمند تلاش مجدد می باشد")
      });
  }
}
