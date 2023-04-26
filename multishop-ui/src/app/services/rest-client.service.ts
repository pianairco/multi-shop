import {Injectable, Injector} from '@angular/core';
import {ConstantService} from "./constant.service";
import axios from "axios";
import {ProductCategory} from "../views/shop/category/category.component";
import {ProductCategoryService} from "./product-category.service";
import {AjaxCallService} from "./ajax-call.service";
import {log} from "util";

@Injectable({
  providedIn: 'root'
})
export class RestClientService {
  ajaxUrlMap = {
    'root': 'api/modules/',
    'shop': 'api/modules/shop/',
    'measurement-unit': 'api/modules/shop/measurement-unit',
    'currency-unit': 'api/modules/shop/currency-unit',
    'product': 'api/modules/shop/product',
    'store-pool': 'api/modules/shop/store-pool',
    'category': 'api/modules/shop/category',
    'siteCategory': 'api/modules/site/category'
  }

  remoteServer: string = "";

  constructor(private constantService: ConstantService,
              private ajaxCallService: AjaxCallService,
              private categoryService: ProductCategoryService) { }

  categoryList() {
    return this.ajaxCallService.read(this.ajaxUrlMap.category + '/list');
  }

  categoryPersist(category) {
    if (category.id === 0) {
      return this.ajaxCallService.save(this.ajaxUrlMap.category, category);
    } else{
      return this.ajaxCallService.update(this.ajaxUrlMap.category, category);
    }
  }

  categoryDelete(category) {
    if (category.id > 0) {
      return this.ajaxCallService.delete(this.ajaxUrlMap.category, category.id);
    }
  }

  productList(routerLink) {
    console.log(routerLink, this.categoryService.getCategoryId(routerLink))
    return this.ajaxCallService.read(this.ajaxUrlMap.product + '/list/' +
      this.categoryService.getCategoryId(routerLink));
    // return this.ajaxCallService.read(this.ajaxUrlMap.product + '/list/' + routerLink);
  }

  storePoolList(routerLink) {
    // console.log(this.categoryService.getCategoryId(routerLink))
    return this.ajaxCallService.read(this.ajaxUrlMap["store-pool"] + '/list/' +
      this.categoryService.getCategoryId(routerLink));
    // return this.ajaxCallService.read(this.ajaxUrlMap.product + '/list/' + routerLink);
  }

  productPersist(product) {
    if (product.id === 0) {
      product.categoryId = this.categoryService.getSelectedCategory().id;
      return this.ajaxCallService.save(this.ajaxUrlMap.product, product);
    } else{
      return this.ajaxCallService.update(this.ajaxUrlMap.product, product);
    }
  }

  productDelete(product) {
    if (product.id > 0) {
      return this.ajaxCallService.delete(this.ajaxUrlMap.product, product.id);
    }
  }

  getSiteCategories() {
    return this.ajaxCallService.read(this.ajaxUrlMap.siteCategory + '/root');
  }

  getAllProductsByCategory(categoryId) {
    return this.ajaxCallService.read(this.ajaxUrlMap.product + '/list/' + categoryId);
  }

  getAllProductsByPianaCategory(categoryId) {
    return this.ajaxCallService.read(this.ajaxUrlMap.product + '/list/by-piana-categories/' + categoryId);
  }

  getAllMeasurementUnitsForSelect() {
    return this.ajaxCallService.read(this.ajaxUrlMap["measurement-unit"] + '/list-for-select');
  }

  getAllMeasurementUnits() {
    return this.ajaxCallService.read(this.ajaxUrlMap["measurement-unit"] + '/list');
  }

  getAllCurrencyUnitsForSelect() {
    return this.ajaxCallService.read(this.ajaxUrlMap["currency-unit"] + '/list-for-select');
  }

  getAllCurrencyUnits() {
    return this.ajaxCallService.read(this.ajaxUrlMap["currency-unit"] + '/list');
  }

}
