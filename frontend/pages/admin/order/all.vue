<template>
  <div class="main-content">
    <div class="container-fluid mt--6">
      <div class="row">
        <div class="col-xl-12">
          <div v-for="order in orders" :key="order.id" class="card">
            <div class="card mb-4">
              <div class="card-header">
                <h3 class="mb-0">
                  {{ order.purchaseTime }}
                </h3>
              </div>
              <div class="card-body">
                <div class="row">
                  <div class="col-md-4">
                    <div class="row">
                      <div class="col-md-0 price">
                        <p>{{ order.price }}</p>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-5 desc">
                    <p>{{ order.email }}</p>
                  </div>
                  <div class="col-md-2 cat">
                    <span v-for="product in order.products" :key="product.id">{{ product.name }}</span>
                  </div>
                  <!-- <div class="col-md-1 settings">
                    <div>
                      <nuxt-link :to="`${product.id}`">
                        <img :src="'/edit-button.png'" alt="edit button" @click="editProduct(product.id)">
                      </nuxt-link>
                      <img :src="freezeSource(product.id)" alt="freeze button" @click="productEnable(product.id)">
                      <img v-b-modal.ensure-modal :src="'/remove-button.png'" alt="remove button" @click="setId(product.id)">
                    </div>
                  </div> -->
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <b-modal id="ensure-modal" hide-footer>
      <p>Are you sure want to remove the product?</p>
      <b-button class="mt-3" block @click="$bvModal.hide('ensure-modal')">
        Cancel
      </b-button>
      <b-button class="mt-3" block @click="remove(tempId)">
        Remove
      </b-button>
    </b-modal>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
export default {
  middleware: 'authenticated',
  data () {
    return {
      tempId: ''
    }
  },
  computed: {
    ...mapGetters(['orders', 'products'])
  },
  methods: {
    ...mapActions(['changeProductEnabled', 'removeProduct']),
    freezeSource (id) {
      return this.products.find(pr => parseInt(pr.id) === parseInt(id)).enabled ? '/verified-button.png' : '/freeze-button.png'
    },
    productEnable (id) {
      this.changeProductEnabled(id)
    },
    remove (id) {
      this.removeProduct(id)
      this.tempId = ''
      this.$bvModal.hide('ensure-modal')
    },
    setId (id) {
      this.tempId = id
    }
  }
}
</script>

<style>
.main-content {
    margin-left: 250px;
}
.mb-4 {
    margin-bottom: 0 !important;
}
.desc div p {
    text-overflow: ellipsis;
    overflow: hidden;
    width: 100%;
    height: 1.2em;
    white-space: nowrap;
    margin-bottom: 0;
}
.settings img {
    float: left;
    width: 25%;
    margin-right: 3%;
    cursor: pointer;
}
.price, .desc, .cat, .dllink, .settings {
    display: table;
    height: 64px;
}
.price p, .desc div, .cat span, .dllink p, .settings div {
    display: table-cell;
    vertical-align: middle;
}
.cat span {
  width: 100%;
  float: left;
}
</style>
