import {Component, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';
import {CommonUtilService} from "../../../services/common-util.service";
import {NotificationService} from "../../../services/notification.service";
import {Product} from "../product/product.component";
import {ProductCategoryComponent} from "../product-category/product-category.component";
import {PictureBoxComponent} from "../../../components/picture-box/picture-box.component";
import {ActivatedRoute, Router} from "@angular/router";
import {ShareStateService} from "../../../services/share-state.service";

@Component({
  selector: 'app-product-editor',
  templateUrl: './product-editor.component.html',
  styleUrls: ['./product-editor.component.css']
})
export class ProductEditorComponent implements OnInit, OnDestroy {
  @Input() product: Product = new Product(null, null, null, null, null, null, null, null);
  @Input() isActive: boolean = false;
  @Input() editable: boolean = false;
  @Input() insertable: boolean = false;
  @Output() insertClick = new EventEmitter<Product>();
  @Output() updateClick = new EventEmitter<{product: Product, component: ProductEditorComponent}>();
  @Output() closeClick = new EventEmitter<{component: ProductEditorComponent}>();
  @ViewChild('pictureBox') pictureBoxComponent: PictureBoxComponent;
  returnUrl: string;
  onInitCalled = false;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private shareStateService: ShareStateService,
    private commonUtilService: CommonUtilService,
    private notificationService: NotificationService) { }

    ngOnInit(): void {
      console.log("MMMMMMMM")
      this.onInitCalled = true;
      this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  ngOnDestroy(): void {
    console.log("WWWWWWW")
    if(this.onInitCalled)
      this.shareStateService.editMode = false;
  }

  selectImage(image) {
    this.product['imageBase64'] = image;
  }

  registerClick() {
    if (!this.commonUtilService.hasStringValue(this.product.title)) {
      this.notificationService.changeMessage("error", "عنوان نباید خالی باشد");
      return;
    } else if (!this.commonUtilService.hasStringValue(this.product.description)) {
      this.notificationService.changeMessage("error", "توضیحات نباید خالی باشد");
      return;
    }/* else if (!this.commonUtilService.hasStringValue(this.product.image)) {
      this.notificationService.changeMessage("error", "تصویر نباید خالی باشد");
      return;
    }*/ else if (!this.commonUtilService.hasNumberValue(this.product.price)) {
      this.notificationService.changeMessage("error", "قیمت نباید خالی باشد");
      return;
    } else if (!this.commonUtilService.hasNumberValue(this.product.measurement)) {
      this.notificationService.changeMessage("error", "مقدار بر حسب واحد نباید خالی باشد");
      return;
    }

    console.log("product:", this.product)
    if(this.insertable && !this.product.id)
      this.insertClick.emit(this.product);
    else if(this.editable && this.product.id) {
      this.updateClick.emit({ product: this.product, component: this });
    }
  }

  public close() {
    this.pictureBoxComponent.clear();
    this.product = new Product(null, null, null, null, null, null, null, null);
    this.router.navigate([this.returnUrl])
    // this.isActive = false;
  }

  public onCloseClick() {
    this.closeClick.emit({component: this});
  }
}
