import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CommonUtilService} from "../../../services/common-util.service";
import {NotificationService} from "../../../services/notification.service";
import {AuthenticationService} from "../../../services/authentication-service.service";
import {ShareStateService} from "../../../services/share-state.service";
import {ActivatedRoute, Router, RouterStateSnapshot} from "@angular/router";
import {delay} from "rxjs/operators";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  @Input() product: Product;

  constructor(
    public router: Router,
    public shareStateService: ShareStateService,
    public authService: AuthenticationService,
    private commonUtilService: CommonUtilService,
    private notificationService: NotificationService) { }

  ngOnInit(): void {
    // this.shareStateService.editModeSubject.subscribe(next => {
    //   this.shareStateService.ifTrue(() => {
    //     this.router.navigate(['/tile/shop/product-editor'],
    //       { queryParams: { returnUrl: this.router.routerState.snapshot.url }});
    //   });
    // });
  }

  // private registerClick() {
  //   console.log("reg")
  //   if (!this.commonUtilService.hasStringValue(this.product.title)) {
  //     this.notificationService.changeMessage("error", "عنوان نباید خالی باشد");
  //     return;
  //   } else if (!this.commonUtilService.hasStringValue(this.product.description)) {
  //     this.notificationService.changeMessage("error", "توضیحات نباید خالی باشد");
  //     return;
  //   } else if (!this.commonUtilService.hasStringValue(this.product.image)) {
  //     this.notificationService.changeMessage("error", "تصویر نباید خالی باشد");
  //     return;
  //   } else if (!this.commonUtilService.hasNumberValue(this.product.price)) {
  //     this.notificationService.changeMessage("error", "قیمت نباید خالی باشد");
  //     return;
  //   } else if (!this.commonUtilService.hasNumberValue(this.product.measurement)) {
  //     this.notificationService.changeMessage("error", "مقدار بر حسب واحد نباید خالی باشد");
  //     return;
  //   }
  //
  //   console.log("product:", this.product)
  //   if(this.insertable && !this.product.id)
  //     this.insertClick.emit(this.product);
  //   else if(this.editable && this.product.id) {
  //     this.updateClick.emit({ product: this.product, component: this });
  //   }
  // }

  // private clearClick() {
  //   this.editMode = false;
  // }
}

export class Product {
  id: number;
  title: string;
  description: string;
  image: string;
  measurement: number;
  measurementUnit: string;
  price: number;
  currency: string;
  percent: number;
  categoryId: number;

  constructor(id, title, description, image, measurement, measurementUnit, price,  currency, percent, categoryId) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.image = image;
    this.measurement = measurement;
    this.measurementUnit = measurementUnit;
    this.price = price;
    this.currency = currency;
    this.percent = percent;
    this.categoryId = categoryId;
  }
};
