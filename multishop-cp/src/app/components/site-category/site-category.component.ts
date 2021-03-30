import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {SiteCategory, SiteCategoryService} from "../../services/site-category.service";

@Component({
  selector: 'app-site-category',
  templateUrl: './site-category.component.html',
  styleUrls: ['./site-category.component.css']
})
export class SiteCategoryComponent implements OnInit {
  selected: SiteCategory;
  last: SiteCategory;
  rootCategory: SiteCategory;

  @Output() onSelect = new EventEmitter<string>();

  constructor(private siteCategoryService: SiteCategoryService) { }

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
    this.selected = siteCategory.children.length > 0 ? siteCategory : this.selected;
    this.last = siteCategory;
    this.onSelect.emit(this.last.number);
  }

  ngOnInit(): void {
    this.siteCategoryService.rootCategorySubject.subscribe(rootCategory => {
      console.log(rootCategory);
      this.rootCategory = rootCategory;
      this.selected = rootCategory;
      this.last = this.selected;
      if(this.selected) {
        console.log(this.selected)
        this.onSelect.emit(this.last.number);
      }
    })
  }

}
