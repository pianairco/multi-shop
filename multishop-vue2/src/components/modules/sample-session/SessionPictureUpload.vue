<template lang="html">
  <div class="card ">
    <div class="card-image">
      <div class="card-image">
        <figure class="image is-64x64" style="overflow: hidden;">
          <img v-if="!item.image" :src="unknownURL" v-on:click="selectImage" class="picture-upload-plus"/>
          <img v-if="item.image" :src="item.image" style="opacity: 0.5; height: 100%;" v-bind:style="{ transform: rotateVal }"/>
          <input type="file" id="file" ref="file" @change="handleFileUpload($event)"
                 class="is-white fa fa-angle-right" style="display: none" />
        </figure>
      </div>
      <div v-if="item.image" class="columns is-overlay is-vcentered is-multiline is-mobile" style="margin: 0px;">
        <div class="column is-clickable" style="margin: 0px; padding: 0px; ">
                <span class="image is-64x32" style="float: right;">
                    <span><i class="fa fa-angle-right sample-item-overlay-button" v-on:click="rotateClick" aria-hidden="true"></i></span>
                </span>
        </div>
        <div class="column is-clickable" style="margin: 0px; padding: 0px; text-align: center;">
                <span class="image is-64x32">
                    <span><i class="fa fa-upload sample-item-overlay-button" v-on:click="uploadClick" aria-hidden="true"></i></span>
                </span>
        </div>
        <div class="column is-clickable" style="margin: 0px; padding: 0px;">
                <span class="image is-64x32" style="float: left;">
                    <span><i class="fa fa-trash sample-item-overlay-button" v-on:click="trashClick" aria-hidden="true"></i></span>
                </span>
        </div>
      </div>
    </div>
  </div>
</template>


<script lang="js">
    export default {
      name: 'SessionPictureUpload',
      props: {
        formName: String,
        propertyName: String,
        unknownURL: {
          default: 'cdn/img/plus.png',
          type: String
        }
      },
      data: function () {
        return {
          item: {
            image: false,
          },
          sharedState: this.$store.state,
          rotate: 0
        }
      },
      components: {},
      computed: {
        rotateVal: function () {
          return 'rotate(' + this.rotate + 'deg)';
        }
      },
      mounted: function () {
      },
      methods: {
        reset: function () {
          this.item.image = false;
        },
        deleteImage: function () {
          this.item.image = false;
        },
        rotateClick: function () {
          this.rotate += 90;
        },
        uploadClick: function () {
          console.log("click")
          this.$emit("add-session-image", this.item.image, this.rotate);
        },
        trashClick: function () {
          this.item.image = false;
        },
        selectImage: function () {
          this.$refs.file.click();
        },
        handleFileUpload: function (event) {
          // console.log(event.target.files[0]);
          // console.log(this.$refs.file.files[0]);
          this.file = this.$refs.file.files[0];
          this.createImage(this.file);
        },
        createImage: function (file) {
          var image = new Image();
          var reader = new FileReader();

          reader.onload = (e) => {
            this.item.image = e.target.result;
            // this.submitFile();
            // if(this.formName) {
            // store.setToForm(this.formName, this.propertyName, this.item.image);
            // }
          };
          reader.readAsDataURL(file);
        },
        next: function () {
          this.idx++;
          if (this.idx >= this.images.length)
            this.idx = 0;
        },
        prev: function () {
          this.idx--;
          if (this.idx < 0)
            this.idx = this.images.length - 1;
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

    .is-clickable {
        cursor: pointer;
    }
</style>
