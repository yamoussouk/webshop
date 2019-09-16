<template>
  <div id="Footer">
    <app-sign-up />
    <app-logo />
    <app-copyright />
  </div>
</template>

<script>
import AppSignUp from '~/components/AppSignUp.vue'
import AppLogo from '~/components/AppLogo.vue'
import AppCopyright from '~/components/AppCopyright.vue'

export default {
  components: {
    AppSignUp,
    AppLogo,
    AppCopyright
  }
}
</script>
<style>
#Footer {
    background-color: #fff;
}
</style>
