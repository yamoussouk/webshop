<template>
  <div class="main-content">
    <div class="container-fluid mt--6">
      <div class="row">
        <div class="col-xl-12">
          <div v-for="coupon in coupons" :key="coupon.id" class="card">
            <div class="card mb-4">
              <div class="card-body">
                <div v-if="!editMode" class="row">
                  <div class="col-md-2 desc">
                    <div>{{ coupon.name }}</div>
                  </div>
                  <div
                    v-bind:class="{'col-md-6 percentage': coupon.from !== '',
                                   'col-md-9 percentage': coupon.from === ''
                                   }">
                    <span>{{ coupon.percent }}%</span>
                  </div>
                  <div v-if="coupon.from !== ''" class="col-md-3 range">
                    <span>{{ convertDate(coupon.from) }} - </span>
                    <span>{{ convertDate(coupon.to) }}</span>
                  </div>
                  <div class="col-md-1 settings">
                    <div>
                      <img :src="'/edit-button.png'" @click="edit(coupon.id)" alt="edit button">
                      <img :src="freezeSource(coupon.id)" @click="couponEnable(coupon.id)" alt="freeze button">
                      <img v-b-modal.ensure-modal :src="'/remove-button.png'" @click="setId(coupon.id)" alt="remove button">
                    </div>
                  </div>
                </div>
                <div v-else class="row">
                  <div class="col-md-2 desc">
                    <div><input v-model="tempName" type="text" class="form-control"></div>
                  </div>
                  <div class="col-md-9 percentage">
                    <span><input v-model="tempPercent" type="number" class="form-control"></span>
                  </div>
                  <div class="col-md-1 settings">
                    <div>
                      <img :src="'/verified-button.png'" @click="save(coupon.id)" alt="save button">
                      <img :src="'/freeze-button.png'" @click="cancel()" alt="cancel button">
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <b-modal id="ensure-modal" hide-footer>
      <p>Are you sure want to remove the coupon?</p>
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
import { mapGetters } from 'vuex'
import axios from 'axios'

export default {
  middleware: 'authenticated',
  data () {
    return {
      coupons: [],
      tempId: '',
      editMode: false,
      tempName: '',
      tempPercent: 0
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
    axios.get('http://localhost:8083/admin/coupon/all', { headers: headers }
    ).then((response) => {
      this.coupons = response.data
      console.log(this.coupons)
    })
  },
  methods: {
    __getObjectById (id) {
      return Object.keys(this.coupons).find(key => parseInt(this.coupons[key].id) === parseInt(id))
    },
    __getEnabledById (id) {
      const t = this.__getObjectById(id)
      return this.coupons[t].enabled === '1'
    },
    freezeSource (id) {
      return this.__getEnabledById(id) ? '/verified-button.png' : '/freeze-button.png'
    },
    couponEnable (id) {
      const headers = {
        'Authorization': this.auth.accessToken
      }
      // eslint-disable-next-line
      axios.get('http://localhost:8083/admin/enable/coupon/' + id, { headers: headers }
      ).then(() => {
        const t = this.__getObjectById(id)
        const neg = this.__getEnabledById(id) ? '0' : '1'
        this.coupons[t].enabled = neg
        console.log(this.coupons)
      })
    },
    remove (id) {
      const headers = {
        'Authorization': this.auth.accessToken
      }
      // eslint-disable-next-line
      axios.get('http://localhost:8083/admin/delete/coupon/' + id, { headers: headers }
      ).then(() => {
        this.coupons.splice(this.coupons.find(pr => parseInt(pr.id) === parseInt(id)), 1)
        this.tempId = ''
        this.$bvModal.hide('ensure-modal')
      })
    },
    setId (id) {
      this.tempId = id
    },
    edit (id) {
      const c = this.coupons.find(pr => parseInt(pr.id) === parseInt(id))
      this.editMode = !this.editMode
      this.tempName = c.name
      this.tempPercent = c.percent
    },
    cancel () {
      this.editMode = !this.editMode
      this.tempName = ''
      this.tempPercent = 0
    },
    save (id) {
      const c = this.coupons.find(pr => parseInt(pr.id) === parseInt(id))
      const headers = {
        'Authorization': this.auth.accessToken,
        'Content-Type': 'application/json'
      }
      const formObject = {
        // eslint-disable-next-line
        'id': id,
        // eslint-disable-next-line
        'name': this.tempName,
        'percent': this.tempPercent,
        'enabled': c.enabled
      }
      // eslint-disable-next-line
      axios.post('http://localhost:8083/admin/coupon/save', JSON.stringify(formObject), { headers: headers }
      ).then((response) => {
        this.coupons = response.data
        this.editMode = !this.editMode
      })
    },
    convertDate (timestamp) {
      const date = new Date(timestamp / 1000 * 1000)
      const year = date.getFullYear()
      const month = '0' + (date.getMonth() + 1)
      const day = '0' + date.getDate()
      return year + '-' + month.substr(-2) + '-' + day.substr(-2)
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
.price p, .desc div, .percentage span, .range span, .settings div {
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
</style>
