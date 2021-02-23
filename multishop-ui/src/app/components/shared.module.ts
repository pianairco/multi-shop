import {NgModule} from '@angular/core';

import {TopbarComponent} from "./topbar/topbar.component";
import {FooterComponent} from "./footer/footer.component";
import {FormMakerComponent} from "./form-maker/form-maker.component";
import {NotificationComponent} from "./notification/notification.component";
import {LoadingComponent} from "./loading/loading.component";
// import {GoogleLoginProvider, SocialAuthServiceConfig, SocialLoginModule} from "angularx-social-login";
import {PictureBoxComponent} from './picture-box/picture-box.component';
import {HeaderComponent} from './header/header.component';
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";


@NgModule({
  declarations: [
    TopbarComponent,
    FooterComponent,
    HeaderComponent,
    FormMakerComponent,
    NotificationComponent,
    LoadingComponent,
    PictureBoxComponent
  ],
  exports: [
    TopbarComponent,
    FooterComponent,
    HeaderComponent,
    FormMakerComponent,
    NotificationComponent,
    LoadingComponent,
    PictureBoxComponent
  ],
  imports: [
    CommonModule,
    RouterModule
  ]
})
export class SharedModule { }
