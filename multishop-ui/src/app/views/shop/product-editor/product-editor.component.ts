import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {CommonUtilService} from "../../../services/common-util.service";
import {NotificationService} from "../../../services/notification.service";
import {Product} from "../product/product.component";
import {PictureBoxComponent} from "../../../components/picture-box/picture-box.component";
import {ActivatedRoute, Router} from "@angular/router";
import {ShareStateService} from "../../../services/share-state.service";
import {AjaxCallService} from "../../../services/ajax-call.service";
import {LoadingService} from "../../../services/loading.service";
import {RestClientService} from "../../../services/rest-client.service";
import {ProductCategoryService} from "../../../services/product-category.service";
import {LogService} from "../../../services/log.service";

@Component({
  selector: 'app-product-editor',
  templateUrl: './product-editor.component.html',
  styleUrls: ['./product-editor.component.css']
})
export class ProductEditorComponent implements OnInit, OnDestroy {
  // @Input() product: Product = new Product(0,null, null, null, null, null, null, null, null);
  // @Input() isActive: boolean = false;
  // @Input() editable: boolean = false;
  // @Input() insertable: boolean = false;
  // @Output() insertClick = new EventEmitter<Product>();
  // @Output() updateClick = new EventEmitter<{product: Product, component: ProductEditorComponent}>();
  // @Output() closeClick = new EventEmitter<{component: ProductEditorComponent}>();
  @ViewChild('pictureBox') pictureBoxComponent: PictureBoxComponent;
  returnUrl: string;
  onInitCalled = false;
  product = null;
  allProducts = null;

  constructor(
    private logService: LogService,
    private router: Router,
    private route: ActivatedRoute,
    private shareStateService: ShareStateService,
    private productCategoryService: ProductCategoryService,
    private restClientService: RestClientService,
    private loadingService: LoadingService,
    private commonUtilService: CommonUtilService,
    private notificationService: NotificationService) {

  }

  ngOnInit(): void {
    this.onInitCalled = true;
    this.product = this.shareStateService.editModeObject.changeable;
    if(!this.product) {
      this.product = new Product(0, '', '', null, 0, '', 0, '', 0, 0);
    }
    // this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  ngOnDestroy(): void {
    if(this.onInitCalled)
      this.shareStateService.clearEditModeObject();
    // if(this.onInitCalled)
      // this.shareStateService.editMode = false;
  }

  selectImage(image) {
    this.product['imageBase64'] = image;
  }

  registerClick() {
    console.log(this.product)
    this.loadingService.changeState(true);
    this.restClientService.productPersist(this.product).then(res => {
      this.productCategoryService.renew();
      this.loadingService.changeState(false);
      this.shareStateService.navigateReturn();
    }, err => {
      this.loadingService.changeState(false);
      this.notificationService.changeMessage('error', 'خطا رخ داده است')
    });
/*    if (!this.commonUtilService.hasStringValue(this.product.title)) {
      this.notificationService.changeMessage("error", "عنوان نباید خالی باشد");
      return;
    } else if (!this.commonUtilService.hasStringValue(this.product.description)) {
      this.notificationService.changeMessage("error", "توضیحات نباید خالی باشد");
      return;
    }*//* else if (!this.commonUtilService.hasStringValue(this.product.image)) {
      this.notificationService.changeMessage("error", "تصویر نباید خالی باشد");
      return;
    }*//* else if (!this.commonUtilService.hasNumberValue(this.product.price)) {
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
    }*/
  }

  deleteClick() {
    this.loadingService.changeState(true);
    this.restClientService.productDelete(this.product).then(res => {
      this.loadingService.changeState(false);
      this.shareStateService.navigateReturn();
    }, err => {
      this.loadingService.changeState(false);
      this.notificationService.changeMessage('error', 'خطا رخ داده است')
    });
  }

  public close() {
    // this.pictureBoxComponent.clear();
    // this.product = new Product(0, null, null, null, null, null, null, null, null);
    this.shareStateService.navigateReturn();
    // this.router.navigate([this.returnUrl])
    // this.isActive = false;
  }

  selectCategory(categoryId) {
    console.log(categoryId)
    this.restClientService.getAllProductsByPianaCategory(categoryId).then(res => {
      if(res.status === 200 && res.data.code === 0) {
        this.allProducts = res.data.data;
        console.log('all-products', this.allProducts)
      }
    }, err => {
      console.log('all-products', null)
    });
  }

  onSelectProduct(product) {
    this.logService.log(product.id)
  }

  registerNewProduct() {
    this.router.navigate(["../product-register"], {relativeTo: this.route})
  }
}
