<template>
  <div>
    <div class="cart-wrapper">
      <nuxt-link :class="{ cartWithItem: cartItems.length > 0 }" to="/checkout">
        <span v-show="cartItems.length > 0"> {{ cartItems.length }}</span><img :src="cartSource" alt="cart">
      </nuxt-link>
    </div>
  </div>
</template>

<script>

export default {
  computed: {
    cartItems () {
      return this.$store.state.localStorage.localCart
    },
    cartSource () {
      return this.cartCount > 0 ? '/page_assets/cart_white.png' : '/page_assets/cart_black.png'
    },
    cartCount () {
      return this.cartItems.reduce((ac, next) => ac + next.quantity, 0)
    }
  }
}
</script>

<style>
@media only screen and (max-width: 1920px){
  .cart_wrapper {
      display: table-cell;
      vertical-align: middle;
  }
  .cartWithItem {
      background-color: #cd9e8f;
  }
}
@media only screen and (max-width: 1440px){
  .cartWithItem {
    right: 0 !important;
  }
}
@media only screen and (max-width: 1366px){
}
@media only screen and (max-width: 1112px){
}
@media only screen and (max-width: 1024px){
  .cartWithItem {
    width: 100%;
  }
}
@media only screen and (max-width: 834px){
}
@media only screen and (max-width: 812px){
}
@media only screen and (max-width: 768px){
  .header_cart_nav img {
    padding-top: 3px;
  }
}
@media only screen and (max-width: 767px){
}
@media only screen and (max-width: 737px){
}
@media only screen and (max-width: 667px){
}
@media only screen and (max-width: 568px){
}
@media only screen and (max-width: 414px){
}
@media only screen and (max-width: 375px){
}
@media only screen and (max-width: 360px){
}
@media only screen and (max-width: 320px){
}
</style>
