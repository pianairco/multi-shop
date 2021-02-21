import {Component, OnInit, ViewChild} from '@angular/core';
import axios from 'axios';
import {ConstantService} from "../../services/constant.service";
import {LoadingService} from "../../services/loading.service";
import {ProductCategory, ProductCategoryComponent} from "./product-category/product-category.component";
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
    new Product('a', 'a', '../../../assets/images/256x256.png', 1, 'عدد', 1000, 'تومان', 0),
    new Product('a', 'a', '../../../assets/images/256x256.png', 1, 'عدد', 1000, 'تومان', 0),
    new Product('a', 'a', '../../../assets/images/256x256.png', 1, 'عدد', 1000, 'تومان', 0),
    new Product('a', 'a', '../../../assets/images/256x256.png', 1, 'عدد', 1000, 'تومان', 0),
    new Product('a', 'a', '../../../assets/images/256x256.png', 1, 'عدد', 1000, 'تومان', 0),
    new Product('a', 'a', '../../../assets/images/256x256.png', 1, 'عدد', 1000, 'تومان', 0),
    new Product('a', 'a', '../../../assets/images/256x256.png', 1, 'عدد', 1000, 'تومان', 0),
    new Product('a', 'a', '../../../assets/images/256x256.png', 1, 'عدد', 1000, 'تومان', 0),
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

  insertNewCategory(productCategory: ProductCategory) {
    console.log("insert:", productCategory)
    this.loadingService.changeState(true);
    this.ajaxCallService.save("api/modules/shop/category/product-categorization", productCategory).then(
      res => {
        this.insertComponent.success();
        this.categorization.push(res.data);
        this.notificationService.changeMessage("success", "ثبت موفق");
        this.loadingService.changeState(false);
      }, err => {
        this.notificationService.changeMessage("error", "خطا رخ داده");
        this.loadingService.changeState(false);
      }
    );
  }

  updateCategory(event: {category: ProductCategory, component: ProductCategoryComponent}) {
    let productCategory: ProductCategory = event.category;
    let updateComponent: ProductCategoryComponent = event.component;
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
        updateComponent.success();
      }, err => {
        this.notificationService.changeMessage("error", "خطا رخ داده");
        this.loadingService.changeState(false);
      }
    );
  }
}
