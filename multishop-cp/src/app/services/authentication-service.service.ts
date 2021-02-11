import {Injectable, isDevMode} from '@angular/core';
import axios from "axios";
import {PianaStorageService} from "./piana-storage.service";
import {LoadingService} from "./loading.service";
import {ConstantService} from "./constant.service";
import {GoogleLoginProvider} from "angularx-social-login";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(
    private constantService: ConstantService,
    private loadingService: LoadingService,
    private pianaStorageService: PianaStorageService) { }

  async googleSignIn () {
    try {
      console.log("service googleSignIn")
      let accessToken = null;
      this.loadingService.changeState(true);
      if(!isDevMode()) {
        // this.authService.signIn(GoogleLoginProvider.PROVIDER_ID)
        //   .then(res => {
        //     console.log(res);
        //   }, err => {
        //     console.log(err);
        //   });
      } else {
        accessToken = "1234";
      }
      if(accessToken == null) {
        return;
      }
      let res = await axios.post(this.constantService.getRemoteServer() + '/api/sign-in',
        { 'accessToken': accessToken },
        { headers: { 'Content-Type': 'APPLICATION/JSON', 'auth-type': 'g-oauth2' } });
      let appInfo = res['data'];
      this.pianaStorageService.putObject('appInfo', appInfo);
      return appInfo;
    } catch (error) {
      throw error;
      //on fail do something
    }
  }

  async login(loginInfo: {
    username: '',
    password: '',
    captcha: ''
  }) {
    try {
      let res = await axios.post('api/sign-in', loginInfo, {headers: {'auth-type': 'form'}});
      console.log(res);
      let appInfo = res['data'];
      // console.log(appInfo);
      // console.log(JSON.stringify(appInfo));
      // localStorage.setItem('currentUser', JSON.stringify(appInfo));
      this.pianaStorageService.putObject('appInfo', appInfo);
      // console.log(localStorage.getItem('currentUser'));
      return appInfo;
    } catch (err) {
      // this.timeStamp = this.timeStamp + 1;
      throw err;
    }
  }

  async logout() {
    console.log("auth service logout")
    // remove user from local storage to log user out
    try {
      let appInfo = this.pianaStorageService.getObject('appInfo');
      if(appInfo == null)
        return;
      let res = await axios.post('api/sign-out', {headers: {}});
      console.log(res);
      if(res.status == 200) {
        this.pianaStorageService.putObject('appInfo', res['data']);
        // localStorage.removeItem('currentUser');
      }
    } catch (err) {
      // this.timeStamp = this.timeStamp + 1;
      throw err;
    }
  }
}
