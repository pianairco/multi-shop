import {NgModule} from '@angular/core';

import {ShopRoutingModule} from './shop-routing.module';
import {ShopComponent} from './shop.component';
import {ProductsGalleryComponent} from "./products-gallery/products-gallery.component";
import {ProductCategoryComponent} from "./product-category/product-category.component";
import {ProductComponent} from "./product/product.component";
import {ProductEditorComponent} from "./product-editor/product-editor.component";
import {CommonModule} from "@angular/common";
import {SharedModule} from "../../components/shared.module";
import {FormsModule} from "@angular/forms";
import {ProductEditorModalComponent} from "./product-editor-modal/product-editor-modal.component";


@NgModule({
  declarations: [
    ShopComponent,
    ProductsGalleryComponent,
    ProductCategoryComponent,
    ProductComponent,
    ProductEditorComponent,
    ProductEditorModalComponent
  ],
  exports:[
    ShopComponent,
    ProductsGalleryComponent,
    ProductCategoryComponent,
    ProductComponent,
    ProductEditorComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    SharedModule,
    ShopRoutingModule
  ]
})
export class ShopModule { }
