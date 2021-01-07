<template lang="html">
  <div class="container" id="bulma-sample-search-page">
    <div class="columns is-mobile is-multiline">
      <div v-if="appInfo && appInfo.isAdmin" class="column is-full-mobile is-one-quarter-desktop">
        <pictorial-sample-item-creator v-on:add-item="addSample" :form-name="'uploader1'" :property-name="'image'">
        </pictorial-sample-item-creator>
      </div>
      <!--v-if="!appInfo || !appInfo.isAdmin"-->
      <div  v-for="d in samples" class="column is-full-mobile is-one-quarter-desktop">
        <pictorial-sample-item v-if="d['id'] != editedId"
                               :link="'/sample/' + d['id']"
                               v-on:edit-item="editItemClick"
                               v-on:delete-item="deleteItemClick"
                               :is-admin="appInfo && appInfo.isAdmin"
                               :id="d['id']"
                               :image="'api/resources/serve/images/' + d['imageSrc']"
                               :description="d['description']"
                               :title="d['title']"></pictorial-sample-item>
        <pictorial-sample-item-creator v-if="d['id'] == editedId"
                                       v-on:add-item="addSample"
                                       v-on:edit-item="editItem"
                                       :edited-item="d"
                                       :form-name="'uploader1'"
                                       :property-name="'image'">
        </pictorial-sample-item-creator>
      </div>
    </div>
  </div>
</template>

<script lang="js">
  import PictorialSampleItem from './modules/sample/PictorialSampleItem';
  import PictorialSampleItemCreator from './modules/sample/PictorialSampleItemCreator';

  export default  {
    name: 'Samples',
    data () {
      return {
        formName: 'uploader1',
        propertyName: 'propertyName1',
        addImage: [
          '/img/add-document.png'
        ],
        images: [
          '/img/480x480.png', '/img/480x480-2.png'
        ],
        samples: [],
        // sharedState: store.state,
        editedId: 0,
        message: ''
      }
    },
    methods: {
      addSample(form) {
        try {
          console.log(JSON.stringify(form));
          // this.$axios.post('/api/sample/add', form, {headers: {'file-group': 'sample'}})
          this.$axios.post('/api/ajax/serve', form,
                  {headers: {'action': 'sample', 'activity': 'addSample','group': 'sample'}})
            .then((response) => {
              console.log(response.data);
              this.samples.push(response.data);
              console.log(this.samples);
            }).catch((err) => {
              this.message = err;
            });
        } catch (e) {
          console.log(e)
        }
      },
      editItem(form) {
        console.log(JSON.stringify(form));
        this.$axios.post('/api/sample/edit', form, { headers: { 'file-group': 'sample' } })
          .then((response) => {
            console.log(response.data);
            let index = this.samples.findIndex(item => item.id === response.data.id);
            console.log(index);
            this.samples.splice(index, 1, response.data);
            this.editedId = 0;
          })
          .catch((err) => { this.message = err; });
      },
      editItemClick(id) {
        console.log(id);
        this.editedId = id;
      },
      deleteItemClick(id) {
        console.log(id);
        this.$axios.post('/api/sample/delete', { "id": id }, { headers: { 'file-group': 'sample' } })
          .then((response) => {
            console.log(response.data);
            let index = this.samples.findIndex(item => item.id === id);
            console.log(index);
            console.log(this.samples);
            this.samples.splice(index, 1);
          })
          .catch((err) => { this.message = err; });
      }
    },
    components: {
      PictorialSampleItem,
      PictorialSampleItemCreator
    },
    created () {
      try {
        // this.$axios.get(this.remoteServer + '/api/samples', {headers: {}})
        this.$axios.post(this.remoteServer + '/api/ajax/serve', {},
                { headers: { 'action': 'sample', activity: 'samples' } })
          .then((response) => {
          console.log(this.samples);
        console.log(response.data);
        // this.samples = response.data;
        response.data.forEach(a => this.samples.push(a));
        // this.samples.push(response.data);
        console.log(this.samples);
        // this.sessions.push(response.data);
        // this.$store.commit('setAppInfo', response.data);
      }).catch((err) => {
          self.isLoading = false;
        this.message = err
      });
      } catch (e) {
        console.error(e);
      }
    },
    computed: {
      appInfo () {
        console.log(this.$store.state.appInfo);
        if(this.$store.state.appInfo)
          console.log(this.$store.state.appInfo.pictureUrl)
        return this.$store.state.appInfo
      }
    }
  };
</script>

<style scoped lang="css">
#bulma-sample-search-page {
  padding-top: 20px;
  padding-bottom: 20px;
}
</style>
