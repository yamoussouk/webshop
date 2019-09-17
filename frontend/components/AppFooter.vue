<template>
  <div id="Footer">
    <app-feed />
    <app-sign-up />
    <app-logo />
    <app-copyright />
  </div>
</template>

<script>
import AppSignUp from '~/components/AppSignUp.vue'
import AppLogo from '~/components/AppLogo.vue'
import AppCopyright from '~/components/AppCopyright.vue'
import AppFeed from '~/components/AppFeed.vue'

export default {
  components: {
    AppSignUp,
    AppLogo,
    AppCopyright,
    AppFeed
  }
}
</script>
<style>
#Footer {
    background-color: #fff;
}
</style>
