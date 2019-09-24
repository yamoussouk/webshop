<template>
    <div class="single_product">
      <bread-crumb :title="product.name" :to="this.$route.params.from" />
      <div class="product_wrapper">
        <div class="product_image_wrapper" ref="product_image_wrapper">
          <div class="product_images">
            <image-carousel :images="product.image"></image-carousel>
          </div>
          <div class="product_price">
            <span>$ {{ product.price }}</span>
          </div>
          <div class="product_cart_buttons">
              <button class="product_add_to_cart_button" @click="add(product)">Add to cart</button>
              <button class="product_quick_buy_button">Buy Now</button>
          </div>
        </div>
        <div class="summary" ref="summary">
          <div class="product_details" ref="product_details">
            <p>{{ product.longDescription }}</p>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
import axios from 'axios'
import { mapActions, mapGetters } from 'vuex'
import BreadCrumb from '~/components/BreadCrumb.vue'
// eslint-disable-next-line
import ImageCarousel from '~/components/ImageCarousel.vue'

export default {
  components: {
    BreadCrumb,
    // eslint-disable-next-line
    ImageCarousel
  },
  data () {
    return {
      id: this.$route.params.id,
      marginBottom: '0px',
      height: '0px'
    }
  },
  created () {
    this.product = (this.$store.getters.getProducts).filter(p => p.id === this.id)[0]
    if (!this.product) {
      axios.get('http://localhost:8083/default/product/' + this.id
      ).then((response) => {
        this.getProducts = response.data
      })
        .catch(function (error) {
          console.log(error)
        })
    }
  },
  computed: {
    ...mapGetters(['getCart'])
  },
  methods: {
    ...mapActions(['addProductToCart', 'increaseQuantity']),
    add (product) {
      // Add the item or increase qty
      const itemInCart = this.getCart.filter(item => item.id === product.id)
      const isItemInCart = itemInCart.length > 0

      if (isItemInCart === false) {
        this.addProductToCart(product)
      } else {
        this.increaseQuantity(product.id)
      }
      product.qty = 1
      // this.addProductToCart(product)
    }
  }
}
</script>

<style>
.product_wrapper {
    margin-bottom: 2%;
}
.product_image_wrapper {
    width: 48%;
    margin-left: 2%;
    float: left;
}
.summary {
    width: 46%;
    margin-right: 1%;
    margin-left: 2%;
    float: right;
    clear: none;
}
.product_details {
    background-color: #fff;
}
.product_images {
    width: 100%;
    height: auto;
}
.product_price {
    color: #000;
    font-family: Audrey;
    font-size: 46pt;
    width: 100%;
    background-color: #fff;
    line-height: 120px;
    text-align: center;
    height: 100px;
}
.product_add_to_cart_button, .product_quick_buy_button {
    width: 100%;
    height: 100px;
    background-color: rgba(163,153,178,1);
    color: #fff;
    font-family: Audery;
    font-size: 46pt;
    margin-bottom: 0px;
}
.product_add_to_cart_button {
    margin-top: 10%;
    margin-bottom: 10%;
}
.summary .product_details p {
    font-family: Audrey;
    font-size: 30px;
    color: #000;
    padding-left: 5%;
    padding-right: 5%;
    line-height: 55px;
    overflow: auto;
    word-break: break-word;
}
</style>
