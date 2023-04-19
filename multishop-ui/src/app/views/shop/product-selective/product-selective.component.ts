import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Router} from "@angular/router";
import {ShareStateService} from "../../../services/share-state.service";
import {AuthenticationService} from "../../../services/authentication-service.service";
import {CommonUtilService} from "../../../services/common-util.service";
import {NotificationService} from "../../../services/notification.service";
import {Product} from "../product/product.component";
import {LogService} from "../../../services/log.service";

@Component({
  selector: 'app-product-selective',
  templateUrl: './product-selective.component.html',
  styleUrls: ['./product-selective.component.css']
})
export class ProductSelectiveComponent implements OnInit {
  @Input() product: Product;
  @Output() onSelect = new EventEmitter<number>();

  constructor(
    private logService: LogService,
    public router: Router,
    public shareStateService: ShareStateService,
    public authService: AuthenticationService,
    private commonUtilService: CommonUtilService,
    private notificationService: NotificationService) { }

  ngOnInit(): void {
  }

  selectClick(product) {
    this.logService.log(product);
    this.onSelect.emit(product);
  }
}
