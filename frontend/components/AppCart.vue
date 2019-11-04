<template>
  <div>
    <!-- Modal -->
    <b-modal id="cart-modal" hide-footer>
      <app-cart-modal :cart-items="cartItems" />
      <b-button class="mt-3" block @click="$bvModal.hide('cart-modal')">
        OK
      </b-button>
      <nuxt-link to="/checkout">
        <b-button v-show="cartItems.length > 0" class="mt-3" block @click="$bvModal.hide('cart-modal')">
          Go to checkout page
        </b-button>
      </nuxt-link>
    </b-modal>
    <div class="cart-wrapper">
      <a v-b-modal.cart-modal :class="{ cartWithItem: cartItems.length > 0 }">
        <span v-show="cartItems.length > 0"> {{ cartItems.length }}</span><img :src="cartSource" alt="cart">
      </a>
    </div>
  </div>
</template>

<script>
import AppCartModal from '~/components/AppCartModal.vue'

export default {
  components: {
    AppCartModal
  },
  computed: {
    cartItems () {
      console.log('items', this.$store.state.localStorage.localCart)
      return this.$store.state.localStorage.localCart
    },
    cartSource () {
      return this.cartCount > 0 ? '/page_assets/cart_white.png' : '/page_assets/cart_black.png'
    },
    cartCount () {
      console.log('count', this.cartItems.reduce((ac, next) => ac + next.quantity, 0))
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
