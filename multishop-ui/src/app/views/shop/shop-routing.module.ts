import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShopComponent } from './shop.component';
import {ProductsGallaryComponent} from "./products-gallary/products-gallary.component";

const routes: Routes = [
  {
    path: '', component: ShopComponent, children: [
      { path: 'products-gallery/:routerLink', component: ProductsGallaryComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ShopRoutingModule { }
