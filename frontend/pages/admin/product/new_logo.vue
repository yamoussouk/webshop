<template>
  <div class="main-content">
    <div class="container-fluid mt--12">
      <div class="row">
        <div class="col-xl-8">
          <div class="card mb-4">
            <div class="card-header">
              <h3 class="mb-0">
                Add new logo
              </h3>
            </div>
            <div class="card-body">
              <div class="row">
                <div class="col-md-6">
                  <div class="form-group">
                    <label class="form-control-label">
                      Logo name
                    </label>
                    <div class="has-name">
                      <input v-model="form.name" type="text" placeholder="Logo name" class="form-control">
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="form-control-label">
                      Logo SKU
                    </label>
                    <div class="has-name">
                      <input v-model="form.sku" type="text" placeholder="Logo SKU" class="form-control">
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="form-control-label">
                      Select category
                    </label>
                    <div class="has-name">
                      <select v-model="form.categories" multiple="multiple" class="form-control">
                        <option>Single Logo</option>
                        <option>Logo Set</option>
                        <option>Custom Logo</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="form-control-label">
                      Logo price
                    </label>
                    <div class="has-name">
                      <input v-model="form.price" type="number" placeholder="Logo price" class="form-control">
                    </div>
                  </div>
                  <label class="form-control-label">Logo description</label>
                  <vue-editor v-model="form.longDescription" />
                </div>
                <div class="col-md-6">
                  <label class="form-control-label">Drop images here</label>
                  <drop-image ref="dropImage" @images="addImages" />
                </div>
              </div>
              <div class="row">
                <div class="col-md-12">
                  <button @click="addNew" class="submitButton">
                    Add Logo
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { VueEditor } from 'vue2-editor'
import axios from 'axios'
import DropImage from '~/components/DropImage.vue'
export default {
  middleware: 'authenticated',
  components: {
    VueEditor,
    DropImage
  },
  data () {
    return {
      form: {
        id: 0,
        name: '',
        price: 0,
        files: [],
        categories: [],
        enabled: 0,
        longDescription: ''
      },
      success: false,
      message: ''
    }
  },
  computed: {
    ...mapGetters(['auth'])
  },
  methods: {
    addImages (imageFile) {
      this.form.files.push(imageFile)
    },
    addNew () {
      this.form.categories.push('Logo')
      const formObject = {
        'id': 0,
        'name': this.form.name,
        'price': parseFloat(this.form.price),
        'categories': this.form.categories,
        'enabled': this.form.enabled,
        'longDescription': this.form.longDescription,
        'sku': this.form.sku
      }
      const headers = {
        'Authorization': this.auth.accessToken
      }
      const formData = new FormData()
      formData.append('product', JSON.stringify(formObject))
      for (let i = 0; i < this.form.files.length; i++) {
        formData.append('imagefile', this.form.files[i])
      }
      // eslint-disable-next-line
      axios.post('http://localhost:8083/admin/add/new/logo', formData, {
        // eslint-disable-next-line
        headers: headers
      }).then((response) => {
        this.resetForm()
        this.success = true
        this.message = 'New logo is added!'
      })
        .catch(function (error) {
          console.log(error)
          this.success = true
          this.message = 'Something went wrong!'
        })
    },
    resetForm () {
      this.form.name = ''
      this.form.price = 0
      this.form.files.length = 0
      this.form.longDescription = ''
      this.form.sku = ''
      this.form.categories.length = 0
      this.$refs.dropImage.resetImages()
    }
  }
}
</script>

<style>
.main-content {
    margin-left: 250px;
}
.submitButton {
    width: 100%;
    margin-top: 20px;
}
</style>
