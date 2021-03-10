import { Component, OnInit } from '@angular/core';
import {SiteCategory, SiteCategoryService} from "../../services/site-category.service";

@Component({
  selector: 'app-site-category',
  templateUrl: './site-category.component.html',
  styleUrls: ['./site-category.component.css']
})
export class SiteCategoryComponent implements OnInit {
  selected: SiteCategory;
  rootCategory: SiteCategory;

  constructor(private siteCategoryService: SiteCategoryService) { }

  ngOnInit(): void {
    this.siteCategoryService.rootCategorySubject.subscribe(rootCategory => {
      this.rootCategory = rootCategory;
      this.selected = rootCategory;
    })
  }

}
