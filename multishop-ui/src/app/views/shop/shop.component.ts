import { Component, OnInit } from '@angular/core';
import axios from 'axios';
import {ConstantService} from "../../services/constant.service";
import {LoadingService} from "../../services/loading.service";

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css']
})
export class ShopComponent implements OnInit {
  categorization: object[] = null;

  constructor(private constantService: ConstantService,
              private loadingService: LoadingService) { }

  async ngOnInit(): Promise<void> {
    try {
      this.loadingService.changeState(true);
      let res = await axios.get(this.constantService.getRemoteServer() + "/api/modules/shop/product-categorization");
      this.categorization = res.data;
      console.log(res.data)
      this.loadingService.changeState(false);
    } catch (err) {

    }
  }


}
