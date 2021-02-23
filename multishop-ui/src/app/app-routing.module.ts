import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AuthGuard} from "./guards/auth.guard";
import {TileComponent} from "./views/tile/tile.component";
import {HomeViewComponent} from "./views/home-view/home-view.component";
import {FormMakerComponent} from "./components/form-maker/form-maker.component";
import {PageNotFoundComponent} from "./views/page-not-found/page-not-found.component";
import {ShopComponent} from "./views/shop/shop.component";
import {ProductsGallaryComponent} from "./views/shop/products-gallary/products-gallary.component";
import {ProductComponent} from "./views/shop/product/product.component";

const routes: Routes = [
  {
    path: '', canActivate:[AuthGuard], children: [
      { path: '', children: [
          { path: '', redirectTo: '/tile/home', pathMatch: 'full' },
          { path: 'tile', component: TileComponent, children:[
              { path: '', redirectTo: '/home', pathMatch: 'full' },
              { path: 'home', component: HomeViewComponent },
              { path: 'shop',
                loadChildren: () => import('./views/shop/shop.module').then(m => m.ShopModule)
              },
              { path: 'add-user/:groupName/:formName', component: FormMakerComponent }
            ] },
        ]
      }
    ]
  },
  { path: 'shop', loadChildren: () => import('./views/shop/shop.module').then(m => m.ShopModule) },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
