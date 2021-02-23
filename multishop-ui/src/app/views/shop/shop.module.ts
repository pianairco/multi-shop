import {NgModule} from '@angular/core';

import {ShopRoutingModule} from './shop-routing.module';
import {ShopComponent} from './shop.component';
import {ProductsGallaryComponent} from "./products-gallary/products-gallary.component";
import {ProductCategoryComponent} from "./product-category/product-category.component";
import {ProductComponent} from "./product/product.component";
import {ProductEditorComponent} from "./product-editor/product-editor.component";
import {CommonModule} from "@angular/common";
import {SharedModule} from "../../components/shared.module";
import {FormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    ShopComponent,
    ProductsGallaryComponent,
    ProductCategoryComponent,
    ProductComponent,
    ProductEditorComponent
  ],
  exports:[
    ShopComponent,
    ProductsGallaryComponent,
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
