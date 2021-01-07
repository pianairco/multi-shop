<template lang="html">
  <div v-if="waitVal" v-bind:class="{'loader-container': waitVal}">
    <div v-bind:class="{'loader': waitVal}"></div>
  </div>
</template>

<script lang="js">
    export default {
      name: 'Spinner',
      props: {},
      methods: {

      },
      computed: {
        waitVal: function () {
          console.log("dddddddd")
          console.log(this.$store.getters.getWaiterVal)
          // console.log(this.$store.getFromForm('waiter', 'wait'))
          return this.$store.getters.getWaiterVal;
          // return this.$store.getFromForm('waiter', 'wait');
        }
      },
      watch: {
        waitVal: function () {
          console.log("ccccccccc")
          this.$store.getters.getWaiterVal;
          // this.$store.getFromForm('waiter', 'wait');
        }
      }
    };
</script>

<style scoped lang="css">
  .loader-container {
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    z-index: 100000000;
  }

  .loader {
    position: fixed;
    left: 50%;
    top: 50%;
    border: 16px solid #f3f3f3; /* Light grey */
    border-top: 16px solid #3498db; /* Blue */
    border-radius: 50%;
    width: 120px;
    height: 120px;
    z-index: 100000000;
    animation: spin 2s linear infinite;
  }

  @keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
  }
</style>
