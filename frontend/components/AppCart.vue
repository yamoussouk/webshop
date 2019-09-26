<template>
    <div>
        <!-- Modal -->
        <b-modal id="cart-modal" hide-footer>
            <app-cart-modal :cartItems="cartItems"></app-cart-modal>
            <b-button class="mt-3" block @click="$bvModal.hide('cart-modal')">OK</b-button>
            <nuxt-link to="/checkout">
              <b-button class="mt-3" v-show="cartItems.length > 0" block @click="$bvModal.hide('cart-modal')">Go to checkout page</b-button>
            </nuxt-link>
        </b-modal>
        <div class="cart-wrapper">
          <a v-b-modal.cart-modal v-bind:class="{ cartWithItem: cartItems.length > 0 }">
              <img :src="cartSource" alt="cart" /><span v-show="cartItems.length > 0"> {{cartItems.length}}</span>
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
  props: {
    cartItems: Array
  },
  computed: {
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
    background-color: rgba(163, 153, 178, 0.77);
    border-radius: 10px;
}
</style>
