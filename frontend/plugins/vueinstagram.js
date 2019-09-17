import Vue from 'vue'
import VueInstagram from 'vue-instagram'

if (process.client) {
  Vue.use(VueInstagram, { name: 'vue-instagram' })
}
