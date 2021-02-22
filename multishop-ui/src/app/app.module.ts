import {APP_INITIALIZER, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import {RootComponent} from "./views/root/root.component";
import {TopbarComponent} from "./components/topbar/topbar.component";
import {FooterComponent} from "./components/footer/footer.component";
import {HomeViewComponent} from "./views/home-view/home-view.component";
import {PageNotFoundComponent} from "./views/page-not-found/page-not-found.component";
import {TileComponent} from "./views/tile/tile.component";
import {FormMakerComponent} from "./components/form-maker/form-maker.component";
import {NotificationComponent} from "./components/notification/notification.component";
import {LoadingComponent} from "./components/loading/loading.component";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
// import {GoogleLoginProvider, SocialAuthServiceConfig, SocialLoginModule} from "angularx-social-login";
import {AuthenticationService} from "./services/authentication-service.service";
import {InitializerService} from "./services/initializer.service";
import { PictureBoxComponent } from './components/picture-box/picture-box.component';
import { HeaderComponent } from './components/header/header.component';
import { ShopComponent } from './views/shop/shop.component';
import { ProductsGallaryComponent } from './views/products-gallary/products-gallary.component';
import { ProductCategoryComponent } from './views/shop/product-category/product-category.component';
import { ProductComponent } from './views/shop/product/product.component';
import {ProductEditorComponent} from "./views/shop/product-editor/product-editor.component";

@NgModule({
  declarations: [
    RootComponent,
    TopbarComponent,
    FooterComponent,
    HomeViewComponent,
    PageNotFoundComponent,
    TileComponent,
    FormMakerComponent,
    NotificationComponent,
    LoadingComponent,
    PictureBoxComponent,
    HeaderComponent,
    ShopComponent,
    ProductsGallaryComponent,
    ProductCategoryComponent,
    ProductComponent,
    ProductEditorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    // SocialLoginModule,
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: environment.production })
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
