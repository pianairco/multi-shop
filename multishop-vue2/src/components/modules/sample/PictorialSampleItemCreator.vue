<template lang="html">
  <div class="card">
    <picture-upload v-if="!editedItem"
                    ref="picUpload"
                    @selected="pictureSelected"
                    :form-name="formName" :propertyName="propertyName" ></picture-upload>
    <picture-upload v-if="editedItem"
                    ref="picUpload"
                    :form-name="formName" :editedImageSrc="'cdn/' + editedItem.imageSrc" :propertyName="propertyName" ></picture-upload>
    <div class="card-content">
      <div class="media" style="padding-top: 15px;">
        <div class="media-content">
          <div class="field">
            <div class="control">
              <input v-if="!editedItem" class="input is-primary" type="text" v-model="item.title" placeholder="عنوان">
              <input v-if="editedItem" class="input is-primary" type="text" v-model="editedItem.title" placeholder="عنوان">
            </div>
          </div>
        </div>
      </div>

      <div class="content">
        <textarea v-if="!editedItem" class="textarea" placeholder="توضیحات" v-model="item.description" rows="2"></textarea>
        <textarea v-if="editedItem" class="textarea" placeholder="توضیحات" v-model="editedItem.description" rows="2"></textarea>
      </div>
    </div>
    <footer class="card-footer">
      <button v-if="!editedItem" class="card-footer-item button is-white" v-on:click="addClick">
        افزودن
      </button>
      <button v-if="editedItem" class="card-footer-item button is-white" v-on:click="editClick">
        تغییر
      </button>
    </footer>
  </div>
</template>

<script lang="js">
  import pictureUpload from '../PictureUpload'

  export default {
    name: 'PictorialSampleItemCreator',
    props: {
      editedItem: {
        type: Object
      },
      formName: {
        type: String
      },
      propertyName: {
        type: String
      },
      link: {
        type: String
      },
      images: {
        type: Array
      },
      caption: {
        type: String
      }
    },
    data: function () {
      return {
        item: {
          id: 0,
          title: '',
          description: '',
          image: {
            base64: '',
            rotate: 0
          },
        },
        title: '',
        description: '',
        imageSrc: '',
        // sharedState: store.state
      }
    },
    components: {
      pictureUpload
    },
    methods: {
      reset: function () {
        this.item.title = '';
        this.item.description = '';
        this.item.image = {
          base64: '',
          rotate: 0
        };
        this.title = '';
        this.description = '';
        this.imageSrc = '';
        this.$refs.picUpload.reset();
      },
      pictureSelected: function (imageSrc) {
        this.item.image.base64 = imageSrc;
        console.log(imageSrc)
      },
      addClick: function () {
        // console.log(JSON.stringify(this.sharedState.forms[this.formName]))
        console.log(this.title)
        console.log(this.description)
        // store.setToForm(this.formName, "title", this.title);
        // store.setToForm(this.formName, "description", this.description);
        this.$emit("add-item", this.item);
        // this.$emit("add-item", this.sharedState.forms[this.formName]);
        this.reset();
      },
      editClick: function () {
        // store.setToForm(this.formName, "id", this.editedItem.id);
        // store.setToForm(this.formName, "title", this.title);
        // store.setToForm(this.formName, "description", this.description);
        // store.setToForm(this.formName, "imageSrc", this.imageSrc);
        this.$emit("edit-item", this.editedItem);
        // this.$emit("edit-item", this.sharedState.forms[this.formName]);
        this.reset();
      }
    },
    mounted: function () {
      console.log(JSON.stringify(this.editedItem))
      if (this.editedItem) {
        this.imageSrc = this.editedItem.imageSrc;
        this.title = this.editedItem.title;
        this.description = this.editedItem.description;
      } else {
        this.title = '';
        this.description = '';
      }
    },
    computed: {
      defaultCaption() {
        if (!this.caption)
          return 'مشاهده';
        return this.caption;
      }
    }
  };
</script>
