<template>
  <div class="checkout_info">
    <div class="left">
      <div class="email">
        <span class="checkout_info_header">1. Your Email</span>
        <span v-show="validatedEmail" @click="editStation" class="edit_button">Edit</span>
        <div v-show="!validatedEmail">
          <input ref="checkout_email" v-model="form.email" type="text">
          <span class="checkout_info_message">You'll receive the product and a receipt!</span>
          <button id="email_continue" type="button" @click="checkEmailAndContinue">
          Continue
          </button>
        </div>
      </div>
      <div class="payment">
        <span class="checkout_info_header">2. Your Payment</span>
        <div v-show="validatedEmail" class="payment_choise">
          <b-tabs>
            <b-tab title="PayPal">
              <paypal
              :items="items"
              :buttonstyle="myStyle"
              :credentials="credentials"
              :total="total"
              :email="form.email"
              :result.sync="result"/>
            </b-tab>
            <b-tab title="Card">
              <card
              :total="total"
              :description="form.email"
              :result.sync="result"/>
            </b-tab>
          </b-tabs>
        </div>
      </div>
      <div class="review" />
    </div>
    <div class="right" />
  </div>
</template>

<script>
import Paypal from '~/components/payments/paypal.vue'
import Card from '~/components/payments/card.vue'
export default {
  components: {
    Paypal,
    Card
  },
  props: {
    items: {
      type: Array,
      default: () => {
        return []
      }
    },
    total: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      form: {
        email: ''
      },
      myStyle: {
        label: 'checkout',
        size: 'responsive',
        shape: 'pill',
        color: 'gold'
      },
      credentials: {
        sandbox: '<YOUR CODE>',
        production: '<YOUR CODE>'
      },
      validatedEmail: false,
      result: ''
    }
  },
  methods: {
    validEmail (email) {
      const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      return re.test(email)
    },
    checkEmailAndContinue () {
      if (!this.validEmail(this.form.email)) {
        this.$refs.checkout_email.style.border = '2px solid rgb(255, 0, 0)'
      } else {
        this.$refs.checkout_email.style.border = '1px solid rgb(0, 0, 0)'
        this.validatedEmail = true
      }
    },
    editStation () {
      this.validatedEmail = !this.validatedEmail
    }
  },
  watch: {
    // eslint-disable-next-line
    result: function (val) {
      if (val) {
        this.$emit('update:result', this.result)
        this.$emit('update:email', this.form.email)
      }
    }
  }
}
</script>

<style>
@media only screen and (max-width: 2560px){
  #email_continue {
    background: #222;
    display: block;
    color: #fff;
    font-size: 30px;
    font-weight: 400;
    line-height: 22px;
    letter-spacing: normal;
    text-transform: capitalize;
    width: 100%;
    height: 60px;
  }
  #purchase_button {
    background: #222;
    display: block;
    color: #fff;
    font-size: 30px;
    font-weight: 400;
    line-height: 22px;
    letter-spacing: normal;
    text-transform: capitalize;
    width: 100%;
    height: 60px;
  }
  .checkout_info {
    width: 1440px;
    margin: 0 auto;
  }
  .checkout_info .left, .checkout_info .right {
    width: 50%;
    float: left;
  }
  .checkout_info .left span {
    font-size: 35px;
  }
  .checkout_info .left .checkout_info_message {
    float: left;
    width: 100%;
    padding: 20px 0 40px 0;
  }
  .checkout_info .left .checkout_info_header {
    font-weight: bold;
    width: 50%;
  }
  .checkout_info .left .edit_button {
    width: 50%;
    float: right;
    text-align: right;
    cursor: pointer;
  }
  .checkout_info .left .email input {
    height: 80px;
    width: 100%;
    font-size: 60px;
    border: 1px solid #000;
    font-family: Daun;
    padding: 25px 0 0 20px;
  }
  .checkout_info .left .email, .checkout_info .left .payment {
    padding: 20px;
    margin-bottom: 30px;
    background: #fff;
  }
  .nav-tabs .nav-link {
    font-size: 40px;
    font-family: Daun;
  }
  .tab-content {
    margin-top: 40px;
  }
}
</style>
