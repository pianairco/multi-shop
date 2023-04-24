import {Component, Input, OnInit, Output, EventEmitter} from '@angular/core';
import {NotificationService} from "../../../services/notification.service";
import {ShareStateService} from "../../../services/share-state.service";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../../services/authentication-service.service";

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {
  // @Input() editable: boolean = false;
  // @Input() insertable: boolean = false;
  @Input() productCategory: ProductCategory = new ProductCategory(0, null, null, null, 0);
  // @Output() insertClick = new EventEmitter<ProductCategory>();
  // @Output() updateClick = new EventEmitter<{category: ProductCategory, component: CategoryComponent}>();
  // originalProductCategory: ProductCategory = new ProductCategory(0, null, null, null);

  constructor(
    public router: Router,
    public authService: AuthenticationService,
    public shareStateService: ShareStateService,
    private notificationService: NotificationService) { }

  ngOnInit(): void {
    // this.originalProductCategory = JSON.parse(JSON.stringify(this.productCategory));
  }

  // private registerClick() {
  //   console.log("reg")
  //   if (this.productCategory.title == null ||
  //     this.productCategory.title == '' ||
  //     this.productCategory.title == undefined) {
  //     this.notificationService.changeMessage("error", "عنوان نباید خالی باشد");
  //     return;
  //   } else if (this.productCategory.routerLink == null ||
  //     this.productCategory.routerLink == '' ||
  //     this.productCategory.routerLink == undefined) {
  //     this.notificationService.changeMessage("error", "لینک نمایشی نباید خالی باشد");
  //     return;
  //   }
  //   // console.log("product:", this.productCategorization)
  //   if(this.insertable && !this.productCategory.id)
  //     this.insertClick.emit(this.productCategory);
  //   else if(this.editable && this.productCategory.id) {
  //     this.updateClick.emit({ category: this.productCategory, component: this });
  //   }
  // }
  //
  // private clearClick() {
  //   this.productCategory.routerLink = this.originalProductCategory.routerLink;
  //   this.productCategory.title = this.originalProductCategory.title;
  // }

  // success() {
  //   this.originalProductCategory = JSON.parse(JSON.stringify(this.productCategory));
  // }

  // fail() {
  //   this.clearClick();
  // }
}

export class ProductCategory {
  id: number;
  title: string;
  routerLink: string;
  orders: string;
  pianaCategoryId: string;

  constructor(id, title, routerLink, orders, pianaCategoryId) {
    this.id = id;
    this.title = title;
    this.routerLink = routerLink;
    this.orders = orders;
    this.pianaCategoryId = pianaCategoryId;
  }
}
