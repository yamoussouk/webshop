<template>
    <div>
        <!-- Modal -->
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <table v-if="getCart.length !== 0" class="table table-cart">
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
                            <td>{{ cartTotal }}</td>
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
import { mapGetters, mapActions } from 'vuex'

export default {
  props: {
    cartItems: Array
  },
  computed: {
    ...mapGetters(['cartTotal', 'getCart'])
  },
  methods: {
    ...mapActions(['removeFromCartByIndex']),
    removeFromCart (index) {
      this.removeFromCartByIndex(index)
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
