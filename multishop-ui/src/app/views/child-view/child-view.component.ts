import { Component, OnInit } from '@angular/core';
import axios from 'axios';

@Component({
  selector: 'app-child-view',
  templateUrl: './child-view.component.html',
  styleUrls: ['./child-view.component.css']
})
export class ChildViewComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    // axios.get('/api/message').then(res => {
    //   console.log(res);
    // }, err => {
    //   console.log(err);
    // })
  }

  close() {
    parent.postMessage("sfasafsafa", "https://shop.piana.ir");
    window.close();
  }
}
