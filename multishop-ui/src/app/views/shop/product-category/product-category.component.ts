import {Component, Input, OnInit, Output, EventEmitter} from '@angular/core';
import {NotificationService} from "../../../services/notification.service";

@Component({
  selector: 'app-product-category',
  templateUrl: './product-category.component.html',
  styleUrls: ['./product-category.component.css']
})
export class ProductCategoryComponent implements OnInit {
  @Input() editable: boolean = false;
  @Input() insertable: boolean = false;
  @Input() productCategory: ProductCategory = new ProductCategory();
  @Output() insertClick = new EventEmitter<ProductCategory>();
  @Output() updateClick = new EventEmitter<{category: ProductCategory, component: ProductCategoryComponent}>();
  originalProductCategory: ProductCategory = new ProductCategory();

  editMode = false;

  constructor(private notificationService: NotificationService) { }

  ngOnInit(): void {
    this.originalProductCategory = JSON.parse(JSON.stringify(this.productCategory));
  }

  private registerClick() {
    console.log("reg")
    if (this.productCategory.title == null ||
      this.productCategory.title == '' ||
      this.productCategory.title == undefined) {
      this.notificationService.changeMessage("error", "عنوان نباید خالی باشد");
      return;
    } else if (this.productCategory.routerLink == null ||
      this.productCategory.routerLink == '' ||
      this.productCategory.routerLink == undefined) {
      this.notificationService.changeMessage("error", "لینک نمایشی نباید خالی باشد");
      return;
    }
    // console.log("product:", this.productCategorization)
    if(this.insertable && !this.productCategory.id)
      this.insertClick.emit(this.productCategory);
    else if(this.editable && this.productCategory.id) {
      this.updateClick.emit({ category: this.productCategory, component: this });
    }
  }

  private clearClick() {
    this.productCategory.routerLink = this.originalProductCategory.routerLink;
    this.productCategory.title = this.originalProductCategory.title;
    this.editMode = false;
  }

  success() {
    this.originalProductCategory = JSON.parse(JSON.stringify(this.productCategory));
  }

  fail() {
    this.clearClick();
  }
}

export class ProductCategory {
  id: number;
  title: string;
  routerLink: string;
  orders: string;
}
