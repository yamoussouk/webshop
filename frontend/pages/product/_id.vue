<template>
  <div class="single_product">
    <bread-crumb :title="product.name" />
    <div class="product_wrapper">
      <div ref="product_image_wrapper" class="product_image_wrapper">
        <div class="product_images">
          <image-carousel :images="product.image" :id="product.id" />
        </div>
        <div class="product-attributes">
          <div id="product-size">
            <span>Size of the product</span>
            <select v-model="size">
              <option value="select">SELECT A SIZE</option>
              <option value="A4">A4</option>
              <option value="A5">A5</option>
              <option value="USLETTER">US LETTER</option>
              <option value="HALFSIZE">HALF SIZE</option>
              <option value="PERSONAL">PERSONAL</option>
            </select>
          </div>
          <div id="product-starting-day">
            <span>Starting day of the product</span>
            <select v-model="startingDay">
              <option value="select">SELECT A STARTING DAY</option>
              <option value="MONDAY">MONDAY</option>
              <option value="SUNDAY">SUNDAY</option>
            </select>
          </div>
        </div>
        <div class="product_price">
          <span>$ {{ product.price }}</span>
        </div>
        <div class="product_cart_buttons">
          <button class="product_add_to_cart_button" @click="add(product)">
            Add to cart
          </button>
          <button class="product_quick_buy_button" @click="buynow(product)">
            Buy Now
          </button>
        </div>
      </div>
      <div ref="summary" class="summary">
        <div ref="product_details" class="product_details">
          <p>{{ product.longDescription }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import BreadCrumb from '~/components/BreadCrumb.vue'
// eslint-disable-next-line
import ImageCarousel from '~/components/ImageCarousel.vue'

export default {
  validate ({ params }) {
    return /^\d+$/.test(params.id)
  },
  components: {
    BreadCrumb,
    // eslint-disable-next-line
    ImageCarousel
  },
  data () {
    return {
      id: parseInt(this.$route.params.id),
      cart: [],
      product: {},
      size: 'select',
      startingDay: 'select'
    }
  },
  async asyncData ({ params }) {
    const { data } = await axios.get(`http://localhost:8083/default/product/${params.id}`)
    return { product: data }
  },
  methods: {
    currentImage () {
      return '/images/1/' + this.product.images[this.activeImage].imageUrl
    },
    addProductToLocalCart (product) {
      this.$store.commit('localStorage/addToLocalCart', this.simplifyProduct(product))
    },
    increaseQuantityOnLocalCart (id) {
      this.$store.commit('localStorage/increaseQuantityInLocalCart', id)
    },
    simplifyProduct (product) {
      return {
        'id': product.id,
        'name': product.name,
        'quantity': product.quantity,
        'price': product.price,
        'size': this.size,
        'startingDay': this.startingDay
      }
    },
    add (product) {
      if (this.size === 'select') {
        console.log('MISSING SIZE')
      } else if (this.startingDay === 'select') {
        console.log('MISSING STARTING DAY')
      } else {
        const itemInCart = this.$store.state.localStorage.localCart.filter(item => item.id === product.id)
        const isItemInCart = itemInCart.length > 0

        if (isItemInCart === false) {
          this.addProductToLocalCart(product)
        } else {
          this.increaseQuantityOnLocalCart(product.id)
        }
      }
    },
    buynow (product) {
      if (this.size === 'select') {
        console.log('MISSING SIZE')
      } else if (this.startingDay === 'select') {
        console.log('MISSING STARTING DAY')
      } else {
        this.add(product)
        this.$router.push('/checkout')
      }
    }
  }
}
</script>

<style>
.product_wrapper {
    margin-bottom: 2%;
}
.product_image_wrapper {
    width: 48%;
    margin-left: 2%;
    float: left;
}
.summary {
    width: 46%;
    margin-right: 1%;
    margin-left: 2%;
    float: right;
    clear: none;
}
.product_details {
    background-color: #fff;
}
.product_images {
    width: 100%;
    height: auto;
}
.product_price {
    color: #000;
    font-family: Audrey;
    font-size: 46pt;
    width: 100%;
    background-color: #fff;
    line-height: 120px;
    text-align: center;
    height: 100px;
}
.product_add_to_cart_button, .product_quick_buy_button {
    width: 100%;
    height: 100px;
    background-color: rgba(163,153,178,1);
    color: #fff;
    font-family: Audery;
    font-size: 46pt;
    margin-bottom: 0px;
}
.product_add_to_cart_button {
    margin-top: 10%;
    margin-bottom: 10%;
}
.summary .product_details p {
    font-family: Audrey;
    font-size: 30px;
    color: #000;
    padding-left: 5%;
    padding-right: 5%;
    line-height: 55px;
    overflow: auto;
    word-break: break-word;
}
#product-size span, #product-starting-day span {
  display: block;
  color: #000;
  font-family: Audrey;
  font-size: 26px;
  padding-bottom: 2%;
}
.product-attributes {
  padding-top: 3%;
  padding-bottom: 3%;
}
.product-attributes select {
  height: 40px;
  width: 100%;
}

</style>
