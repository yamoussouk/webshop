<template>
  <div id="order_wrapper">
    <table class="table table-filter">
      <tr v-for="detail in order.orderDetails" :key="detail.id" class="order_inner">
        <td>
          <div class="media">
            <img :src="'/uploaded/' + getImageSource(detail)" class="media-photo">
            <div class="media-body">
              <span class="media-meta pull-right">{{ formatPurchaseTime(order) }}</span>
              <h4 class="title">
                {{ getOrderName(detail) }}
                <span class="pull-right pagado"></span>
              </h4>
              <p class="summary">$ {{ getPrice(detail) }}</p>
            </div>
          </div>
        </td>
        <td class="product_details">
            <span v-show="detail.size">Size: {{ detail.size }}</span>
            <span v-show="detail.startingDay">Starting day: {{ detail.startingDay }}</span>
            <span v-show="detail.logoText">Logo text: {{ detail.logoText }}</span>
        </td>
      </tr>
      <tr class="order_details">
        <td><span>Email:</span></td>
        <td><span>{{ order.email }}</span></td>
      </tr>
      <tr class="order_details">
        <td><span>Price:</span></td>
        <td><span>$ {{ order.price }}</span></td>
      </tr>
      <tr v-show="order.coupon" class="order_details">
        <td><span>Used coupon:</span></td>
        <td><span>{{ order.coupon }}</span></td>
      </tr>
    </table>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  middleware: 'authenticated',
  computed: {
    ...mapGetters(['orders']),
    order () {
      return this.orders.find(o => parseInt(o.id) === parseInt(this.id))
    }
  },
  data () {
    return {
      id: this.$route.params.id
    }
  },
  methods: {
    getOrderName (detail) {
      if (detail.logoProduct !== null) {
        return detail.logoProduct.name
      } else {
        return detail.plannerProduct.name
      }
    },
    formatPurchaseTime () {
      const date = new Date(this.order.purchaseTime)
      const y = date.getFullYear()
      let m = date.getMonth()
      let d = date.getDate()
      let h = date.getHours()
      let min = date.getMinutes()
      if (d < 10) {
        d = '0' + d
      }
      if (m < 10) {
        m = '0' + m
      }
      if (h < 10) {
        h = '0' + h
      }
      if (min < 10) {
        min = '0' + min
      }
      return y + '-' + m + '-' + d + ' ' + h + ':' + min
    },
    getImageSource (detail) {
      let id = null
      if (detail.logoProduct !== null) {
        id = detail.logoProduct.id
      } else {
        id = detail.plannerProduct.id
      }
      return id + '/' + detail.image.imageUrl
    },
    getPrice (detail) {
      let price = null
      if (detail.logoProduct !== null) {
        price = detail.logoProduct.price
      } else {
        price = detail.plannerProduct.price
      }
      return price
    }
  }
}
</script>

<style scoped>
#order_wrapper {
    margin-left: 250px;
}
.media-photo {
    width: 15%;
}
.order_details td span, .product_details {
    font-family: Daun;
    font-size: 40px;
}
.order_details td {
    width: 50%;
}
.product_details span {
  word-break: break-all;
}
</style>
