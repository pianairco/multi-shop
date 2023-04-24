import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {SiteCategory, SiteCategoryService} from "../../services/site-category.service";
import {LogService} from "../../services/log.service";

@Component({
  selector: 'app-site-category',
  templateUrl: './site-category.component.html',
  styleUrls: ['./site-category.component.css'],
  providers: [
    LogService, {provide: 'fromComponent', useValue: 'SiteCategoryComponent'}
  ]
})
export class SiteCategoryComponent implements OnInit {
  selected: SiteCategory;
  last: SiteCategory;
  rootCategory: SiteCategory;

  @Output() onSelect = new EventEmitter<string>();

  constructor(private logService:LogService,
    private siteCategoryService: SiteCategoryService) { }

  goBack(siteCategory: SiteCategory) {
    if(this.last != this.selected)
      this.last = this.selected;
    else {
      this.selected = this.selected.parent ?  this.selected.parent : this.selected;
      this.last = this.selected;
    }
    this.onSelect.emit(this.last.number);
  }

  goNext(siteCategory: SiteCategory) {
    this.logService.log(34, siteCategory)
    this.selected = siteCategory.children.length > 0 ? siteCategory : this.selected;
    this.last = siteCategory;
    this.onSelect.emit(this.last.number);
  }

  ngOnInit(): void {
    this.siteCategoryService.rootCategorySubject.subscribe(rootCategory => {
      this.logService.log(43, rootCategory);
      this.rootCategory = rootCategory;
      this.selected = rootCategory;
      this.last = this.selected;
      if(this.selected) {
        this.logService.log(48, this.selected)
        this.onSelect.emit(this.last.number);
      }
    })
  }

}
