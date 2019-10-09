const cookieparser = process.server ? require('cookieparser') : undefined

export const state = () => ({
  cartUIStatus: 'idle',
  cart: [],
  products: [],
  tempImages: [],
  auth: null
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
  },
  getProducts: (state) => {
    if (!state.products.length) {
      return []
    }
    return state.products
  },
  getCart: (state) => {
    return state.cart
  },
  getTempImages: (state) => {
    return state.tempImages
  },
  auth: (state) => {
    return state.auth
  }
}

export const mutations = {
  setProducts: (state, products) => {
    state.products = products
  },
  addToCart: (state, product) => {
    state.cart.push(product)
  },
  increaseQuantity: (state, id) => {
    state.cart.filter(product => product.id === id)[0].quantity += 1
  },
  removeFromCartByIndex: (state, index) => {
    state.cart.splice(index, 1)
  },
  emptyCart: (state) => {
    state.cart = []
  },
  saveTempImages: (state, images) => {
    state.tempImages = images
  },
  clearTempImages: (state) => {
    state.tempImages = []
  },
  setAuth (state, auth) {
    state.auth = auth
  }
}

export const actions = {
  setProducts: ({ commit }, products) => {
    commit('setProducts', products)
  },
  emptyCart: ({ commit }) => {
    commit('emptyCart')
  },
  addProductToCart: ({ commit }, product) => {
    commit('addToCart', product)
  },
  increaseQuantity: ({ commit }, id) => {
    commit('increaseQuantity', id)
  },
  removeFromCartByIndex: ({ commit }, index) => {
    commit('removeFromCartByIndex', index)
  },
  saveTempImages: ({ commit }, images) => {
    commit('saveTempImages', images)
  },
  clearTempImages: ({ commit }) => {
    commit('clearTempImages')
  },
  nuxtServerInit ({ commit }, { req }) {
    let auth = null
    if (req.headers.cookie) {
      const parsed = cookieparser.parse(req.headers.cookie)
      try {
        auth = {
          'accessToken': parsed.auth
        }
      } catch (err) {
        console.log('NO VALID COOKIE!!!')
      }
      commit('setAuth', auth)
    }
  }
}
