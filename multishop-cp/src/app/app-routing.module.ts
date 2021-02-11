import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from "./guards/auth.guard";
import {TileComponent} from "./views/tile/tile.component";
import {HomeViewComponent} from "./views/home-view/home-view.component";
import {FormMakerComponent} from "./components/form-maker/form-maker.component";
import {LoginComponent} from "./views/login/login.component";
import {PageNotFoundComponent} from "./views/page-not-found/page-not-found.component";

const routes: Routes = [
  {
    path: '', canActivate:[AuthGuard], children: [
      { path: '', children: [
          { path: '', redirectTo: '/tile/home', pathMatch: 'full' },
          { path: 'tile', component: TileComponent, children:[
              { path: '', redirectTo: '/home', pathMatch: 'full' },
              { path: 'home', component: HomeViewComponent },
              { path: 'add-user/:groupName/:formName', component: FormMakerComponent }
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
