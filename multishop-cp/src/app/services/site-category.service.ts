import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {PianaStorageService} from "./piana-storage.service";
import {RestClientService} from "./rest-client.service";

@Injectable({
  providedIn: 'root'
})
export class SiteCategoryService {
  public _rootCategory: SiteCategory = null;
  public subject: any;

  constructor(
    private pianaStorageService: PianaStorageService,
    private restClientService: RestClientService) {
    this.subject = new BehaviorSubject<any>(this._rootCategory);
    this.rootCategory = this.pianaStorageService.getObject("rootCategory");
    console.log("cached", this._rootCategory)
    if(!this._rootCategory) {
      this.restClientService.getSiteCategories().then(res => {
        console.log(res.data.data)
        this.pianaStorageService.putObject("rootCategory", res.data.data);
      }, err => {
        console.log(err);
      })
    }
  }

  set rootCategory(_rootCategory) {
    this._rootCategory = _rootCategory;
    this.subject.next(this._rootCategory);
  }

  get rootCategorySubject(): Observable<SiteCategory> {
    return this.subject.asObservable();
  }
}

export class SiteCategory {
  id: string;
  title: string;
  image: string;
  children: SiteCategory[];
}
