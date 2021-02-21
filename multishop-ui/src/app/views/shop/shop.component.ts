import {Component, OnInit, ViewChild} from '@angular/core';
import axios from 'axios';
import {ConstantService} from "../../services/constant.service";
import {LoadingService} from "../../services/loading.service";
import {ProductCategoryComponent} from "./product-category/product-category.component";
import {AjaxCallService} from "../../services/ajax-call.service";
import {NotificationService} from "../../services/notification.service";
import {Product} from "./product/product.component";

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css']
})
export class ShopComponent implements OnInit {
  categorization: object[] = null;
  @ViewChild('insert') insertComponent: ProductCategoryComponent;
  products: Product[] = [
    new Product('a', 'a', '../../../assets/images/256x256.png', 0, null, null),
    new Product('b', 'b', '../../../assets/images/256x256.png', 0, null, null),
    new Product('v', 'v', '../../../assets/images/256x256.png', 0, null, null),
    new Product('q', 'q', '../../../assets/images/256x256.png', 0, null, null),
    new Product('w', 'w', '../../../assets/images/256x256.png', 0, null, null),
    new Product('e', 'e', '../../../assets/images/256x256.png', 0, null, null),
    new Product('n', 'n', '../../../assets/images/256x256.png', 0, null, null)
  ];

  constructor(private constantService: ConstantService,
              private ajaxCallService: AjaxCallService,
              private notificationService: NotificationService,
              private loadingService: LoadingService) { }

  async ngOnInit(): Promise<void> {
    try {
      this.loadingService.changeState(true);
      let res = await axios.get(this.constantService.getRemoteServer() + "/api/modules/shop/category/product-categorization");
      this.categorization = res.data;
      console.log(res.data)
      this.loadingService.changeState(false);
    } catch (err) {

    }
  }

  insertNewCategory(productCategory: ProductCategorization) {
    console.log("insert:", productCategory)
    this.loadingService.changeState(true);
    this.ajaxCallService.save("api/modules/shop/category/product-categorization", productCategory).then(
      res => {
        this.insertComponent.clear();
        this.categorization.push(res.data);
        this.notificationService.changeMessage("success", "ثبت موفق");
        this.loadingService.changeState(false);
      }, err => {
        this.notificationService.changeMessage("error", "خطا رخ داده");
        this.loadingService.changeState(false);
      }
    );
  }

  updateCategory(productCategory: ProductCategorization) {
    console.log("edit:", productCategory)
    this.loadingService.changeState(true);
    this.ajaxCallService.update("api/modules/shop/category/product-categorization", productCategory).then(
      res => {
        let index = -1;
        this.categorization.forEach((cat, idx) => {
          if(cat['id'] = res.data['id'])
            index = idx;
        });
        this.categorization[index] = res.data;
        this.notificationService.changeMessage("success", "ثبت موفق");
        this.loadingService.changeState(false);
      }, err => {
        this.notificationService.changeMessage("error", "خطا رخ داده");
        this.loadingService.changeState(false);
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
