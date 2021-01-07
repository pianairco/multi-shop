<template lang="html">

  <div class="container" id="bulma-shop-page">
    <div class="columns is-mobile is-multiline">
      <div class="column is-full-mobile is-one-quarter-desktop">
        <pictorial-shop-item :images="[require('../assets/img/480x480.png'), require('../assets/img/480x480-2.png')]"
                             :title="'کالا'"
                             :description="'خرید از فروشنده محصولات سالم و بهداشتی'">
        </pictorial-shop-item>
      </div>
      <div class="column is-full-mobile is-one-quarter-desktop">
        <pictorial-shop-item :images="[require('../assets/img/480x480.png'), require('../assets/img/480x480-2.png')]"
                             :title="'کالا'"></pictorial-shop-item>
      </div>
      <div class="column is-full-mobile is-one-quarter-desktop">
        <pictorial-shop-item :images="[require('../assets/img/480x480.png'), require('../assets/img/480x480-2.png')]"
                             :title="'کالا'"></pictorial-shop-item>
      </div>
      <div class="column is-full-mobile is-one-quarter-desktop">
        <pictorial-shop-item :images="[require('../assets/img/480x480.png')]"
                             :title="'کالا'"></pictorial-shop-item>
      </div>
      <div class="column is-full-mobile is-one-quarter-desktop">
        <pictorial-shop-item :images="[require('../assets/img/480x480.png'), require('../assets/img/480x480-2.png')]"
                             :title="'کالا'"></pictorial-shop-item>
      </div>
      <div class="column is-full-mobile is-one-quarter-desktop">
        <pictorial-shop-item :images="[require('../assets/img/480x480.png'), require('../assets/img/480x480-2.png')]"
                             :title="'کالا'"></pictorial-shop-item>
      </div>
      <div class="column is-full-mobile is-one-quarter-desktop">
        <pictorial-shop-item :images="[require('../assets/img/480x480.png'), require('../assets/img/480x480-2.png')]"
                             :title="'کالا'"></pictorial-shop-item>
      </div>
      <div class="column is-full-mobile is-one-quarter-desktop">
        <pictorial-shop-item :images="[require('../assets/img/480x480.png'), require('../assets/img/480x480-2.png')]"
                             :title="'کالا'"></pictorial-shop-item>
      </div>
    </div>
  </div>

</template>

<script lang="js">
  import PictorialShopItem from './modules/shop/PictorialShopItem';

  export default  {
    name: 'Shop',
    props: [],
    mounted () {

    },
    data () {
      return {
        image:  require('../assets/img/480x480.png'),
        images: [
          require('../assets/img/480x480.png'),
          require('../assets/img/480x480-2.png')
        ]
      }
    },
    methods: {

    },
    computed: {

    },
    components: {
      PictorialShopItem
    }
}


</script>

<style scoped lang="css">
  .shop {

  }
</style>
