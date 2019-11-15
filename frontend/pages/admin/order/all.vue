<template>
  <div class="main-content">
    <div class="container-fluid mt--6">
      <div class="row">
        <div class="col-xl-12">
          <div class="table_container">
            <table class="table table-filter">
              <tr v-for="order in orders" :key="order.id">
                <td>
                  <div class="media">
                    <img :src="'/uploaded/' + getImageSource(order)" class="media-photo">
                    <div class="media-body">
                      <span class="media-meta pull-right">{{ formatPurchaseTime(order) }}</span>
                      <h4 class="title">
                        {{ getOrderName(order) }}
                        <span class="pull-right pagado"></span>
                      </h4>
                      <p class="summary">$ {{ order.price }}</p>
                    </div>
                    <div class="button_wrapper">
                      <nuxt-link :to="`${order.id}`">
                        <img class="details" :src="'/page_assets/details.png'" alt="order information"/>
                      </nuxt-link>
                    </div>
                  </div>
                </td>
              </tr>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  middleware: 'authenticated',
  computed: {
    ...mapGetters(['orders'])
  },
  mounted () {
    console.log(this.orders)
  },
  methods: {
    getOrderName (order) {
      if (order.orderDetails.logoProduct !== null) {
        return order.orderDetails[0].logoProduct.name
      } else {
        return order.orderDetails[0].plannerProduct.name
      }
    },
    formatPurchaseTime (order) {
      const date = new Date(order.purchaseTime)
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
    getImageSource (order) {
      let id = null
      if (order.orderDetails.logoProduct !== null) {
        id = order.orderDetails[0].logoProduct.id
      } else {
        id = order.orderDetails[0].plannerProduct.id
      }
      return id + '/' + order.orderDetails[0].image.imageUrl
    }
  }
}
</script>

<style>
.main-content {
    margin-left: 250px;
}
.mb-4 {
    margin-bottom: 0 !important;
}
.media-photo {
  width: 130px;
}
.media-body {
  margin-left: 2%;
}
.media-meta {
  font-size: 30px;
  font-family: Daun;
  color: #999;
}
.media .title {
  color: #2BBCDE;
  font-size: 30px;
  font-weight: bold;
  line-height: normal;
  margin: 0;
  font-family: Daun;
}
.media .title span {
  font-size: .8em;
  margin-right: 20px;
}
.details {
  width: 26px;
  cursor: pointer;
}
.button_wrapper {
  margin: 50px 50px 0 0;
}
</style>
