<template>
  <div class="checkout-page-wrapper">
    <b-container v-if="cart.length !== 0">
      <b-row>
        <b-col md="8">
          <table class="table table-cart">
            <thead>
              <th>Product</th>
              <th>Quantity</th>
              <th>Size</th>
              <th>Starting day</th>
              <th>Price</th>
              <th />
            </thead>
            <tbody>
              <tr v-for="(item, index) in cart" :key="item.id">
                <td>{{ item.name }}</td>
                <td>{{ item.quantity }}</td>
                <td>{{ item.size }}</td>
                <td>{{ item.startingDay }}</td>
                <td>{{ item.price }}</td>
                <td><span class="glyphicon glyphicon-remove" @click="removeFromCart(index)">X</span></td>
              </tr>
            </tbody>
            <tfoot>
              <td><b>Total:</b></td>
              <td />
              <td />
              <td />
              <td style="border: 1px solid;">
                {{ total }}
              </td>
              <td />
            </tfoot>
          </table>
        </b-col>
        <b-col md="4">
          <h1>Order details:</h1>
          <b-form action="#" method="post" @submit="order">
            <b-form-group id="input-group-1" label="Your Email:" label-for="order-email">
              <b-form-input
                id="order-email"
                v-model="form.email"
                type="email"
                required
                placeholder="Enter email"
              />
            </b-form-group>
            <b-form-group id="input-group-2" label="Card Owner:" label-for="order-owner">
              <b-form-input
                id="order-owner"
                v-model="form.owner"
                type="text"
                required
                placeholder="Enter owner"
              />
            </b-form-group>
            <b-form-group id="input-group-3" label="Card Number:" label-for="order-card">
              <b-form-input
                id="order-card"
                v-model="form.cardNumber"
                type="text"
                required
                placeholder="Enter card number"
              />
            </b-form-group>
            <b-form-group id="input-group-4" label="CVV:" label-for="order-cvv">
              <b-form-input
                id="order-cvv"
                v-model="form.cvv"
                type="number"
                required
              />
            </b-form-group>
            <b-form-group id="input-group-5" label="Expiring year:" label-for="order-expYear">
              <b-form-select
                id="order-expYear"
                v-model="form.expYear"
                :options="years"
                required
              />
            </b-form-group>
            <b-form-group id="input-group-6" label="Expiring month:" label-for="order-expMonth">
              <b-form-select
                id="order-expMonth"
                v-model="form.expMonth"
                :options="months"
                required
              />
            </b-form-group>
            <button type="submit">
              Confirm
            </button>
            <span v-show="errors.length > 0" class="errors">
              <span v-for="error in errors" :key="error.id">
                {{ error }}
              </span>
            </span>
          </b-form>
        </b-col>
      </b-row>
    </b-container>
    <div v-else class="empty-cart">
      <div v-show="success" class="order-result">
        <p>{{ message }}</p>
      </div>
      <div class="empty-message">
        <p>Cart is empty</p>
      </div>
      <div class="link-to-shop">
        <nuxt-link to="/shop">
          Back to shop
        </nuxt-link>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      errors: [],
      form: {
        email: '',
        cardNumber: '',
        cvv: '',
        owner: '',
        expYear: '',
        expMonth: ''
      },
      success: false,
      message: '',
      // cart: this.$store.state.localStorage.localCart,
      years: [{ text: 'Select One', value: null }, '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30'],
      months: [{ text: 'Select One', value: null }, '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12']
    }
  },
  computed: {
    total () {
      return this.$store.state.localStorage.localCart.reduce((ac, next) => ac + next.quantity * next.price, 0)
    },
    cart () {
      return this.$store.state.localStorage.localCart
    }
  },
  methods: {
    removeFromCart (index) {
      this.$store.commit('localStorage/removeFromLocalCartByIndex', index)
    },
    emptyLocalCart () {
      this.$store.commit('localStorage/emptyLocalCart')
    },
    validEmail (email) {
      const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      return re.test(email)
    },
    validCVV (cvv) {
      const re = /^\b[0-9]{3}\b$/
      return re.test(cvv)
    },
    validCardNumber (number) {
      const re = /^$/
      return re.test(number)
    },
    order (event) {
      event.preventDefault()
      if (!this.validEmail(this.form.email)) {
        this.errors.push('Valid email required.')
      } else if (!this.validCVV(this.form.cvv)) {
        this.errors.push('Valid CVV required.')
      } else {
        this.errors = []
      }
      if (!this.errors.length) {
        const formObject = {
          'price': this.total,
          'purchaseTime': new Date().getTime(),
          'user': null,
          'email': this.form.email,
          'products': this.cart
        }
        const headers = {
          'Content-Type': 'application/json'
        }
        // eslint-disable-next-line
        axios.post('http://localhost:8083/order/add', JSON.stringify(formObject), {
          // eslint-disable-next-line
          headers: headers
        }).then((response) => {
          this.resetForm()
          this.emptyLocalCart()
          this.success = true
          this.message = 'Product successfully ordered!'
        })
          .catch(function (error) {
            console.log(error)
            this.success = true
            this.message = 'Something went wrong with the order!'
          })
      }
    },
    resetForm () {
      this.form.email = ''
      this.form.owner = ''
      this.form.cardNumber = ''
      this.form.expMonth = ''
      this.form.cvv = ''
      this.form.expYear = ''
    }
  }
}
</script>

<style scoped>
.checkout-page-wrapper {
    margin-top: 3%;
    margin-bottom: 3%;
}
.glyphicon-remove {
    width: 30px;
    height: 30px;
    background-color: #544c62;
    padding: 15px;
    border-radius: 6px;
    color: #fff;
    cursor: pointer;
    opacity: 0.77;
}
.empty-cart div.link-to-shop, .empty-cart div.empty-message {
    float: left;
    width: 49%;
    text-align: center;
    display: table;
}
.empty-cart div.empty-messae {
    margin-right: 1%;
}
.empty-cart div.link-to-shop {
    margin-left: 1%;
}
.empty-cart div.link-to-shop p, .empty-cart div.empty-message p {
    text-align: center;
    font-size: 40px;
    font-family: Audrey;
    display: table-cell;
    vertical-align: middle;
}
.empty-cart div.order-result p {
    text-align: center;
    font-size: 40px;
    font-family: Audrey;
}
.empty-cart div.order-result {
    margin-bottom: 5%;
}
.empty-cart a {
    color: #000;
    font-size: 40px;
    background-color: rgba(163, 153, 178, 0.77);
    padding: 10px;
    border-radius: 10px;
    text-align: center;
    display: table-cell;
    vertical-align: middle;
}
.empty-cart a:hover {
    text-decoration: none;
    color: #000;
}
.container {
    border: 1px solid #fff;
    padding: 10px;
}
.table thead th, .table td {
    font-size: 20px;
    padding: 1rem;
}
form button {
    background-color: #544c62;
    color: #fff;
    width: 100%;
    font-size: 43px;
    font-family: Audrey;
    height: 60px;
    opacity: 0.77;
    border-radius: 10px;
    margin-top: 5%;
    margin-bottom: 2%;
}
.errors span {
    color: #ff0000;
    font-size: 20px;
}
</style>
