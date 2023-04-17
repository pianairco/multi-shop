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
import {FormsModule} from "@angular/forms";
import {TipComponent} from "./tip/tip.component";
import {InfoBoxComponent} from "./info-box/info-box.component";
import {SiteCategoryComponent} from "./site-category/site-category.component";


@NgModule({
  declarations: [
    TopbarComponent,
    FooterComponent,
    HeaderComponent,
    TipComponent,
    InfoBoxComponent,
    FormMakerComponent,
    NotificationComponent,
    LoadingComponent,
    PictureBoxComponent,
    SiteCategoryComponent
  ],
  exports: [
    TopbarComponent,
    FooterComponent,
    HeaderComponent,
    TipComponent,
    InfoBoxComponent,
    FormMakerComponent,
    NotificationComponent,
    LoadingComponent,
    PictureBoxComponent,
    SiteCategoryComponent
  ],
    imports: [
        CommonModule,
        RouterModule,
        FormsModule
    ]
})
export class SharedModule { }
