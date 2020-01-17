<template>
  <div class="main-content">
    <h1>VAT Statistic</h1>
    <div class="table_container">
      <table class="table table-filter">
        <tr v-for="(value, key) in vat" :key="value">
          <td>
            {{ key }}
          </td>
          <td>
            $ {{ value }}
          </td>
        </tr>
      </table>
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
      vat: []
    }
  },
  computed: {
    ...mapGetters(['auth'])
  },
  created () {
    const headers = {
      'Authorization': this.auth.accessToken
    }
    console.log(this.auth.accessToken)
    // eslint-disable-next-line
    axios.get('http://localhost:8083/order/allvat', { headers: headers }
    ).then((response) => {
      this.vat = response.data
    })
  }
}
</script>

<style scoped>

</style>
