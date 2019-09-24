<template>
    <div>
        <!-- Modal -->
        <b-modal id="cart-modal" hide-footer>
            <app-cart-modal :cartItems="cartItems"></app-cart-modal>
            <b-button class="mt-3" block @click="$bvModal.hide('cart-modal')">OK</b-button>
            <nuxt-link to="/checkout">
              <b-button class="mt-3" block @click="$bvModal.hide('cart-modal')">Go to checkout page</b-button>
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
import { mapGetters } from 'vuex'
import AppCartModal from '~/components/AppCartModal.vue'

export default {
  components: {
    AppCartModal
  },
  props: {
    cartItems: Array
  },
  computed: {
    ...mapGetters(['cartCount']),
    cartSource () {
      return this.cartCount > 0 ? '/page_assets/cart_white.png' : '/page_assets/cart_black.png'
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
