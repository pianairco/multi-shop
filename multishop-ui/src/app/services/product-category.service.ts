import {Injectable, Injector} from '@angular/core';
import {ProductCategory} from "../views/shop/category/category.component";
import {Router} from "@angular/router";
import {PianaStorageService} from "./piana-storage.service";
import {BehaviorSubject, Observable} from "rxjs";
import {RestClientService} from "./rest-client.service";

@Injectable({
  providedIn: 'root'
})
export class ProductCategoryService {
  private _categoriesSubject: any;
  private _categories: ProductCategory[] = [];
  private _selectedSubject: any;
  private _selected: ProductCategory = null;

  constructor(private injector: Injector,
              private router: Router,
              private pianaStorageService: PianaStorageService) {
    this._categoriesSubject = new BehaviorSubject<any>(this._categories);
    this._selectedSubject = new BehaviorSubject<any>(this._selected);
  }

  get categoriesSubject(): Observable<ProductCategory[]> {
    return this._categoriesSubject.asObservable();
  }

  set categories(_categories) {
    this._categories = _categories;
    this._categoriesSubject.next(this._categories);
  }

  set selected(_selected) {
    this._selected = _selected;
    this._selectedSubject.next(this._selected);
  }

  get selectedSubject(): Observable<ProductCategory> {
    return this._selectedSubject.asObservable();
  }

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
  }

  get categories(): ProductCategory[] {
    return this._categories;
  }

  async renew() {
    let res = await this.injector.get(RestClientService).categoryList()
    if(res.status == 200) {
      this.categories = res.data;
      console.log(res.data)
    }
  }

  setAsSelectedCategory(category) {
    // console.log("bbbbbb", category)
    this.pianaStorageService.putObject("selected-category", { "selected": category });
    this.selected = category;
  }

  private checkCategory(routerLink) {
    if(this._selected && this._selected.routerLink === routerLink) {
      return true;
    } else {
      let selectedCategory = this.pianaStorageService.getObject("selected-category");
      if(selectedCategory && selectedCategory.hasOwnProperty('selected')) {
        let selected: ProductCategory = selectedCategory['selected'];
        if(selected.routerLink === routerLink) {
          this.selected = selected;
          return true;
        }
      }
    }
    this.router.navigate(['/tile/shop/products-gallery']);
    // else if(this._selected && this._selected.routerLink != routerLink) {
    //   this.router.navigate(['/tile/shop/products-gallery'])
    // } else if(!this._selected && this._categories && this._categories.length > 0) {
    //   this._categories.forEach(c => {
    //     if(c.routerLink === routerLink) {
    //       this._selected = c;
    //       return;
    //     }
    //   });
    // }
  }

  getSelectedCategory(): ProductCategory {
    return this._selected;
  }

  getCategoryId(routerLink) {
    this.checkCategory(routerLink);
    for(let category of this._categories) {
      if (category.routerLink == routerLink) {
        // console.log(routerLink, category, category['pianaCategoryId'], category.id)
        return category['id'];
      }
    }
    return 0;
  }
}
