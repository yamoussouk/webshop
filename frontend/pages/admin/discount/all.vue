<template>
  <div class="main-content">
    <div class="container-fluid mt--6">
      <div class="row">
        <div class="col-xl-12">
          <div v-for="discount in discounts" :key="discount.id" class="card">
            <div class="card mb-4">
              <div class="card-body">
                <div class="row">
                  <div class="col-md-2 desc">
                    <div v-for="(name, idx) in convertListFromNames(discount.productNames)" :key="convertListFromIds(idx, discount)">
                      {{ name }}
                    </div>
                  </div>
                  <div
                    v-bind:class="{'col-md-6 percentage': discount.from !== '',
                                   'col-md-9 percentage': discount.from === ''
                                   }">
                    <span>{{ discount.percent }}%</span>
                  </div>
                  <div v-if="discount.from !== ''" class="col-md-3 range">
                    <span>{{ convertDate(discount.from) }} - </span>
                    <span>{{ convertDate(discount.to) }}</span>
                  </div>
                  <div class="col-md-1 settings">
                    <div>
                      <img :src="freezeSource(discount.id)" @click="discountEnable(discount.id)" alt="freeze button">
                      <img :src="'/remove-button.png'" @click="setId(discount.id)" alt="remove button">
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <error
    :text="errorText"
    :left="errorLeft"
    :right="errorRight"
    :visible="errorVisible"
    :cancel.sync="errorVisible"
    @done="remove"/>
    <b-modal id="ensure-modal" hide-footer>
      <p>Are you sure want to remove the discount?</p>
      <b-button @click="$bvModal.hide('ensure-modal')" class="mt-3" block>
        Cancel
      </b-button>
      <b-button @click="remove(tempId)" class="mt-3" block>
        Remove
      </b-button>
    </b-modal>
  </div>
</template>

<script>
import Vue from 'vue'
import { mapGetters } from 'vuex'
import axios from 'axios'
import Error from '~/components/Error.vue'

export default {
  middleware: 'authenticated',
  components: {
    Error
  },
  data () {
    return {
      discounts: [],
      tempId: '',
      errorText: 'Are you sure want to remove the discount?',
      errorLeft: 'Remove',
      errorRight: 'Cancel',
      errorVisible: false
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
    __getObjectById (id) {
      return Object.keys(this.discounts).find(key => parseInt(this.discounts[key].id) === parseInt(id))
    },
    __getEnabledById (id) {
      const t = this.__getObjectById(id)
      return this.discounts[t].enabled === '1'
    },
    convertDate (timestamp) {
      const date = new Date(timestamp / 1000 * 1000)
      const year = date.getFullYear()
      const month = '0' + (date.getMonth() + 1)
      const day = '0' + date.getDate()
      return year + '-' + month.substr(-2) + '-' + day.substr(-2)
    },
    convertListFromNames (productNames) {
      return productNames.split(', ')
    },
    convertListFromIds (idx, discount) {
      return discount.productIds.split(', ')[idx]
    },
    freezeSource (id) {
      return this.__getEnabledById(id) ? '/verified-button.png' : '/freeze-button.png'
    },
    setId (id) {
      this.tempId = id
      this.errorVisible = true
    },
    discountEnable (id) {
      const headers = {
        'Authorization': this.auth.accessToken
      }
      const formData = new FormData()
      const o = this.__getObjectById(id)
      const type = this.discounts[o].from === '' ? 'normal' : 'range'
      formData.append('type', type)
      // eslint-disable-next-line
      axios.post('http://localhost:8083/admin/enable/discount/' + id, formData, { headers: headers }
      ).then(() => {
        const t = this.__getObjectById(id)
        const neg = this.__getEnabledById(id) ? '0' : '1'
        this.discounts[t].enabled = neg
        if (type === 'range') {
          this.discounts[t].from = ''
          this.discounts[t].to = ''
        }
      })
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
    },
    remove () {
      const headers = {
        'Authorization': this.auth.accessToken
      }
      // eslint-disable-next-line
      axios.get('http://localhost:8083/admin/delete/discount/' + this.tempId, { headers: headers }
      ).then(() => {
        Vue.delete(this.discounts, this.__getObjectById(this.tempId))
        this.tempId = ''
        this.errorVisible = false
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
.desc div p {
    text-overflow: ellipsis;
    overflow: hidden;
    width: 100%;
    height: 1.2em;
    white-space: nowrap;
    margin-bottom: 0;
}
.settings img {
    float: left;
    width: 25%;
    margin-right: 3%;
    cursor: pointer;
}
.price, .desc, .percentage, .settings, .range {
    display: table;
    height: 64px;
}
.price p, .percentage span, .range span, .settings div {
    display: table-cell;
    vertical-align: middle;
}
.cat span {
  width: 100%;
  float: left;
}
.product-thumbnail {
  width: 64px;
  height: 64px;
}
.disabled {
  filter: opacity(0.5);
}
.card {
  height: 106px;
  overflow: hidden;
}
</style>
