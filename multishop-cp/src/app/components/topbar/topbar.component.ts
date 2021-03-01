import {AfterViewChecked, Component, ElementRef, OnInit, ViewChild} from '@angular/core';

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
      // console.log(window.getComputedStyle(this.toggled.nativeElement).visibility)
      this.istoggled = true;
    }
  }

  forceShowClick() {
    console.log(window.getComputedStyle(this.toggled.nativeElement).visibility)
    console.log("asfasd", this.toggled.nativeElement.getAttribute('display'), this.toggled.nativeElement)
    if(this.toggled)
      this.istoggled = true;
    this.forceShow = !this.forceShow
  }

  loginClick() {
    console.log(localStorage.getItem('currentUser'));
    console.log(localStorage.getItem('currentUser')['username']);
  }
}
