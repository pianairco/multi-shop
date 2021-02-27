import {Injectable, Injector} from '@angular/core';
import {ConstantService} from "./constant.service";
import axios from "axios";
import {ProductCategory} from "../views/shop/category/category.component";
import {ProductCategoryService} from "./product-category.service";

@Injectable({
  providedIn: 'root'
})
export class AjaxCallService {
  ajaxUrlMap = {
    'product': 'api/modules/shop/product',
    'category': 'api/modules/shop/category'
  }

  remoteServer: string = "";

  constructor(private constantService: ConstantService,
              private injector: Injector) { }

  read(url) {
    return axios.get(this.constantService.getRemoteServer() + "/" + url,
      {headers: {}});
  }

  save(url, obj) {
    return axios.post(this.constantService.getRemoteServer() + "/" + url,
      obj,
      {headers: {"content-type": "application/json"}});
  }

  update(url, obj) {
    return axios.put(this.constantService.getRemoteServer() + "/" + url,
      obj,
      {headers: {"content-type": "application/json"}});
  }

  categoryList() {
    return this.read(this.ajaxUrlMap.category + '/list');
  }

  categoryPersist(category) {
    if (category.id === 0) {
      return this.save(this.ajaxUrlMap.category, category);
    } else{
      return this.update(this.ajaxUrlMap.category, category);
    }
  }

  productList(routerLink) {
    return this.read(this.ajaxUrlMap.product + '/list/' + this.injector.get(ProductCategoryService).getCategoryId(routerLink));
  }

  productPersist(product) {
    if (product.id === 0) {
      product.categoryId = this.injector.get(ProductCategoryService).getSelectedCategory().id;
      return this.save(this.ajaxUrlMap.product, product);
    } else{
      return this.update(this.ajaxUrlMap.product, product);
    }
  }
}
