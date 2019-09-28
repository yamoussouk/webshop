<template>
  <div class="shop">
    <div class="wrapper">
      <div class="shop_slider">
        <div class="shop_sub_cat left_align">
          <nuxt-link to="/printablePlanners">
            <h1>PRINTABLE PLANNERS</h1>
          </nuxt-link>
        </div>
        <div class="shop_sub_carousel left_align">
          <!-- TODO: replace 1 with id of the product.
        First, make crud actions which creates the folder of the images based on the product's id -->
          <carousel :per-page="4" :mouse-drag="false" :center-mode="true" :paginationEnabled="false">
            <slide v-for="planner in printablePlanners" :key="planner">
              <img :src="'/images/1/' + planner.image[0].imageUrl">
            </slide>
          </carousel>
        </div>
      </div>
      <div class="shop_slider">
        <div class="shop_sub_cat right_align">
          <nuxt-link to="/logodesign">
            <h1>LOGO DESIGN</h1>
          </nuxt-link>
        </div>
        <div class="shop_sub_carousel right_align">
          <!-- replace it with valid category products -->
          <carousel :per-page="4" :mouse-drag="false" :center-mode="true" :paginationEnabled="false">
            <slide v-for="planner in printablePlanners" :key="planner">
              <img :src="'/images/1/' + planner.image[0].imageUrl">
            </slide>
          </carousel>
        </div>
      </div>
      <div class="shop_slider">
        <div class="shop_sub_cat left_align">
          <nuxt-link to="/socialmediatemplates">
            <h1>SOCIAL MEDIA TEMPLATES</h1>
          </nuxt-link>
        </div>
        <div class="shop_sub_carousel left_align">
          <!-- replace it with valid category products -->
          <carousel :per-page="4" :mouse-drag="false" :center-mode="true" :paginationEnabled="false">
            <slide v-for="planner in printablePlanners" :key="planner">
              <img :src="'/images/1/' + planner.image[0].imageUrl">
            </slide>
          </carousel>
        </div>
      </div>
      <div class="shop_slider">
        <div class="shop_sub_cat right_align">
          <nuxt-link to="/artprints">
            <h1>ART PRINTS</h1>
          </nuxt-link>
        </div>
        <div class="shop_sub_carousel right_align">
          <!-- replace it with valid category products -->
          <carousel :per-page="4" :mouse-drag="false" :center-mode="true" :paginationEnabled="false">
            <slide v-for="planner in printablePlanners" :key="planner">
              <img :src="'/images/1/' + planner.image[0].imageUrl">
            </slide>
          </carousel>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapActions } from 'vuex'

export default {
  data () {
    return {
      products: [],
      printablePlanners: [],
      logoDesign: [],
      socialMediaTemplates: [],
      artPrints: []
    }
  },
  created () {
    axios.get('http://localhost:8083/default/products/all'
    ).then((response) => {
      this.products = response.data
      this.setProducts(this.products)
      for (const o of this.products) {
        for (const cat of o.category) {
          if (cat.name === 'Printable Planners') {
            this.printablePlanners.push(o)
          } else if (cat.name === 'Logo Design') {
            this.logoDesign.push(o)
          } else if (cat.name === 'Social Media Templates') {
            this.socialMediaTemplates.push(o)
          } else if (cat.name === 'Art Prints') {
            this.artPrints.push(o)
          }
        }
      }
    })
      .catch(function (error) {
        console.log(error)
      })
  },
  methods: {
    ...mapActions(['setProducts'])
  }
}
</script>

<style>
.wrapper {
    margin-top: 2.5%;
}
.shop_sub_cat {
    width: 15%;
    height: 230px;
    background-color: #fff;
}
.shop_sub_cat a {
    display: table;
    height: 230px;
    width: 100%;
}
.shop_sub_cat.left_align {
    margin-left: 7%;
    margin-right: 4.8%;
}
.shop_sub_cat.right_align {
    margin-left: 4.1%;
    margin-right: 7%;
}
.shop_sub_cat h1 {
    font-size: 54px;
    font-family: Rock;
    text-align: center;
    display: table-cell;
    vertical-align: middle;
    color: #000;
}
.shop_slider {
    height: 230px;
    margin-bottom: 3%;
}
.shop_sub_carousel {
    width: 66.94%;
    height: 230px;
}
.shop_sub_carousel .VueCarousel .VueCarousel-slide img {
    height: 230px;
}
</style>
