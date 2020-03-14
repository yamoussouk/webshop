<template>
  <div class="main-content">
    <h1>Set Discount</h1>
    <div class="discount-name">
      <p>Add a name to the discount:</p>
        <input v-model="discountName" type="text" class="form-control" required/>
    </div>
    <div class="discount-dropdown">
      <p>Select percentage of the discount:</p>
      <b-form-select v-model="discountPercentage" :options="__makePercentages()" />
    </div>
    <div>
        <b-form-group label="How would you like to set the discount?">
        <b-form-radio v-model="discountRange" name="some-radios" value="without">Without range</b-form-radio>
        <b-form-radio v-model="discountRange" name="some-radios" value="with">With range</b-form-radio>
        </b-form-group>
    </div>
    <div v-if="discountRange === 'with'" class="discount-range">
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
    <error :text="errorText" :left="errorLeft" :right="errorRight" :visible="errorVisible" :cancel.sync="errorVisible"/>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import axios from 'axios'
import Error from '~/components/Error.vue'

export default {
  middleware: 'authenticated',
  components: {
    'date-pick': () => import('vuejs-datepicker'),
    Error
  },
  data () {
    return {
      errorText: '',
      errorLeft: '',
      errorRight: '',
      errorVisible: false,
      nameEq: false,
      productEq: false,
      futureEq: false,
      discountPercentage: 0,
      discountName: '',
      discountRange: '',
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
      discounts: {},
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
    ...mapGetters(['logos', 'planners', 'auth']),
    filteredList () {
      return this.products.filter(e => e.categories.includes(this.group))
    }
  },
  mounted () {
    this.products = this.logos.concat(this.planners)
  },
  created () {
    const headers = {
      'Authorization': this.auth.accessToken
    }
    // eslint-disable-next-line
    axios.get('http://localhost:8083/admin/discount/all', { headers: headers }
    ).then((response) => {
      this.discounts = response.data
      console.log(this.discounts)
    })
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
    },
    set () {
      Object.keys(this.discounts).forEach(function (key) {
        if (this.discounts[key].name === this.discountName) {
          this.nameEq = true
        }
      }.bind(this))
      if (this.nameEq) {
        this.errorText = 'With ' + this.discountName + ' name a discount already exists.'
        this.errorRight = 'Close'
        this.errorVisible = true
        this.nameEq = false
      } else {
        const choosenProducts = this.filteredList.map(e => e.id)
        Object.keys(this.discounts).forEach(function (key) {
          const ids = this.discounts[key].productIds.split(', ')
          if (ids.some(r => choosenProducts.includes(parseInt(r)))) {
            this.productEq = true
          }
        }.bind(this))
        if (this.productEq) {
          Object.keys(this.discounts).forEach(function (key) {
            if (this.discounts[key].to !== '') {
              this.futureEq = true
            }
          }.bind(this))
          if (this.futureEq) {
            this.errorText = 'To this product a future discount is already set.'
            this.errorRight = 'Close'
            this.errorVisible = true
            this.futureEq = false
            this.productEq = false
          } else {
            this.errorText = 'To this product a discount is already set.'
            this.errorRight = 'Close'
            this.errorVisible = true
            this.productEq = false
          }
        } else {
          const headers = {
            'Authorization': this.auth.accessToken
          }
          let df = null
          let dt = null
          const formData = new FormData()
          if (this.dateFrom !== null && this.dateTo !== null) {
            df = new Date(this.dateFrom)
            dt = new Date(this.dateTo)
            df.setHours(0)
            dt.setHours(0)
            df.setMinutes(0)
            dt.setMinutes(0)
            df.setSeconds(0)
            dt.setSeconds(0)
            formData.append('from', Date.parse(df))
            formData.append('to', Date.parse(dt))
          } else {
            formData.append('from', df)
            formData.append('to', dt)
          }
          formData.append('percent', this.discountPercentage)
          formData.append('name', this.discountName)
          formData.append('products', choosenProducts)
          formData.append('enabled', this.discountRange === 'without' ? 1 : 0)
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
