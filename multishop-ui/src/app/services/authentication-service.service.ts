import {Injectable} from '@angular/core';
import axios from "axios";
import {PianaStorageService} from "./piana-storage.service";
import {LoadingService} from "./loading.service";
import {ConstantService} from "./constant.service";
// import {GoogleLoginProvider, SocialAuthService} from "angularx-social-login";

const googleLoginOptions = {
  scope: 'profile email',
  prompt: 'select_account'
}; // https://developers.google.com/api-client-library/javascript/reference/referencedocs#gapiauth2clientconfig

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  uuid = null;
  appInfo: AppInfo = {
    "username": null,
    "email": null,
    "pictureUrl": null,
    "isLoggedIn": false,
    "isFormPassword": false,
    "isAdmin": false};

  setAppInfo(tempAppInfo: AppInfo) {
    this.appInfo.email = tempAppInfo.email;
    this.appInfo.username = tempAppInfo.username;
    this.appInfo.pictureUrl = tempAppInfo.pictureUrl;
    this.appInfo.isLoggedIn = tempAppInfo.isLoggedIn;
    this.appInfo.isFormPassword = tempAppInfo.isFormPassword;
    this.appInfo.isAdmin = tempAppInfo.isAdmin;
  }

  constructor(
    /*private authService: SocialAuthService,*/
    private constantService: ConstantService,
    private loadingService: LoadingService,
    private pianaStorageService: PianaStorageService) { }

  isLoggedIn(): boolean {
    return this.appInfo.isLoggedIn;
  }

  async initialToSignIn() {
    try {
      let res = await axios.post('api/sign-in/sub-domain', {}, {headers: {"content-type": "application/json"}});
      this.uuid = res.data['uuid'];
      return res.data['redirect'];
    } catch(err) {
      console.log(err)
    }
  }

  async getAppInfo() {
    let res = await axios.post('api/app-info', {}, {headers: {}});
    if (res.status === 200) {
      this.setAppInfo(res['data']);
      // console.log(appInfo);
      // console.log(JSON.stringify(appInfo));
      // console.log(localStorage.getItem('appInfo'));

      this.pianaStorageService.putObject('appInfo', this.appInfo);
      // localStorage.setItem('currentUser', JSON.stringify(appInfo))
      // console.log(this.pianaStorageService.getObject('appInfo')['username'])
      // console.log(this.pianaStorageService.getFieldValue('appInfo', 'username'))
      // console.log(JSON.parse(localStorage.getItem('appInfo'))['username'])
    }
  }

  async login() {
    try {
      let res = await axios.post(this.constantService.getRemoteServer() + '/api/sign-in',
        { uuid: this.uuid },
        { headers: { 'Content-Type': 'APPLICATION/JSON' } });
      console.log(res);
      this.setAppInfo(res['data']);
      this.pianaStorageService.putObject('appInfo', this.appInfo);
      return this.appInfo;
    } catch (err) {
      throw err;
    }
  }

  async logout() {
    console.log("auth service logout")
    // remove user from local storage to log user out
    try {
      // let appInfo = this.pianaStorageService.getObject('appInfo');
      if(this.appInfo == null)
        return;
      let res = await axios.post('api/sign-out', {headers: {}});
      console.log(res);
      if(res.status == 200) {
        this.setAppInfo(new AppInfo());
        // this.pianaStorageService.putObject('appInfo', res['data']);
        // localStorage.removeItem('currentUser');
      }
    } catch (err) {
      // this.timeStamp = this.timeStamp + 1;
      throw err;
    }
  }
}

export class AppInfo {
  username: string;
  email: string;
  pictureUrl: string;
  isLoggedIn: boolean;
  isFormPassword: boolean;
  isAdmin: boolean;
}
