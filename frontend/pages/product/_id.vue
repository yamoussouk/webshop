<template>
  <div class="single_product">
    <bread-crumb :title="product.name" />
    <div class="product_wrapper">
      <div ref="product_image_wrapper" class="product_image_wrapper">
        <div class="product_images">
          <image-carousel :images="product.image" :id="product.id" />
        </div>
        <div class="product_price">
          <span>$ {{ product.price }}</span>
        </div>
        <div class="product_cart_buttons">
          <button class="product_add_to_cart_button" @click="add(product)">
            Add to cart
          </button>
          <button class="product_quick_buy_button" @click="buynow(product)">
            Buy Now
          </button>
        </div>
      </div>
      <div ref="summary" class="summary">
        <div ref="product_details" class="product_details">
          <p>{{ product.longDescription }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import BreadCrumb from '~/components/BreadCrumb.vue'
// eslint-disable-next-line
import ImageCarousel from '~/components/ImageCarousel.vue'

export default {
  validate ({ params }) {
    return /^\d+$/.test(params.id)
  },
  components: {
    BreadCrumb,
    // eslint-disable-next-line
    ImageCarousel
  },
  data () {
    return {
      id: parseInt(this.$route.params.id),
      cart: [],
      product: {}
    }
  },
  async asyncData ({ params }) {
    const { data } = await axios.get(`http://localhost:8083/default/product/${params.id}`)
    return { product: data }
  },
  methods: {
    currentImage () {
      return '/images/1/' + this.product.images[this.activeImage].imageUrl
    },
    addProductToLocalCart (product) {
      this.$store.commit('localStorage/addToLocalCart', this.simplifyProduct(product))
    },
    increaseQuantityOnLocalCart (id) {
      this.$store.commit('localStorage/increaseQuantityInLocalCart', id)
    },
    simplifyProduct (product) {
      return {
        'id': product.id,
        'name': product.name,
        'quantity': product.quantity,
        'price': product.price
      }
    },
    add (product) {
      const itemInCart = this.$store.state.localStorage.localCart.filter(item => item.id === product.id)
      const isItemInCart = itemInCart.length > 0

      if (isItemInCart === false) {
        this.addProductToLocalCart(product)
      } else {
        this.increaseQuantityOnLocalCart(product.id)
      }
    },
    buynow (product) {
      this.add(product)
      this.$router.push('/checkout')
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
