import Vue from 'vue'
import { VueEditor } from 'vue2-editor'

if (process.client) {
  Vue.use(VueEditor, { name: 'vue-editor' })
}
