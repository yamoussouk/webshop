<template>
  <div>
    <div id="card-element"></div>
    <span class="card_info">Transactions are secure and encrypted.</span>
    <button id="purchase_button" @click="purchase">Purchase</button>
    <div ref="card_errors" id="card_errors" role="alert"></div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data () {
    return {
      imports: undefined,
      card: undefined
    }
  },
  props: {
    total: {
      type: String,
      default: ''
    },
    description: {
      type: String,
      default: 'Charge'
    }
  },
  mounted () {
    this.$nextTick(function () {
      if (this.card === undefined) {
        this.imports = this.$stripe.import()
        const elements = this.imports.elements()
        this.card = elements.create('card', {
          hidePostalCode: true,
          style: {
            base: {
              iconColor: '#666EE8',
              color: '#31325F',
              lineHeight: '40px',
              fontWeight: 300,
              fontFamily: '"Helvetica Neue", Helvetica, sans-serif',
              fontSize: '15px',
              '::placeholder': {
                color: '#CFD7E0'
              }
            }
          }
        })
        // Add an instance of the card Element into the `card-element` <div>
        this.card.mount('#card-element')
      }
    })
  },
  methods: {
    purchase () {
      this.imports.createToken(this.card).then(function (result) {
        if (result.error) {
          // Inform the customer that there was an error.
          const errorElement = this.$refs.card_errors
          errorElement.textContent = result.error.message
        } else {
          const params = new URLSearchParams()
          console.log(this.total)
          params.append('amount', (parseFloat(this.total) * 100))
          params.append('currency', 'usd')
          params.append('description', 'Test charging')
          params.append('source', result.token.id)
          const headers = {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Bearer <YOUR SECRET>'
          }
          const errorElement = this.$refs.card_errors
          // eslint-disable-next-line
          axios.post('https://api.stripe.com/v1/charges', params, { headers: headers }
          ).then((response) => {
            console.log(response)
            errorElement.style.display = 'none'
            this.$emit('update:result', 'COMPLETED')
          })
            .catch((error) => {
              errorElement.textContent = error.response.data.error.message
              errorElement.style.display = 'block'
              this.$emit('update:result', 'ERROR')
            })
        }
      }.bind(this))
    }
  }
}
</script>
<style>
.card_info {
    font-size: 11px !important;
    line-height: 22px;
    color: #a1a1a1;
}
#card-element {
  box-sizing: border-box;
  border: 1px solid #e4e4e4;
  border-radius: 3px;
  padding: 11px;
  padding-right: 0;
}
#card_errors {
  display: none;
  margin-left: -22px;
  margin-right: -22px;
  margin-top: 11px;
  color: #ffffff;
  background: #ec3e3d;
  padding: 3px 22px;
  font-size: 12px;
  line-height: 22px;
  font-weight: 400;
}
</style>
