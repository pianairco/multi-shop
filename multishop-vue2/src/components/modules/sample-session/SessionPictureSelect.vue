<template lang="html">
  <div class="card picture-upload-plus">
    <div class="card-image">
      <div class="card-image">
        <figure class="image is-64x64" :style="[activeId == sessionImage['id'] ? {'background-color': 'red'} : {'background-color': '#FFF'}]">
          <img :src="'api/resources/serve/images/' + sessionImage['imageSrc']"/>
        </figure>
      </div>
      <div class="columns is-overlay is-vcentered is-multiline is-mobile" style="margin: 0px;">
        <div v-if="activeId != sessionImage['id']" class="column is-full" style="margin: 0px; padding: 0px;">
                <span class="image is-64x64">
                    <img src="cdn/img/select.png" style="opacity: 0.08;" v-on:click="selectImage">
                </span>
        </div>
        <div v-if="activeId == sessionImage['id']" class="column is-full" style="margin: 0px; padding: 0px;">
                <span class="image is-64x32">
                    <img src="cdn/img/edit.png" style="opacity: 0.4;" v-on:click="selectImage">
                </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="js">
    export default {
      name: 'SessionPictureSelect',
      props: {
        formName: String,
        propertyName: String,
        sessionImage: {},
        activeId: 0,
        unknownURL: {
          default: '/img/plus.png',
          type: String
        }
      },
      data: function () {
        return {
          item: {
            image: false,
          },
          sharedState: this.$store.state
        }
      },
      components: {},
      mounted: function () {
      },
      methods: {
        reset: function () {
        },
        deleteImage: function () {
        },
        selectImage: function () {
          console.log("---------00000");
          // this.$store.commit('setActiveSessionImage', this.sessionImage['id']);
          this.$emit("select-session-image", this.sessionImage['id']);
        }
      }
    };
</script>

<style scoped lang="css">
  .picture-upload-center-control-container {
    margin-top: 0px;
    margin-bottom: 0px;
    height: 100%;
  }
  .picture-upload-plus {
    cursor: pointer;
    color: green;
    opacity: 0.8;
  }
  .picture-upload-plus:hover {
    background-color: #f1eaff;
    padding: 4px;
    border-radius: 6px;
    cursor: pointer !important;
  }
</style>
