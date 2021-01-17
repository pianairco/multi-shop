<template lang="html">
  <div class="container" id="bulma-sample-page">
<!--    <button v-on:click="testAdd">ew</button>-->
    <spinner></spinner>
    <div class="columns is-mobile is-multiline">
      <div class="column is-full-mobile is-one-quarter-desktop">
        <pictorial-menu-item-creator
                v-if="appInfo && appInfo.isAdmin"
                :sampleId="sampleId" v-on:add-item="addSession" :form-name="'uploader1'" :icon-property-name="'icon'">
        </pictorial-menu-item-creator>
        <aside class="menu">
          <!--style=" overflow-y: auto; display: flex; flex-direction: column; max-height: 800px;"-->
          <ul class="menu-list" >
          <li v-for="session in sessions">
            <pictorial-menu-item
              :sample-id="sampleId"
              v-on:session-selected="sessionSelected"
              v-if="session['id'] != editedId"
              :active-id="activeId"
              :id="session['id']"
              :image="'/api/resources/serve/images/' + session['iconSrc']"
              :description="session['description']"
              :title="session['title']"></pictorial-menu-item>
          </li>
          </ul>
        </aside>
      </div>
      <div class="column is-full-mobile is-three-quarters-desktop" id="dddd">
        <router-view/>
<!--        <sample-session-picture-manager-->
<!--          v-on:delete-session-image="deleteSessionImage"-->
<!--          v-on:replace-session-image="replaceSessionImage"-->
<!--          v-on:add-session-image="addSessionImage"-->
<!--          v-on:select-session-image="selectSessionImage"-->
<!--          v-if="activeId"></sample-session-picture-manager>-->
      </div>
    </div>
  </div>
</template>

<script lang="js">
  import Spinner from './modules/spinner';
  import PictorialMenuItem from './modules/sample/PictorialMenuItem';
  import PictorialMenuItemCreator from './modules/sample/PictorialMenuItemCreator';
  import SampleSessionPictureManager from './modules/sample-session/SampleSessionPictureManager';

  export default {
    name: 'Sample',
    props: ['sampleId'],
    data: function () {
      return {
        sample: {},
        sessionMap: {},
        sessionImageMap: {},
        sessions: [],
        editedId: 0,
        activeId: 0,
        imageUploadGroup: 'insert-sample-session-image',
        imageUploadUrl: '/api/images/image-upload',
      }
    },
    methods: {
      testAdd: function () {
        console.log("111111")
        this.$store.commit('addWaiterWait');
        this.$store.commit('addWaiterWait');
        // this.$store.setToForm('waiter', 'wait', 1)
        setTimeout(() => {
          this.$store.commit('subWaiterWait');
          setTimeout(() => this.$store.commit('subWaiterWait'), 2000);
          }, 2000
        );
        // console.log(this.$store.state)
      },
      addSession: function (form) {
        console.log(form)
        // form['samples_id'] = this.sample.id;
        form['orders'] = this.sessions.length + 1;
        // this.$axios.post('/api/sample/session/add', form, {headers: {'file-group': 'session'}})
        this.$axios.post(this.remoteServer + '/api/ajax/serve', form,
                { headers: { 'action': 'session', 'activity': 'addSession','group': 'session' } })
                // {headers: {'file-group': 'session'}})
          .then((response) => {
            console.log(response.data);
            this.sessions.push(response.data);
          })
          .catch((err) => {
            this.message = err;
          });
      },
      sessionSelected: function (id) {
        console.log("session selected!", id);
        this.activeId = id;
        this.$axios.post('/api/sample/session/images', {"id": this.activeId}, {headers: {'file-group': 'sample-session'}})
          .then((response) => {
            console.log(response.data);
            console.log(this.$store.getFromForm('session', 'images'))
            // if(!this.$store.getFromForm('session', 'images'))
            this.$store.setToForm('session', 'images', response.data);
            console.log(this.$store.getFromForm('session', 'images'))
            this.$store.setToForm('session', 'activeId', 0);
            // let obj = Object.assign({}, this.sharedState.forms['sessoin']);
            // obj['images'] = response.data;
            // this.$store.State.forms['session'] = obj;
            //this.$store.setToForm("sessoin", "images", response.data);
            this.sessionImageMap = response.data;
          })
          .catch((err) => {
            console.log(err);
          });
      },
      selectSessionImage: function (id) {
        console.log(id)
        this.$store.setToForm('session', 'activeId', id);
      },
      replaceSessionImage: function (image, imageId) {
        console.log("replace new session image:", image, imageId);
        let formData = new FormData();
        formData.append('file', image);
        let headers = {
          'image_upload_group': 'replace-sample-session-image',
          'id': imageId,
          'Content-Type': 'multipart/form-data'
        };

        this.$axios.post(this.imageUploadUrl, formData, {
          headers: headers
        }).then((res) => {
          this.$store.replaceToFormProperty('session', 'images', res['data']['id'], 'imageSrc', res['data']['imageSrc']);
        }).catch((e) => {
          console.log('FAILURE!!');
          console.log(e);
        });
      },
      deleteSessionImage: function (imageId) {
        console.log("delete new session image:", imageId);
        let headers = {
          'business': 'delete-session-image',
          'Content-Type': 'application/json'
        };

        this.$axios.post('/api/sample/session/image/delete', {'id': imageId}, {
          headers: headers
        }).then((res) => {
          this.$store.removeFromFormProperty('session', 'images', res['data']['id']);
          this.$store.setToForm('session', 'activeId', 0);
        }).catch((e) => {
          console.log('FAILURE!!');
          console.log(e);
        });
      }
    },
    computed: {
      appInfo () {
        console.log(this.$store.state.appInfo);
        if(this.$store.state.appInfo)
          console.log('pictureUrl', this.$store.state.appInfo.pictureUrl)
        return this.$store.state.appInfo
      }
    },
    mounted: function () {
      // console.log(Object.keys(this.sessionImages).length);
      // console.log(this.sessionImages);
      // console.log(this.sessionMap);
    },
    components: {
      Spinner,
      PictorialMenuItem,
      PictorialMenuItemCreator,
      SampleSessionPictureManager
    },
    created: function () {
      try {
        // this.$axios.get(this.remoteServer + '/api/sample/session/' + this.sampleId, {headers: {}})
        this.$axios.post(this.remoteServer + '/api/ajax/serve', { sampleId: this.sampleId },
                { headers: { 'action': 'session', activity: 'sessions' } })
                // { headers: { 'action': 'sample', activity: 'sampleById' } })
          .then((response) => {
            console.log("---------------")
            console.log(this.sessions);
            console.log(response.data);
            // this.samples = response.data;
            response.data.forEach(a => this.sessions.push(a));
            console.log('33333333333');
            console.log(this.$router.history.current.path)
        let newPath = '/sample/' + this.sampleId + '/session/' + this.sessions[0].id;
        if(this.$router.history.current.path !== newPath)
        this.$router.push({ path: newPath })
            console.log('44444444444');
            // this.samples.push(response.data);
            console.log(this.sessions);
            // this.sessions.push(response.data);
            // this.$store.commit('setAppInfo', response.data);
          }).catch((err) => {
          self.isLoading = false;
          this.message = err
          console.log(err)
        });
      } catch (e) {
        console.error(e);
      }
    }
  };
</script>

<style scoped lang="css">
  #bulma-sample-page {
    padding-top: 20px;
    padding-bottom: 20px;
  }

  aside.menu {
    padding-top: 3rem;
  }

  aside.menu .menu-list {
    line-height: 1.5;
  }

  aside.menu .menu-label {
    padding-left: 10px;
    font-weight: 700;
  }

  .menu-list li{
    padding: 5px;
  }
</style>
