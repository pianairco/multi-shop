import {Injectable, isDevMode} from '@angular/core';
import axios from "axios";
import {PianaStorageService} from "./piana-storage.service";
import {LoadingService} from "./loading.service";
import {ConstantService} from "./constant.service";
import {GoogleLoginProvider, SocialAuthService} from "angularx-social-login";

const googleLoginOptions = {
  scope: 'profile email',
  prompt: 'select_account'
}; // https://developers.google.com/api-client-library/javascript/reference/referencedocs#gapiauth2clientconfig

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  appInfo = null;

  constructor(
    private authService: SocialAuthService,
    private constantService: ConstantService,
    private loadingService: LoadingService,
    private pianaStorageService: PianaStorageService) { }

  async getToken () {
    try {
      console.log("service googleSignIn")
      let socialUser = await this.authService.signIn(GoogleLoginProvider.PROVIDER_ID, googleLoginOptions);
      let accessToken = socialUser['authToken'];
      return accessToken;
    } catch (e) {

    }
  }

  async getAppInfo() {
    let res = await axios.post('api/app-info', {}, {headers: {}});
    if (res.status === 200) {
      this.appInfo = res['data'];
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

  async googleSignIn (subDomain) {
    try {
      console.log("service googleSignIn")
      let accessToken = null;
      this.loadingService.changeState(true);
      if(isDevMode() && false) {
        console.log("is-dev")
        accessToken = "1234";
        //   .then(res => {
        //     console.log(res);
        //   }, err => {
        //     console.log(err);
        //   });
      } else {
        console.log("is-prod")
        let socialUser = await this.authService.signIn(GoogleLoginProvider.PROVIDER_ID, googleLoginOptions);
        accessToken = socialUser['authToken'];
        // console.log(accessToken, socialUser)
      }
      if(accessToken == null) {
        return;
      }
      if(subDomain != null) {
        let res = await axios.post(this.constantService.getRemoteServer() + '/api/sign-in/sub-domain/set-token',
          { 'accessToken': accessToken, uuid: subDomain },
          { headers: { 'Content-Type': 'APPLICATION/JSON', 'auth-type': 'g-oauth2' } });
        if(res.status == 200) {
          return "close";
        }
      } else {
        let res = await axios.post(this.constantService.getRemoteServer() + '/api/sign-in',
          { 'accessToken': accessToken },
          { headers: { 'Content-Type': 'APPLICATION/JSON', 'auth-type': 'g-oauth2' } });
        let appInfo = res['data'];
        this.pianaStorageService.putObject('appInfo', appInfo);
        return appInfo;
      }
    } catch (error) {
      throw error;
      //on fail do something
    }
  }

  async login(loginInfo: {
    username: '',
    password: '',
    captcha: '',
    uuid: ''
  }, subDomain) {
    try {
      if(subDomain != null) {
        loginInfo.uuid = subDomain;
        let res = await axios.post(this.constantService.getRemoteServer() + '/api/sign-in/sub-domain/set-login-info',
          loginInfo,
          { headers: { 'Content-Type': 'APPLICATION/JSON', 'auth-type': 'form' } });
        if(res.status == 200) {
          return "close";
        }
      } else {
        let res = await axios.post('api/sign-in', loginInfo, {headers: {'auth-type': 'form'}});
        console.log(res);
        let appInfo = res['data'];
        // console.log(appInfo);
        // console.log(JSON.stringify(appInfo));
        // localStorage.setItem('currentUser', JSON.stringify(appInfo));
        this.pianaStorageService.putObject('appInfo', appInfo);
        console.log(appInfo)
        console.log(this.pianaStorageService.getObject('appInfo'));
        // console.log(localStorage.getItem('currentUser'));
        return appInfo;
      }
    } catch (err) {
      // this.timeStamp = this.timeStamp + 1;
      throw err;
    }
  }

  changePassword(password, passwordRepeat) {
    return axios.post('api/change-password',
      {password: password, passwordRepeat: passwordRepeat},
      {headers: {'content-type': 'application/json'}});
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
