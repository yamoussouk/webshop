export const state = () => ({
  localCart: [],
  discount: 0
})

export const getters = {
  localCart: (state) => {
    return state.localCart
  },
  localCartTotal: (state) => {
    if (!state.localCart.length) {
      return 0
    }
    return state.localCart.reduce((ac, next) => ac + next.quantity * next.price, 0)
  },
  localCartCount: (state) => {
    if (!state.localCart.length) {
      return 0
    }
    return state.localCart.reduce((ac, next) => ac + next.quantity, 0)
  },
  payPalCompatibleCart: (state) => {
    const cart = []
    for (const e in state.localCart) {
      const temp = this.cart[e]
      const desc = 'logoText' in temp ? 'logoText: ' + temp.logoText : 'size: ' + temp.size + ', starting day: ' + temp.startingDay
      const el = {
        'name': temp.name,
        'description': desc,
        'quantity': temp.quantity,
        'price': temp.price,
        'currency': 'USD'
      }
      cart.push(el)
    }
    return cart
  },
  discount: (state) => {
    return state.discount
  }
}

export const mutations = {
  addToLocalCart: (state, product) => {
    state.localCart.push(product)
  },
  increaseQuantityInLocalCart: (state, id) => {
    state.localCart.filter(product => product.id === id)[0].quantity += 1
  },
  removeFromLocalCartByIndex: (state, index) => {
    state.localCart.splice(index, 1)
  },
  emptyLocalCart: (state) => {
    state.localCart = []
  },
  setDiscount: (state, discount) => {
    state.discount = discount
  },
  emptyDiscount: (state) => {
    state.discount = 0
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
    commit('emptyLocalCart')
  },
  setDiscount: ({ commit, discount }) => {
    commit('setDiscount', discount)
  },
  emptyDiscount: ({ commit }) => {
    commit('emptyDiscount')
  }
}
