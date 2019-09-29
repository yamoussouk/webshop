<template>
  <div class="header_banner">
    <div class="header_banner_icons">
      <a class="header_banner_icon" href="#">
        <img src="/page_assets/facebook.png" alt="facebook">
      </a>
      <a class="header_banner_icon" href="#">
        <img src="/page_assets/tumblr.png" alt="tumblr">
      </a>
      <a class="header_banner_icon" href="#">
        <img src="/page_assets/pinterest.png" alt="pinterest">
      </a>
      <a class="header_banner_icon" href="#">
        <img src="/page_assets/instagram.png" alt="instagram">
      </a>
      <a class="header_banner_icon" href="#">
        <img src="/page_assets/etsy.png" alt="etsy">
      </a>
    </div>
    <div class="header_banner_text">
      <span class="header_banner_text_top">CREATE YOURSELF</span>
      <span class="header_banner_text_bottom">PRINTABLE PLANNERS, LOGO DESIGN AND ILLUSTRATION</span>
    </div>
    <div class="header_cart">
      <app-cart />
    </div>
  </div>
</template>

<script>
import AppCart from '~/components/AppCart.vue'

export default {
  components: {
    AppCart
  },
  data () {
    return {
      cart: this.$store.state.localStorage.localCart
    }
  },
  computed: {
    cartCount () {
      return this.cart.reduce((ac, next) => ac + next.quantity, 0)
    },
    cartSource () {
      return this.cartCount > 0 ? '/page_assets/cart_white.png' : '/page_assets/cart_black.png'
    }
  }
}
</script>
<style>
.header_banner {
    height: 240px;
    margin-top: 70px;
    background-color: #fff;
    -webkit-box-shadow: 3px 3px 5px 6px #ccc;
    box-shadow: 3px 3px 5px 6px #ccc;
    -moz-box-shadow: 3px 3px 5px 6px #ccc;
    border-top: 1px solid #ccc;
}
.header_banner_icons {
    float: left;
    width: 20%;
    margin-left: 5%;
    display: table;
    height: 240px;
}
.header_banner_icon {
    text-decoration: none;
    margin: 0 2%;
    cursor: pointer;
    display: table-cell;
    vertical-align: middle;
}
.header_banner_text {
    float: left;
    width: 55%;
    margin-left: 10%;
    height: 240px;
}
.header_banner_text_top {
    font-size: 92px;
    text-align: center;
    font-family: Rock;
    width: 60%;
    float: left;
    margin-bottom: 30px;
}
.header_banner_text_bottom {
    font-size: 22px;
    font-family: Audrey;
    text-align: center;
    float: left;
}
.header_cart {
    float: left;
    width: 6%;
    height: 240px;
}
.header_cart > div {
    display: table;
    height: 240px;
    width: 100%;
}
.header_cart > div > div {
    display: table-cell;
    vertical-align: middle;
}
.header_cart a {
    display: table;
}
.header_cart a img {
    position: relative;
    bottom: 6px;
    left: 2px;
}
.header_cart a span {
    font-size: 40px;
    font-family: Audrey;
    display: table-cell;
    position: relative;
    top: 5px;
    padding-left: 10%;
    color: #fff;
}
</style>
