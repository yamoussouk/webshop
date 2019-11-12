<template>
  <div class="main_section">
    <div class="wrap">
      <div class="wrap-inner">
        <div class="full-width">
          <div class="column">
            <div class="anchor_box left_box">
              <div class="text_wrapper left_align" style="background-color: #c8b65e;">
                <nuxt-link to="/shop">
                  shop
                </nuxt-link>
              </div>
            </div>
            <img class="shop-image left-image" alt="shop-image" src="/page_assets/shop_photos-01.png">
            <img class="shop-image left-image" alt="shop-image" src="/page_assets/shop_photos-02.png">
            <img class="shop-image left-image" alt="shop-image" src="/page_assets/shop_photos-03.png">
          </div>
        </div>
      </div>
    </div>

    <div class="wrap">
      <div class="wrap-inner">
        <div class="full-width">
          <div class="column">
            <img class="shop-image right-image" alt="freebies-image" src="/page_assets/freebies_photos-01.png">
            <img class="shop-image right-image" alt="freebies-image" src="/page_assets/freebies_photos-02.png">
            <img class="shop-image right-image" alt="freebies-image" src="/page_assets/freebies_photos-03.png">
            <div class="anchor_box right_box">
              <div class="text_wrapper right_align" style="background-color: #cd9e8f;">
                <nuxt-link to="/freebies">
                  freebies
                </nuxt-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="wrap">
      <div class="wrap-inner">
        <div class="full-width">
          <div class="column">
            <div class="anchor_box left_box">
              <div class="text_wrapper left_align" style="background-color: #5ea99e;">
                <nuxt-link to="/aboutme">
                  about me
                </nuxt-link>
              </div>
            </div>
            <img class="shop-image left-image" alt="shop-image" src="/page_assets/aboutme_photos-01.png">
            <img class="shop-image left-image" alt="shop-image" src="/page_assets/aboutme_photos-02.png">
            <img class="shop-image left-image" alt="shop-image" src="/page_assets/aboutme_photos-03.png">
          </div>
        </div>
      </div>
    </div>

    <div class="wrap">
      <div class="wrap-inner">
        <div class="full-width">
          <div class="column">
            <img class="shop-image right-image" alt="freebies-image" src="/page_assets/contact_photos-01.png">
            <img class="shop-image right-image" alt="freebies-image" src="/page_assets/contact_photos-02.png">
            <img class="shop-image right-image" alt="freebies-image" src="/page_assets/contact_photos-03.png">
            <div class="anchor_box right_box">
              <div class="text_wrapper right_align" style="background-color: #1f5961;">
                <nuxt-link to="/contact">
                  contact
                </nuxt-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
.main_section {
    max-width: 100%;
    margin: 0 auto;
    margin-top: 2%;
    height: 1328px;
}
.wrap {
    padding: 0 5%;
    width: 100%;
    float: left;
}
.one-sixth {
    width: 14.666%;
    float: left;
    margin: 0 1% 40px;
}
.full-width {
    width: 100%;
    margin: 0 1% 40px;
}
.column {
    transition: all .4s ease-in-out;
    text-align: left;
}
.anchor_box {
    height: 290px;
    width: 23%;
}
.left_align {
    float: left;
}
.right_align {
    float: right;
}
.text_wrapper {
    text-align: center;
    position: relative;
    height: 290px;
    display: table;
    width: 90%;
}
.text_wrapper a {
    font-size: 79px;
    font-family: Monoline;
    text-align: center;
    color: #ffffff;
    display: table-cell;
    vertical-align: middle;
}
.text_wrapper a:hover {
    text-decoration: none;
}
.text_wrapper:hover {
    opacity: 0.5;
}
.left_box {
    margin-right: 2%;
    float: left;
}
.left-image {
    margin-right: 2%;
}
.right_box {
  margin-right: 1.4%;
  float: right;
}
.right-image {
    margin-right: 2%;
}
.shop-image {
    width: 23%;
}
.left-image.shop-image:nth-child(4) {
    margin-right: 0;
}
.right-image.shop-image:nth-child(3) {
    margin-right: 0;
}
</style>
