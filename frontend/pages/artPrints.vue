<template>
  <div class="logos">
    <bread-crumb :title="title" :to="shop" />
    <div class="shop_wrapper">
      <div class="shop_aside">
        <side-bar :search.sync="search" @filter="showByFilter" :categories="categories" />
      </div>
      <div class="shop_inner">
        <ul class="products_wrapper">
          <li v-for="product in filteredList" :key="product.id" class="product">
            <div class="image_frame">
              <div class="image_wrapper">
                <nuxt-link :to="`logo/${product.id}`">
                  <img :src="'/uploaded/' + product.id + '/' + product.image[0].imageUrl" alt="product image">
                </nuxt-link>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
// import axios from 'axios'
import BreadCrumb from '~/components/BreadCrumb.vue'
import SideBar from '~/components/SideBar.vue'

export default {
  components: {
    BreadCrumb,
    SideBar
  },
  data () {
    return {
      title: 'art prints',
      shop: 'shop',
      products: [],
      search: '',
      filteredProducts: [],
      categories: ['all', 'sticker sheets', 'single stickers', 'art prints', 'zines']
    }
  },
  computed: {
    filteredList () {
      if (this.search !== '') {
        // SEARCH
        return this.products.filter((product) => {
          return product.name.toLowerCase().includes(this.search.toLowerCase())
        })
      } else if (this.filteredProducts.length === 0) {
        // ALL
        return this.products
      } else if (this.filteredProducts.length > 0 && this.filteredProducts[0] !== null) {
        // FILTER BY PRODUCT CATEGORY
        return this.filteredProducts
      } else {
        console.log(this.filteredProducts)
        // NO FILTER FOUND
        return []
      }
    }
  },
  //   created () {
  //     this.products = this.$store.getters.logos
  //     if (this.products.length === 0) {
  //       axios.get('http://localhost:8083/default/logos/all'
  //       ).then((response) => {
  //         this.products = response.data
  //         for (const p of this.products) {
  //           p.image.sort((a, b) => (a.imageUrl < b.imageUrl) ? 1 : ((b.imageUrl < a.imageUrl) ? -1 : 0))
  //           console.log(p.image)
  //         }
  //       })
  //         .catch(function (error) {
  //           console.log(error)
  //         })
  //     }
  //   },
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
        if (this.filteredProducts.length === 0) {
          this.filteredProducts.push(null)
        }
      }
    }
  }
}
</script>

<style scoped>
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
</style>
