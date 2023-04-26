import {Injectable, Injector} from '@angular/core';
import {ProductCategory} from "../views/shop/category/category.component";
import {Router} from "@angular/router";
import {PianaStorageService} from "./piana-storage.service";
import {BehaviorSubject, Observable} from "rxjs";
import {RestClientService} from "./rest-client.service";
import {CurrencyUnitDto} from "./currency-unit.service";

@Injectable({
  providedIn: 'root'
})
export class MeasurementUnitService {
  private _measurementUnitsSubject: any;
  private _measurementUnits: MeasurementUnitDto[] = [];
  private _measurementUnitsMap: {};
  constructor(private injector: Injector,
              private router: Router,
              private pianaStorageService: PianaStorageService) {
    this._measurementUnitsSubject = new BehaviorSubject<any>(this._measurementUnits);
  }

  get measurementUnitsSubject(): Observable<MeasurementUnitDto[]> {
    return this._measurementUnitsSubject.asObservable();
  }

  set measurementUnits(_measurementUnits) {
    this._measurementUnits = _measurementUnits;
    this._measurementUnitsMap = this._measurementUnits.reduce(function(map, obj) {
      map[obj['id']] = obj;
      return map;
    }, {});
    this._measurementUnitsSubject.next(this._measurementUnits);
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

  get measurementUnits(): MeasurementUnitDto[] {
    return this._measurementUnits;
  }

  byMeasurementId(id): CurrencyUnitDto {
    return this._measurementUnitsMap[id];
  }

  async renew() {
    let res = await this.injector.get(RestClientService).getAllMeasurementUnitsForSelect()
    if(res.status == 200) {
      this.measurementUnits = res.data;
      console.log(res.data)
    }
  }
}

export class MeasurementUnitDto {
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
