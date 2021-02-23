import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShopComponent } from './shop.component';
import {ProductsGalleryComponent} from "./products-gallery/products-gallery.component";
import {ProductEditorComponent} from "./product-editor/product-editor.component";
import {EditorGuard} from "../../guards/editor.guard";

const routes: Routes = [
  {
    path: '', component: ShopComponent, children: [
      { path: 'product-editor', component: ProductEditorComponent, canActivate: [EditorGuard] },
      { path: 'products-gallery/:routerLink', component: ProductsGalleryComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ShopRoutingModule { }
