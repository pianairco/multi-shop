import { Component, OnInit } from '@angular/core';
import axios from 'axios';

@Component({
  selector: 'app-home-view',
  templateUrl: './home-view.component.html',
  styleUrls: ['./home-view.component.css']
})
export class HomeViewComponent implements OnInit {
  constructor() { }

  ngOnInit(): void {
    // axios.get('/api/message').then(res => {
    //   console.log(res);
    // }, err => {
    //   console.log(err);
    // })
  }

  windowRef=null;

  openLoginWindow(){
    this.windowRef= window.open("https://shop.piana.ir:8443/#/child-view","child", "toolbar=no,location=no,directories=no,status=no,menubar=no,titlebar=no,fullscreen=no,scrollbars=1,resizable=no,width=430,height=220,left=500,top=100");
    if (this.windowRef.addEventListener) {
      console.log("addEventListener")
      this.windowRef.addEventListener("message", this.receivemessage.bind(this), true);
    } else {
      console.log("attachEvent")
      this.windowRef.attachEvent("onmessage", this.receivemessage.bind(this));
    }
  }

  receivemessage(evt:any){
    console.log(evt)
    console.log(evt.data)
  }

}
