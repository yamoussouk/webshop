<template>
  <div class="main-content">
    <h1>Set Discount</h1>
    <div class="discount-dropdown">
      <p>Select percentage of the discount:</p>
      <b-form-select v-model="discountPercentage" :options="__makePercentages()" />
    </div>
    <div class="discount-range">
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
    <div class="discount-products">
      <div class="products-frame">
        <b-form-select v-model="group" :options="categories()" />
        <ul v-show="group != ''">
          <li v-for="product in filteredList" :key="product.id" class="discount-product">
            <div>
              <img :src="'/uploaded/' + product.id + '/' + product.image[0].imageUrl" alt="product image">
              <p>{{ product.name }}</p>
              <p @click="remove(product.id)">
                x
              </p>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <div class="set-discount">
      <button @click="set">
        SET DISCOUNT
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
      discountPercentage: 0,
      group: '',
      percentages: [
        { text: '10%', value: 10 },
        { text: '20%', value: 20 },
        { text: '30%', value: 30 },
        { text: '40%', value: 40 },
        { text: '50%', value: 50 },
        { text: '60%', value: 60 },
        { text: '70%', value: 70 },
        { text: '80%', value: 80 },
        { text: '90%', value: 90 }
      ],
      dateFrom: null,
      dateTo: null,
      format: 'yyyy.MM.dd',
      products: [],
      disabledFrom: {
        customPredictor (date) {
          const today = new Date()
          if (date.getDate() <= today.getDate()) {
            return true
          }
        }
      },
      disabledTo: {
        customPredictor (date) {
          const today = new Date()
          today.setHours(24)
          if (date.getDate() <= today.getDate()) {
            return true
          }
        }
      }
    }
  },
  computed: {
    ...mapGetters(['logos', 'planners', 'auth']),
    filteredList () {
      return this.products.filter(e => e.categories.includes(this.group))
    }
  },
  mounted () {
    this.products = this.logos.concat(this.planners)
  },
  methods: {
    categories () {
      const logos = this.logos.map(o => o.categories)
      const planners = this.planners.map(o => o.categories)
      const logosCats = this.__getCat(logos)
      const plannersCats = this.__getCat(planners)
      return this.__makeOptions(logosCats.concat(plannersCats))
    },
    __getCat (a) {
      const cats = []
      for (const t of a) {
        for (const e of t) {
          if (!cats.includes(e)) {
            cats.push(e)
          }
        }
      }
      return cats
    },
    __makeOptions (a) {
      const options = []
      for (const c of a) {
        options.push({ text: c, value: c })
      }
      return options
    },
    __makePercentages () {
      const percentages = []
      for (let i = 1; i < 10; i++) {
        percentages.push({ text: 10 * i + '%', value: 10 * i })
      }
      return percentages
    },
    remove (id) {
      this.products.splice(this.products.findIndex(pr => parseInt(pr.id) === parseInt(id)), 1)
    },
    reset () {
      this.group = ''
      this.discountPercentage = ''
      this.$refs.dateFrom.selectedDate = ''
      this.$refs.dateTo.selectedDate = ''
    },
    set () {
      const headers = {
        'Authorization': this.auth.accessToken
      }
      const df = new Date(this.dateFrom)
      const dt = new Date(this.dateTo)
      df.setHours(0)
      dt.setHours(0)
      df.setMinutes(0)
      dt.setMinutes(0)
      df.setSeconds(0)
      dt.setSeconds(0)
      const formData = new FormData()
      formData.append('percent', this.discountPercentage)
      formData.append('from', Date.parse(df))
      formData.append('to', Date.parse(dt))
      formData.append('products', this.filteredList.map(e => e.id))
      // eslint-disable-next-line
      axios.post('http://localhost:8083/admin/discount/set', formData, {
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
