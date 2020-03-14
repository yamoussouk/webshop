<template>
  <div class="main-content">
    <h1>Set Coupon</h1>
    <div class="discount-dropdown">
      <p>Add name of the coupon</p>
      <input v-model="couponName" type="text" class="form-control" required/>
      <p>Add percentage of the coupon:</p>
      <input v-model="couponPercentage" type="number" class="form-control" required/>
    </div>
    <div>
        <b-form-group label="How would you like to set the coupon?">
        <b-form-radio v-model="couponRange" name="some-radios" value="without">Without range</b-form-radio>
        <b-form-radio v-model="couponRange" name="some-radios" value="with">With range</b-form-radio>
        </b-form-group>
    </div>
    <div v-if="couponRange === 'with'" class="discount-range">
      <no-ssr>
        <div class="range-from">
          <p>Select a starting date:</p>
          <date-pick
            ref="dateFrom"
            v-model="dateFrom"
            :format="format"
            :clear-button="true"
            :disabledDates="disabledFrom"
            placeholder="Select From Date"
          />
        </div>
        <div class="range-from">
          <p>Select an end date:</p>
          <date-pick
            ref="dateTo"
            v-model="dateTo"
            :format="format"
            :clear-button="true"
            :disabledDates="disabledTo"
            placeholder="Select To Date"
          />
        </div>
      </no-ssr>
    </div>
    <div class="set-discount">
      <button @click="set">
        SET COUPON
      </button>
      <button @click="reset">
        RESET
      </button>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import axios from 'axios'

export default {
  middleware: 'authenticated',
  components: {
    'date-pick': () => import('vuejs-datepicker')
  },
  data () {
    return {
      couponPercentage: 0,
      couponName: '',
      couponRange: '',
      dateFrom: null,
      dateTo: null,
      format: 'yyyy.MM.dd',
      disabledFrom: {
        customPredictor (date) {
          const today = new Date()
          if (date <= today) {
            return true
          }
        }
      },
      disabledTo: {
        customPredictor (date) {
          const today = new Date()
          today.setHours(24)
          if (date <= today) {
            return true
          }
        }
      }
    }
  },
  computed: {
    ...mapGetters(['auth'])
  },
  methods: {
    reset () {
      this.couponPercentage = 0
      this.couponName = ''
    },
    set () {
      const headers = {
        'Authorization': this.auth.accessToken
      }
      const formData = new FormData()
      if (this.dateFrom !== null && this.dateTo !== null) {
        const df = new Date(this.dateFrom)
        const dt = new Date(this.dateTo)
        df.setHours(0)
        dt.setHours(0)
        df.setMinutes(0)
        dt.setMinutes(0)
        df.setSeconds(0)
        dt.setSeconds(0)
        formData.append('from', Date.parse(df))
        formData.append('to', Date.parse(dt))
        // formData.append('from', '1583442670000')
        // formData.append('to', '1583442730000')
      }
      formData.append('name', this.couponName)
      formData.append('percent', this.couponPercentage)
      // eslint-disable-next-line
      axios.post('http://localhost:8083/admin/coupon/set', formData, {
        // eslint-disable-next-line
        headers: headers
      }).then((response) => {
        this.reset()
      })
        .catch(function (error) {
          console.log(error)
        })
    }
  }
}
</script>

<style scoped>
.discount-dropdown p, .discount-range p {
  width: 28%;
  float: left;
}
.discount-dropdown select, .discount-range select, .discount-products select {
  width: 50%;
}
.discount-range, .discount-dropdown, .range-from {
  clear: both;
  margin-bottom: 15px;
}
.vdp-datepicker {
  width: 50%;
}
.discount-product div {
  width: 100%;
  height: 100px;
}
.discount-product img {
  width: 100px;
  height: 100px;
  margin-right: 5%;
  float: left;
}
.discount-product p:nth-child(2) {
  width: 50%;
  font-size: 25px;
  float: left;
  padding-top: 35px;
}
.discount-product p:nth-child(3) {
  font-size: 25px;
  padding-top: 35px;
  cursor: pointer;
}
.discount-product {
  list-style: none;
  clear: both;
  height: 100px;
  margin-bottom: 5px;
}
.products-frame ul {
  margin-top: 10px;
  height: 400px;
  overflow: scroll;
  width: 80%;
  border: 1px solid #000;
  background: #fff;
}.products-frame ul li:nth-child(2n) {
  background: #eee;
}
</style>
