import {Component, OnInit, ViewChild} from '@angular/core';
import {PictureBoxComponent} from "../../../components/picture-box/picture-box.component";
import {LogService} from "../../../services/log.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ShareStateService} from "../../../services/share-state.service";
import {ProductCategoryService} from "../../../services/product-category.service";
import {RestClientService} from "../../../services/rest-client.service";
import {LoadingService} from "../../../services/loading.service";
import {CommonUtilService} from "../../../services/common-util.service";
import {NotificationService} from "../../../services/notification.service";
import {Product} from "../product/product.component";
import {MeasurementUnitService} from "../../../services/measurement-unit.service";

@Component({
  selector: 'app-product-register',
  templateUrl: './product-register.component.html',
  styleUrls: ['./product-register.component.css']
})
export class ProductRegisterComponent implements OnInit {
  @ViewChild('pictureBox') pictureBoxComponent: PictureBoxComponent;
  returnUrl: string;
  onInitCalled = false;
  product = null;
  allProducts = null;

  constructor(
    private logService: LogService,
    public measurementUnitService: MeasurementUnitService,
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
    this.product = new Product(0, '', '', null, 0, '', 0, '', 0, 0);
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
}
