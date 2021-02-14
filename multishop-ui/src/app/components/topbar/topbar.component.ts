import {AfterViewChecked, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import axios from "axios";

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.css']
})
export class TopbarComponent implements OnInit, AfterViewChecked {
  @ViewChild('toggled') toggled: ElementRef;

  istoggled: boolean = false;
  forceShow: boolean = false;

  constructor() { }

  ngOnInit(): void {
    // console.log(this.toggled['display'])
  }

  ngAfterViewChecked() {
    if(this.toggled) {
      console.log(window.getComputedStyle(this.toggled.nativeElement).visibility)
      this.istoggled = true;
    }
  }

  forceShowClick() {
    // console.log(window.getComputedStyle(this.toggled.nativeElement).visibility)
    // console.log("asfasd", this.toggled.nativeElement.getAttribute('display'), this.toggled.nativeElement)
    if(this.toggled)
      this.istoggled = true;
    this.forceShow = !this.forceShow
  }

  windowRef=null;

  loginClick() {
    // console.log(localStorage.getItem('currentUser'));
    // console.log(localStorage.getItem('currentUser')['username']);
    axios.post('api/sign-in/sub-domain', {}, {headers: {"content-type": "application/json" }})
      .then(res => {
        this.windowRef= window.open(res.data['redirect'],"child", "toolbar=no,location=no,directories=no,status=no,menubar=no,titlebar=no,fullscreen=no,scrollbars=1,resizable=no,width=430,height=220,left=500,top=100");
        this.windowRef.addEventListener("message",this.receivemessage.bind(this), false);
      }, err => {
        console.log(err)
      })
  }

  receivemessage(evt:any){
    console.log(evt.data)
  }
}
