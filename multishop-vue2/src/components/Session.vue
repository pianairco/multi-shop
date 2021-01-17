<template lang="html">
  <div class="card">
    <div class="card-image">
      <figure class="image is-4by3" style="overflow: hidden;">
        <img v-if="activeId == 0" :src="unknownURL" class="eee">
        <img v-if="activeId != 0" :src="'/api/resources/serve/images/' + activeImage['imageSrc']" v-bind:style="{ transform: rotateVal }">
      </figure>
      <div class="columns is-mobile is-vcentered is-overlay is-multiline" style="margin: 0px;">
        <div class="column is-full" style="height: 20%;">
          <div v-if="activeId" class="columns" style="padding: 0px; margin: 0px;">
            <input type="file" id="file" ref="file" @change="handleFileUpload($event)"
                   class="is-white fa fa-angle-right" style="display: none" />
            <div v-if="appInfo && appInfo.isAdmin" class="column" style="padding: 0px; margin: 0px;">
              <span><i class="fa fa-image sample-item-overlay-button" v-on:click="selectImage" aria-hidden="true"></i></span>
              <span><i class="fa fa-trash sample-item-overlay-button" v-on:click="deleteClick" aria-hidden="true"></i></span>
              <span><i class="fa fa-share sample-item-overlay-button" v-on:click="rotateRightClick" aria-hidden="true"></i></span>
              <span><i class="fa fa-share fa-flip-horizontal sample-item-overlay-button" v-on:click="rotateLeftClick" aria-hidden="true"></i></span>
              <span><i class="fa fa-upload sample-item-overlay-button" v-on:click="rotateLeftClick" aria-hidden="true"></i></span>
            </div>
          </div>
        </div>
        <div class="column is-full is-info" style="height: 60%;">
          <div class="columns is-mobile is-vcentered is-multiline picture-box-center-control-container"
               v-if="images && images.length > 1" style="margin-top: auto; margin-bottom: auto;">
            <div class="column is-narrow">
              <button v-on:click="next" class="button is-white fa fa-angle-right" style="opacity: 0.4;"></button>
            </div>
            <div class="column">&nbsp;</div>
            <div class="column is-narrow">
              <button v-on:click="prev" class="button is-white is-transparent fa fa-angle-left" style="opacity: 0.4;"></button>
            </div>
          </div>
        </div>
        <div class="column is-full is-info " style="height: 20%;">
        </div>
      </div>
    </div>
    <div class="card-content">
      <div class="columns is-mobile is-multiline" style="margin: 0px;">
        <session-picture-upload ref="spuRef"
                                v-if="appInfo && appInfo.isAdmin"
                                v-on:add-session-image="addSessionImage"></session-picture-upload>
        <session-picture-select
          v-for="image in images"
          :session-image="image"
          v-bind:active-id="activeId"
          v-on:select-session-image="selectSessionImage"></session-picture-select>
      </div>
      <div class="columns is-mobile is-multiline" style="margin: 20px 0px;">
        <div v-if="appInfo && !appInfo.isAdmin" class="content" style="overflow-x: hidden; overflow-y: auto; height: 72px;">
          {{detail}}
        </div>
<!--        <textarea v-if="!detail" class="textarea" placeholder="توضیحات" v-model="detail" rows="16"></textarea>-->
        <textarea v-if="appInfo && appInfo.isAdmin" class="textarea" placeholder="توضیحات" v-model="detail" rows="16"></textarea>

        <button v-if="appInfo && appInfo.isAdmin" v-on:click="uploadDetail" class="button is-success is-small is-fullwidth" style="margin-top: 8px">Upload
          <i class="fa fa-upload" style="margin-right: 6px"></i>
        </button>

        <!--<div class="content" style="overflow-x: hidden; overflow-y: auto; height: 72px;">
          {{detail}}
        </div>-->
      </div>
    </div>
  </div>
</template>

<script lang="js">
  import SessionPictureUpload from './modules/sample-session/SessionPictureUpload'
  import SessionPictureSelect from './modules/sample-session/SessionPictureSelect'

  export default {
    name: 'Session',
    props: {
      id: 0
    },
    data: function () {
      return {
        idx: 0,
        item: {
          image: false,
        },
        detail: '',
        file: '',
        sharedState: this.$store.state,
        images: [],
        activeId: 0,
        rotate: 0,
        xxx: null,
        formName: String,
        propertyName: String,
        isUpload: true,
        isActive: false,
        sessionId: {
          type: Number
        },
        url: String,
        imageUploadGroup: 'insert-session-image',
        order: {
          type: String
        },
        editedImageSrc: {
          type: String
        },
        title: {
          type: String
        },
        unknownURL: '/cdn/img/no-image.png'
      }
    },
    components: {
      SessionPictureUpload,
      SessionPictureSelect
    },
    computed: {
      appInfo () {
        console.log(this.$store.state.appInfo);
        if(this.$store.state.appInfo)
          console.log('pictureUrl', this.$store.state.appInfo.pictureUrl)
        return this.$store.state.appInfo
      },
      activeImage: function () {
        for(const [i, img] of this.images.entries()) {
          if (img['id'] === this.activeId) {
            this.idx = i;
            return img;
          }
        }
        this.activeId = 0
      },
      rotateVal: function () {
        return 'rotate(' + this.rotate + 'deg)';
      }
    },
    watch: {
      $route: function (to, from){
        this.loadImages();
        this.loadDetail();
      },
      // waiterVal: function () {
      //   this.$store.getFromForm('waiter', 'wait');
      // },
      // imagesVal: function () {
      //   this.images = this.$store.getFromForm('session', 'images');
      // },
      // activeIdVal: function () {
      //   this.activeId = this.$store.getters.getFromForm('session', 'activeId');
      // }
    },
    methods: {
      uploadDetail: function () {
        this.$store.commit('addWaiterWait');
        this.$axios.post(this.remoteServer + '/api/ajax/serve', { sessionId: this.id, detail: this.detail },
                { headers: {'action': 'session', 'activity': 'updateSessionDetail' }})
                // { headers: headers })
                .then((res) => {
                  this.$store.commit('subWaiterWait');
                  console.log(res.data);
                  this.detail = res.data.detail;
                }).catch((e) => {
          this.$store.commit('subWaiterWait');
          console.log(e);
          this.$store.commit('subWaiterWait');
        });
      },
      reset: function () {
        this.item.image = false;
      },
      selectSessionImage: function (id) {
        console.log("id ", id)
        this.activeId = id;
      },
      // addSessionImage: function (image, rotate) {
      //   console.log("click")
      //   this.$emit("add-session-image", image, rotate);
      // },
      addSessionImage: function (image, rotate) {
        console.log("add new session image:", image, rotate);
        this.$store.commit('addWaiterWait');

        // this.$axios.post('/api/images/image-upload', formData, {
        this.$axios.post(this.remoteServer + '/api/ajax/serve', { sessionId: this.id, image: image, rotate: rotate, orders: this.images.length + 1 },
                {headers: {'action': 'session', 'activity': 'addSessionImage', 'group': 'insert-sample-session-image'}})
                // { headers: headers })
                .then((res) => {
          this.$store.commit('subWaiterWait');
          console.log(res.data);
          this.images.push(res.data);
          this.$refs.spuRef.reset();
          this.idx = res.data.orders - 1;
          this.activeId = this.images[this.idx].id;
          console.log(this.images);
          // this.$store.setToFormProperty('session', 'images', res['data']['id'], res['data']);
          // this.$store.setToForm('waiter', 'wait', 0)
        }).catch((e) => {
          this.$store.commit('subWaiterWait');
          console.log('FAILURE!!');
          console.log(e);
          this.$store.commit('subWaiterWait');
        });
      },
      deleteClick: function () {
        this.$emit("delete-session-image", this.activeId);
      },
      rotateLeftClick: function () {
        this.rotate -= 90;
        console.log("rotate left", this.rotate)
      },
      rotateRightClick: function () {
        this.rotate += 90;
        console.log("rotate right", this.rotate)
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
          this.$emit("replace-session-image", this.file, this.activeId);
        };
        reader.readAsDataURL(file);
      },
      next: function () {
        this.idx++;
        if (this.idx >= this.images.length)
          this.idx = 0;
        this.activeId = this.images[this.idx].id;
      },
      prev: function () {
        this.idx--;
        if (this.idx < 0)
          this.idx = this.images.length - 1;
        this.activeId = this.images[this.idx].id;
      },
      loadDetail: function () {
        this.$axios.post(this.remoteServer + '/api/ajax/serve?sessionId=' + this.id, {},
                { headers: { 'action': 'session', 'activity': 'detail' } })
                .then((response) => {
                  this.detail = response.data.detail;
                }).catch((err) => {
          console.error(err);
          self.isLoading = false;
          this.message = err
        });
      },
      loadImages: function () {
        console.log(this.id)
        this.idx = 0;
        this.activeId = 0;
        try {
          // this.$axios.get(this.remoteServer + '/api/sample/session/images?id=' + this.id, {headers: {}})
          this.$axios.post(this.remoteServer + '/api/ajax/serve?sessionId=' + this.id, {},
                  { headers: { 'action': 'session', 'activity': 'images' } })
            .then((response) => {
            this.images = response.data;
            if(this.images.length > 0)
              this.activeId = this.images[this.idx].id;
        }).catch((err) => {
            console.error(err);
            self.isLoading = false;
            this.message = err
        });
        } catch (e) {
          console.error(e);
        }
      }
    },
    mounted: function () {
    },
    beforeRouteUpdate: function (to, from, next) {
      next();
      // this.getContent(to.params.uid);
    },
    updated: function () {
      console.log("+")
      // this.loadImages();
    },
    created: function () {
      this.loadImages();
      this.loadDetail();
    }
  };
</script>

<style scoped lang="css">
  .sample-item-overlay-button {
    color: #c69500;
    cursor: pointer;
    padding: 4px;
  }
  .sample-item-overlay-button:hover {
    background-color: #c2e0f5;
    padding: 4px;
    border-radius: 3px;
  }
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

  .eee {
    color: blue;
  }
</style>
