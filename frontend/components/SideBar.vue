<template>
  <div class="sidebar">
    <div class="sidebar_top">
      <div class="sidebar_search">
        <div class="search_with_icon">
          <span>
            <img :src="'/page_assets/magnifier.png'" alt="magnifier">
          </span>
          <div class="search_field">
            <input id="product_search_field" placeholder="search items" :value="search" @keyup="onChange($event)">
          </div>
        </div>
      </div>
      <div class="sidebar_categories">
        <ul>
          <li v-for="(category, index) in cats" :key="index" ref="cats" @click="filterCategory(category.toUpperCase(), index)">
            {{ category.toUpperCase() }}
          </li>
        </ul>
      </div>
      <nuxt-link to="/contact">
        <div class="contact_me_wrapper">
          <img :src="'/page_assets/envelop.png'" alt="envelop">
          <span>contact me</span>
        </div>
      </nuxt-link>
    </div>
    <div class="sidebar_bottom">
      <div class="ratings">
        <img :src="'/page_assets/star.png'" alt="star">
        <img :src="'/page_assets/star.png'" alt="star">
        <img :src="'/page_assets/star.png'" alt="star">
        <img :src="'/page_assets/star.png'" alt="star">
        <img :src="'/page_assets/star.png'" alt="star">
      </div>
      <div class="comments">
        <p>"This is my absolutely favourite daily insert that I've found. Thank you so much!"</p>
        <p>"This planner is perfect for getting my blog started and going!"</p>
        <p>"The perfect addition to my digital planner. What a wonderfool tool to keep me on track!"</p>
        <p>"Love this spread. Even if I'm uninspired on any particular day, one look at this page and I want to fill it ou!"</p>
        <p>"I am very happy whit this purchase. Definitely made my planner setup very easy."</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    search: {
      type: String,
      default: ''
    },
    categories: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  computed: {
    cats () {
      return this.$props.categories
    }
  },
  mounted () {
    this.$refs.cats[0].classList.add('active')
  },
  methods: {
    onChange (event) {
      this.$emit('update:search', event.target.value)
    },
    filterCategory (filter, index) {
      this.$emit('filter', filter)
      for (const ref in this.$refs.cats) {
        this.$refs.cats[ref].classList.remove('active')
      }
      this.$refs.cats[index].classList.add('active')
    }
  }
}
</script>

<style scoped>
.sidebar_top {
    margin-bottom: 10%;
    padding-bottom: 5%;
    background-color: #fff;
}
.sidebar_search {
    margin-left: 4%;
    padding-top: 9%;
}
.search_with_icon span {
    float: left;
    margin-right: 3%;
}
.search_with_icon span img {
    padding-top: 5px;
}
#product_search_field {
    width: 78% !important;
    height: 60px;
    border-radius: 10px;
    font-family: Daun !important;
    font-size: 45px;
    padding: 20px 0 0 10px;
    border: 1px solid #000;
}
.sidebar_categories {
    margin-bottom: 10%;
}
.sidebar_categories ul {
    padding-top: 5%;
    list-style: none;
    padding-left: 0;
}
.sidebar_categories ul li {
    color: #000;
    font-family: Daun;
    font-size: 50px;
    letter-spacing: 3px;
    padding-left: 15%;
    padding-top: 3%;
}
.contact_me_wrapper {
    border: 1px solid #000;
    border-radius: 10px;
    width: 70%;
    margin-left: 15%;
    display: table;
}
.contact_me_wrapper img {
    float: left;
    position: relative;
    left: 80px;
    top: 20px;
}
.contact_me_wrapper span {
    color: #000;
    font-family: Daun;
    font-size: 50px;
    letter-spacing: 3px;
    display: table-cell;
    vertical-align: middle;
    text-align: center;
    position: relative;
    top: 5px;
}
.sidebar_bottom {
    background-color: #fff;
    padding-bottom: 10px;
}
.ratings {
    padding-top: 10%;
    text-align: center;
}
.ratings img {
    margin-right: 2%;
}
.comments {
    margin: 5% 10% 0 15%;
}
.comments p {
    font-family: Daun;
    font-size: 40px;
    line-height: 40px;
    text-align: justify;
    margin-bottom: 8%;
}
.sidebar_categories ul li.active {
  background-color: #cd9e8f;
  color: #fff;
}
</style>
