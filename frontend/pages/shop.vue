<template>
  <div class="shop">
    <div class="wrapper">
      <div class="shop_categories planners">
        <nuxt-link to="/printablePlanners">
          <div class="category_img">
            <h1>printable planners</h1>
          </div>
          <div class="category_text">
            <p><b>DAILY, WEEKLY,<br>MONTHLY INSERTS</b></p>
            <p>COMPLETE<br>FUNCTIONAL PLANNERS</p>
            <p><b>BULLET JOURNAL<br>PAGES</b></p>
          </div>
        </nuxt-link>
      </div>
      <div class="shop_categories logo">
        <nuxt-link to="/logoDesign">
          <div class="category_img">
            <h1>logo design</h1>
          </div>
          <div class="category_text">
            <p><b>SINGLE LOGOS</b></p>
            <p>LOGO SETS</p>
            <p><b>PRE-DESIGNED AND<br>CUSTOM WORK</b></p>
            <p>BRANDING</p>
          </div>
        </nuxt-link>
      </div>
      <div class="shop_categories templates">
        <nuxt-link to="/socialMediaTemplates">
          <div class="category_img">
            <h1>social media templates</h1>
          </div>
          <div class="category_text">
            <p><b>INSTAGRAM POSTS,<br>ICONS</b></p>
            <p>FACEBOOK AND ETSY<br>BANNER SETS</p>
            <p><b>PERSONAL AND<br>COMMERCIAL USE<br>GRAPHICS</b></p>
          </div>
        </nuxt-link>
      </div>
      <div class="shop_categories prints">
        <nuxt-link to="/artPrints">
          <div class="category_img">
            <h1>art prints</h1>
          </div>
          <div class="category_text">
            <p><b>STICKER SHEETS</b></p>
            <p>A5 AND MINI PRINTS</p>
            <p><b>SINGLE STICKERS</b></p>
            <p>ZINES</p>
          </div>
        </nuxt-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  middleware: 'location'
}
</script>

<style scoped>
@media only screen and (max-width: 2560px){
  .wrapper {
      margin: 2.5% auto 0 auto;
      width: 1328px;
      height: 550px;
  }
  .shop_categories {
      height: 500px;
      margin-bottom: 3%;
      float: left;
      width: 22%;
      margin-right: 3%;
      background-color: #fff;
      transition: all .5s ease-in-out;
  }
  .shop_categories .category_text p {
      font-family: Daun;
      font-size: 20px;
      text-align: center;
  }
  .shop_categories h1 {
      font-family: Monoline;
      color: #fff;
      font-size: 60px;
      text-align: center;
  }
  .shop_categories a {
      color: #000;
  }
  .shop_categories:hover {
      opacity: 0.5;
      transform: scale(1.05);
  }
  .shop_categories a:hover {
      text-decoration: none;
  }
  .shop_categories.planners .category_img {
      background-color: #c8b65e;
  }
  .shop_categories.logo .category_img {
      background-color: #cd9e8f;
  }
  .shop_categories.templates .category_img {
      background-color: #5ea99e;
  }
  .shop_categories.prints .category_img {
      background-color: #1f5961;
  }
  .shop_categories .category_img {
      width: 90%;
      margin: 5% auto;
      height: 40%;
  }
  .shop_categories.planners h1, .templates h1 {
      padding: 12%;
  }
  .shop_categories.logo h1, .prints h1 {
      padding: 25% 12%;
  }
  .category_text {
      margin-top: 12%;
      height: 250px;
  }
  .category_text p {
      line-height: 30px;
  }
  .shop_categories.planners .category_text p {
      height: 70px;
  }
  .shop_categories.logo .category_text p:nth-child(1) {
      height: 17%;
  }
  .shop_categories.logo .category_text p:nth-child(2) {
      height: 17%;
  }
  .shop_categories.logo .category_text p:nth-child(3) {
      height: 27%;
  }
  .shop_categories.templates .category_text p {
      height: 21%;
  }
  .shop_categories.prints .category_text p {
      height: 20%;
  }
}
@media only screen and (max-width: 1920px){
  .wrapper {
      margin: 2.5% auto 0 auto;
      width: 1328px;
      height: 550px;
  }
  .shop_categories {
      height: 500px;
      margin-bottom: 3%;
      float: left;
      width: 22%;
      margin-right: 3%;
      background-color: #fff;
      transition: all .5s ease-in-out;
  }
  .shop_categories .category_text p {
      font-family: Daun;
      font-size: 20px;
      text-align: center;
  }
  .shop_categories h1 {
      font-family: Monoline;
      color: #fff;
      font-size: 60px;
      text-align: center;
  }
  .shop_categories a {
      color: #000;
  }
  .shop_categories:hover {
      opacity: 0.5;
      transform: scale(1.05);
  }
  .shop_categories a:hover {
      text-decoration: none;
  }
  .shop_categories.planners .category_img {
      background-color: #c8b65e;
  }
  .shop_categories.logo .category_img {
      background-color: #cd9e8f;
  }
  .shop_categories.templates .category_img {
      background-color: #5ea99e;
  }
  .shop_categories.prints .category_img {
      background-color: #1f5961;
  }
  .shop_categories .category_img {
      width: 90%;
      margin: 5% auto;
      height: 40%;
  }
  .shop_categories.planners h1, .templates h1 {
      padding: 12%;
  }
  .shop_categories.logo h1, .prints h1 {
      padding: 25% 12%;
  }
  .category_text {
      margin-top: 12%;
      height: 250px;
  }
  .category_text p {
      line-height: 30px;
  }
  .shop_categories.planners .category_text p {
      height: 70px;
  }
  .shop_categories.logo .category_text p:nth-child(1) {
      height: 17%;
  }
  .shop_categories.logo .category_text p:nth-child(2) {
      height: 17%;
  }
  .shop_categories.logo .category_text p:nth-child(3) {
      height: 27%;
  }
  .shop_categories.templates .category_text p {
      height: 21%;
  }
  .shop_categories.prints .category_text p {
      height: 20%;
  }
}
@media only screen and (max-width: 1680px){
}
@media only screen and (max-width: 1600px){
}
@media only screen and (max-width: 1440px){
}
@media only screen and (max-width: 1366px){
}
@media only screen and (max-width: 1112px){
  .wrapper {
    width: 1090px;
    height: 530px;
  }
  .shop_categories {
    width: 23.5%;
    margin-right: 2%;
  }
  .shop_categories:nth-child(4) {
    margin-right: 0;
  }
  .shop_categories h1 {
    font-size: 50px;
  }
  .shop_categories .category_img {
    display: table;
  }
  .shop_categories.planners h1, .templates h1 {
    padding: 0;
    display: table-cell;
    vertical-align: middle;
  }
  .shop_categories.logo h1, .prints h1 {
    padding: 29% 12%;
  }
  .shop_categories.planners .category_text p {
    height: 53px;
  }
  .shop_categories.logo .category_text p:nth-child(1), .shop_categories.prints .category_text p {
    height: 20.5%;
  }
  .shop_categories.logo .category_text p:nth-child(2) {
    height: 20.5%;
  }
  .shop_categories.planners .category_text p {
    height: 71px;
  }
  .shop_categories.logo .category_text p:nth-child(3) {
    height: 21%;
  }
  .shop_categories.templates .category_text p {
    height: 22.4%;
  }
}
@media only screen and (max-width: 1024px){
  .wrapper {
    width: 95%;
  }
  .shop_categories.logo h1, .prints h1 {
    padding: 31% 12%;
  }
}
@media only screen and (max-width: 834px){
  .shop_categories {
    width: 48%;
    margin-right: 4%;
    margin-bottom: 4%
  }
  .shop_categories:nth-child(2n) {
    margin-right: 0;
  }
  .shop_categories h1 {
    font-size: 60px;
  }
  .shop_categories.logo h1, .prints h1 {
    padding: 21% 12% 0;
  }
  .category_text[data-v-760d1422] {
    margin-top: 6%;
  }
  .shop_categories.planners .category_text p {
    height: 80px;
  }
  .shop_categories.logo .category_text p:nth-child(3) {
    height: 28%;
  }
  .shop_categories.templates .category_text p {
    height: 26.4%;
  }
  .shop_categories.logo .category_text p:nth-child(1), .shop_categories.prints .category_text p {
    height: 23%;
  }
}
@media only screen and (max-width: 812px){
  .shop_categories.planners .category_text p {
    height: 82px;
  }
  .shop_categories.logo .category_text p:nth-child(1), .shop_categories.prints .category_text p {
    height: 23.5%;
  }
}
@media only screen and (max-width: 737px){
  .shop_categories.logo .category_text p:nth-child(3) {
    height: 27.5%;
  }
}
@media only screen and (max-width: 667px){
  .shop_categories.logo h1, .prints h1 {
    padding: 25% 12% 0;
  }
}
@media only screen and (max-width: 568px){
  .shop_categories h1 {
    font-size: 50px;
  }
  .shop_categories.logo h1, .prints h1 {
    padding: 32% 12% 0;
  }
}
@media only screen and (max-width: 414px){
  .shop_categories {
    width: 100%;
    margin-right: 0;
    margin-bottom: 7%;
  }
  .shop_categories.logo h1, .prints h1 {
    padding: 0;
    display: table-cell;
    vertical-align: middle;
  }
}
@media only screen and (max-width: 375px){
}
@media only screen and (max-width: 360px){
  .shop_categories h1 {
    font-size: 45px;
  }
}
@media only screen and (max-width: 320px){
  .shop_categories h1 {
    font-size: 35px;
  }
}
</style>
