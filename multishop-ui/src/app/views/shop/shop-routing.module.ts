import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShopComponent } from './shop.component';
import {ProductsGalleryComponent} from "./products-gallery/products-gallery.component";
import {ProductEditorComponent} from "./product-editor/product-editor.component";
import {EditorGuard} from "../../guards/editor.guard";
import {CategoryEditorComponent} from "./category-editor/category-editor.component";
import {AdminGuard} from "../../guards/admin.guard";
import {ProductRegisterComponent} from "./product-register/product-register.component";

const routes: Routes = [
  {
    path: '', component: ShopComponent, children: [
      // { path: '', redirectTo: '/tile/shop/products-gallery/default', pathMatch: 'full' },
      { path: 'product-editor', component: ProductEditorComponent, canActivate: [AdminGuard, EditorGuard] },
      { path: 'product-creator', component: ProductEditorComponent, canActivate: [AdminGuard, EditorGuard] },
      { path: 'product-register', component: ProductRegisterComponent, canActivate: [AdminGuard, EditorGuard] },
      { path: 'category-editor', component: CategoryEditorComponent, canActivate: [AdminGuard, EditorGuard] },
      { path: 'category-creator', component: CategoryEditorComponent, canActivate: [AdminGuard, EditorGuard] },
      { path: 'products-gallery/:routerLink', component: ProductsGalleryComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ShopRoutingModule { }
