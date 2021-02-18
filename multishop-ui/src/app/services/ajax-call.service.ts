import { Injectable } from '@angular/core';
import {ConstantService} from "./constant.service";
import axios from "axios";

@Injectable({
  providedIn: 'root'
})
export class AjaxCallService {

  remoteServer: string = "";

  constructor(private constantService: ConstantService) { }

  save(url, obj) {
    return axios.post(this.constantService.getRemoteServer() + "/" + url,
      {obj},
      {headers: {}});
  }
}
