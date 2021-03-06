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
                    <input :value="product.name" type="text" class="form-control" @input="updateName($event)">
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
                      <input :value="product.price" type="number" class="form-control" @input="updatePrice($event)">
                    </div>
                  </div>
                  <div class="col-md-6 desc">
                    <label class="form-control-label">Product description</label>
                    <vue-editor v-model="product.content" />
                  </div>
                  <div class="form-group col-md-2 categories">
                    <label class="form-control-label">
                      Select category
                    </label>
                    <div class="has-name">
                      <select v-model="categories" multiple="multiple" class="form-control" @input="updateCategory($event)">
                        <option>Printable Planners</option>
                        <option>Inserts</option>
                        <option>Daily Planners</option>
                        <option>Lifestyle Planners</option>
                        <option>Monthly Planners</option>
                        <option>Weekly Planners</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group col-md-2 dllink">
                    <label class="form-control-label">
                      Product download link
                    </label>
                    <div class="has-name">
                      <input :value="product.downloadLink" type="text" class="form-control" @input="updateDllink($event)">
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
                    <button class="submitButton" @click="save">
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
        content: '',
        dllink: '',
        original: [],
        files: [],
        removed: [],
        cats: []
      },
      showDismissibleAlert: false,
      message: '',
      type: 'success',
      rerender: 0
    }
  },
  computed: {
    ...mapGetters(['getProducts', 'auth']),
    product () {
      return this.getProducts.find(pr => parseInt(pr.id) === parseInt(this.id))
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
  },
  methods: {
    ...mapActions(['saveProduct', 'setCategory', 'refreshPoduct']),
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
    updateContent (e) {
      this.form.content = e.target.value
    },
    updateDllink (e) {
      this.form.dllink = e.target.value
    },
    updateCategory (e) {
      this.setCategory(this.product.id, e.target.value)
    },
    save () {
      const prod = {
        'id': parseInt(this.id),
        'name': this.form.name === '' ? this.product.name : this.form.name,
        'longDescription': this.form.longDescription === '' ? this.product.longDescription : this.form.longDescription,
        'price': this.form.price === 0 ? this.product.price : this.form.price,
        'categories': this.categories,
        'quantity': 1,
        'enabled': this.product.enabled,
        'downloadLink': this.form.dllink === '' ? this.product.downloadLink : this.form.dllink
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
      axios.post('http://localhost:8083/admin/update/product/', formData, {
        // eslint-disable-next-line
        headers: headers
      }).then((response) => {
        this.saveProduct(prod)
        this.showDismissibleAlert = true
        this.message = 'Modification saved!'
        this.type = 'success'
        this.refreshPoduct(response.data)
        this.form.removed = []
        this.rerender++
      })
        .catch(function (error) {
          console.log(error)
          this.showDismissibleAlert = true
          this.message = 'Something went wrong!'
          this.type = 'danger'
        })
    }
  }
}
</script>

<style scoped>
.product-wrapper {
    margin-left: 250px;
}
</style>
