<template>
  <div id="Footer">
    <app-feed v-show="isHome"/>
    <app-sign-up />
    <app-icons />
    <app-logo />
    <app-copyright />
  </div>
</template>

<script>
import AppSignUp from '~/components/AppSignUp.vue'
import AppLogo from '~/components/AppLogo.vue'
import AppCopyright from '~/components/AppCopyright.vue'
import AppFeed from '~/components/AppFeed.vue'
import AppIcons from '~/components/AppIcons.vue'

export default {
  components: {
    AppSignUp,
    AppLogo,
    AppCopyright,
    AppFeed,
    AppIcons
  },
  computed: {
    isHome () {
      return this.$route.path === '/'
    }
  }
}
</script>
<style>
#Footer {
    background-color: #fff;
    clear: both;
}
</style>
