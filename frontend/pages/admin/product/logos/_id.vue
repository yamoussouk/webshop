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
                  </div>
                  <div class="col-md-6 desc">
                    <label class="form-control-label">Product description</label>
                    <vue-editor v-model="form.content" />
                  </div>
                  <div class="form-group col-md-2 categories">
                    <label class="form-control-label">
                      Select category
                    </label>
                    <div class="has-name">
                      <select v-model="form.categories" @change="updateCategory($event)" multiple="multiple" class="form-control">
                        <option>Single Logo</option>
                        <option>Logo Set</option>
                        <option>Custom Logo</option>
                      </select>
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
        categories: []
      },
      showDismissibleAlert: false,
      message: '',
      type: 'success',
      rerender: 0
    }
  },
  computed: {
    ...mapGetters(['logos', 'auth']),
    product () {
      return this.logos.find(pr => parseInt(pr.id) === parseInt(this.id))
    }
  },
  mounted () {
    this.form.original = [...this.product.image]
    this.form.content = this.product.longDescription
    this.form.categories = this.product.categories
  },
  methods: {
    ...mapActions(['saveLogo', 'setLogoCategory', 'refreshLogo']),
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
    updateCategory (e) {
      console.log('target', e.target.value)
      // const temp = e.target.value
      // this.setLogoCategory(this.product.id, temp)
    },
    save () {
      const prod = {
        'id': parseInt(this.id),
        'name': this.form.name === '' ? this.product.name : this.form.name,
        'longDescription': this.form.content,
        'price': this.form.price === 0 ? this.product.price : this.form.price,
        'sku': this.form.sku === 0 ? this.product.sku : this.form.sku,
        'categories': this.form.categories,
        'quantity': 1,
        'enabled': this.product.enabled
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
      axios.post('http://localhost:8083/admin/update/logo/', formData, {
        // eslint-disable-next-line
        headers: headers
      }).then((response) => {
        this.saveLogo(prod)
        this.showDismissibleAlert = true
        this.message = 'Modification saved!'
        this.type = 'success'
        this.refreshLogo(response.data)
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
