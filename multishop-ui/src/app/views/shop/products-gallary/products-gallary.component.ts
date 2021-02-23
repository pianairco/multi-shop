import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {LoadingService} from "../../../services/loading.service";
import {NotificationService} from "../../../services/notification.service";

@Component({
  selector: 'app-products-gallary',
  templateUrl: './products-gallary.component.html',
  styleUrls: ['./products-gallary.component.css']
})
export class ProductsGallaryComponent implements OnInit {
  @Input() routerLink: string;
  private sub: any;

  constructor(private route: ActivatedRoute,
              private loadingService: LoadingService,
              private notificationService: NotificationService) { }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.routerLink = params['routerLink'];
    });
  }

}
