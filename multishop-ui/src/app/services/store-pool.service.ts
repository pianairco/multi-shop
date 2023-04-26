import {Injectable, Injector} from '@angular/core';
import {ProductCategory} from "../views/shop/category/category.component";
import {Router} from "@angular/router";
import {PianaStorageService} from "./piana-storage.service";
import {BehaviorSubject, Observable} from "rxjs";
import {RestClientService} from "./rest-client.service";

@Injectable({
  providedIn: 'root'
})
export class StorePoolService {
  private _storePoolSubject: any;
  private _storePoolProducts: StorePoolProductDto[] = [];

  constructor(private injector: Injector,
              private router: Router,
              private pianaStorageService: PianaStorageService) {
    this._storePoolSubject = new BehaviorSubject<any>(this._storePoolProducts);
  }

  get storePoolProductsSubject(): Observable<StorePoolProductDto[]> {
    return this._storePoolSubject.asObservable();
  }

  set storePoolProducts(_storePoolProducts) {
    this._storePoolProducts = _storePoolProducts;
    this._storePoolSubject.next(this._storePoolProducts);
  }
  /*
  addCategory(_category) {
    let index = -1;
    for(let i = 0; i < this._categories.length; i++) {
      if(this._categories[i].id === _category.id) {
        index = i;
        break;
      }
    }
    if(index > -1) {
      this._categories[index] = _category;
    } else {
      this._categories.push(_category);
    }
    this._categoriesSubject.next(this._categories);
  }

  removeCategory(_category) {
    let index = -1;
    for (let i = 0; i < this._categories.length; i++) {
      if(this._categories[i].id === _category.id) {
        index = i;
        break;
      }
    }
    if(index > -1) {
      this._categories.splice(index, 1);
      this._categoriesSubject.next(this._categories);
    }
  }*/

  get storePoolProducts(): StorePoolProductDto[] {
    return this._storePoolProducts;
  }

  async renew(routerLink) {
    let res = await this.injector.get(RestClientService).storePoolList(routerLink)
    if(res.status == 200) {
      this.storePoolProducts = res.data['data'];
      console.log(res.data)
    }
  }
}

export class StorePoolProductDto {
  id: number;
  productId: number;
  siteId: number;
  pianaCategoryId: number;
  categoryId: number;
  measurementUnitId: number;
  measurementUnitName: string;
  measurementUnitRatio: number;
  currencyUnitId: number;
  title: string;
  description: string;
  image: string;
  amount: number;
  price: number;
  percentage: number;

  constructor(id,
  productId,
  siteId,
  pianaCategoryId,
  categoryId,
  measurementUnitId,
  measurementUnitName,
  measurementUnitRatio,
  currencyUnitId,
  title,
  description,
  image,
  amount,
  price,
  percentage) {
    this.id = id;
    this.productId = productId;
    this.siteId = siteId;
    this.pianaCategoryId = pianaCategoryId;
    this.categoryId = categoryId;
    this.measurementUnitId = measurementUnitId;
    this.measurementUnitName = measurementUnitName;
    this.measurementUnitRatio = measurementUnitRatio;
    this.currencyUnitId = currencyUnitId;
    this.title = title;
    this.description = description;
    this.image = image;
    this.amount = amount;
    this.price = price;
    this.percentage = percentage;
  }
}
