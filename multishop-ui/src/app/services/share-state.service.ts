import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {PianaStorageService} from "./piana-storage.service";
import {Router} from "@angular/router";
import {AuthenticationService} from "./authentication-service.service";
import {ProductCategoryService} from "./product-category.service";

@Injectable({
  providedIn: 'root'
})
export class ShareStateService {
  private urlMap = {
    product: '/tile/shop/product-editor',
    'product-register': '/tile/shop/product-register',
    'product-creator': '/tile/shop/product-creator',
    category: '/tile/shop/category-editor',
    'category-creator': '/tile/shop/category-creator'
  };

  private _editModeSubject: any;
  private _editModeObject = null;
  private _lastLink = null;
  private LAST_LINK: string = "last-link";
  private EDIT_MODE_STATE: string = "edit-mode-state";

  constructor(
    private router: Router,
    private categoryService: ProductCategoryService,
    private authService: AuthenticationService,
    private pianaStorageService: PianaStorageService) {
    router.events.subscribe((val) => {
      if(val['routerEvent']) {
        // console.log(val['routerEvent'].url)
        if (val['routerEvent'].url === '/tile/shop' ||
          val['routerEvent'].url === '/tile/shop/') {
          this.navigateToShop(null);
        }
      }
    });

    this._lastLink = pianaStorageService.getObject(this.LAST_LINK);
    if(!this._lastLink) {
      this._lastLink = {};
      pianaStorageService.putObject(this.LAST_LINK, this._lastLink);
    }
    this._editModeObject = pianaStorageService.getObject(this.EDIT_MODE_STATE);
    // console.log(this._editModeObject)
    if (!this._editModeObject || !this._editModeObject.hasOwnProperty('changeable')) {
      this._editModeObject = new EditModeObject(
        false, null, null, null);
      pianaStorageService.putObject(this.EDIT_MODE_STATE, this._editModeObject);
    }
    if (this._editModeObject) {
      if(this._editModeObject['editMode'] && !authService.isAdmin()) {
        this._editModeObject = new EditModeObject(
          false, null, null, null);
        this._editModeSubject = new BehaviorSubject<any>(this._editModeObject);
      } else {
        this._editModeSubject = new BehaviorSubject<any>(this._editModeObject);
      }
    } else {
      this._editModeObject = new EditModeObject(
        false, null, null, null);
      pianaStorageService.putObject(this.EDIT_MODE_STATE, this._editModeObject);
      this._editModeSubject = new BehaviorSubject<any>(this._editModeObject);
    }

    this.editModeSubject.subscribe(next => {
      // console.log(next)
      if (next.editMode)
        this.router.navigate([this.urlMap[next.urlKey]], { queryParams: { }});
      // this.router.navigate([this.urlMap[next.urlKey]], { queryParams: { returnUrl: next.returnUrl } })
    });
  }

  get editModeSubject(): Observable<EditModeObject> {
    return this._editModeSubject.asObservable();
  }

  get editModeObject(): EditModeObject {
    return this._editModeObject;
  }

  set editModeObject(editModeObject) {
    this._editModeObject = editModeObject;
    console.trace()
    console.log(editModeObject, this.pianaStorageService.getObject(this.EDIT_MODE_STATE))
    this.pianaStorageService.putObject(this.EDIT_MODE_STATE, this._editModeObject);
    this._editModeSubject.next(this._editModeObject);
  }

  setEditModeObject(editMode, changeable, urlKey, returnUrl) {
    console.log(returnUrl)
    this.editModeObject = new EditModeObject(editMode, changeable, urlKey, returnUrl);
  }

  clearEditModeObject() {
    this.editModeObject = new EditModeObject(false, null, null, null);
  }

  navigateReturn () {
    let returnUrl = this._editModeObject.returnUrl;
    console.log(returnUrl)
    this._editModeObject = new EditModeObject(
      false, null, null, null);
    // this.router.navigateByUrl('/tile/home');
    this.router.navigate([returnUrl]);
  }

  navigateToShop (category) {
    console.log(category)
    if(category) {
      console.log('category')
      this.pianaStorageService.setFieldValue(this.LAST_LINK, 'shop-category', category);
      this.categoryService.setAsSelectedCategory(category);
      this.router.navigate(['/tile/shop/products-gallery/' + category.routerLink]);
    } else {
      console.log('no category')
      category = this.pianaStorageService.getFieldValue(this.LAST_LINK, 'shop-category');
      if(category) {
        console.log('category1')
        this.categoryService.setAsSelectedCategory(category);
        this.router.navigate(['/tile/shop/products-gallery/' + category.routerLink]);
      } else {
        console.log('category2')
        this.categoryService.setAsSelectedCategory(null);
        this.pianaStorageService.setFieldValue(this.LAST_LINK, 'shop-category', {routerLink: 'default'});
        this.router.navigate(['/tile/shop/products-gallery/default']);
      }
    }
  }

  isCategoryActive (category) {
    // console.log(category)
    let cat = this.pianaStorageService.getFieldValue(this.LAST_LINK, 'shop-category');
    // console.log(cat)
    if (cat != null && this.router.url.startsWith('/tile/shop/products-gallery/')
      && cat['routerLink'] === category.routerLink)
      return true;
    return false;
  }
}

export class EditModeObject {
  editMode: boolean;
  changeable: any;
  urlKey: string;
  returnUrl: string;

  constructor(editMode, changeable, urlKey, returnUrl) {
   this.editMode = editMode;
   this.changeable = changeable;
   this.urlKey = urlKey;
   this.returnUrl = returnUrl;
  }
};
