import Vue from 'vue'
import VuejsDatePicker from 'vuejs-datepicker'

if (process.client) {
  Vue.use(VuejsDatePicker, { name: 'datepicker' })
}
