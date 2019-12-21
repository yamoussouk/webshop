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
      <span class="header_banner_text_top">create yourself</span>
      <span class="header_banner_text_bottom">PRINTABLE PLANNERS, LOGO DESIGN AND ILLUSTRATION</span>
      <div class="header_banner_icons_mobile">
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
@media only screen and (max-width: 1920px){
  .header_banner {
      height: 240px;
      margin-top: 70px;
      background-color: #fff;
  }
  .header_banner_icons, .header_banner_icons_mobile {
      float: left;
      width: 20%;
      height: 240px;
      padding-top: 2%;
  }
  .header_banner_icon {
      text-decoration: none;
      margin: 0 2%;
      cursor: pointer;
  }
  .header_banner_icon:nth-child(1) {
      margin-left: 10%;
  }
  .header_banner_text {
      float: left;
      width: 60%;
      height: 240px;
  }
  .header_banner_text_top {
      font-size: 100px;
      text-align: center;
      font-family: Monoline;
      width: 100%;
      float: left;
      margin-bottom: -15px;
      margin-top: 20px;
      color: #393839;
  }
  .header_banner_text_bottom {
      font-size: 53px;
      font-family: Daun;
      text-align: center;
      float: left;
      color: #7d7e81;
      width: 100%;
  }
  .header_cart {
      float: left;
      width: 20%;
      height: 240px;
      padding-top: 2%;
  }
  .header_cart > div {
      height: 240px;
      width: 100%;
  }
  .header_cart a {
      display: table;
  }
  .header_cart a img {
      position: relative;
      bottom: 0px;
      left: 30px;
      width: 90%;
  }
  .header_cart a span {
      font-size: 50px;
      font-family: Daun;
      display: table-cell;
      position: relative;
      top: 10px;
      color: #fff;
      left: 20px;
  }
  .cart-wrapper a {
      float: right;
      position: relative;
      right: 0px;
      padding-right: 15%;
      height: 75px;
  }
  .cart-wrapper a:hover {
    text-decoration: none;
  }
  .header_banner_icons_mobile {
    display: none;
  }
}
@media only screen and (max-width: 1680px){
  .header_banner_text_bottom {
    font-size: 51px;
  }
}
@media only screen and (max-width: 1600px){
  .header_banner_text_bottom {
    font-size: 48px;
  }
}
@media only screen and (max-width: 1440px){
  .cart-wrapper a {
    right: 30px;
    top: 30px;
  }
  .header_banner_icon {
    margin: 0 1%;
  }
  .header_banner_icons {
    padding-top: 4%;
  }
  .header_banner_text_bottom {
    font-size: 42px;
  }
}
@media only screen and (max-width: 1366px){
  .header_banner_text_top {
    font-size: 80px;
    margin-bottom: 5px;
  }
  .header_banner_text_bottom {
    font-size: 38px;
  }
  .header_banner_icon img {
    width: 3.8vh;
  }
  .header_banner_icon:nth-child(1) img {
    width: 3vh;
  }
  .header_banner_icon:nth-child(2) img {
    width: 3.5vh;
  }
}
@media only screen and (max-width: 1112px){
  .header_banner_text_top {
    font-size: 70px;
    margin-top: 40px;
  }
  .header_banner_text_bottom {
    font-size: 33px;
  }
  .header_banner_icons {
    padding-top: 7%;
  }
  .cart-wrapper a {
    top: 45px;
  }
}
@media only screen and (max-width: 1024px){
  .header_banner_text_top {
    font-size: 70px;
    margin-top: 10px;
    margin-bottom: 0;
  }
  .header_banner_text_bottom {
    font-size: 33px;
    margin-top: -5px;
  }
  .header_cart, .header_banner_icons {
    display: none;
  }
  .header_banner_icons_mobile {
    width: 100%;
    height: 0;
    padding-top: 2%;
    text-align: center;
    display: block;
  }
  .header_banner_icon:nth-child(1) {
    margin-left: 0;
  }
  .header_banner_text {
    width: 100%;
  }
  .header_banner {
    margin-top: 20px;
  }
}
@media only screen and (max-width: 812px){
  .header_banner_icon:nth-child(1) img {
    width: 2%;
  }
  .header_banner_icon:nth-child(2) img {
    width: 2.3%;
  }
  .header_banner_icon img {
    width: 2.5%;
  }
  .header_cart_nav img {
    width: 10vh;
    padding-top: 10px;
  }
  .header_banner, .header_banner_text {
    height: 200px;
  }
  .header_banner_text_top {
    font-size: 60px;
  }
  .header_banner_text_bottom {
    font-size: 28px;
  }
}
@media only screen and (max-width: 768px){
  .header_banner_icon:nth-child(1) img {
    width: 2vh;
  }
  .header_banner_icon:nth-child(2) img {
    width: 2.3vh;
  }
  .header_banner_icon img {
    width: 2.5vh;
  }
}
@media only screen and (max-width: 767px){
  .header_banner_icon:nth-child(1) img {
    width: 2%;
  }
  .header_banner_icon:nth-child(2) img {
    width: 2.3%;
  }
  .header_banner_icon img {
    width: 2.5%;
  }
}
@media only screen and (max-width: 736px){
}
@media only screen and (max-width: 667px){
}
@media only screen and (max-width: 568px){
  .header_banner, .header_banner_text {
    height: 160px;
  }
  .header_banner_text_top {
    font-size: 50px;
  }
  .header_banner_text_bottom {
    font-size: 20px;
  }
}
@media only screen and (max-width: 414px){
  .header_banner_icon:nth-child(1) img {
    width: 3%;
  }
  .header_banner_icon:nth-child(2) img {
    width: 3.5%;
  }
  .header_banner_icon img {
    width: 4%;
  }
}
@media only screen and (max-width: 375px){
  .header_banner_text_bottom {
    font-size: 19px;
  }
  .header_banner_text_top {
    font-size: 40px;
  }
  .header_banner_icons_mobile {
    padding-top: 0%;
  }
}
@media only screen and (max-width: 320px){
  .header_banner_text_bottom {
    font-size: 17px;
    margin-bottom: 4px;
  }
}
</style>
