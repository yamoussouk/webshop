<template>
  <div>
    <admin-side-bar />
    <nuxt-child />
  </div>
</template>

<script>
import AdminSideBar from '~/components/AdminSidebar.vue'

export default {
  middleware: 'authenticated',
  components: {
    AdminSideBar
  }
}
</script>
