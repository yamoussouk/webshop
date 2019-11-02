const cookieparser = process.server ? require('cookieparser') : undefined

export const state = () => ({
  cartUIStatus: 'idle',
  cart: [],
  products: [],
  tempImages: [],
  auth: null,
  orders: [],
  subscribers: [],
  planners: [],
  logos: []
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
  },
  subscribers: (state) => {
    return state.subscribers
  },
  planners: (state) => {
    return state.planners
  },
  logos: (state) => {
    return state.logos
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
  changePlannerEnabled (state, id) {
    const product = state.planners.find(pr => parseInt(pr.id) === parseInt(id))
    product.enabled = !product.enabled
  },
  changeLogoEnabled (state, id) {
    const product = state.logos.find(pr => parseInt(pr.id) === parseInt(id))
    product.enabled = !product.enabled
  },
  removePlanner: (state, id) => {
    state.planners.splice(state.planners.findIndex(pr => parseInt(pr.id) === parseInt(id)), 1)
  },
  removeLogo: (state, id) => {
    state.logos.splice(state.logos.findIndex(pr => parseInt(pr.id) === parseInt(id)), 1)
  },
  setPlannerCategory: (state, id, value) => {
    const product = state.planners.find(pr => parseInt(pr.id) === parseInt(id))
    console.log(product)
    if (!product.categories.includes(value)) {
      state.planners.find(pr => parseInt(pr.id) === parseInt(id)).categories.push(value)
    } else {
      const index = state.planners.categories.indexOf(value)
      if (index !== -1) {
        state.planners.find(pr => parseInt(pr.id) === parseInt(id)).categories.splice(index, 1)
      }
    }
  },
  setLogoCategory: (state, id, value) => {
    console.log(id)
    const product = state.logos.find(pr => parseInt(pr.id) === parseInt(id))
    console.log(value)
    if (!product.categories.includes(value)) {
      state.logos.find(pr => parseInt(pr.id) === parseInt(id)).categories.push(value)
    } else {
      const index = state.logos.categories.indexOf(value)
      if (index !== -1) {
        state.logos.find(pr => parseInt(pr.id) === parseInt(id)).categories.splice(index, 1)
      }
    }
  },
  savePlanner: (state, product) => {
    const idx = state.planners.findIndex(e => e.id === product.id)
    state.planners[idx] = product
  },
  saveLogo: (state, product) => {
    const idx = state.logos.findIndex(e => e.id === product.id)
    state.logos[idx] = product
  },
  refreshPlanner: (state, product) => {
    const index = state.planners.findIndex(item => item.id === product.id)
    state.planners.splice(index, 1, product)
  },
  refreshLogo: (state, product) => {
    const index = state.logos.findIndex(item => item.id === product.id)
    state.logos.splice(index, 1, product)
  },
  setOrders: (state, orders) => {
    state.orders = orders
  },
  setSubscribers: (state, subscribers) => {
    state.subscribers = subscribers
  },
  setPlanners: (state, planners) => {
    state.planners = planners
  },
  setLogos: (state, logos) => {
    state.logos = logos
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
  changePlannerEnabled: ({ commit }, id) => {
    commit('changePlannerEnabled', id)
  },
  changeLogoEnabled: ({ commit }, id) => {
    commit('changeLogoEnabled', id)
  },
  removePlanner: ({ commit }, id) => {
    commit('removePlanner', id)
  },
  removeLogo: ({ commit }, id) => {
    commit('removeLogo', id)
  },
  setPlannerCategory: ({ commit }, id, value) => {
    commit('setPlannerCategory', id, value)
  },
  setLogoCategory: ({ commit }, id, value) => {
    commit('setLogoCategory', id, value)
  },
  savePlanner: ({ commit }, product) => {
    commit('savePlanner', product)
  },
  saveLogo: ({ commit }, product) => {
    commit('saveLogo', product)
  },
  refreshPlanner: ({ commit }, product) => {
    commit('refreshPlanner', product)
  },
  refreshLogo: ({ commit }, product) => {
    commit('refreshLogo', product)
  },
  setOrders: ({ commit }, orders) => {
    commit('setOrders', orders)
  },
  setSubscribers: ({ commit }, subscribers) => {
    commit('setSubscribers', subscribers)
  },
  setPlanners: ({ commit }, planners) => {
    commit('setPlanners', planners)
  },
  setLogos: ({ commit }, logos) => {
    commit('setLogos', logos)
  }
}
