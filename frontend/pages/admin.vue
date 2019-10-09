<template>
  <div>
    <admin-side-bar />
    <nuxt-child />
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import axios from 'axios'
import AdminSideBar from '~/components/AdminSidebar.vue'

export default {
  middleware: 'authenticated',
  components: {
    AdminSideBar
  },
  computed: {
    ...mapGetters(['auth'])
  },
  created () {
    const headers = {
      'Authorization': this.auth.accessToken
    }
    // eslint-disable-next-line
    axios.get('http://localhost:8083/admin/products/all', { headers: headers }
    ).then((response) => {
      this.setProducts(response.data)
    })
  },
  methods: {
    ...mapActions(['setProducts'])
  }
}
</script>
