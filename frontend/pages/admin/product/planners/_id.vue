<template>
  <div class="product-wrapper">
    <div class="container-fluid mt--6">
      <div class="row">
        <div class="col-xl-12">
          <div class="card">
            <div class="card mb-4">
              <div class="card-header">
                <div class="form-group">
                  <label class="form-control-label">
                    Product name
                  </label>
                  <h3 class="mb-0">
                    <input :value="product.name" @input="updateName($event)" type="text" class="form-control">
                  </h3>
                </div>
              </div>
              <div class="card-body">
                <div class="row">
                  <div class="form-group col-md-2 price">
                    <label class="form-control-label">
                      Product price
                    </label>
                    <div class="has-name">
                      <input :value="product.price" @input="updatePrice($event)" type="number" class="form-control">
                    </div>
                    <label class="form-control-label">
                      Product SKU
                    </label>
                    <div class="has-name">
                      <input :value="product.sku" @input="updateSku($event)" type="text" class="form-control">
                    </div>
                    <p>Last updated: <br> {{ convertDate(product.lastUpdated) }}</p>
                  </div>
                  <div class="col-md-6 desc">
                    <label class="form-control-label">Product description</label>
                    <vue-editor v-model="form.content" />
                  </div>
                  <div class="col-md-4">
                    <div class="row">
                      <div class="form-group col-md-6 categories">
                        <label class="form-control-label">
                          Select category
                        </label>
                        <div class="has-name">
                          <select v-model="categories" @change="updateCategory($event)" multiple="multiple" class="form-control">
                            <option>Inserts</option>
                            <option>Daily Planners</option>
                            <option>Lifestyle Planners</option>
                            <option>Monthly Planners</option>
                            <option>Weekly Planners</option>
                          </select>
                        </div>
                      </div>
                      <div class="form-group col-md-6 dllink">
                        <label class="form-control-label">
                          Product download link
                        </label>
                        <div class="has-name">
                          <input :value="product.downloadLink" @input="updateDllink($event)" type="text" class="form-control">
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="form-group col-md-12 metas">
                        Meta tags:
                        <ul>
                          <li v-for="(m, index) in form.metas" :key="index">
                            <input type="text" v-model="m.name">
                            <input type="text" v-model="m.content">
                            <button @click="deleteMeta(index)">Delete</button>
                          </li>
                        </ul>
                        <button @click="addMeta">Add row</button>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="mb-0">
                    <drop-image :rerender="rerender" :alreadyin="product.image" :productid="product.id" @images="addImages" @remove="removeImages" />
                    <!-- <div v-for="image in product.files" :key="image.size" class="image-wrapper">
                      <img :src="'/images/1/' + image" alt="product-image">
                    </div> -->
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-12">
                    <button @click="save" class="submitButton">
                      Save Product
                    </button>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-12">
                    <b-alert v-model="showDismissibleAlert" :variant="type" dismissible show>
                      {{ message }}
                    </b-alert>
                  </div>
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
import { mapGetters, mapActions } from 'vuex'
import axios from 'axios'
import { VueEditor } from 'vue2-editor'
import DropImage from '~/components/DropImage'

export default {
  middleware: 'authenticated',
  components: {
    VueEditor,
    DropImage
  },
  data () {
    return {
      id: this.$route.params.id,
      form: {
        name: '',
        price: 0,
        sku: '',
        content: '',
        dllink: '',
        original: [],
        files: [],
        removed: [],
        cats: [],
        categories: [],
        metas: []
      },
      showDismissibleAlert: false,
      message: '',
      type: 'success',
      rerender: 0
    }
  },
  computed: {
    ...mapGetters(['planners', 'auth']),
    product () {
      return this.planners.find(pr => parseInt(pr.id) === parseInt(this.id))
    },
    categories: {
      // eslint-disable-next-line
      get: function () {
        return this.product.categories
      },
      // eslint-disable-next-line
      set: function (value) {

      }
    }
  },
  mounted () {
    this.form.original = [...this.product.image]
    this.form.content = this.product.longDescription
    this.form.categories = this.product.categories
    this.form.metas = JSON.parse(JSON.stringify(this.product.metaTags))
  },
  methods: {
    ...mapActions(['savePlanner', 'setPlannerCategory', 'refreshPlanner']),
    addImages (imageFile) {
      this.form.files.push(imageFile)
    },
    removeImages (imageFile) {
      this.form.original.splice(this.form.original.indexOf(imageFile), 1)
      this.form.removed.push(imageFile)
    },
    updateName (e) {
      this.form.name = e.target.value
    },
    updatePrice (e) {
      this.form.price = e.target.value
    },
    updateSku (e) {
      this.form.sku = e.target.value
    },
    updateContent (e) {
      this.form.content = e.target.value
    },
    updateDllink (e) {
      this.form.dllink = e.target.value
    },
    updateCategory (e) {
      // this.setCategory(this.product.id, e.target.value)
    },
    save () {
      const prod = {
        'id': parseInt(this.id),
        'name': this.form.name === '' ? this.product.name : this.form.name,
        'longDescription': this.form.content,
        'price': this.form.price === 0 ? this.product.price : this.form.price,
        'sku': this.form.sku === '' ? this.product.sku : this.form.sku,
        'categories': this.categories,
        'quantity': 1,
        'enabled': this.product.enabled,
        'downloadLink': this.form.dllink === '' ? this.product.downloadLink : this.form.dllink,
        'metaTags': this.form.metas
      }
      const headers = {
        'Authorization': this.auth.accessToken,
        'Content-Type': 'application/json'
      }
      const formData = new FormData()
      formData.append('product', JSON.stringify(prod))
      for (let i = 0; i < this.form.removed.length; i++) {
        formData.append('removed', this.form.removed[i].id)
      }
      for (let i = 0; i < this.form.files.length; i++) {
        formData.append('imagefile', this.form.files[i])
      }
      // eslint-disable-next-line
      axios.post('http://localhost:8083/admin/update/planner/', formData, {
        // eslint-disable-next-line
        headers: headers
      }).then((response) => {
        this.savePlanner(prod)
        this.showDismissibleAlert = true
        this.message = 'Modification saved!'
        this.type = 'success'
        this.refreshPlanner(response.data)
        this.form.removed = []
        this.rerender++
      })
        .catch(function (error) {
          console.log(error)
          this.showDismissibleAlert = true
          this.message = 'Something went wrong!'
          this.type = 'danger'
        })
    },
    convertDate (timestamp) {
      const date = new Date(timestamp / 1000 * 1000)
      const year = date.getFullYear()
      const month = '0' + (date.getMonth() + 1)
      const day = '0' + date.getDate()
      const hour = date.getHours()
      const minute = date.getMinutes()
      const second = date.getSeconds()
      return year + '-' + month.substr(-2) + '-' + day.substr(-2) + ' ' + hour + ':' + minute + ':' + second
    },
    addMeta () {
      this.form.metas.push({
        name: '',
        content: ''
      })
    },
    deleteMeta (index) {
      this.form.metas.splice(index, 1)
    }
  }
}
</script>

<style scoped>
.product-wrapper {
    margin-left: 250px;
}
</style>
