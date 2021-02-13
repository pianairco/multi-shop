import {APP_INITIALIZER, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import {RootComponent} from "../../../multishop-cp/src/app/views/root/root.component";
import {TopbarComponent} from "../../../multishop-cp/src/app/components/topbar/topbar.component";
import {FooterComponent} from "../../../multishop-cp/src/app/components/footer/footer.component";
import {HomeViewComponent} from "../../../multishop-cp/src/app/views/home-view/home-view.component";
import {LoginComponent} from "../../../multishop-cp/src/app/views/login/login.component";
import {PageNotFoundComponent} from "../../../multishop-cp/src/app/views/page-not-found/page-not-found.component";
import {TileComponent} from "../../../multishop-cp/src/app/views/tile/tile.component";
import {FormMakerComponent} from "../../../multishop-cp/src/app/components/form-maker/form-maker.component";
import {NotificationComponent} from "../../../multishop-cp/src/app/components/notification/notification.component";
import {LoadingComponent} from "../../../multishop-cp/src/app/components/loading/loading.component";
import {PasswordSettingComponent} from "../../../multishop-cp/src/app/views/password-setting/password-setting.component";
import {SiteSettingComponent} from "../../../multishop-cp/src/app/views/site-setting/site-setting.component";
import {MySitesComponent} from "../../../multishop-cp/src/app/views/my-sites/my-sites.component";
import {NewSiteComponent} from "../../../multishop-cp/src/app/views/new-site/new-site.component";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {GoogleLoginProvider, SocialAuthServiceConfig, SocialLoginModule} from "angularx-social-login";
import {AuthenticationService} from "../../../multishop-cp/src/app/services/authentication-service.service";
import {InitializerService} from "../../../multishop-cp/src/app/services/initializer.service";
import { PictureUploadComponent } from './modules/picture-upload/picture-upload.component';
import { PictureBoxComponent } from './modules/picture-box/picture-box.component';
import { ColumnPictureUploadComponent } from './modules/column-picture-upload/column-picture-upload.component';
import { PictorialShopItemComponent } from './modules/shop/pictorial-shop-item/pictorial-shop-item.component';
import { SessionPictureUploadComponent } from './modules/sample-session/session-picture-upload/session-picture-upload.component';
import { SessionPictureSelectComponent } from './modules/sample-session/session-picture-select/session-picture-select.component';
import { SampleSessionPictureManagerComponent } from './modules/sample-session/sample-session-picture-manager/sample-session-picture-manager.component';
import { PictorialMenuItemComponent } from './modules/sample/pictorial-menu-item/pictorial-menu-item.component';
import { PictorialMenuItemCreatorComponent } from './modules/sample/pictorial-menu-item-creator/pictorial-menu-item-creator.component';
import { PictorialSampleItemComponent } from './modules/sample/pictorial-sample-item/pictorial-sample-item.component';
import { PictorialSampleItemCreatorComponent } from './modules/sample/pictorial-sample-item-creator/pictorial-sample-item-creator.component';
import { ShopGroupComponent } from './modules/group/shop-group/shop-group.component';

@NgModule({
  declarations: [
    RootComponent,
    TopbarComponent,
    FooterComponent,
    HomeViewComponent,
    LoginComponent,
    PageNotFoundComponent,
    TileComponent,
    FormMakerComponent,
    NotificationComponent,
    LoadingComponent,
    PasswordSettingComponent,
    SiteSettingComponent,
    MySitesComponent,
    NewSiteComponent,
    PictureUploadComponent,
    PictureBoxComponent,
    ColumnPictureUploadComponent,
    PictorialShopItemComponent,
    SessionPictureUploadComponent,
    SessionPictureSelectComponent,
    SampleSessionPictureManagerComponent,
    PictorialMenuItemComponent,
    PictorialMenuItemCreatorComponent,
    PictorialSampleItemComponent,
    PictorialSampleItemCreatorComponent,
    ShopGroupComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    SocialLoginModule,
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: environment.production })
  ],
  providers: [
    AuthenticationService,
    InitializerService,
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              '290205995528-g4ieuas6ffi6vk19dftm312uqlfma2er.apps.googleusercontent.com'
            )
          }
        ]
      } as SocialAuthServiceConfig,
    },
    {
      provide: APP_INITIALIZER,
      useFactory: (initializerService: InitializerService) => () => initializerService.load(),
      deps: [InitializerService],
      multi: true
    }],
  bootstrap: [RootComponent]
})
export class AppModule { }
