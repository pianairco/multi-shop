import {Component, OnInit, ViewChild} from '@angular/core';
import axios from 'axios';
import {ConstantService} from "../../services/constant.service";
import {LoadingService} from "../../services/loading.service";
import {ProductCategoryCreatorComponent} from "./product-category-creator/product-category-creator.component";
import {AjaxCallService} from "../../services/ajax-call.service";
import {NotificationService} from "../../services/notification.service";

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css']
})
export class ShopComponent implements OnInit {
  categorization: object[] = null;
  @ViewChild('insert') insertComponent: ProductCategoryCreatorComponent;

  constructor(private constantService: ConstantService,
              private ajaxCallService: AjaxCallService,
              private notificationService: NotificationService,
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

  insertNewCategory(productCategory: ProductCategorization) {
    console.log("insert:", productCategory)
    this.insertComponent.clear();
    this.ajaxCallService.save("api/modules/shop/product-categorization", productCategory).then(
      res => {
        this.insertComponent.clear();
        this.categorization.push(res.data);
        this.notificationService.changeMessage("success", "ثبت موفق");
      }, err => {
        this.notificationService.changeMessage("error", "خطا رخ داده");
      }
    );
  }
}

export class ProductCategorization {
  id: number;
  title: string;
  routerLink: string;
  orders: string;
}
