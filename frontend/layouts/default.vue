<template>
  <div>
    <app-top v-show="isDefault()"/>
    <app-nav v-show="isDefault()"/>
    <nuxt />
    <app-footer v-show="isDefault()"/>
  </div>
</template>

<script>
import AppTop from '~/components/AppTop.vue'
import AppNav from '~/components/AppNav.vue'
import AppFooter from '~/components/AppFooter.vue'

export default {
  components: {
    AppTop,
    AppNav,
    AppFooter
  },
  data () {
    return {
      route: this.$route.name
    }
  },
  methods: {
    isDefault () {
      return this.route !== 'login' && !this.route.includes('admin') && this.route !== 'secret'
    }
  }
}
</script>

<style>
@font-face {
  font-family: Meownella;
  src: url(/fonts/MeownellaRegular.ttf);
  src: url(/fonts/MeownellaRegular.woff);
}
@font-face {
  font-family: Audrey;
  src: url(/fonts/Audrey-Normal.ttf);
  src: url(/fonts/Audrey-Normal.woff);
}
@font-face {
  font-family: Rock;
  src: url(/fonts/rock.ttf);
  src: url(/fonts/rock.woff);
}
@font-face {
  font-family: Monoline;
  src: url(/fonts/monoline.ttf);
  src: url(/fonts/monoline.woff);
}
@font-face {
  font-family: Daun;
  src: url(/fonts/DaunPenh.ttf);
  src: url(/fonts/DaunPenh.woff);
}
html {
  font-family: 'Source Sans Pro', -apple-system, BlinkMacSystemFont, 'Segoe UI',
    Roboto, 'Helvetica Neue', Arial, sans-serif;
  font-size: 16px;
  word-spacing: 1px;
  -ms-text-size-adjust: 100%;
  -webkit-text-size-adjust: 100%;
  -moz-osx-font-smoothing: grayscale;
  -webkit-font-smoothing: antialiased;
  box-sizing: border-box;
  background-color: rgba(226,212,203,0.34);
}

*,
*:before,
*:after {
  box-sizing: border-box;
  margin: 0;
}

.button--green {
  display: inline-block;
  border-radius: 4px;
  border: 1px solid #3b8070;
  color: #3b8070;
  text-decoration: none;
  padding: 10px 30px;
}

.button--green:hover {
  color: #fff;
  background-color: #3b8070;
}

.button--grey {
  display: inline-block;
  border-radius: 4px;
  border: 1px solid #35495e;
  color: #35495e;
  text-decoration: none;
  padding: 10px 30px;
  margin-left: 15px;
}

.button--grey:hover {
  color: #fff;
  background-color: #35495e;
}
#__layout {
  background-color: rgba(226,212,203,0.34);
}
</style>
