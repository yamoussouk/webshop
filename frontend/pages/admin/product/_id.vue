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
                      <select v-model="product.categories" multiple="multiple" class="form-control" @input="updateCategory($event)">
                        <option>Printable planners</option>
                        <option>Insert</option>
                        <option>Daily planners</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group col-md-2 dllink">
                    <label class="form-control-label">
                      Product download link
                    </label>
                    <div class="has-name">
                      <input :value="product.dllink" type="text" class="form-control" @input="updateDllink($event)">
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="mb-0">
                    <drop-image :alreadyin="product.files" @images="addImages" @remove="removeImages" />
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
import { VueEditor } from 'vue2-editor'
import DropImage from '~/components/DropImage'

export default {
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
        categories: [],
        files: []
      }
    }
  },
  computed: {
    ...mapGetters(['products']),
    product () {
      return this.products.find(pr => parseInt(pr.id) === parseInt(this.id))
    }
  },
  mounted () {
    console.log(this.product)
  },
  methods: {
    ...mapActions(['saveProduct']),
    addImages (imageFile) {
      this.form.files.push(imageFile)
    },
    removeImages (imageFile) {
      this.form.files.splice(this.form.files.indexOf(imageFile), 1)
    },
    updateName (e) {
      this.form.name = e.target.value
      console.log(this.form.name)
    },
    updatePrice (e) {
      this.form.price = e.target.value
      console.log(this.form.price)
    },
    updateContent (e) {
      this.form.content = e.target.value
      console.log(this.form.content)
    },
    updateDllink (e) {
      this.form.dllink = e.target.value
      console.log(this.form.dllink)
    },
    updateCategory (e) {
      console.log(e.target._value)
      if (!this.form.categories.includes(e.target.value)) {
        this.form.categories.push(e.target.value)
      }
      console.log(this.form.categories)
    },
    save () {
      console.log(this.form.files)
      const prod = {
        'name': this.form.name === '' ? this.product.name : this.form.name,
        'id': this.id,
        'files': this.form.files
      }
      this.saveProduct(prod)
    }
  }
}
</script>

<style scoped>
.product-wrapper {
    margin-left: 250px;
}
</style>
