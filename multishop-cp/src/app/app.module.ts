import {APP_INITIALIZER, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {ServiceWorkerModule} from '@angular/service-worker';
import {environment} from '../environments/environment';

import {RootComponent} from './views/root/root.component';
import {TopbarComponent} from './components/topbar/topbar.component';
import {FooterComponent} from './components/footer/footer.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HomeViewComponent} from './views/home-view/home-view.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {LoginComponent} from './views/login/login.component';
import {PageNotFoundComponent} from './views/page-not-found/page-not-found.component';
import {TileComponent} from './views/tile/tile.component';
import {InitializerService} from "./services/initializer.service";
import {AuthenticationService} from "./services/authentication-service.service";
import {FormMakerComponent} from './components/form-maker/form-maker.component';
import {NotificationComponent} from './components/notification/notification.component';
import {LoadingComponent} from './components/loading/loading.component';
import {GoogleLoginProvider, SocialAuthServiceConfig, SocialLoginModule} from 'angularx-social-login';
import {PasswordSettingComponent} from './views/password-setting/password-setting.component';
import {SiteSettingComponent} from './views/site-setting/site-setting.component';
import {MySitesComponent} from './views/my-sites/my-sites.component';
import {NewSiteComponent} from './views/new-site/new-site.component';
import {TextMaskModule} from "angular2-text-mask";
import {NumberOnlyDirective} from './directives/number-only.directive';
import {PictureBoxComponent} from "./components/picture-box/picture-box.component";

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
    NumberOnlyDirective,
    PictureBoxComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    SocialLoginModule,
    TextMaskModule,
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
    }
  ],
  bootstrap: [RootComponent]
})
export class AppModule { }
