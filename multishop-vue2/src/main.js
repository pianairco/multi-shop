// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
// npm install vuex --save
import Vuex from 'vuex'
import { mapState } from 'vuex'
import App from './App'
import router from './router'
import axios from 'axios'
import Buefy from 'buefy'
import 'buefy/dist/buefy.css'
import GAuth from 'vue-google-oauth2'

const gauthOption = {
  clientId: '290205995528-g4ieuas6ffi6vk19dftm312uqlfma2er.apps.googleusercontent.com',
  // clientId: '290205995528-o268sq4cttuds0f44jnre5sb6rudfsb5.apps.googleusercontent.com',
  scope: 'profile email',
  prompt: 'select_account'
}
Vue.use(GAuth, gauthOption)

Vue.use(Buefy)

Vue.prototype.$axios = axios

Vue.config.productionTip = false

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    appResource: {

    },
    activeSessionImage: 0,
    appInfo: null,
    forms: {waiter: {wait: 0}}
  },
  getters: {
    getWaiterVal: function (state) {
      return state.forms.waiter.wait
    },
    getActiveIdVal: function (state) {
      return state.activeSessionImage
    }
  },
  mutations: {
    setActiveSessionImage: function (state, id) {
      state.activeSessionImage = id;
    },
    addWaiterWait: function (state) {
      state.forms.waiter.wait += 1;
    },
    subWaiterWait: function (state) {
      state.forms.waiter.wait -= 1;
      if(state.forms.waiter.wait < 0)
        state.forms.waiter.wait = 0;
    },
    setAppInfo: function (state, appInfo) {
      state.appInfo = appInfo
    },
    setToForm: function(state, form) {
      console.log(JSON.stringify(form));
      console.log(JSON.stringify(state));
      state.forms[form.formName] = form.model;
      // state.forms[form.formName][form.propertyName] = form.property;
      console.log(JSON.stringify(state));
      // state.forms[formName] = JSON.parse(JSON.stringify(this.state.forms[formName]));
    },
    getFromForm: function(formName, propertyName) {
      return this.state.forms[formName][propertyName];
    },
    setToFormProperty: function(formName, propertyName, property, value) {
      this.state.forms[formName][propertyName][property] = value;
      this.state.forms[formName] =  JSON.parse(JSON.stringify(this.state.forms[formName]));
    },
    removeFromFormProperty: function(formName, propertyName, property) {
      delete this.state.forms[formName][propertyName][property];
      this.state.forms[formName] =  JSON.parse(JSON.stringify(this.state.forms[formName]));
    },
    replaceToFormProperty: function(formName, propertyName, property, field, value) {
      this.state.forms[formName][propertyName][property][field] = value;
      this.state.forms[formName] =  JSON.parse(JSON.stringify(this.state.forms[formName]));
    }
  }
})

Vue.mixin({
  data: function() {
    return {
      get remoteServer() {
        // return "https://vavishka.herokuapp.com/";
        return "";
        // return "http://localhost";
      }
    }
  }
});

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
});
