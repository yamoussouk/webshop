<template>
    <div class="sidebar">
      <div class="sidebar_top">
          <div class="sidebar_search">
              <div class="search_with_icon">
                  <span>
                      <img :src="'/page_assets/magnifier.png'" alt="magnifier" />
                  </span>
                  <div class="search_field">
                      <input id="product_search_field" placeholder="Search" v-on:keyup="onChange($event)" :value="search"/>
                  </div>
              </div>
          </div>
          <div class="sidebar_categories">
            <ul>
                <li @click="filterCategory('ALL')">ALL</li>
                <li @click="filterCategory('INSERTS')">INSERTS</li>
                <li @click="filterCategory('LIFESTYLE PLANNERS')">LIFESTYLE PLANNERS</li>
                <li @click="filterCategory('MONTHLY PLANNERS')">MONTHLY PLANNERS</li>
                <li @click="filterCategory('DAILY PLANNERS')">DAILY PLANNERS</li>
            </ul>
          </div>
          <nuxt-link to="/contact">
            <div class="contact_me_wrapper">
              <img :src="'/page_assets/envelop.png'" alt="envelop" />
              <span>contact me</span>
            </div>
          </nuxt-link>
      </div>
      <div class="sidebar_bottom">
          <div class="ratings">
              <img :src="'/page_assets/star.png'" alt="star" />
              <img :src="'/page_assets/star.png'" alt="star" />
              <img :src="'/page_assets/star.png'" alt="star" />
              <img :src="'/page_assets/star.png'" alt="star" />
              <img :src="'/page_assets/star.png'" alt="star" />
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
    search: String
  },
  methods: {
    onChange (event) {
      this.$emit('update:search', event.target.value)
    },
    filterCategory (filter) {
      this.$emit('filter', filter)
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
    font-family: Audrey !important;
    font-size: 25px;
    padding-left: 10px;
}
.sidebar_categories {
    margin-bottom: 10%;
}
.sidebar_categories ul {
    margin-left: 15%;
    padding-top: 5%;
    list-style: none;
    padding-left: 0;
}
.sidebar_categories ul li {
    margin-top: 5%;
    color: #000;
    font-family: Audrey;
    font-size: 36px;
    letter-spacing: 3px;
}
.contact_me_wrapper {
    border: 1px solid #000;
    border-radius: 10px;
    width: 75%;
    padding: 3% 0;
    margin-left: 15%;
    display: table;
}
.contact_me_wrapper img {
    float: left;
    position: relative;
    left: 80px;
    top: 10px;
}
.contact_me_wrapper span {
    color: #000;
    font-family: Audrey;
    font-size: 36px;
    letter-spacing: 3px;
    display: table-cell;
    vertical-align: middle;
    text-align: center;
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
    font-family: Audrey;
    font-size: 30px;
    line-height: 40px;
    text-align: justify;
    margin-bottom: 8%;
}
</style>
