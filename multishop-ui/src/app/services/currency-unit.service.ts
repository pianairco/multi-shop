import {Injectable, Injector} from '@angular/core';
import {ProductCategory} from "../views/shop/category/category.component";
import {Router} from "@angular/router";
import {PianaStorageService} from "./piana-storage.service";
import {BehaviorSubject, Observable} from "rxjs";
import {RestClientService} from "./rest-client.service";

@Injectable({
  providedIn: 'root'
})
export class CurrencyUnitService {
  private _currencyUnitsSubject: any;
  private _currencyUnits: CurrencyUnitDto[] = [];
  private _currencyUnitsMap: {};

  constructor(private injector: Injector,
              private router: Router,
              private pianaStorageService: PianaStorageService) {
    this._currencyUnitsSubject = new BehaviorSubject<any>(this._currencyUnits);
  }

  get currencyUnitsSubject(): Observable<CurrencyUnitDto[]> {
    return this._currencyUnitsSubject.asObservable();
  }

  set currencyUnits(_currencyUnits) {
    this._currencyUnits = _currencyUnits;
    this._currencyUnitsMap = this._currencyUnits.reduce(function(map, obj) {
      map[obj['id']] = obj;
      return map;
    }, {});
    this._currencyUnitsSubject.next(this._currencyUnits);
  }

  byCurrencyId(id): CurrencyUnitDto {
    return this._currencyUnitsMap[id];
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

  get currencyUnits(): CurrencyUnitDto[] {
    return this._currencyUnits;
  }

  async renew() {
    let res = await this.injector.get(RestClientService).getAllCurrencyUnits()
    if(res.status == 200) {
      this.currencyUnits = res.data;
      console.log(res.data)
    }
  }
}

export class CurrencyUnitDto {
  id: number;
  englishName: string;
  persianName: string;
  description: string;

  constructor(id, englishName, persianName, description) {
    this.id = id;
    this.englishName = englishName;
    this.persianName = persianName;
    this.description = description;
  }
}
