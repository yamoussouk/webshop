<template>
  <div class="single_product">
    <bread-crumb :title="product.name" />
    <div class="product_wrapper">
      <div ref="product_image_wrapper" class="product_image_wrapper">
        <div class="product_images">
          <image-carousel :id="product.id" :images="product.image" />
        </div>
        <div class="product_price">
          <span>$ {{ product.price }}</span>
        </div>
        <div class="product-attributes">
          <input ref="logotext" v-model="logoText" type="text" class="logo_text" placeholder="Add logo text">
        </div>
        <div class="product_cart_buttons">
          <button class="product_add_to_cart_button" @click="add(product)">
            add to cart
          </button>
          <button class="product_quick_buy_button" @click="buynow(product)">
            buy it now
          </button>
        </div>
      </div>
      <div ref="summary" class="summary">
        <div ref="product_details" class="product_details">
          <p v-html="product.longDescription" />
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
      product: {},
      logoText: ''
    }
  },
  async asyncData ({ params }) {
    const { data } = await axios.get(`http://localhost:8083/default/logo/${params.id}`)
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
        'price': product.price,
        'logoText': this.logoText,
        'image': product.image.find(i => i.imageUrl.includes('cover')),
        'type': 'Logo'
      }
    },
    add (product) {
      if (this.logoText === '') {
        this.$refs.logotext.style.border = '1px solid #ff0000'
      } else {
        this.$refs.logotext.style.border = 'none'
        const itemInCart = this.$store.state.localStorage.localCart.filter(item => item.id === product.id)
        const isItemInCart = itemInCart.length > 0

        if (isItemInCart === false) {
          this.addProductToLocalCart(product)
        } else {
          this.increaseQuantityOnLocalCart(product.id)
        }
      }
    },
    buynow (product) {
      if (this.logoText === '') {
        this.$refs.logotext.style.border = '1px solid #ff0000'
      } else {
        this.$refs.logotext.style.border = 'none'
        this.add(product)
        this.$router.push('/checkout')
      }
    }
  }
}
</script>

<style>
.product_wrapper {
    margin-bottom: 2%;
}
.product_image_wrapper {
    width: 50%;
    float: left;
}
.summary {
    width: 44%;
    margin-right: 1%;
    margin-left: 2%;
    float: right;
    clear: none;
}
.product_details {
    background-color: #fff;
}
.product_images {
    width: 93%;
    height: auto;
    margin-left: 7%;
}
.product_price {
    color: #000;
    font-family: Daun;
    font-size: 90px;
    width: 100%;
    background-color: #fff;
    line-height: 120px;
    text-align: center;
    height: 100px;
    margin-top: 5%;
}
.product_add_to_cart_button, .product_quick_buy_button {
    width: 100%;
    height: 100px;
    background-color: rgb(205, 158, 143);
    color: #fff;
    font-family: Daun;
    font-size: 90px;
    margin-bottom: 0px;
    border: none;
}
.product_add_to_cart_button {
    margin-bottom: 3%;
}
.summary .product_details p span {
    font-family: Daun;
    font-size: 40px;
    color: #000;
    padding-left: 5%;
    padding-right: 5%;
    line-height: 55px;
    display: inline-block;
}
.product-attributes {
    padding-top: 2%;
    padding-bottom: 3%;
}
.card-img > img {
    width: 100%;
}
.product-attributes .logo_text {
      color: #000;
      font-family: Daun;
      font-size: 90px;
      width: 100%;
      background-color: #fff;
      line-height: 120px;
      text-align: center;
      height: 100px;
      margin-top: 1%;
      border: none;
      padding-top: 30px;
}
</style>
