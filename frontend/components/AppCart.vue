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
        <img :src="cartSource" alt="cart"><span v-show="cartItems.length > 0"> {{ cartItems.length }}</span>
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
      return this.$store.state.localStorage.localCart
    },
    cartSource () {
      return this.$store.state.localStorage.localCartCount > 0 ? '/page_assets/cart_white.png' : '/page_assets/cart_black.png'
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
    background-color: rgba(226,212,203, 0.89);
    border-radius: 10px;
}
</style>
