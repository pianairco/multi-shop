import {Component, OnInit} from '@angular/core';
import {RestClientService} from "../../services/rest-client.service";
import {SiteInfo} from "../new-site/new-site.component";

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

  constructor(private restClientService: RestClientService) {

  }

  ngOnInit(): void {
    this.restClientService.getAllSites().then(res => {
      if(res.status === 200 && res.data.code === 0) {
        this.allSites = res.data.data;
      }
    }, err => {

    });

    // axios.get('/api/message').then(res => {
    //   console.log(res);
    // }, err => {
    //   console.log(err);
    // })
  }

}
