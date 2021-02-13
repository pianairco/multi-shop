import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AuthGuard} from "../../../multishop-cp/src/app/guards/auth.guard";
import {TileComponent} from "../../../multishop-cp/src/app/views/tile/tile.component";
import {HomeViewComponent} from "../../../multishop-cp/src/app/views/home-view/home-view.component";
import {FormMakerComponent} from "../../../multishop-cp/src/app/components/form-maker/form-maker.component";
import {PasswordSettingComponent} from "../../../multishop-cp/src/app/views/password-setting/password-setting.component";
import {SiteSettingComponent} from "../../../multishop-cp/src/app/views/site-setting/site-setting.component";
import {MySitesComponent} from "../../../multishop-cp/src/app/views/my-sites/my-sites.component";
import {NewSiteComponent} from "../../../multishop-cp/src/app/views/new-site/new-site.component";
import {LoginComponent} from "../../../multishop-cp/src/app/views/login/login.component";
import {PageNotFoundComponent} from "../../../multishop-cp/src/app/views/page-not-found/page-not-found.component";

const routes: Routes = [
  {
    path: '', canActivate:[AuthGuard], children: [
      { path: '', children: [
          { path: '', redirectTo: '/tile/home', pathMatch: 'full' },
          { path: 'tile', component: TileComponent, children:[
              { path: '', redirectTo: '/home', pathMatch: 'full' },
              { path: 'home', component: HomeViewComponent },
              { path: 'add-user/:groupName/:formName', component: FormMakerComponent },
              { path: 'password-setting', component: PasswordSettingComponent },
              { path: 'site-setting', component: SiteSettingComponent, children:[
                  { path: '', redirectTo: 'my-sites', pathMatch: 'full' },
                  { path: 'my-sites', component: MySitesComponent },
                  { path: 'new-site', component: NewSiteComponent }
                ]
              }
            ] },
        ]
      },
      { path: 'login', component: LoginComponent, canActivate:[AuthGuard] },
      { path: 'logout', component: LoginComponent, canActivate:[AuthGuard] },
    ]
  },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
