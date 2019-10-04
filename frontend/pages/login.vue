<template>
  <div class="container">
    <h1>Sign in to access the secret page</h1>
    <div>
      <label for="email">
        <input id="email" v-model="email" type="email" value="test">
      </label>
      <label for="password">
        <input id="password" v-model="password" type="password" value="test">
      </label>
      <button @click="postLogin">
        login
      </button>
      <p>The credentials are not verified for the example purpose.</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import VueJwtDecode from 'vue-jwt-decode'
const Cookie = process.client ? require('js-cookie') : undefined

export default {
  data () {
    return {
      email: '',
      password: ''
    }
  },
  middleware: 'notAuthenticated',
  methods: {
    postLogin () {
      axios.post('http://localhost:8083/auth', {
        'username': this.email,
        'password': this.password
      })
        .then((resp) => {
          const token = resp.data.token
          const auth = {
            accessToken: 'Bearer ' + token,
            user: VueJwtDecode.decode(token).sub
          }
          this.$store.commit('setAuth', auth)
          // axios.defaults.headers.common.Authorization = token
          Cookie.set('auth', 'Bearer ' + token)
          this.$router.push('/admin')
        })
        .catch((err) => {
          console.log(err)
          console.log('Do something, wrong credentials')
        })
    }
  }
}
</script>
