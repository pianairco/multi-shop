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
import { HeaderComponent } from './components/header/header.component';
import { ShopComponent } from './views/shop/shop.component';
import { ProductsGallaryComponent } from './views/products-gallary/products-gallary.component';
import { ProductCategorizationComponent } from './views/shop/product-categorization/product-categorization.component';
import { ProductCategoryComponent } from './views/shop/product-category/product-category.component';
import { ProductCategoryCreatorComponent } from './views/shop/product-category-creator/product-category-creator.component';

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
    ShopGroupComponent,
    HeaderComponent,
    ShopComponent,
    ProductsGallaryComponent,
    ProductCategorizationComponent,
    ProductCategoryComponent,
    ProductCategoryCreatorComponent
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
