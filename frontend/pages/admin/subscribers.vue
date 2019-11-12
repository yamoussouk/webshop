<template>
  <div class="main-content">
    <div class="container-fluid mt--6">
      <div class="row">
        <div class="col-xl-12">
          <table>
            <thead>
              <tr>
                <th>Subscribers</th>
                <th>Enabled</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="subscriber in subscribers" :key="subscriber.id">
                <td>{{ subscriber.email }}</td>
                <td>{{ subscriber.isEnabled }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapGetters, mapActions } from 'vuex'

export default {
  computed: {
    ...mapGetters(['subscribers', 'auth'])
  },
  mounted () {
    const headers = {
      'Authorization': this.auth.accessToken
    }
    // eslint-disable-next-line
    axios.get('http://localhost:8083/admin/subscribers/all', { headers: headers }
    ).then((response) => {
      this.setSubscribers(response.data)
    })
  },
  methods: {
    ...mapActions(['setSubscribers'])
  }
}
</script>

<style>
.main-content {
    margin-left: 250px;
}
</style>
