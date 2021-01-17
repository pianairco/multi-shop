<template>
  <div id="app" >
    <topbar></topbar>
    <app-header v-if="isHome"></app-header>
    <router-view/>
    <app-footer></app-footer>
  </div>
</template>

<script>
  import Topbar from './components/Topbar';
  import AppHeader from './components/Header';
  import AppFooter from './components/Footer';

  export default {
    name: 'App',
    async created() {
      // async beforeCreate () {
      console.log('Nothing gets called before me!')
      console.log(this.remoteServer);
      // let appInfo = await this.$axios.get('http://localhost' + '/api/app-info').then((res) => {
      await this.$axios.post(this.remoteServer + '/api/ajax/serve', {},
              { headers: { 'action': 'auth', activity: 'appInfo' } }).then((res) => {
        let appInfo = res['data'];
        console.log('appInfo =>', appInfo)
        this.$store.commit('setAppInfo', appInfo)
        return appInfo
      });
      await this.$axios.get(this.remoteServer + '/api/shop/group/items',
              { }).then((res) => {
        let groupItems = res['data'];
        console.log('appInfo =>', groupItems)
        this.$store.commit('setGroupItems', groupItems)
        return groupItems
      });
      console.log("-----------------")
    },
    async mounted () {

    },
    computed: {
      isHome() {
        return this.$route.name === 'Home'
      }
    },
    components: {
      'topbar': Topbar,
      'app-header': AppHeader,
      'app-footer': AppFooter
    }
  }
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
    margin-top: 12px;
    margin-bottom: 12px;
  }
</style>
