<template>
  <div class="sidebar">
    <div class="sidebar_top">
      <div class="sidebar_search">
        <div class="search_with_icon">
          <span>
            <img :src="'/page_assets/magnifier.png'" alt="magnifier">
          </span>
          <div class="search_field">
            <input id="product_search_field" :value="search" @keyup="onChange($event)" placeholder="search items">
          </div>
        </div>
      </div>
      <div class="sidebar_categories">
        <ul>
          <li ref="cats" v-for="(category, index) in cats" :key="index" @click="filterCategory(category.toUpperCase(), index)">
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
    <div @click="toggleMenu" class="sidebar_mobile">
      <div class="sidebar_button_wrapper">
        <span>SECTIONS</span>
        <span><img :class="{opened : openSubmenu}" :src="'/page_assets/arrow-02.png'" alt="dropdown-carrot"></span>
      </div>
      <div v-show="openSubmenu" class="sidebar_mobile_menu_wrapper">
        <ul>
          <li ref="catsmobile" v-for="(category, index) in cats" :key="index" @click="filterCategory(category.toUpperCase(), index)">
            <span>{{ category.toUpperCase() }}</span>
          </li>
        </ul>
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
  data () {
    return {
      openSubmenu: false
    }
  },
  computed: {
    cats () {
      return this.$props.categories
    }
  },
  mounted () {
    this.$refs.cats[0].classList.add('active')
    this.$refs.catsmobile[0].classList.add('active')
  },
  methods: {
    onChange (event) {
      this.$emit('update:search', event.target.value)
    },
    filterCategory (filter, index) {
      this.$emit('filter', filter)
      for (const ref in this.$refs.cats) {
        this.$refs.cats[ref].classList.remove('active')
        this.$refs.catsmobile[ref].classList.remove('active')
      }
      this.$refs.cats[index].classList.add('active')
      this.$refs.catsmobile[index].classList.add('active')
    },
    toggleMenu () {
      this.openSubmenu = !this.openSubmenu
    }
  }
}
</script>

<style scoped>
@media only screen and (max-width: 2560px){
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
  .sidebar_mobile {
    display: none;
  }
}
@media only screen and (max-width: 1920px){
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
  .sidebar_mobile {
    display: none;
  }
}
@media only screen and (max-width: 1680px){
  .contact_me_wrapper img {
    left: 50px;
  }
}
@media only screen and (max-width: 1600px){
  .ratings img:nth-child(1) {
    margin-left: 6%;
  }
}
@media only screen and (max-width: 1440px){
  .sidebar_categories ul li {
    font-size: 40px;
  }
  .sidebar_categories ul li {
    padding-left: 17%;
  }
  .contact_me_wrapper {
    margin-left: 17%;
    width: 75%;
  }
  .contact_me_wrapper span {
    font-size: 45px;
    top: 7px;
  }
  .sidebar_categories {
    margin-bottom: 5%;
  }
  .ratings img:nth-child(1) {
    margin-left: 9%;
  }
  .ratings img {
    width: 12%;
  }
  .comments {
    margin: 5% 10% 0 17%;
  }
  .comments p {
    font-size: 35px;
  }
}
@media only screen and (max-width: 1366px){
  .sidebar_categories ul li {
    font-size: 40px;
  }
  #product_search_field {
    font-size: 40px;
  }
  .contact_me_wrapper span {
    font-size: 40px;
  }
  .contact_me_wrapper img {
    width: 60%;
    top: 16px;
  }
}
@media only screen and (max-width: 1112px){
  .sidebar_categories ul li, .comments p {
    font-size: 28px;
  }
  .sidebar_categories ul li {
    padding-left: 21%;
  }
  .contact_me_wrapper span {
    font-size: 30px;
  }
  .contact_me_wrapper img {
    width: 50%;
    top: 12px;
    left: 40px;
  }
}
@media only screen and (max-width: 1024px){
  .sidebar {
    width: 20%;
    float: left;
    margin-left: 5%;
    margin-right: 5%;
  }
  .sidebar_top, .sidebar_bottom {
    display: none;
  }
  .sidebar_mobile {
    background: #fff;
    padding: 7% 8% 0;
    font-family: Daun;
    font-size: 30px;
    cursor: pointer;
    position: relative;
    display: block;
    letter-spacing: 3px;
    height: 60px;
  }
  .sidebar_button_wrapper img {
    width: 20%;
  }
  .sidebar_mobile_menu_wrapper {
    position: absolute;
    width: 300px;
    background: #fff;
    left: 0;
    top: 75px;
    z-index: 100;
  }
  .sidebar_mobile_menu_wrapper ul {
    list-style: none;
    padding-top: 25px;
    padding-left: 0;
  }
  .sidebar_button_wrapper ul ul li.active {
    background-color: #cd9e8f;
    color: #fff;
  }
  .sidebar_button_wrapper img.opened {
    transform: rotate(180deg);
  }
  .sidebar_button_wrapper img {
    transition: all .5s ease;
  }
  .sidebar_mobile_menu_wrapper ul li.active {
    background-color: #cd9e8f;
    color: #fff;
  }
  .sidebar_mobile_menu_wrapper ul li {
    padding: 5px 0 0 20px;
    height: 40px;
  }
}
@media only screen and (max-width: 834px){
  .sidebar {
    width: 25%;
  }
}
@media only screen and (max-width: 812px){
  .sidebar_mobile {
    padding: 6% 8% 0;
    font-size: 25px;
    height: 50px;
  }
  .sidebar_button_wrapper img {
    width: 15%;
  }
  .sidebar {
    width: 21%;
  }
}
@media only screen and (max-width: 768px){
  .sidebar_mobile {
    padding: 8% 8% 0;
    font-size: 24px;
  }
  .sidebar_mobile_menu_wrapper {
    top: 55px;
  }
}
@media only screen and (max-width: 767px){
}
@media only screen and (max-width: 737px){
  .sidebar_mobile {
    font-size: 23px;
  }
}
@media only screen and (max-width: 667px){
  .sidebar_mobile {
    padding: 11% 8% 0;
    font-size: 20px;
  }
  .sidebar_mobile_menu_wrapper {
    width: 190px;
    top: 55px;
  }
}
@media only screen and (max-width: 568px){
  .sidebar_mobile {
    letter-spacing: 1px;
  }
}
@media only screen and (max-width: 414px){
  .sidebar_mobile {
    font-size: 17px;
    height: 40px;
    width: 30%;
    margin-left: 2.5%;
    padding: 2.5% 4% 0;
  }
  .sidebar {
    display: table-footer-group;
    float: none;
  }
  .sidebar_button_wrapper img {
    width: 20%;
  }
  .sidebar_mobile_menu_wrapper {
    top: 45px;
  }
  .sidebar_mobile_menu_wrapper ul {
    margin-bottom: 0;
    padding-top: 10px;
  }
}
@media only screen and (max-width: 375px){
  .sidebar_mobile {
    margin-left: 2.5%;
    padding: 2.8% 3% 0;
  }
}
@media only screen and (max-width: 360px){
  .sidebar_mobile {
    padding: 2.8% 2.7% 0;
  }
}
@media only screen and (max-width: 320px){
  .sidebar_mobile {
    padding: 2.8% 3% 0;
    width: 35%;
  }
}
</style>
