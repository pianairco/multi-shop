import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  @Input() product: Product;

  constructor() { }

  ngOnInit(): void {
  }

}

export class Product {
  title: string;
  description: string;
  price: number;
  unit: string;
  currency: string;
  image: string;

  constructor(title, description, image, price, unit, currency) {
    this.title = title;
    this.description = description;
    this.image = image;
    this.price = price;
    this.unit = unit;
    this.currency = currency;
  }
}
