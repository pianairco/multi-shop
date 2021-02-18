import {Component, Input, OnInit, Output, EventEmitter} from '@angular/core';
import {ProductCategorization} from "../shop.component";
import {NotificationService} from "../../../services/notification.service";

@Component({
  selector: 'app-product-category-creator',
  templateUrl: './product-category-creator.component.html',
  styleUrls: ['./product-category-creator.component.css']
})
export class ProductCategoryCreatorComponent implements OnInit {
  @Input() editable: boolean = false;
  @Input() insertable: boolean = false;
  @Input() productCategorization: ProductCategorization = new ProductCategorization();
  @Output() insertClick = new EventEmitter<ProductCategorization>();
  @Output() updateClick = new EventEmitter<ProductCategorization>();

  constructor(private notificationService: NotificationService) { }

  ngOnInit(): void {
  }

  registerClick() {
    if (this.productCategorization.title == null ||
      this.productCategorization.title == '' ||
      this.productCategorization.title == undefined) {
      this.notificationService.changeMessage("error", "عنوان نباید خالی باشد");
      return;
    } else if (this.productCategorization.routerLink == null ||
      this.productCategorization.routerLink == '' ||
      this.productCategorization.routerLink == undefined) {
      this.notificationService.changeMessage("error", "لینک نمایشی نباید خالی باشد");
      return;
    }
    // console.log("product:", this.productCategorization)
    if(this.insertable && !this.productCategorization.id)
      this.insertClick.emit(this.productCategorization);
    else if(this.editable && this.productCategorization.id) {
      this.updateClick.emit(this.productCategorization);
    }
  }

  clear() {
    this.productCategorization = new ProductCategorization();
  }
}
