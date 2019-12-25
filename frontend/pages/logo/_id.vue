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
        <button class="show_more_button" @click="toggle($event)">Show {{button_text}}</button>
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
      logoText: '',
      button_text: 'more'
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
    },
    toggle (e) {
      const h = this.$refs.product_image_wrapper.clientHeight
      print(h - 35)
      if (this.button_text === 'more') {
        this.$refs.product_details.style.height = 'auto'
        this.$refs.product_details.style.overflow = 'auto'
        this.button_text = this.button_text === 'more' ? 'less' : 'more'
      } else {
        this.$refs.product_details.style.height = h - 35 + 'px'
        this.$refs.product_details.style.overflow = 'hidden'
        this.button_text = this.button_text === 'more' ? 'less' : 'more'
      }
    }
  }
}
</script>

<style scoped>
@media only screen and (max-width: 2560px){
  .product_wrapper {
      margin-bottom: 2%;
  }
  .product_image_wrapper {
      width: 50%;
      float: left;
      margin-bottom: 2%;
  }
  .summary {
      width: 44%;
      margin-right: 1%;
      margin-left: 2%;
      float: right;
      clear: none;
      margin-bottom: 1%;
  }
  .product_details {
      background-color: #fff;
  }
  .summary .product_details p {
    padding-top: 25px;
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
  .summary .product_details p {
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
  .show_more_button {
    display: none;
  }
}
@media only screen and (max-width: 1920px){
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
      margin-bottom: 1%;
  }
  .product_details {
      background-color: #fff;
  }
  .summary .product_details p {
    padding-top: 25px;
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
  .summary .product_details p {
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
  .show_more_button {
    display: none;
  }
}
@media only screen and (max-width: 1680px){
}
@media only screen and (max-width: 1600px){
  .product_price {
    font-size: 80px;
    line-height: 130px;
  }
  .product-attributes .logo_text {
    font-size: 80px;
  }
  .product_add_to_cart_button, .product_quick_buy_button {
    font-size: 80px;
  }
}
@media only screen and (max-width: 1440px){
}
@media only screen and (max-width: 1366px){
  .product_price {
    font-size: 70px;
    line-height: 120px;
    height: 90px;
  }
  .product-attributes .logo_text {
    font-size: 70px;
    height: 90px;
  }
  .product_add_to_cart_button, .product_quick_buy_button {
    font-size: 70px;
    height: 90px;
    line-height: 120px;
  }
  .summary .product_details p span {
    font-size: 35px;
    line-height: 50px;
  }
}
@media only screen and (max-width: 1112px){
  .summary .product_details p span {
    padding-right: 3%;
  }
  .product_add_to_cart_button, .product_quick_buy_button {
    font-size: 65px;
    height: 85px;
    line-height: 115px;
  }
  .product_price {
    font-size: 65px;
    line-height: 115px;
    height: 85px;
  }
  .product-attributes .logo_text {
    font-size: 65px;
    height: 85px;
  }
}
@media only screen and (max-width: 1024px){
  .product_price {
    font-size: 60px;
    line-height: 100px;
    height: 75px;
  }
  .product-attributes .logo_text {
    font-size: 60px;
    height: 75px;
  }
  .product_add_to_cart_button, .product_quick_buy_button {
    font-size: 60px;
    height: 75px;
    line-height: 100px;
  }
  .summary .product_details p span {
    line-height: 40px;
  }
}
@media only screen and (max-width: 834px){
}
@media only screen and (max-width: 812px){
  .product_wrapper {
    margin-top: 2%;
  }
  .summary .product_details p span {
    font-size: 30px;
  }
  .product_price {
    font-size: 50px;
    line-height: 90px;
    height: 65px;
  }
  .product-attributes .logo_text {
    font-size: 50px;
    height: 65px;
    padding-top: 20px;
  }
  .product_add_to_cart_button, .product_quick_buy_button {
    font-size: 50px;
    height: 65px;
    line-height: 85px;
  }
  .summary .product_details p {
    font-size: 30px;
  }
}
@media only screen and (max-width: 768px){
  .product_image_wrapper {
    width: 60%;
    margin-bottom: 2%;
  }
  .summary {
    width: 34%;
  }
  .show_more_button {
    display: block;
    font-size: 30px;
    height: 35px;
    line-height: 38px;
    background-color: rgb(205, 158, 143);
    color: #fff;
    font-family: Daun;
    width: 100%;
    border: 0;
  }
  .product_details {
    height: 848px;
    overflow: hidden;
  }
  .summary {
    margin-bottom: 20px;
  }
}
@media only screen and (max-width: 767px){
  .summary .product_details p span {
    line-height: 30px;
    font-size: 25px;
  }
  .show_more_button {
    line-height: 45px;
  }
}
@media only screen and (max-width: 737px){
}
@media only screen and (max-width: 667px){
  .product_price {
    font-size: 40px;
    line-height: 75px;
    height: 55px;
  }
  .product-attributes .logo_text {
    font-size: 40px;
    height: 55px;
  }
  .product_add_to_cart_button, .product_quick_buy_button {
    font-size: 40px;
    height: 55px;
    line-height: 70px;
  }
}
@media only screen and (max-width: 568px){
  .product_price {
    font-size: 30px;
    line-height: 60px;
    height: 45px;
  }
  .product-attributes .logo_text {
    font-size: 30px;
    height: 45px;
    padding-top: 15px;
  }
  .product_add_to_cart_button, .product_quick_buy_button {
    font-size: 30px;
    height: 45px;
    line-height: 55px;
  }
  .summary .product_details p span {
    line-height: 25px;
    font-size: 22px;
  }
}
@media only screen and (max-width: 414px){
  .show_more_button {
    font-size: 25px;
    line-height: 42px;
  }
}
@media only screen and (max-width: 375px){
  .product_price {
    font-size: 25px;
    line-height: 45px;
    height: 35px;
  }
  .product-attributes .logo_text {
    font-size: 25px;
    height: 35px;
    padding-top: 10px;
  }
  .product_add_to_cart_button, .product_quick_buy_button {
    font-size: 25px;
    height: 35px;
    line-height: 42px;
  }
}
@media only screen and (max-width: 360px){
}
@media only screen and (max-width: 320px){
}
</style>
