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

<style scoped>
.cart_wrapper {
    display: table-cell;
    vertical-align: middle;
}
.cartWithItem {
    background-color: #cd9e8f;
}
</style>
