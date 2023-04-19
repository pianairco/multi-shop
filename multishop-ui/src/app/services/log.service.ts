import {Injectable, Injector} from '@angular/core';
import {ConstantService} from "./constant.service";
import axios from "axios";
import {ProductCategory} from "../views/shop/category/category.component";
import {ProductCategoryService} from "./product-category.service";

@Injectable({
  providedIn: 'root'
})
export class LogService {
  isDebugMode: boolean = true;

  constructor(private constantService: ConstantService,
              private injector: Injector) { }

  public log(...messages) {
    if (this.isDebugMode)
      console.log(messages);
  }
}
