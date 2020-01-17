<template>
  <nav class="menu_wrapper">
    <div class="inner">
      <ul>
        <li>
          <nuxt-link exact to="/">
            <span>HOME</span>
          </nuxt-link>
        </li>
        <li>
          <nuxt-link to="/shop">
            <span>SHOP</span>
          </nuxt-link>
        </li>
        <li>
          <nuxt-link to="/freebies">
            <span>FREEBIES</span>
          </nuxt-link>
        </li>
        <li>
          <nuxt-link to="/aboutme">
            <span>ABOUT ME</span>
          </nuxt-link>
        </li>
        <li>
          <nuxt-link to="/contact">
            <span>CONTACT</span>
          </nuxt-link>
        </li>
      </ul>
    </div>
    <div class="inner_mobile">
      <div @click="toggle" class="responsive_menu">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" aria-hidden="true" focusable="false"><path d="M20,7H4C3.4,7,3,6.6,3,6s0.4-1,1-1h16c0.6,0,1,0.4,1,1S20.6,7,20,7z" /><path d="M15.2,13H4c-0.6,0-1-0.4-1-1s0.4-1,1-1h11.2c0.6,0,1,0.4,1,1S15.8,13,15.2,13z" /><path d="M20,19H4c-0.6,0-1-0.4-1-1s0.4-1,1-1h16c0.6,0,1,0.4,1,1S20.6,19,20,19z" /></svg>
      </div>
      <transition name="slide-fade">
        <div v-if="isOpen" class="menu">
          <div class="app_logo">
            <img src="/page_assets/logo.png" alt="logo">
          </div>
          <div @click="toggle" class="close_opened_menu">
            <span>
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" aria-hidden="true" focusable="false"><path d="M13.414,12l6.293-6.293a1,1,0,0,0-1.414-1.414L12,10.586,5.707,4.293A1,1,0,0,0,4.293,5.707L10.586,12,4.293,18.293a1,1,0,1,0,1.414,1.414L12,13.414l6.293,6.293a1,1,0,0,0,1.414-1.414Z" /></svg>
            </span>
          </div>
          <ul>
            <li>
              <nuxt-link exact to="/">
                <span>HOME</span>
              </nuxt-link>
            </li>
            <li>
              <nuxt-link to="/shop">
                <span>SHOP</span>
              </nuxt-link>
            </li>
            <li>
              <nuxt-link to="/freebies">
                <span>FREEBIES</span>
              </nuxt-link>
            </li>
            <li>
              <nuxt-link to="/aboutme">
                <span>ABOUT ME</span>
              </nuxt-link>
            </li>
            <li>
              <nuxt-link to="/contact">
                <span>CONTACT</span>
              </nuxt-link>
            </li>
          </ul>
        </div>
      </transition>
      <transition name="fade">
        <div v-if="isOpen" class="blurred_bg" />
      </transition>
      <div class="header_cart_nav">
        <app-cart />
      </div>
    </div>
  </nav>
</template>

<script>
import AppCart from '~/components/AppCart.vue'

export default {
  components: {
    AppCart
  },
  data () {
    return {
      isOpen: false
    }
  },
  methods: {
    toggle () {
      this.isOpen = !this.isOpen
    }
  }
}
</script>

<style>
@media only screen and (max-width: 2560px){
  .menu_wrapper {
      width: 100%;
      text-align: center;
      clear: both;
      line-height: 0;
      height: 70px;
      margin-top: 50px;
      background-color: rgba(228,214,206,0.88);
  }
  .menu_wrapper div.inner {
      width: 70%;
      margin-left: 15%;
  }
  .menu_wrapper ul {
      list-style: none outside;
      height: 70px;
      margin-bottom: 0;
  }
  .menu_wrapper ul li {
      float: left;
      position: relative;
      display: block;
      height: 70px;
      display: table;
  }
  .menu_wrapper ul li a {
      color: #000;
      padding: 0 100px;
      font-family: Daun;
      height: 70px;
      display: table-cell;
      vertical-align: middle;
  }
  .menu_wrapper ul li a span {
      position: relative;
      top: 8px;
      font-size: 40px;
      letter-spacing: 6px;
  }
  .menu_wrapper ul li a:hover {
      text-decoration: none;
  }
  li a.nuxt-link-active {
      background-color: rgba(255,255,255,0.4);
  }
  .inner_mobile {
    display: none;
  }
}
@media only screen and (max-width: 1920px){
  .menu_wrapper {
      width: 100%;
      text-align: center;
      clear: both;
      line-height: 0;
      height: 70px;
      margin-top: 50px;
      background-color: rgba(228,214,206,0.88);
  }
  .menu_wrapper div.inner {
      width: 70%;
      margin-left: 15%;
  }
  .menu_wrapper ul {
      list-style: none outside;
      height: 70px;
      margin-bottom: 0;
  }
  .menu_wrapper ul li {
      float: left;
      position: relative;
      display: block;
      height: 70px;
      display: table;
  }
  .menu_wrapper ul li a {
      color: #000;
      padding: 0 55px;
      font-family: Daun;
      height: 70px;
      display: table-cell;
      vertical-align: middle;
  }
  .menu_wrapper ul li a span {
      position: relative;
      top: 8px;
      font-size: 40px;
      letter-spacing: 6px;
  }
  .menu_wrapper ul li a:hover {
      text-decoration: none;
  }
  li a.nuxt-link-active {
      background-color: rgba(255,255,255,0.4);
  }
  .inner_mobile {
    display: none;
  }
}
@media only screen and (max-width: 1680px){
  .menu_wrapper ul li a {
    padding: 0 38px;
  }
}
@media (max-height: 2224px) and (max-width: 1668px) {
  .menu_wrapper ul li a {
    padding: 0 37px;
  }
}
@media only screen and (max-width: 1600px){
  .menu_wrapper ul li a span {
    font-size: 35px;
  }
}
@media only screen and (max-width: 1536px){
  .menu_wrapper ul li a {
    padding: 0 35px;
  }
}
@media only screen and (max-width: 1440px){
  .menu_wrapper ul li a span {
    font-size: 37px;
  }
  .menu_wrapper ul li a {
    padding: 0 22px;
  }
  .menu_wrapper {
    margin-top: 20px;
  }
}
@media only screen and (max-width: 1366px){
  .menu_wrapper ul li a span {
    font-size: 33px;
  }
}
@media (max-height: 1024px) and (max-width: 1280px) {
  .menu_wrapper ul li a {
    padding: 0 20px;
  }
}
@media (max-height: 2688px) and (max-width: 1242px) {
  .menu_wrapper ul li a {
    padding: 0 17px;
  }
}
@media (max-height: 2436px) and (max-width: 1125px) {
  .menu_wrapper ul li a {
    padding: 0 9px;
  }
}
@media only screen and (max-width: 1112px){
  .menu_wrapper ul li a span {
    font-size: 31px;
    letter-spacing: 5px;
  }
  .menu_wrapper ul li a {
    padding: 0 12px;
  }
}
@media only screen and (max-width: 1024px){
  .menu_wrapper .inner {
    display: none;
  }
  .inner_mobile {
    display: block;
  }
  .responsive_menu svg {
    width: 100%;
  }
  .inner_mobile .menu {
    display: none;
  }
  .responsive_menu {
    width: 4%;
    margin-left: 2%;
    padding-top: 13px;
    float: left;
    cursor: pointer;
  }
  .cart-wrapper a {
    top: 0;
    right: 0;
    position: initial;
    padding-right: 0;
    padding-top: 10px;
    margin-right: 30px;
    height: 70px;
  }
  .cart-wrapper a.cartWithItem {
    top: 0;
    position: initial;
    padding-right: 0;
    padding-top: 10px;
    height: 70px;
    width: 100%;
    padding-right: 20px;
    padding-left: 30px;
    margin-right: 0;
  }
  .cart-wrapper a.cartWithItem span {
    font-size: 50px;
    font-family: Daun;
    display: table-cell;
    position: relative;
    top: 35px;
    color: #fff;
    right: 20px;
  }
  .header_cart_nav {
    float: right;
  }
  .inner_mobile .menu {
    display: block;
    position: fixed;
    width: 30%;
    height: 100vh;
    top: 0;
    background: #fff;
    z-index: 999;
  }
  .menu_wrapper ul li {
    width: 100%;
    text-align: left;
  }
  .close_opened_menu {
    display: block;
    position: absolute;
    top: 2%;
    right: 5%;
    cursor: pointer;
  }
  .menu_wrapper ul {
    height: 0;
    width: 100%;
    float: left;
    padding: 0;
  }
  .close_opened_menu span svg {
    width: 24px;
    height: 24px;
  }
  .menu_wrapper ul li a {
    padding-left: 10%;
  }
  .menu_wrapper ul li a, .menu_wrapper ul li {
    height: 50px;
  }
  li a.nuxt-link-active {
    background-color: rgba(228,214,206,0.88);
  }
  .slide-fade-enter-active {
  transition: all .3s ease;
  }
  .slide-fade-leave-active {
    transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
  }
  .slide-fade-enter, .slide-fade-leave-to {
    transform: translateX(-10px);
    opacity: 0;
  }
  .fade-enter-active, .fade-leave-active {
  transition: opacity .8s;
  }
  .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
    opacity: 0;
  }
  .blurred_bg {
    background-color: rgba(0, 0, 0, 0.5);
    width: 70%;
    height: 100vh;
    position: fixed;
    z-index: 998;
    top: 0;
    margin-left: 30%;
  }
  .header_cart_nav img {
    width: 6vh;
    padding-top: 5px;
  }
}
@media only screen and (max-width: 834px){
  .responsive_menu {
    padding-top: 18px;
  }
  .header_cart_nav img {
    width: 95%;
    padding-top: 3px;
  }
}
@media only screen and (max-width: 812px){
  .menu_wrapper {
    height: 50px;
  }
  .responsive_menu {
    padding-top: 10px;
  }
  .header_cart_nav img {
    width: 80%;
    padding-top: 0px;
    margin-top: -3px;
  }
  .cart-wrapper a {
    height: 65px;
  }
  .cart-wrapper a.cartWithItem {
    height: 50px;
  }
  .cart-wrapper a.cartWithItem span {
    top: 25px;
  }
  .app_logo img {
    width: 80%;
  }
  .menu_wrapper ul li a, .menu_wrapper ul li {
    height: 35px;
  }
  .menu_wrapper ul li a span {
    font-size: 25px;
  }
}
@media only screen and (max-width: 768px){
  .header_cart_nav img {
    width: 3.5vh;
    padding-top: 0px;
  }
}
@media only screen and (max-width: 767px){
  .header_cart_nav img {
    width: 57%;
    padding-top: 5px;
  }
  .cart-wrapper a {
    margin-right: 10px;
  }
}
@media only screen and (max-width: 736px){
  .menu_wrapper ul li a span {
    font-size: 22px;
  }
  .menu_wrapper ul li a, .menu_wrapper ul li {
    height: 35px;
  }
  .app_logo img {
    width: 70%;
  }
  .close_opened_menu span svg {
    width: 20px;
    height: 20px;
  }
  .cart-wrapper a.cartWithItem {
    padding-right: 10px;
    padding-left: 20px;
  }
  .cart-wrapper a.cartWithItem span {
    top: 24px;
    font-size: 40px;
    right: 10px;
  }
}
@media only screen and (max-width: 667px){
}
@media only screen and (max-width: 568px){
  .menu_wrapper {
    height: 40px;
    margin-top: 10px;
  }
  .responsive_menu {
    padding-top: 8px;
  }
  .header_cart_nav img {
    padding-top: 0px;
  }
  .cart-wrapper a {
    height: 0;
  }
  .menu_wrapper ul li a span {
    font-size: 20px;
    letter-spacing: 5px;
  }
  .menu_wrapper ul li a, .menu_wrapper ul li {
    height: 30px;
  }
  .cart-wrapper a.cartWithItem {
    height: 40px;
  }
  .cart-wrapper a.cartWithItem span {
    top: 19px;
  }
}
@media only screen and (max-width: 414px){
  .responsive_menu {
    padding-top: 11px;
  }
  .header_cart_nav img {
    width: 47%;
    padding-top: 2px;
  }
  .inner_mobile .menu {
    width: 50%;
  }
  .blurred_bg {
    width: 50%;
    margin-left: 50%;
  }
  .cart-wrapper a.cartWithItem {
    padding-right: 0px;
  }
}
@media only screen and (max-width: 375px){
  .responsive_menu svg {
    width: 20px;
    margin-top: -1px;
  }
  .cart-wrapper a.cartWithItem span {
    top: 16px;
    font-size: 30px;
    right: 5px;
  }
  .cart-wrapper a.cartWithItem {
    padding-left: 15px;
  }
}
@media only screen and (max-width: 320px){
}
</style>
