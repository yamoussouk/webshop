<template>
  <div class="shop">
    <div class="wrapper">
      <div class="shop_categories planners">
        <nuxt-link to="/printablePlanners">
          <div class="category_img">
            <h1>printable planners</h1>
          </div>
          <div class="category_text">
            <p><b>DAILY, WEEKLY,<br/>MONTHLY INSERTS</b></p>
            <p>COMPLETE<br/>FUNCTIONAL PLANNERS</p>
            <p><b>BULLET JOURNAL<br/>PAGES</b></p>
          </div>
        </nuxt-link>
      </div>
      <div class="shop_categories logo">
        <nuxt-link to="/logodesign">
          <div class="category_img">
            <h1>logo design</h1>
          </div>
          <div class="category_text">
            <p><b>SINGLE LOGOS</b></p>
            <p>LOGO SETS</p>
            <p><b>PRE-DESIGNED AND<br/>CUSTOM WORK</b></p>
            <p>BRANDING</p>
          </div>
        </nuxt-link>
      </div>
      <div class="shop_categories templates">
        <nuxt-link to="/socialmediatemplates">
          <div class="category_img">
            <h1>social media templates</h1>
          </div>
          <div class="category_text">
            <p><b>INSTAGRAM POSTS,<br/>ICONS</b></p>
            <p>FACEBOOK AND ETSY<br/>BANNER SETS</p>
            <p><b>PERSONAL AND<br/>COMMERCIAL USE<br/>GRAPHICS</b></p>
          </div>
        </nuxt-link>
      </div>
      <div class="shop_categories prints">
        <nuxt-link to="/artprints">
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

<style>
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
</style>
