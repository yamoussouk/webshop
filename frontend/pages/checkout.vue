<template>
  <div class="checkout-page-wrapper">
    <bread-crumb :title="title" :to="shop" :back="back" />
    <div id="checkout_wrapper">
      <div v-if="cart.length !== 0">
        <checkout-table :cart="cart" />
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
      coupon: '',
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
        return ('' + (this.$store.state.localStorage.localCart.reduce((ac, next) => ac + next.quantity * next.price, 0) * (1 - this.discount)).toFixed(2)).replace('.', ',')
      } else {
        return ('' + this.$store.state.localStorage.localCart.reduce((ac, next) => ac + next.quantity * next.price, 0).toFixed(2)).replace('.', ',')
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
    }
  },
  watch: {
    // eslint-disable-next-line
    result: function(val) {
      if (val === 'COMPLETED') {
        const planners = this.cart.filter(pr => pr.type === 'Planner')
        const logos = this.cart.filter(pr => pr.type === 'Logo')
        const formObject = {
          'price': parseFloat(this.total.replace(',', '.')),
          'purchaseTime': new Date().getTime(),
          'user': null,
          'email': this.email,
          // eslint-disable-next-line
          'planners': planners,
          // eslint-disable-next-line
          'logos': logos
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
  font-size: 30px;
  color: #fff;
  text-align: center;
  height: 70px;
  margin-bottom: 20px;
  cursor: pointer;
  border: none;
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
</style>
