import Vue from 'vue'
import VueCarousel from 'vue-carousel'

if (process.client) {
  Vue.use(VueCarousel, { name: 'vue-carousel' })
}
