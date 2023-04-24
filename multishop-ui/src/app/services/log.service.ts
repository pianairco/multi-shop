import {Inject, Injectable, Injector, Optional} from '@angular/core';
import {ConstantService} from "./constant.service";
// import axios from "axios";

@Injectable({
  providedIn: 'root'
})
export class LogService {
  isDebugMode: boolean = true;

  constructor(private constantService: ConstantService,
              private injector: Injector,
              @Inject('fromComponent') @Optional() private fromComponent?: string) {
  }

  public log(line, ...messages) {
    if (this.isDebugMode)
      console.log(this.fromComponent ? this.fromComponent : '', line, messages);
  }

  public logForce(line, ...messages) {
    console.log(this.fromComponent ? this.fromComponent : '', line, messages);
  }
}
