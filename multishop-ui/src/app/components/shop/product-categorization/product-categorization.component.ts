import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-product-categorization',
  templateUrl: './product-categorization.component.html',
  styleUrls: ['./product-categorization.component.css']
})
export class ProductCategorizationComponent implements OnInit {
  @Input() categorization: object[];

  constructor() { }

  ngOnInit(): void {
  }

}
