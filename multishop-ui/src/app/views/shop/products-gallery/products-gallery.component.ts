import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {LoadingService} from "../../../services/loading.service";
import {NotificationService} from "../../../services/notification.service";
import {ProductEditorComponent} from "../product-editor/product-editor.component";
import {Product} from "../product/product.component";
import {ConstantService} from "../../../services/constant.service";
import {AjaxCallService} from "../../../services/ajax-call.service";
import {ProductEditorModalComponent} from "../product-editor-modal/product-editor-modal.component";
import {ShareStateService} from "../../../services/share-state.service";
import {AuthenticationService} from "../../../services/authentication-service.service";
import {ProductCategoryService} from "../../../services/product-category.service";
import {RestClientService} from "../../../services/rest-client.service";

@Component({
  selector: 'app-products-gallery',
  templateUrl: './products-gallery.component.html',
  styleUrls: ['./products-gallery.component.css']
})
export class ProductsGalleryComponent implements OnInit {
  // @Input() routerLink: string;
  routerLink: string;
  // private sub: any;
  products: Product[] = [];
  firstReload: boolean = false;
  // @ViewChild('insertProduct') insertProductComponent: ProductEditorComponent;

  // products: Product[] = [
  //   new Product(1,'a', 'a', '../../../assets/images/256x256.png', 1, 'عدد', 1000, 'تومان', 0),
  //   new Product(2,'a', 'a', '../../../assets/images/256x256.png', 1, 'عدد', 1000, 'تومان', 0),
  //   new Product(3,'a', 'a', '../../../assets/images/256x256.png', 1, 'عدد', 1000, 'تومان', 0),
  //   new Product(4,'a', 'a', '../../../assets/images/256x256.png', 1, 'عدد', 1000, 'تومان', 0),
  //   new Product(5,'a', 'a', '../../../assets/images/256x256.png', 1, 'عدد', 1000, 'تومان', 0),
  //   new Product(6,'a', 'a', '../../../assets/images/256x256.png', 1, 'عدد', 1000, 'تومان', 0),
  //   new Product(7,'a', 'a', '../../../assets/images/256x256.png', 1, 'عدد', 1000, 'تومان', 0),
  //   new Product(8,'a', 'a', '../../../assets/images/256x256.png', 1, 'عدد', 1000, 'تومان', 0),
  // ];

  // insertMode = false;

  constructor(private route: ActivatedRoute,
              public router: Router,
              private loadingService: LoadingService,
              private categoryService: ProductCategoryService,
              public authService: AuthenticationService,
              public shareStateService: ShareStateService,
              private constantService: ConstantService,
              private restClient: RestClientService,
              private notificationService: NotificationService) { }

  async ngOnInit(): Promise<void> {
    this.route.params.subscribe(async params => {
      this.routerLink = params['routerLink'];
      this.loadingService.changeState(true);
      try {
        this.firstReload = true;
        // this.categoryService.checkCategory(this.routerLink);
        // console.log(this.routerLink)
        let res = await this.restClient.productList(this.routerLink);
        this.products = res['data'];
        this.loadingService.changeState(false);
      } catch (e) {
        this.loadingService.changeState(false);
      }

      // this.products.forEach(p => {
      //   p.title = this.routerLink;
      // })
    });

    if(!this.firstReload)
      console.log(this.routerLink)
  }

  // openProductCreator() {
  //   this.insertMode = true;
  // }
  //
  // closeProductCreator(productCreator: { component: ProductEditorModalComponent}) {
  //   productCreator['component'].close();
  //   this.insertMode = false;
  // }

  // insertNewProduct(product: Product) {
  //   this.loadingService.changeState(true);
  //   this.ajaxCallService.save("api/modules/shop/product", product).then(
  //     res => {
  //       this.products.push(res.data);
  //       this.notificationService.changeMessage("success", "ثبت موفق");
  //       this.loadingService.changeState(false);
  //       // this.closeProductCreator({component: this.insertProductComponent});
  //     }, err => {
  //       this.notificationService.changeMessage("error", "خطا رخ داده");
  //       this.loadingService.changeState(false);
  //     }
  //   );
  // }
}
