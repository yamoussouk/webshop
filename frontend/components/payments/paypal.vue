<template>
  <div>
    <client-only>
      <paypal-checkout
        :env="env"
        :amount="fixedTotal"
        currency="USD"
        locale="en_US"
        :client="credentials"
        :items="items"
        :button-style="buttonstyle"
        :invoice-number="invoice"
        @payment-completed="completed"
        @payment-cancelled="cancelled"
      />
    </client-only>
  </div>
</template>

<script>
export default {
  props: {
    total: {
      type: String,
      default: ''
    },
    credentials: {
      type: Object,
      defualt: () => {
        return []
      }
    },
    buttonstyle: {
      type: Object,
      default: () => {
        return {}
      }
    },
    invoice: {
      type: String,
      default: ''
    },
    env: {
      type: String,
      default: 'sandbox'
    },
    items: {
      type: Array,
      default: () => {
        return []
      }
    },
    email: {
      type: String,
      default: ''
    }
  },
  computed: {
    fixedTotal () {
      return this.total.replace(',', '.')
    }
  },
  methods: {
    cancelled () {
      this.$emit('update:result', 'COMPLETED')
    },
    completed () {
      this.$emit('update:result', 'COMPLETED')
    }
  }
}
</script>
