<template>
  <div class="single_product">
    <bread-crumb :title="product.name" />
    <div class="product_wrapper">
      <div ref="product_image_wrapper" class="product_image_wrapper">
        <div class="product_images">
          <image-carousel :id="product.id" :images="product.image" />
        </div>
        <div class="product_price">
          <span>$ {{ (product.price + product.vat).toFixed(2) }}</span>
        </div>
        <div class="product-attributes">
          <custom-select
            ref="ref_size"
            v-model="selected_size"
            refs="size"
            :options="sizes"
            value-key="id"
            label-key="name"
          />
          <custom-select
            ref="ref_day"
            v-model="selected_day"
            refs="day"
            :options="days"
            value-key="id"
            label-key="name"
          />
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
        <div ref="product_details" class="product_details_planner">
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
import CustomSelect from '~/components/CustomSelect.vue'
// eslint-disable-next-line
import ImageCarousel from '~/components/ImageCarousel.vue'

export default {
  validate ({ params }) {
    return /^\d+$/.test(params.id)
  },
  // middleware: 'location',
  components: {
    BreadCrumb,
    // eslint-disable-next-line
    ImageCarousel,
    CustomSelect
  },
  data () {
    return {
      id: parseInt(this.$route.params.id),
      cart: [],
      product: {},
      size: 'select',
      sizes: [
        { name: 'SELECT SIZE', id: 0 },
        { name: 'A4', id: 1 },
        { name: 'A5', id: 2 },
        { name: 'US LETTER', id: 3 },
        { name: 'HALF SIZE', id: 4 },
        { name: 'PERSONAL', id: 5 }
      ],
      days: [
        { name: 'FIRST DAY OF THE WEEK', id: 0 },
        { name: 'SUNDAY', id: 1 },
        { name: 'MONDAY', id: 2 }
      ],
      selected_size: { name: 'SELECT SIZE', id: 0 },
      selected_day: { name: 'FIRST DAY OF THE WEEK', id: 0 },
      startingDay: 'select',
      button_text: 'more'
    }
  },
  async asyncData ({ req, params, store }) {
    // EU IP
    const ip = '109.74.53.10'
    // US IP
    // const ip = '72.229.28.185'
    const res = await axios.get(`http://www.geoplugin.net/json.gp?ip=${ip}`)
    // eslint-disable-next-line
    store.dispatch('setIP', { ip: ip, countryCode: res.data.geoplugin_countryCode,
      continentCode: res.data.geoplugin_continentCode })
    const { data } = await axios.get(`http://localhost:8083/default/planner/${params.id}/${res.data.geoplugin_countryCode}`)
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
      const s = this.selected_size.name
      return {
        'id': product.id,
        'name': product.name,
        'quantity': product.quantity,
        'price': product.price,
        'vat': product.vat,
        'size': s.replace(/\s/g, ''),
        'startingDay': this.selected_day.name,
        'image': product.image[0],
        'type': 'Planner'
      }
    },
    add (product) {
      if (this.selected_size.id === 0) {
        console.log('MISSING SIZE')
      } else if (this.selected_day.id === 0) {
        console.log('MISSING STARTING DAY')
      } else {
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
      if (this.selected_size.id === 0) {
        this.$refs.ref_size.$refs.size.style.border = '1px solid #ff0000'
      } else if (this.selected_day.id === 0) {
        this.$refs.ref_size.$refs.size.style.border = 'none'
        this.$refs.ref_day.$refs.day.style.border = '1px solid #ff0000'
      } else {
        this.$refs.ref_day.$refs.day.style.border = 'none'
        this.add(product)
        this.$router.push('/checkout')
      }
    },
    toggle (e) {
      const h = this.$refs.product_image_wrapper.clientHeight
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

<style>
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
  }
  .product_details_planner {
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
      line-height: 140px;
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
  .summary .product_details_planner p span {
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
  .product-attributes .select:nth-child(1) {
      margin-bottom: 3%;
  }
  .card-img > img {
      width: 100%;
  }
  .summary .product_details_planner > p > p:nth-child(1) {
      padding-top: 25px;
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
  }
  .product_details_planner {
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
      line-height: 140px;
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
  .summary .product_details_planner p span {
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
  .product-attributes .select:nth-child(1) {
      margin-bottom: 3%;
  }
  .card-img > img {
      width: 100%;
  }
  .summary .product_details_planner > p > p:nth-child(1) {
      padding-top: 25px;
  }
  .show_more_button {
      display: none;
  }
}
@media only screen and (max-width: 1680px){
}
@media (max-height: 2224px) and (max-width: 1668px) {
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
    line-height: 130px;
  }
}
@media only screen and (max-width: 1536px){
}
@media (max-height: 772px) and (max-width: 1535px) {
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
  .summary .product_details_planner p span {
    font-size: 35px;
    line-height: 50px;
  }
}
@media only screen and (max-width: 1360px){
}
@media (max-height: 1024px) and (max-width: 1280px) {
    /* CSS stuff */
}
@media (max-height: 720px) and (max-width: 1280px) {
    /* CSS stuff */
}
@media (max-height: 800px) and (max-width: 1280px) {
    /* CSS stuff */
}
@media (max-height: 2688px) and (max-width: 1242px) {
    /* CSS stuff */
}
@media (max-height: 2208px) and (max-width: 1242px) {
    /* CSS stuff */
}
@media (max-height: 2436px) and (max-width: 1125px) {
    /* CSS stuff */
}
@media only screen and (max-width: 1112px){
  .summary .product_details_planner p span {
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
  .summary .product_details_planner p span {
    line-height: 40px;
  }
}
@media only screen and (max-width: 834px){
}
@media (max-height: 1792px) and (max-width: 828px) {
    /* CSS stuff */
}
@media only screen and (max-width: 812px){
  .product_wrapper {
    margin-top: 2%;
  }
  .summary .product_details_planner p span {
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
  .summary .product_details_planner p span {
    font-size: 30px;
  }
}
@media only screen and (max-width: 800px){
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
  .product_details_planner {
    height: 848px;
    overflow: hidden;
  }
  .summary {
    margin-bottom: 20px;
  }
}
@media only screen and (max-width: 767px){
  .summary .product_details_planner p span {
    line-height: 30px;
    font-size: 25px;
  }
  .show_more_button {
    line-height: 45px;
  }
}
@media (max-height: 1334px) and (max-width: 750px) {
    /* CSS stuff */
}
@media only screen and (max-width: 737px){
  .product_details_planner {
    height: 827px;
  }
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
  .product_details_planner {
    height: 745px;
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
  .summary .product_details_planner p span {
    line-height: 25px;
    font-size: 22px;
  }
  .product_details_planner {
    height: 593px;
  }
}
@media only screen and (max-width: 414px){
  .show_more_button {
    font-size: 25px;
    line-height: 42px;
  }
  .product_details_planner {
    height: 475px;
  }
}
@media only screen and (max-width: 384px){
  .product_details_planner {
    height: 457px;
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
  .product_details_planner {
    height: 422px;
  }
}
@media only screen and (max-width: 360px){
  .product_details_planner {
    height: 413px;
  }
}
@media only screen and (max-width: 320px){
  .product_details_planner {
    height: 382px;
  }
}
</style>
