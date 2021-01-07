<template lang="html">

  <section class="hero is-fullheight" style="background-color: #b9dbff;">
    <!-- Hero head: will stick at the top -->
    <!-- Hero content: will be in the middle -->

    <div id="my-back" class="hero-body">
      <div class="container has-text-centered">
        <h1 class="title" style="font-family: IranNastaliq; font-size: 28px;">
          واویشکا شاپ
        </h1>
        <h2 class="subtitle" style="font-family: IranNastaliq; font-size: 22px;">
          آموزش آشپزی همراه با تامین مواد غذایی از دل طبیعت
        </h2>
      </div>
    </div>
  </section>

</template>

<script lang="js">

  export default  {
    name: 'AppHeader',
    props: [],
    data() {
      return {
      }
    },
    mounted () {

    },
    data () {
      return {

      }
    },
    methods: {

    },
    computed: {

    }
}


</script>

<style scoped lang="css">
#my-back {
  background-size: cover;
  backgroundRepeat: 'no-repeat';
  background-image: url('../assets/img/optimised.svg');
}
</style>
