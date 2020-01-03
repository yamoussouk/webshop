<template>
  <div class="checkout-page-wrapper">
    <bread-crumb :title="title" :to="shop" :back="back" />
    <div id="checkout_wrapper">
      <div v-if="cart.length !== 0">
        <!-- checkout table -->
        <checkout-table :cart="cart" />
        <!-- end of checkout table -->
        <!-- mobile checkout -->
        <div class="checkout_table_mobile">
          <div v-for="item in cart" :key="item.id" class="checkout_mobile_item_wrapper">
            <div class="checkout_mobile_image_wrapper">
              <img v-if="item.image" class="image" alt="product-image" :src="'/uploaded/' + item.id + '/' + item.image.imageUrl">
            </div>
            <div class="checkout_mobile_description_wrapper">
              <div class="checkout_mobile_description">
                <span class="name">{{ item.name }}</span>
                <span v-if="item.size" class="size">{{ item.size }}</span>
                <span v-if="item.startingDay" class="starting_day">{{ item.startingDay }}</span>
                <span v-if="item.logoText" class="logo_text" :title="item.logoText">{{ item.logoText }}</span>
              </div>
              <div class="price_mobile_wrapper">
                <div class="price">
                  <span>$ {{ ('' + (item.price + item.vat).toFixed(2)).replace('.', ',') }}</span>
                </div>
              </div>
              <div class="close_mobile_wrapper">
                <div>
                  <span @click="removeFromCart(index)">Remove</span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- end of mobile checkout -->
        <div class="checkout_subtotal_row">
          <div class="coupon_row">
            <span>COUPON:</span>
          </div>
          <div class="coupon_input">
            <input v-model="coupon" type="text">
            <button type="button" @click="applyCoupon">
              Apply
            </button>
          </div>
        </div>

        <div class="checkout_subtotal_row">
          <div class="subtotal_text">
            <span>SUBTOTAL:</span>
          </div>
          <div class="subtotal_price">
            <span>$ {{ total }}</span>
          </div>
        </div>
        <div class="button_wrapper">
          <transition name="fade">
            <div v-show="!proceed" id="browse">
              <button type="button">
                <nuxt-link to="/shop">
                  browse more
                </nuxt-link>
              </button>
            </div>
          </transition>
          <div id="checkout">
            <button type="button" @click="proceedCheckout">
              checkout
            </button>
          </div>
        </div>
        <transition name="fade">
          <checkout-module v-show="proceed" :items="items" :total="total" :result.sync="result" :email.sync="email"/>
        </transition>
      </div>
      <div v-else class="empty-cart">
        <div v-show="success" class="order-result">
          <p>{{ message }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import BreadCrumb from '~/components/BreadCrumb.vue'
import CheckoutModule from '~/components/CheckoutModule.vue'
import CheckoutTable from '~/components/checkout/CheckoutTable.vue'

export default {
  components: {
    BreadCrumb,
    CheckoutModule,
    CheckoutTable
  },
  data () {
    return {
      errors: [],
      title: 'my shopping cart',
      shop: 'shop',
      back: false,
      email: '',
      coupon: null,
      discount: 0,
      success: false,
      message: '',
      proceed: false,
      result: ''
    }
  },
  computed: {
    total () {
      if (this.discount !== 0) {
        return ('' + (this.$store.state.localStorage.localCart.reduce((ac, next) => ac + next.quantity * (next.price + next.vat), 0) * (1 - this.discount)).toFixed(2)).replace('.', ',')
      } else {
        return ('' + this.$store.state.localStorage.localCart.reduce((ac, next) => ac + next.quantity * (next.price + next.vat), 0).toFixed(2)).replace('.', ',')
      }
    },
    cart () {
      return this.$store.state.localStorage.localCart
    },
    items () {
      return this.$store.state.localStorage.payPalCompatibleCart
    }
  },
  methods: {
    emptyLocalCart () {
      this.$store.commit('localStorage/emptyLocalCart')
    },
    validEmail (email) {
      const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      return re.test(email)
    },
    validCardNumber (number) {
      const re = /^$/
      return re.test(number)
    },
    applyCoupon () {
      // eslint-disable-next-line
      axios.get('http://localhost:8083/default/coupon/' + this.coupon
      ).then((response) => {
        if (typeof response.data === 'number') {
          this.discount = parseFloat(response.data) / 100
        } else {
          console.log('error', response.data)
        }
      })
        .catch(function (error) {
          console.log(error)
          this.success = true
          this.message = 'Something went wrong with applying the coupon'
        })
    },
    proceedCheckout () {
      this.proceed = !this.proceed
    },
    removeFromCart (index) {
      this.$store.commit('localStorage/removeFromLocalCartByIndex', index)
    }
  },
  watch: {
    // eslint-disable-next-line
    result: function(val) {
      if (val === 'COMPLETED') {
        const planners = this.cart.filter(pr => pr.type === 'Planner')
        const logos = this.cart.filter(pr => pr.type === 'Logo')
        let priceTemp = planners.reduce((ac, next) => ac + next.quantity * next.price, 0)
        priceTemp = priceTemp + logos.reduce((ac, next) => ac + next.quantity * next.price, 0)
        let vatTemp = planners.reduce((ac, next) => ac + next.quantity * next.vat, 0)
        vatTemp = vatTemp + logos.reduce((ac, next) => ac + next.quantity * next.vat, 0)
        const formObject = {
          'price': priceTemp.toFixed(2),
          'vat': vatTemp.toFixed(2),
          'purchaseTime': new Date().getTime(),
          'user': null,
          'email': this.email,
          // eslint-disable-next-line
          'planners': planners,
          // eslint-disable-next-line
          'logos': logos,
          'coupon': this.coupon
        }
        const headers = {
          'Content-Type': 'application/json'
        }
        // eslint-disable-next-line
        axios.post('http://localhost:8083/order/add', JSON.stringify(formObject), {
          // eslint-disable-next-line
          headers: headers
        }).then((response) => {
          this.emptyLocalCart()
          this.success = true
          this.message = 'Product successfully ordered!'
        })
          .catch(function (error) {
            console.log(error)
          })
      } else {
        this.errors.push('Purchase cancelled! Try it again.')
      }
    }
  }
}
</script>

<style scoped>
@media only screen and (max-width: 2560px){
  .checkout-page-wrapper {
      margin-bottom: 3%;
  }
  .empty-cart div.order-result p {
      text-align: center;
      font-size: 40px;
      font-family: Audrey;
  }
  .empty-cart div.order-result {
      margin-bottom: 5%;
  }
  #checkout_wrapper {
    width: 1440px;
    margin: 0 auto 20px auto;
  }
  .checkout_row, .checkout_subtotal_row {
    background: #fff;
  }
  .checkout_subtotal_row {
    width: 100%;
    height: 100px;
  }
  .checkout_row:nth-last-child(2) {
    box-shadow: none;
    padding-bottom: 20px;
  }
  .subtotal_text, .coupon_row {
    width: 70%;
    float: left;
  }
  .subtotal_text span, .coupon_row span {
    font-size: 55px;
    margin-left: 35%;
    position: relative;
    top: 20px;
  }
  .subtotal_price, .coupon_input {
    float: left;
    width: 30%;
  }
  .checkout_subtotal_row {
    margin: 2% 0;
  }
  .button_wrapper {
    width: 1440px;
    height: 200px;
    margin: 0 auto;
  }
  #browse button, #checkout button {
    width: 100%;
    font-size: 60px;
    color: #fff;
    text-align: center;
    height: 70px;
    margin-bottom: 20px;
    cursor: pointer;
    border: none;
    font-family: Daun;
  }
  #browse button {
    background: #cd9e8f;
  }
  #browse button a {
    color: #fff;
  }
  #browse button a:hover {
    color: #fff;
    text-decoration: none;
  }
  #checkout button {
    background: #c8b65e;
  }
  #checkout_wrapper span {
    font-family: Daun;
  }
  .tab-content > .active {
    margin-top: 30px;
  }
  .coupon_input input {
    height: 60px;
    width: 67%;
    margin-top: 5%;
    font-family: Daun;
    font-size: 40px;
    text-align: center;
    padding-top: 15px;
  }
  .coupon_input button {
    background: #cd9e8f;
    color: #fff;
    font-size: 20px;
    font-weight: 400;
    line-height: 22px;
    letter-spacing: normal;
    text-transform: capitalize;
    width: 28%;
    height: 60px;
    float: right;
    margin-top: 5%;
    margin-right: 3%;
    border: 0;
  }
  .fade-enter-active, .fade-leave-active {
    transition: opacity 1s;
  }
  .fade-enter, .fade-leave-to {
    opacity: 0;
  }
  .subtotal_price span {
    font-size: 50px;
    position: relative;
    top: 20px;
  }
  .subtotal_price {
    width: 20%;
    text-align: center;
  }
  .checkout_table_mobile {
    display: none;
  }
}
@media only screen and (max-width: 1920px){
  .checkout-page-wrapper {
      margin-bottom: 3%;
  }
  .empty-cart div.order-result p {
      text-align: center;
      font-size: 40px;
      font-family: Audrey;
  }
  .empty-cart div.order-result {
      margin-bottom: 5%;
  }
  #checkout_wrapper {
    width: 1440px;
    margin: 0 auto 20px auto;
  }
  .checkout_row, .checkout_subtotal_row {
    background: #fff;
  }
  .checkout_subtotal_row {
    width: 100%;
    height: 100px;
  }
  .checkout_row:nth-last-child(2) {
    box-shadow: none;
    padding-bottom: 20px;
  }
  .subtotal_text, .coupon_row {
    width: 70%;
    float: left;
  }
  .subtotal_text span, .coupon_row span {
    font-size: 55px;
    margin-left: 35%;
    position: relative;
    top: 20px;
  }
  .subtotal_price, .coupon_input {
    float: left;
    width: 30%;
  }
  .checkout_subtotal_row {
    margin: 2% 0;
  }
  .button_wrapper {
    width: 1440px;
    height: 200px;
    margin: 0 auto;
  }
  #browse button, #checkout button {
    width: 100%;
    font-size: 60px;
    color: #fff;
    text-align: center;
    height: 70px;
    margin-bottom: 20px;
    cursor: pointer;
    border: none;
    font-family: Daun;
  }
  #browse button {
    background: #cd9e8f;
  }
  #browse button a {
    color: #fff;
  }
  #browse button a:hover {
    color: #fff;
    text-decoration: none;
  }
  #checkout button {
    background: #c8b65e;
  }
  #checkout_wrapper span {
    font-family: Daun;
  }
  .tab-content > .active {
    margin-top: 30px;
  }
  .coupon_input input {
    height: 60px;
    width: 67%;
    margin-top: 5%;
    font-family: Daun;
    font-size: 40px;
    text-align: center;
    padding-top: 15px;
  }
  .coupon_input button {
    background: #cd9e8f;
    color: #fff;
    font-size: 20px;
    font-weight: 400;
    line-height: 22px;
    letter-spacing: normal;
    text-transform: capitalize;
    width: 28%;
    height: 60px;
    float: right;
    margin-top: 5%;
    margin-right: 3%;
    border: 0;
  }
  .fade-enter-active, .fade-leave-active {
    transition: opacity 1s;
  }
  .fade-enter, .fade-leave-to {
    opacity: 0;
  }
  .subtotal_price span {
    font-size: 50px;
    position: relative;
    top: 20px;
  }
  .subtotal_price {
    width: 20%;
    text-align: center;
  }
  .checkout_table_mobile {
    display: none;
  }
}
@media only screen and (max-width: 1680px){
  .coupon_input input {
    margin-left: 1px;
  }
}
@media only screen and (max-width: 1440px){
}
@media only screen and (max-width: 1366px){
  #checkout_wrapper {
    width: 80%;
  }
  .button_wrapper {
    width: 100%;
  }
  .coupon_input input, .coupon_input button {
    margin-top: 5.6%;
  }
}
@media only screen and (max-width: 1112px){
  #checkout_wrapper {
    width: 90%;
  }
  .subtotal_price span {
    font-size: 40px;
    top: 30px;
  }
  .coupon_input input, .coupon_input button {
    margin-top: 5%;
  }
  .coupon_input input {
    margin-left: 3px;
  }
  .subtotal_text span, .coupon_row span {
    font-size: 45px;
    margin-left: 38%;
    top: 25px;
  }
}
@media only screen and (max-width: 1024px){
  .coupon_input input, .coupon_input button {
    margin-top: 7.8%;
  }
}
@media only screen and (max-width: 834px){
  .checkout_table_mobile {
    display: block;
  }
  #checkout_wrapper {
    width: 100%;
    margin: 0;
  }
  .checkout_mobile_item_wrapper {
    width: 90%;
    margin: auto;
    height: 220px;
    background: #fff;
    margin-bottom: 15px;
  }
  .checkout_mobile_image_wrapper {
    float: left;
    width: 200px;
    margin-right: 5%;
    padding-top: 10px;
    padding-left: 10px;
  }
  .checkout_mobile_image_wrapper img {
    width: 200px;
    border: 1px solid #eee;
  }
  .checkout_mobile_description_wrapper {
    float: left;
    width: 50%;
  }
  .checkout_mobile_description_wrapper {
    text-overflow: ellipsis;
    overflow: hidden;
    width: 65%;
  }
  .checkout_mobile_description span {
    width: 100%;
    float: left;
  }
  .checkout_mobile_description .name {
    font-size: 40px;
  }
  .checkout_mobile_description .logo_text {
    font-size: 35px;
    color: #bbb;
  }
  .price_mobile_wrapper .price span {
    font-size: 40px;
  }
  .price_mobile_wrapper, .close_mobile_wrapper {
    float: left;
    width: 50%;
  }
  .checkout_mobile_description {
    padding-top: 10px;
    height: 170px;
  }
  .close_mobile_wrapper span {
    font-size: 40px;
    float: right;
  }
  .subtotal_text span, .coupon_row span {
    font-size: 40px;
    margin-left: 10px;
    top: 30px;
  }
  .checkout_subtotal_row {
    width: 90%;
    margin: 2% auto;
  }
  .coupon_row, .coupon_input {
    width: 50%;
  }
  .coupon_input input, .coupon_input button {
    margin-top: 20px;
  }
  .subtotal_text {
    width: 80%;
  }
  .subtotal_price {
    text-align: right;
    padding-right: 20px;
  }
  .button_wrapper {
    width: 90%;
  }
}
@media only screen and (max-width: 812px){
  #checkout_wrapper {
    margin-top: 20px;
  }
}
@media only screen and (max-width: 768px){
  .price_mobile_wrapper, .close_mobile_wrapper {
    padding-right: 10px;
  }
  .coupon_input {
    width: 49%;
    margin-left: 1%;
  }
}
@media only screen and (max-width: 767px){
}
@media only screen and (max-width: 736px){
  .checkout_mobile_description_wrapper {
    width: 64%;
  }
}
@media only screen and (max-width: 667px){
  .checkout_mobile_description_wrapper {
    width: 61%;
  }
  #browse button, #checkout button {
    font-size: 50px;
    padding-top: 5px;
  }
  .checkout_subtotal_row {
    height: 70px;
  }
  .subtotal_text span, .coupon_row span {
    font-size: 30px;
    top: 20px;
  }
  .coupon_input input, .coupon_input button {
    height: 50px;
  }
  .coupon_input input, .coupon_input button {
    margin-top: 10px;
  }
  .subtotal_price span {
    font-size: 35px;
    top: 15px;
  }
}
@media only screen and (max-width: 568px){
  .checkout_mobile_image_wrapper img {
    width: 150px;
  }
  .checkout_mobile_item_wrapper {
    height: 170px;
  }
  .checkout_mobile_image_wrapper {
    width: 150px;
  }
  .checkout_mobile_description {
    height: 130px;
  }
  .checkout_mobile_description_wrapper {
    width: 65%;
  }
  .checkout_mobile_description .name, .price_mobile_wrapper .price span, .close_mobile_wrapper span {
    font-size: 30px;
  }
  .checkout_mobile_description .logo_text {
    font-size: 25px;
  }
}
@media only screen and (max-width: 414px){
  .checkout_mobile_image_wrapper img, .checkout_mobile_image_wrapper {
    width: 100px;
  }
  .checkout_mobile_image_wrapper {
    padding-top: 10px;
  }
  .checkout_mobile_description .name, .price_mobile_wrapper .price span, .close_mobile_wrapper span {
    font-size: 20px;
  }
  .checkout_mobile_item_wrapper {
    height: 120px;
  }
  .checkout_mobile_description {
    height: 90px;
  }
  .checkout_mobile_description .logo_text {
    font-size: 20px;
  }
  .checkout_subtotal_row {
    height: 50px;
  }
  .subtotal_text span, .coupon_row span {
    font-size: 20px;
    top: 15px;
  }
  .coupon_input input, .coupon_input button {
    margin-top: 0;
  }
  .coupon_row, .coupon_input {
    width: 45%;
  }
  .coupon_input {
    width: 54%;
    margin-left: 1px;
    margin-top: 5px;
  }
  .coupon_input button {
    font-size: 17px;
    line-height: 25px;
  }
  .coupon_input input, .coupon_input button {
    height: 40px;
  }
  .coupon_input input {
    font-size: 30px;
  }
  .subtotal_price span {
    font-size: 25px;
    top: 10px;
  }
  #browse button, #checkout button {
    font-size: 30px;
    padding-top: 10px;
    height: 50px;
    margin-bottom: 10px;
  }
  .button_wrapper {
    height: 110px;
}
  }
@media only screen and (max-width: 375px){
  .coupon_input button {
    font-size: 14px;
  }
  .subtotal_price span {
    font-size: 22px;
    top: 14px;
  }
}
@media only screen and (max-width: 360px){
  .checkout_mobile_description_wrapper {
    width: 64%;
  }
  .subtotal_price span {
    font-size: 20px;
  }
}
@media only screen and (max-width: 320px){
  .checkout_mobile_description_wrapper {
    width: 60%;
  }
  .checkout_mobile_description .name, .price_mobile_wrapper .price span, .close_mobile_wrapper span {
    font-size: 16px;
  }
  .checkout_mobile_description .logo_text {
    font-size: 14px;
  }
  .coupon_input button {
    font-size: 12px;
  }
  .subtotal_price span {
    font-size: 17px;
  }
  button, #checkout button, #browse button {
    font-size: 25px;
    padding-top: 6px;
    height: 40px;
  }
  .button_wrapper {
    height: 90px;
  }
}
</style>
