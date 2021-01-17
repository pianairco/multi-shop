<template lang="html">

  <div class="container" id="bulma-shop-page">
    <div class="columns" >
      <div class="column is-full-mobile is-one-fifth-desktop">
        <shop-group></shop-group>
      </div>
      <div class="column is-full-mobile is-four-fifth-desktop">
        <div class="columns is-three-quarter-desktop is-mobile is-multiline" >
          <router-view/>
            <!--          <shop-vitrine></shop-vitrine>-->
        </div>
      </div>
    </div>
  </div>

</template>

<script lang="js">
  import ShopGroup from './modules/group/ShopGroup';

  // import ShopVitrine from "./ShopVitrine";

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
      // ShopVitrine,
      ShopGroup
    }
}


</script>

<style scoped lang="css">
  .shop {

  }
</style>
