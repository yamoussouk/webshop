<template>
  <div class="single_product">
    <bread-crumb :title="product.name" />
    <div class="product_wrapper">
      <div ref="product_image_wrapper" class="product_image_wrapper">
        <div class="product_images">
          <image-carousel :images="product.image" :id="product.id" />
        </div>
        <div class="product_price">
          <span>$ {{ product.price }}</span>
        </div>
        <div class="product-attributes">
          <custom-select
                  ref="ref_size"
                  refs="size"
                  :options="sizes"
                  value-key="id"
                  label-key="name"
                  v-model="selected_size"></custom-select>
          <custom-select
                  ref="ref_day"
                  refs="day"
                  :options="days"
                  value-key="id"
                  label-key="name"
                  v-model="selected_day"></custom-select>
        </div>
        <div class="product_cart_buttons">
          <button class="product_add_to_cart_button" @click="add(product)">
            add to cart
          </button>
          <button class="product_quick_buy_button" @click="buynow(product)">
            buy it now
          </button>
        </div>
      </div>
      <div ref="summary" class="summary">
        <div ref="product_details" class="product_details">
          <p v-html="product.longDescription"></p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import BreadCrumb from '~/components/BreadCrumb.vue'
import CustomSelect from '~/components/CustomSelect.vue'
// eslint-disable-next-line
import ImageCarousel from '~/components/ImageCarousel.vue'

export default {
  validate ({ params }) {
    return /^\d+$/.test(params.id)
  },
  components: {
    BreadCrumb,
    // eslint-disable-next-line
    ImageCarousel,
    CustomSelect
  },
  data () {
    return {
      id: parseInt(this.$route.params.id),
      cart: [],
      product: {},
      size: 'select',
      sizes: [
        { name: 'SELECT SIZE', id: 0 },
        { name: 'A4', id: 1 },
        { name: 'A5', id: 2 },
        { name: 'US LETTER', id: 3 },
        { name: 'HALF SIZE', id: 4 },
        { name: 'PERSONAL', id: 5 }
      ],
      days: [
        { name: 'FIRST DAY OF THE WEEK', id: 0 },
        { name: 'SUNDAY', id: 1 },
        { name: 'MONDAY', id: 2 }
      ],
      selected_size: { name: 'SELECT SIZE', id: 0 },
      selected_day: { name: 'FIRST DAY OF THE WEEK', id: 0 },
      startingDay: 'select'
    }
  },
  async asyncData ({ params }) {
    const { data } = await axios.get(`http://localhost:8083/default/planner/${params.id}`)
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
        'size': this.selected_size.name,
        'startingDay': this.selected_day.name,
        'type': 'Planner'
      }
    },
    add (product) {
      if (this.selected_size.id === 0) {
        console.log('MISSING SIZE')
      } else if (this.selected_day.id === 0) {
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
      if (this.selected_size.id === 0) {
        this.$refs.ref_size.$refs.size.style.border = '1px solid #ff0000'
      } else if (this.selected_day.id === 0) {
        this.$refs.ref_size.$refs.size.style.border = 'none'
        this.$refs.ref_day.$refs.day.style.border = '1px solid #ff0000'
      } else {
        this.$refs.ref_day.$refs.day.style.border = 'none'
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
    width: 50%;
    float: left;
}
.summary {
    width: 44%;
    margin-right: 1%;
    margin-left: 2%;
    float: right;
    clear: none;
}
.product_details {
    background-color: #fff;
}
.product_images {
    width: 93%;
    height: auto;
    margin-left: 7%;
}
.product_price {
    color: #000;
    font-family: Daun;
    font-size: 90px;
    width: 100%;
    background-color: #fff;
    line-height: 120px;
    text-align: center;
    height: 100px;
    margin-top: 5%;
}
.product_add_to_cart_button, .product_quick_buy_button {
    width: 100%;
    height: 100px;
    background-color: rgb(205, 158, 143);
    color: #fff;
    font-family: Daun;
    font-size: 90px;
    margin-bottom: 0px;
    border: none;
}
.product_add_to_cart_button {
    margin-bottom: 3%;
}
.summary .product_details p span {
    font-family: Daun;
    font-size: 40px;
    color: #000;
    padding-left: 5%;
    padding-right: 5%;
    line-height: 55px;
    display: inline-block;
}
.product-attributes {
    padding-top: 2%;
    padding-bottom: 3%;
}
.product-attributes .select:nth-child(1) {
    margin-bottom: 3%;
}
.card-img > img {
    width: 100%;
}
</style>
