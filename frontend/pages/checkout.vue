<template>
    <div class="checkout-page-wrapper">
        <b-container v-if="cart.length !== 0">
            <b-row>
                <b-col md="8">
                    <table class="table table-cart">
                    <thead>
                        <th>Product</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th></th>
                    </thead>
                    <tbody>
                        <tr v-for="(item, index) in cart" v-bind:key="item">
                            <td>{{ item.name }}</td>
                            <td>{{ item.quantity }}</td>
                            <td>{{ item.price }}</td>
                            <td><span class="glyphicon glyphicon-remove" @click="removeFromCart(index)">X</span></td>
                        </tr>
                    </tbody>
                    <tfoot>
                        <td><b>Total:</b></td>
                        <td></td>
                        <td style="border: 1px solid;">{{ total }}</td>
                        <td></td>
                    </tfoot>
                </table>
                </b-col>
                <b-col md="4">
                    <h1>Order details:</h1>
                    <b-form v-on:submit="order" action="#" method="post">
                    <b-form-group id="input-group-1" label="Your Email:" label-for="order-email">
                        <b-form-input
                            type="email"
                            id="order-email"
                            v-model="form.email"
                            required
                            placeholder="Enter email">
                        </b-form-input>
                    </b-form-group>
                    <b-form-group id="input-group-2" label="Card Owner:" label-for="order-owner">
                        <b-form-input
                            type="string"
                            id="order-owner"
                            v-model="form.owner"
                            required
                            placeholder="Enter owner">
                        </b-form-input>
                    </b-form-group>
                    <b-form-group id="input-group-3" label="Card Number:" label-for="order-card">
                        <b-form-input
                            type="text"
                            id="order-card"
                            v-model="form.cardNumber"
                            required
                            placeholder="Enter card number">
                        </b-form-input>
                    </b-form-group>
                    <b-form-group id="input-group-4" label="CVV:" label-for="order-cvv">
                        <b-form-input
                            type="number"
                            id="order-cvv"
                            v-model="form.cvv"
                            required>
                        </b-form-input>
                    </b-form-group>
                    <b-form-group id="input-group-5" label="Expiring year:" label-for="order-expYear">
                        <b-form-select
                        id="order-expYear"
                        v-model="form.expYear"
                        :options="years"
                        required
                        ></b-form-select>
                    </b-form-group>
                    <b-form-group id="input-group-6" label="Expiring month:" label-for="order-expMonth">
                        <b-form-select
                        id="order-expMonth"
                        v-model="form.expMonth"
                        :options="months"
                        required
                        ></b-form-select>
                    </b-form-group>
                    <button type="submit">Confirm</button>
                    <span class="errors" v-show="errors.length > 0">
                        <span v-for="error in errors" v-bind:key="error">
                            {{ error }}
                        </span>
                    </span>
                </b-form>
                </b-col>
            </b-row>
        </b-container>
        <div v-else class="empty-cart">
            <div>
                <p>Cart is empty</p>
            </div>
            <div>
                <nuxt-link to="/shop">Back to shop</nuxt-link>
            </div>
        </div>
    </div>
</template>

<script>
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
      cart: this.$store.state.localStorage.localCart,
      years: [{ text: 'Select One', value: null }, '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30'],
      months: [{ text: 'Select One', value: null }, '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12']
    }
  },
  computed: {
    total () {
      return this.$store.state.localStorage.localCart.reduce((ac, next) => ac + next.quantity * next.price, 0)
    }
  },
  methods: {
    removeFromCart (index) {
      this.$store.commit('localStorage/removeFromLocalCartByIndex', index)
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
        this.resetForm()
        this.emptyCart()
        console.log(this.form)
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
.empty-cart div {
    float: left;
    width: 49%;
    text-align: center;
    display: table;
}
.empty-cart div:nth-child(1) {
    margin-right: 1%;
}
.empty-cart div:nth-child(2) {
    margin-left: 1%;
}
.empty-cart p {
    text-align: center;
    font-size: 40px;
    font-family: Audrey;
    display: table-cell;
    vertical-align: middle;
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
