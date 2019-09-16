export const state = () => ({
  cartUIStatus: 'idle',
  cart: []
})

export const getters = {
  cartCount: (state) => {
    if (!state.cart.length) {
      return 0
    }
    return state.cart.reduce((ac, next) => ac + next.quantity, 0)
  },
  cartTotal: (state) => {
    if (!state.cart.length) {
      return 0
    }
    return state.cart.reduce((ac, next) => ac + next.quantity * next.price, 0)
  }
}
