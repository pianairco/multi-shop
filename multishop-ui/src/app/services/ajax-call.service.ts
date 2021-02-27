import {Injectable, Injector} from '@angular/core';
import {ConstantService} from "./constant.service";
import axios from "axios";
import {ProductCategory} from "../views/shop/category/category.component";
import {ProductCategoryService} from "./product-category.service";

@Injectable({
  providedIn: 'root'
})
export class AjaxCallService {
  remoteServer: string = "";

  constructor(private constantService: ConstantService,
              private injector: Injector) { }

  read(url) {
    return axios.get(this.constantService.getRemoteServer() + "/" + url,
      { headers: {} });
  }

  save(url, obj) {
    return axios.post(this.constantService.getRemoteServer() + "/" + url,
      obj,
      { headers: {"content-type": "application/json"} });
  }

  update(url, obj) {
    return axios.put(this.constantService.getRemoteServer() + "/" + url,
      obj,
      { headers: {"content-type": "application/json"} });
  }

  delete(url, id) {
    return axios.delete(this.constantService.getRemoteServer() + "/" + url + "/" + id,
      { headers: {"content-type": "application/json"} });
  }
}
