<template>
  <div class="planners">
    <bread-crumb :title="title" :to="shop" />
    <div class="printable_mobile">
      <side-bar :search.sync="search" :categories="categories" @filter="showByFilter" />
      <product-search :search.sync="search" />
    </div>
    <div class="shop_wrapper">
      <div class="shop_aside">
        <side-bar :search.sync="search" :categories="categories" @filter="showByFilter" />
      </div>
      <div class="shop_inner">
        <ul class="products_wrapper">
          <li v-for="product in filteredList" :key="product.id" class="product">
            <div class="image_frame">
              <div class="image_wrapper">
                <nuxt-link :to="`planner/${product.id}`">
                  <img :src="'/uploaded/' + product.id + '/' + product.image[0].imageUrl" alt="product image">
                </nuxt-link>
              </div>
            </div>
            <div class="desc">
              <h4>
                <nuxt-link :to="`planner/${product.id}`">
                  <span class="product_title">{{ product.name }}</span>
                </nuxt-link>
              </h4>
              <div class="price">
                <span>$ {{ product.price }}</span>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import BreadCrumb from '~/components/BreadCrumb.vue'
import SideBar from '~/components/SideBar.vue'
import ProductSearch from '~/components/ProductSearch.vue'

export default {
  components: {
    BreadCrumb,
    SideBar,
    ProductSearch
  },
  data () {
    return {
      title: 'printable planners',
      shop: 'shop',
      products: [],
      search: '',
      filteredProducts: [],
      categories: ['all', 'lifestyle planners', 'monthly planners', 'weekly planners', 'daily planners', 'digital planners']
    }
  },
  computed: {
    filteredList () {
      if (this.search !== '' || this.filteredProducts.length === 0) {
        return this.products.filter((product) => {
          return product.name.toLowerCase().includes(this.search.toLowerCase())
        })
      } else if (this.filteredProducts.length === 0) {
        return this.products
      } else {
        return this.filteredProducts
      }
    }
  },
  created () {
    this.products = this.$store.getters.getProducts
    if (this.products.length === 0) {
      axios.get('http://localhost:8083/default/planners/all'
      ).then((response) => {
        this.products = response.data
      })
        .catch(function (error) {
          console.log(error)
        })
    }
  },
  methods: {
    showByFilter (filter) {
      this.search = ''
      if (filter === 'ALL') {
        this.filteredProducts = []
      } else {
        this.filteredProducts = []
        for (const product of this.products) {
          for (const cat of product.category) {
            if (cat.name.toLowerCase() === filter.toLowerCase()) {
              this.filteredProducts.push(product)
            }
          }
        }
      }
    }
  }
}
</script>

<style>
@media only screen and (max-width: 1920px){
  .printable_mobile {
    display: none;
  }
  .shop_aside {
      width: 31.333%;
      float: left;
  }
  .shop_inner {
      width: 64.666%;
      float: left;
  }
  .product {
      width: 28%;
      background-color: #fff;
      -webkit-box-shadow: 0px 0px 7px 0px rgba(0,0,0,0.75);
      -moz-box-shadow: 0px 0px 7px 0px rgba(0,0,0,0.75);
      box-shadow: 0px 0px 7px 0px rgba(0,0,0,0.75);
      margin: 0 2% 40px!important;
      float: left;
      list-style: none;
      height: 340px;
      overflow-x: hidden;
  }
  .product:hover {
      transition: all 1s;
      transform: scale(1.02);
  }
  .image_frame {
      border-color: #fff;
      position: relative;
      display: block;
      margin: 0;
      border-width: 8px;
      border-style: solid;
      max-width: 100%;
      line-height: 0;
  }
  .image_wrapper {
      width: 95%;
      margin: 2.5%;
      border-color: #e2e2e2;
  }
  .image_wrapper a {
      font-family: Meownella;
      font-size: 28px;
      font-weight: bold;
      text-decoration: none;
      display: block;
  }
  .image_wrapper img {
      width: 100%;
  }
  .desc {
      padding: 5px 5px 0px 5px;
      background: #fff;
  }
  .desc h4 {
      text-align: center;
      margin-bottom: 0px;
      white-space: nowrap;
      text-overflow: ellipsis;
      overflow: hidden;
  }
  .desc h4 a {
      font-family: Daun;
      font-size: 38px;
      color: #000;
  }
  .desc .price {
      text-align: center;
      color: #8f9194;
      font-family: Daun;
      font-size: 40px;
      margin-bottom: 1%;
      margin-top: -4%;
  }
}
@media only screen and (max-width: 1680px){
  .product {
    height: 295px;
  }
}
@media only screen and (max-width: 1600px){
  .product {
    height: 280px;
  }
}
@media only screen and (max-width: 1440px){
  .product {
    height: 265px;
  }
  .shop_inner {
    width: 67.666%;
  }
}
@media only screen and (max-width: 1366px){
  .product {
    height: 250px;
  }
}
@media only screen and (max-width: 1112px){
  .product {
    height: 200px;
  }
}
@media only screen and (max-width: 1024px){
  .printable_mobile {
    display: block;
  }
  .shop_aside {
    /* width: 25%; */
    display: none;
  }
  .shop_wrapper {
    width: 90%;
    margin-left: 5%;
  }
  .shop_inner {
    width: 100%;
  }
  .product {
    height: 250px;
  }
}
@media only screen and (max-width: 834px){
  .product {
    height: 200px;
  }
  .shop_inner {
    margin-top: 20px;
  }
}
@media only screen and (max-width: 812px){
  .printable_mobile {
    margin-top: 4%;
  }
  .product {
    height: 195px;
  }
}
@media only screen and (max-width: 768px){
  .product {
    height: 185px;
  }
}
@media only screen and (max-width: 767px){
  .product {
    height: 182px;
  }
}
@media only screen and (max-width: 737px){
  .product {
    height: 175px;
  }
}
@media only screen and (max-width: 667px){
  .product {
    height: 157px;
  }
}
@media only screen and (max-width: 568px){
  .product {
    height: 207px;
    width: 44%;
  }
}
@media only screen and (max-width: 414px){
  .custom_breadcrumb .back_button img {
    padding-top: 35%;
  }
  .printable_mobile {
    display: table;
    width: 100%;
  }
  .product {
    height: 355px;
    width: 95%;
    margin: 0 2% 20px!important;
  }
  .products_wrapper {
    padding: 0;
  }
}
@media only screen and (max-width: 375px){
  .product {
    height: 321px;
  }
}
@media only screen and (max-width: 360px){
  .product {
    height: 308px;
  }
}
@media only screen and (max-width: 320px){
  .product {
    height: 275px;
  }
}
</style>
