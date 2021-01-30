<template lang="html">

  <b-navbar :fixed-top="true">
    <template slot="brand">
      <b-navbar-item tag="router-link" :to="{ path: '/' }">
        <img
          src="../assets/img/vavishka.png"
          alt="Lightweight UI components for Vue.js based on Bulma"
        >
      </b-navbar-item>
    </template>
    <template slot="start">
      <router-link to="/home" class="navbar-item">صفحه اصلی</router-link>
      <router-link to="/samples" class="navbar-item">آموزش ها</router-link>
      <router-link to="/shop" class="navbar-item">فروشگاه</router-link>
<!--      <router-link to="/gallery" class="navbar-item">گالری تصاویر</router-link>-->
<!--      <b-navbar-dropdown label="Info">-->
<!--        <b-navbar-item href="#">-->
<!--          About-->
<!--        </b-navbar-item>-->
<!--        <b-navbar-item href="#">-->
<!--          Contact-->
<!--        </b-navbar-item>-->
<!--      </b-navbar-dropdown>-->
    </template>

    <template slot="end">
      <b-navbar-item tag="div">
        <div class="buttons">
<!--          <b-button size="" class="is-success" icon-pack="fab"
                    v-on:click="handleClickSignIn"
                    v-if="!appInfo || !appInfo.isLoggedIn"
                    icon-left="google">
            Sign in with Google
          </b-button>-->

          <button v-if="!appInfo || !appInfo.isLoggedIn" class="button is-danger is-outlined" v-on:click="handleClickSignIn">
            <span>ورود با گوگل</span>
<!--            <span>Sign in with Google</span>-->
            <span class="icon is-small">
              <i class="fab fa-google"></i>
            </span>
          </button>

          <button v-if="appInfo && appInfo.isLoggedIn" class="button is-danger is-outlined" v-on:click="handleClickSignOut">
            <span style="padding-left: 4px;">
              <figure class="image is-24x24">
                <img class="is-rounded" v-bind:src="appInfo.pictureUrl">
              </figure>
            </span>
            <span>{{appInfo.username}}</span>
            <span class="icon is-small">
              <i class="fas fa-sign-out-alt"></i>
            </span>
          </button>
<!--          <b-button size="" class="is-danger" icon-pack="fas"
                    v-on:click="handleClickSignOut"
                    v-if="appInfo && appInfo.isLoggedIn"
                    icon-left="sign-out-alt">
            {{appInfo.username}}
            <img v-if="appInfo && appInfo.isLoggedIn" v-bind:src="appInfo.pictureUrl">
          </b-button>
          <b-image
            style="width: 25px; margin-bottom: 8px; padding-right:3px;"
            v-if="appInfo && appInfo.isLoggedIn"
            v-bind:src="appInfo.pictureUrl"
            :rounded="rounded"
          ></b-image>-->
        </div>
      </b-navbar-item>
      <b-loading :is-full-page="isFullPage" v-model="isLoading" :can-cancel="false">
        <b-icon
          pack="fas"
          icon="sync-alt"
          size="is-large"
          custom-class="fa-spin">
        </b-icon>
      </b-loading>
    </template>
  </b-navbar>

</template>

<script lang="js">
  import Vue from 'vue'

  export default  {
    name: 'Topbar',
    props: [],
    mounted () {

    },
    data () {
      return {
        pictureUrl: null,
        rounded: true,
        isLoading: false,
        isFullPage: true,
        isSignIn: false
      }
    },
    methods: {
      handleClickLogin() {
        console.log(Vue.config.devtools);
        this.isLoading = true;
        this.$gAuth.getAuthCode().then((authCode) => {
          //on success
          this.isLoading = false;
          console.log("authCode", authCode);
        })
      .catch((error) => {
          //on fail do something
          this.isLoading = false;
          console.log("error", error);
        });
      },
      async handleClickSignIn() {
        try {
          this.isLoading = true;
          let accessToken = null;
          if(!Vue.config.devtools) {
            const googleUser = await this.$gAuth.signIn();
            if (!googleUser) {
              return null;
            }
            accessToken = this.$gAuth.GoogleAuth.currentUser.get().getAuthResponse()['access_token'];
            this.isSignIn = this.$gAuth.isAuthorized;
            console.log("accessToken", accessToken);
          } else {
            accessToken = "1234"
            console.log("accessToken", "is debug mode");
          }

          // console.log("googleUser", googleUser);
          // console.log("getId", googleUser.getId());
          // console.log("getBasicProfile", googleUser.getBasicProfile());
          // console.log("getAuthResponse", googleUser.getAuthResponse());
          // console.log("getAuthResponse", this.$gAuth.GoogleAuth.currentUser.get().getAuthResponse());

          let self = this;
          this.$axios.post(this.remoteServer + '/api/sign-in',
              { 'accessToken': accessToken },
              { headers: { 'Content-Type': 'APPLICATION/JSON' } })
            .then((response) => {
              this.isLoading = false;
              console.log(response.data);
              // this.sessions.push(response.data);
              this.pictureUrl = response.data['pictureUrl'];
              this.$store.commit('setAppInfo', response.data);
              console.log(this.pictureUrl)
            }).catch((err) => {
              self.isLoading = false;
              this.message = err
            });
        } catch (error) {
          this.isLoading = false;
          //on fail do something
          console.error(error);
        }
      },
      async handleClickSignOut() {
    try {
      this.isLoading = true;
      await this.$gAuth.signOut();
      let isSignIn = this.$gAuth.isAuthorized;
      if (isSignIn) {
        this.isLoading = false;
      } else {
        let self = this;
        this.$axios.post(this.remoteServer + '/api/sign-out', {}, { headers: { 'Content-Type': 'APPLICATION/JSON' } })
          .then((response) => {
          this.isLoading = false;
        console.log(response.data);
        // this.sessions.push(response.data);
        this.$store.commit('setAppInfo', response.data);
      }).catch((err) => {
          self.isLoading = false;
        this.message = err
      });
      }
    } catch (error) {
      this.isLoading = false;
      //on fail do something
      console.error(error);
    }
      },
      handleClickDisconnect() {
        window.location.href = `https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=${window.location.href}`;
      },
    },
    created() {
      let that = this;
      let checkGauthLoad = setInterval(function () {
        that.isInit = that.$gAuth.isInit;
        that.isSignIn = that.$gAuth.isAuthorized;
        if (that.isInit) clearInterval(checkGauthLoad);
      }, 1000);
    },
    computed: {
      appInfo () {
        console.log(this.$store.state.appInfo);
        if(this.$store.state.appInfo)
          console.log(this.$store.state.appInfo.pictureUrl)
        return this.$store.state.appInfo
      }
    }
}


</script>

<style lang="css">
.navbar-burger {
  margin-left: unset;
}

.navbar-start {
  margin-right: unset;
}

.navbar-end {
  margin-left: unset;
}

.router-link-active {
  color: #ff7749;
}

.router-link-active:focus {
  background-color: inherit !important;
  color: #ff7749 !important;
}

.router-link-active:hover {
  background-color: #fafafa !important;
  color: #ff7749 !important;
}
</style>
