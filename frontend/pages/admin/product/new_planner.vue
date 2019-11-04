<template>
  <div class="main-content">
    <div class="container-fluid mt--12">
      <div class="row">
        <div class="col-xl-8">
          <div class="card mb-4">
            <div class="card-header">
              <h3 class="mb-0">
                Add new product
              </h3>
            </div>
            <div class="card-body">
              <div class="row">
                <div class="col-md-6">
                  <div class="form-group">
                    <label class="form-control-label">
                      Product name
                    </label>
                    <div class="has-name">
                      <input v-model="form.name" type="text" placeholder="Product name" class="form-control">
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="form-control-label">
                      Select category
                    </label>
                    <div class="has-name">
                      <select v-model="form.categories" multiple="multiple" class="form-control">
                        <option>Inserts</option>
                        <option>Daily Planners</option>
                        <option>Lifestyle Planners</option>
                        <option>Monthly Planners</option>
                        <option>Weekly Planners</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="form-control-label">
                      Product price
                    </label>
                    <div class="has-name">
                      <input v-model="form.price" type="number" placeholder="Product price" class="form-control">
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="form-control-label">
                      Product download link
                    </label>
                    <div class="has-name">
                      <input v-model="form.dllink" type="text" placeholder="Product download link" class="form-control">
                    </div>
                  </div>
                  <label class="form-control-label">Product description</label>
                  <vue-editor v-model="form.longDescription" />
                </div>
                <div class="col-md-6">
                  <label class="form-control-label">Drop images here</label>
                  <drop-image ref="dropImage" @images="addImages" />
                </div>
              </div>
              <div class="row">
                <div class="col-md-12">
                  <button class="submitButton" @click="addNew">
                    Add Product
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
        dllink: '',
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
      this.form.categories('Printable Planners')
      const formObject = {
        'id': 0,
        'name': this.form.name,
        'price': parseInt(this.form.price),
        'downloadLink': this.form.dllink,
        'categories': this.form.categories,
        'enabled': this.form.enabled,
        'longDescription': this.form.longDescription
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
      axios.post('http://localhost:8083/admin/add/new/planner', formData, {
        // eslint-disable-next-line
        headers: headers
      }).then((response) => {
        this.resetForm()
        this.success = true
        this.message = 'New product is added!'
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
      this.form.dllink = ''
      this.form.files.length = 0
      this.form.longDescription = ''
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
