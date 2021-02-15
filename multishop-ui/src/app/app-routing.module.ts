import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AuthGuard} from "./guards/auth.guard";
import {TileComponent} from "./views/tile/tile.component";
import {HomeViewComponent} from "./views/home-view/home-view.component";
import {FormMakerComponent} from "./components/form-maker/form-maker.component";
import {PasswordSettingComponent} from "./views/password-setting/password-setting.component";
import {SiteSettingComponent} from "./views/site-setting/site-setting.component";
import {MySitesComponent} from "./views/my-sites/my-sites.component";
import {NewSiteComponent} from "./views/new-site/new-site.component";
import {LoginComponent} from "./views/login/login.component";
import {PageNotFoundComponent} from "./views/page-not-found/page-not-found.component";
import {ChildViewComponent} from "./views/child-view/child-view.component";

const routes: Routes = [
  {
    path: '', canActivate:[AuthGuard], children: [
      { path: '', children: [
          { path: '', redirectTo: '/tile/home-view', pathMatch: 'full' },
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
      { path: 'home-view', component: HomeViewComponent, canActivate:[AuthGuard] },
      { path: 'child-view', component: ChildViewComponent, canActivate:[AuthGuard] },
      { path: 'login', component: LoginComponent, canActivate:[AuthGuard] },
      { path: 'logout', component: LoginComponent, canActivate:[AuthGuard] },
    ]
  },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
