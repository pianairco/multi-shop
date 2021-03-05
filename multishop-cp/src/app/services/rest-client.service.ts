import {Injectable, Injector} from '@angular/core';
import {ConstantService} from "./constant.service";
import {AjaxCallService} from "./ajax-call.service";
import {log} from "util";

@Injectable({
  providedIn: 'root'
})
export class RestClientService {
  ajaxUrlMap = {
    'site': 'api/modules/site'
  }

  remoteServer: string = "";

  constructor(private constantService: ConstantService,
              private ajaxCallService: AjaxCallService) { }

  getAllSites() {
    return this.ajaxCallService.read(this.ajaxUrlMap.site + '/all-sites');
  }
}
