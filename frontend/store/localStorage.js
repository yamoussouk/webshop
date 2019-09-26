export const state = () => ({
  localCart: []
})

export const getters = {
  localCart: (state) => {
    return state.localCart
  },
  localCartTotal: (state) => {
    if (!state.localCart.length) {
      return 0
    }
    console.log(state.localCart)
    return state.localCart.reduce((ac, next) => ac + next.quantity * next.price, 0)
  },
  localCartCount: (state) => {
    if (!state.localCart.length) {
      return 0
    }
    return state.localCart.reduce((ac, next) => ac + next.quantity, 0)
  }
}

export const mutations = {
  addToLocalCart: (state, product) => {
    state.localCart.push(product)
  },
  increaseQuantityInLocalCart: (state, id) => {
    console.log(id)
    state.localCart.filter(product => product.id === id)[0].quantity += 1
  },
  removeFromLocalCartByIndex: (state, index) => {
    state.localCart.splice(index, 1)
  },
  emptyLocalCart: (state) => {
    state.localCart = []
  }
}

export const actions = {
  addToLocalCart: ({ commit }, product) => {
    commit('addToLocalCart', product)
  },
  increaseQuantityInLocalCart: ({ commit }, id) => {
    commit('increaseQuantityInLocalCart', id)
  },
  removeFromLocalCartByIndex: ({ commit }, index) => {
    commit('removeFromLocalCartByIndex', index)
  },
  emptyLocalCart: ({ commit }) => {
    commit('emptyCart')
  }
}
