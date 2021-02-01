<template>
  <div class="container" id="bulma-sample-page">
    <!--    <button v-on:click="testAdd">ew</button>-->

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
    </div>
  </div>
</template>

<script>
import Topbar from "./Topbar";
export default {
  name: 'HelloWorld',
  components: {Topbar},
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
