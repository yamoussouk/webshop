<template>
  <div class="main-content">
    <div class="container-fluid mt--6">
      <div class="row">
        <div class="col-xl-12">
          <div class="table_container">
            <table class="table table-filter">
              <tr v-for="(discount, index) in discounts" :key="discount.from">
                <td>
                  <div class="discounts-wrapper">
                    <span>Discount from: {{ formatPurchaseTime(discount.from) }}</span>
                    <span>Discount to: {{ formatPurchaseTime(discount.to) }}</span>
                    <span>Discount: {{ discount.percent }} %</span>
                    <span>Products: {{ discount.products }}</span>
                    <span @click="cancelDiscount(index)">Cancel discount</span>
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
import axios from 'axios'

export default {
  middleware: 'authenticated',
  data () {
    return {
      discounts: []
    }
  },
  computed: {
    ...mapGetters(['auth'])
  },
  created () {
    const headers = {
      'Authorization': this.auth.accessToken
    }
    // eslint-disable-next-line
    axios.get('http://localhost:8083/admin/discount/all', { headers: headers }
    ).then((response) => {
      this.discounts = response.data
    })
  },
  methods: {
    formatPurchaseTime (timestamp) {
      const str = timestamp.substring(0, timestamp.length - 3)
      const date = new Date(str * 1000)
      const y = date.getFullYear()
      let m = date.getMonth() + 1
      let d = date.getDate()
      let h = date.getHours()
      let min = date.getMinutes()
      let sec = date.getSeconds()
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
      if (sec < 10) {
        sec = '0' + sec
      }
      return y + '-' + m + '-' + d + ' ' + h + ':' + min + ':' + sec
    },
    cancelDiscount (index) {
      const headers = {
        'Authorization': this.auth.accessToken
      }
      // eslint-disable-next-line
      axios.get('http://localhost:8083/admin/discount/cancel/' + index, { headers: headers }
      ).then((response) => {
        this.discounts = response.data
      })
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
.discounts-wrapper {
  width: 50%;
  margin: auto auto;
  font-size: 30px;
}
.discounts-wrapper span {
  width: 100%;
  float: left;
}
</style>
