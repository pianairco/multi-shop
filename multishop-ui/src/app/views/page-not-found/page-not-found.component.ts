import { Component, OnInit } from '@angular/core';
import {Product} from "../shop/product/product.component";

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.css']
})
export class PageNotFoundComponent implements OnInit {
  editMode = false;
  product = new Product('a', 'a', '../../../assets/images/256x256.png', 1, 'عدد', 1000, 'تومان', 0)
  constructor() { }

  ngOnInit(): void {
  }

}
