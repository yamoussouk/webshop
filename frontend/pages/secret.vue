<template>
  <div>
    <h1>This is secret page.</h1>
    <NuxtLink to="/">
      Back home
    </NuxtLink>
  </div>
</template>

<script>
import axios from 'axios'
import { mapGetters } from 'vuex'

export default {
  middleware: 'authenticated',
  computed: {
    ...mapGetters(['auth'])
  },
  created () {
    const config = {
      headers: {
        'Authorization': this.auth.accessToken
      }
    }
    axios.get('http://localhost:8083/admin/products/all', config
    ).then((response) => {
      console.log(response.data)
    })
      .catch(function (error) {
        console.log(error)
      })
  }
}
</script>
