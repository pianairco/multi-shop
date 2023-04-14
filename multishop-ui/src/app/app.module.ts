import {APP_INITIALIZER, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {ServiceWorkerModule} from '@angular/service-worker';
import {environment} from '../environments/environment';
import {RootComponent} from "./views/root/root.component";
import {HomeViewComponent} from "./views/home-view/home-view.component";
import {PageNotFoundComponent} from "./views/page-not-found/page-not-found.component";
import {TileComponent} from "./views/tile/tile.component";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
// import {GoogleLoginProvider, SocialAuthServiceConfig, SocialLoginModule} from "angularx-social-login";
import {AuthenticationService} from "./services/authentication-service.service";
import {InitializerService} from "./services/initializer.service";
import {SharedModule} from "./components/shared.module";


@NgModule({
  declarations: [
    RootComponent,
    HomeViewComponent,
    PageNotFoundComponent,
    TileComponent,
  ],
  exports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: environment.production }),
    // SocialLoginModule,
    SharedModule
  ],
  providers: [
    AuthenticationService,
    InitializerService,
    // {
    //   provide: 'SocialAuthServiceConfig',
    //   useValue: {
    //     autoLogin: false,
    //     providers: [
    //       {
    //         id: GoogleLoginProvider.PROVIDER_ID,
    //         provider: new GoogleLoginProvider(
    //           '290205995528-g4ieuas6ffi6vk19dftm312uqlfma2er.apps.googleusercontent.com'
    //         )
    //       }
    //     ]
    //   } as SocialAuthServiceConfig,
    // },
    {
      provide: APP_INITIALIZER,
      useFactory: (initializerService: InitializerService) => () => initializerService.load(),
      deps: [InitializerService],
      multi: true
    }],
  bootstrap: [RootComponent]
})
export class AppModule { }
