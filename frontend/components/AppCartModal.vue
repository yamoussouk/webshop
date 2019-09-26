<template>
    <div>
        <!-- Modal -->
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <table v-if="cartItems.length !== 0" class="table table-cart">
                        <thead>
                            <th>Product</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th></th>
                        </thead>
                        <tbody>
                            <tr v-for="(item, index) in cartItems" v-bind:key="item">
                                <td>{{ item.name }}</td>
                                <td>{{ item.quantity }}</td>
                                <td>{{ item.price }}</td>
                                <td><span class="glyphicon glyphicon-remove" @click="removeFromCart(index)">X</span></td>
                            </tr>
                        </tbody>
                        <tfoot>
                            <td><b>Total:</b></td>
                            <td></td>
                            <td>{{ total }}</td>
                            <td></td>
                        </tfoot>
                    </table>
                    <p v-else>Cart is empty</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
  props: {
    cartItems: Array
  },
  computed: {
    total () {
      return this.$store.state.localStorage.localCart.reduce((ac, next) => ac + next.quantity * next.price, 0)
    }
  },
  methods: {
    removeFromCart (index) {
      this.$store.commit('localStorage/removeFromLocalCartByIndex', index)
    }
  }
}
</script>

<style scoped>
.glyphicon-remove {
    width: 30px;
    height: 30px;
    background-color: red;
    padding: 10px;
    border-radius: 6px;
    color: #fff;
    cursor: pointer;
}
</style>
