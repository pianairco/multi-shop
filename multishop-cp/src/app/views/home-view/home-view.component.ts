import {Component, OnInit} from '@angular/core';
import {RestClientService} from "../../services/rest-client.service";
import {SiteInfo} from "../new-site/new-site.component";
import {PianaStorageService} from "../../services/piana-storage.service";
import {SiteCategoryService} from "../../services/site-category.service";

interface Food {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-home-view',
  templateUrl: './home-view.component.html',
  styleUrls: ['./home-view.component.css']
})
export class HomeViewComponent implements OnInit {
  foods: Food[] = [
    {value: 'steak-0', viewValue: 'Steak'},
    {value: 'pizza-1', viewValue: 'Pizza'},
    {value: 'tacos-2', viewValue: 'Tacos'}
  ];

  allSites: SiteInfo[] = [];

  constructor(
    private pianaStorageService: PianaStorageService,
    private restClientService: RestClientService,
    private siteCategoryService: SiteCategoryService) {

  }

  ngOnInit(): void {
    this.restClientService.getAllSites().then(res => {
      if(res.status === 200 && res.data.code === 0) {
        this.allSites = res.data.data;
      }
    }, err => {

    });

    // this.pianaStorageService.removeObject("categories");

    console.log("home-view")
    console.log(this.siteCategoryService.rootCategorySubject)
    this.siteCategoryService.rootCategorySubject.subscribe(rootCategory => {
      console.log(rootCategory);
    })

    // axios.get('/api/message').then(res => {
    //   console.log(res);
    // }, err => {
    //   console.log(err);
    // })
  }

}
