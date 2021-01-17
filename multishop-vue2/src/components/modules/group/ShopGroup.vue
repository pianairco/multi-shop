<template lang="html">
  <div class="columns is-mobile is-multiline">
    <div class="column is-full is-mobile">
      <section class="hero medium is-dark">
        <div class="hero-body">
          <div class="container">
            <h1 class="title">
              منو
            </h1>
          </div>
        </div>
      </section>
    </div>
    <div class="column is-full is-mobile">
      <ul class="menu-list">
        <li v-for="groupItem in groupItems">
          <router-link
                  v-bind:to="'/shop/vitrine/' + groupItem.routerKey" class="navbar-item">{{groupItem.name}}</router-link>
        </li>
      </ul>
    </div>
  </div>
</template>

<script >
  export default {
    name: 'ShopGroup',
    props: {
    },
    data: function () {
      return {
      }
    },
    components: {
    },
    methods: {
      sessionSelected: function () {
        console.log("session selected!", this.id);
        this.$emit("session-selected", this.id);
      }
    },
    created() {
    },
    computed: {
      groupItems () {
        console.log(this.$store.state.groupItems);
        return this.$store.state.groupItems
      }
    }
  };
</script>

<style scoped lang="css">
  section {
    height: 56px;
  }

  .hero-body {
    padding: 10px 10px;
  }
    .media.active {
        border: 2px solid black;
        border-radius: 6px;
        padding: 12px;
        background-color: aliceblue;
    }

    button.icon {
        background-color: inherit;
        border: none;
    }

    button.icon:hover {
        background-color: #cfd7d4;
        border: 1px solid black;
        border-radius: 3px;
    }

    ::-webkit-scrollbar {
        width: 12px;
    }

    ::-webkit-scrollbar-track {
        -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
        border-radius: 10px;
    }

    ::-webkit-scrollbar-thumb {
        border-radius: 10px;
        -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.5);
    }

    .media-left img {
      border-radius: 50%;
    }

    .media-content p {
      font-size: 14px;
      line-height: 2.3;
      font-weight: 700;
      color: #8F99A3;
    }

    article.post {
      margin: 1rem;
      padding-bottom: 1rem;
      border-bottom: 1px solid #E6EAEE;
    }

    article.post:last-child {
      padding-bottom: 0;
      border-bottom: none;
    }

  .router-link-active {
    color: #dd6b20 !important;
  }
</style>
