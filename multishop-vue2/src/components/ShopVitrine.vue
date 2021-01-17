<template lang="html">

  <div class="container" id="bulma-shop-page">
    <div class="columns is-multiline" >
      <div class="column is-full-mobile is-one-quarter-desktop" v-for="productItem in productItems">
        <pictorial-shop-item :images="[require('../assets/img/480x480.png'), require('../assets/img/480x480-2.png')]"
                             :title="productItem.name"
                             :uuid="productItem.uuid"
                             :description="'خرید از فروشنده محصولات سالم و بهداشتی'">
        </pictorial-shop-item>
     </div>
    </div>
  </div>

</template>

<script lang="js">
  import PictorialShopItem from './modules/shop/PictorialShopItem';

  export default  {
    name: 'ShopVitrine',
    props: [

    ],
    mounted () {

    },
    data () {
      return {
        productItems: []
        // image:  require('../assets/img/480x480.png'),
        // images: [
        //   require('../assets/img/480x480.png'),
        //   require('../assets/img/480x480-2.png')
        // ]
      }
    },
    methods: {

    },
    computed: {

    },
    components: {
      PictorialShopItem
    },
    async created() {
      this.productItems = await this.$axios.get(this.remoteServer + '/api/shop/product/items/' + this.$route.params.routerKey,
              { }).then((res) => {
        let productItems = res['data'];
        return productItems
      });
    },
    watch: {
      async $route(to, from) {
        // react to route changes...
        console.log(from)
        console.log(to)
        console.log(to.params.routerKey)
        this.productItems = await this.$axios.get(this.remoteServer + '/api/shop/product/items/' + to.params.routerKey,
                { }).then((res) => {
          let productItems = res['data'];
          return productItems
        });
      }
    }
}


</script>

<style scoped lang="css">
  .shop {

  }
</style>
