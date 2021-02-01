import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '@/components/Home'
import Shop from '@/components/Shop'
import Samples from '@/components/Samples'
import Sample from '@/components/Sample'
import Session from '@/components/Session'
import ShopVitrine from "../components/ShopVitrine";
import HelloWorld from "../components/HelloWorld";

Vue.use(VueRouter)

export default new VueRouter({
  routes: [
    { path: '/', redirect: '/Home' },
    {
      path: '/HelloWorld',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/Home',
      name: 'Home',
      component: Home
    },
    {
      path: '/Shop',
      name: 'Shop',
      component: Shop,
      children: [
        {
          // UserProfile will be rendered inside User's <router-view>
          // when /user/:id/profile is matched
          path: 'vitrine/:routerKey',
          component: ShopVitrine
        }
      ]
    },
    {
      path: '/samples',
      name: 'Samples',
      component: Samples
    },
    {
      path: '/sample/:sampleId',
      name: 'Sample',
      component: Sample,
      props: true,
      children: [
        {
          path: 'session/:id',
          name: 'Session',
          component: Session,
          props: true
        }
      ]
    }
  ]
})
