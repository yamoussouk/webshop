const cookieparser = process.server ? require('cookieparser') : undefined

export const state = () => ({
  cartUIStatus: 'idle',
  cart: [],
  products: [],
  tempImages: [],
  auth: null,
  orders: []
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
  },
  orders: (state) => {
    return state.orders
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
  },
  changeProductEnabled (state, id) {
    const product = state.products.find(pr => parseInt(pr.id) === parseInt(id))
    product.enabled = !product.enabled
  },
  removeProduct: (state, id) => {
    state.products.splice(state.products.findIndex(pr => parseInt(pr.id) === parseInt(id)), 1)
  },
  setCategory: (state, id, value) => {
    const product = state.products.find(pr => parseInt(pr.id) === parseInt(id))
    console.log(product)
    if (!product.categories.includes(value)) {
      state.products.find(pr => parseInt(pr.id) === parseInt(id)).categories.push(value)
    } else {
      const index = state.product.categories.indexOf(value)
      if (index !== -1) {
        state.products.find(pr => parseInt(pr.id) === parseInt(id)).categories.splice(index, 1)
      }
    }
  },
  saveProduct: (state, product) => {
    const idx = state.products.findIndex(e => e.id === product.id)
    state.products[idx] = product
  },
  refreshProduct: (state, product) => {
    const index = state.products.findIndex(item => item.id === product.id)
    state.products.splice(index, 1, product)
  },
  setOrders: (state, orders) => {
    state.orders = orders
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
  },
  changeProductEnabled: ({ commit }, id) => {
    commit('changeProductEnabled', id)
  },
  removeProduct: ({ commit }, id) => {
    commit('removeProduct', id)
  },
  setCategory: ({ commit }, id, value) => {
    commit('setCategory', id, value)
  },
  saveProduct: ({ commit }, product) => {
    commit('saveProduct', product)
  },
  refreshPoduct: ({ commit }, product) => {
    commit('refreshProduct', product)
  },
  setOrders: ({ commit }, orders) => {
    commit('setOrders', orders)
  }
}
