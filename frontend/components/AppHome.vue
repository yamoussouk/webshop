<template>
  <div class="main_section">
    <div class="wrap">
      <div class="wrap-inner">
        <div class="one-sixth"></div>
        <div class="five-sixth">
          <div class="column">
            <div class="home_page_box">
              <div class="grey_line_wrapper left_align">
                <span class="grey_line"></span>
              </div>
              <div class="text_wrapper left_align">
                <nuxt-link to="/shop">SHOP</nuxt-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="wrap">
      <div class="wrap-inner">
        <div class="five-sixth">
          <div class="column">
            <div class="home_page_box">
              <div class="grey_line_wrapper right_align">
                <span class="grey_line"></span>
              </div>
              <div class="text_wrapper right_align">
                <nuxt-link to="/freebies">FREEBIES</nuxt-link>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="one-sixth"></div>
    </div>

    <div class="wrap">
      <div class="wrap-inner">
        <div class="one-sixth"></div>
        <div class="five-sixth">
          <div class="column">
            <div class="home_page_box">
              <div class="grey_line_wrapper left_align">
                <span class="grey_line"></span>
              </div>
              <div class="text_wrapper left_align">
                <nuxt-link to="/aboutme">ABOUT ME</nuxt-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="wrap">
      <div class="wrap-inner">
        <div class="five-sixth">
          <div class="column">
            <div class="home_page_box">
              <div class="grey_line_wrapper right_align">
                <span class="grey_line"></span>
              </div>
              <div class="text_wrapper right_align">
                <nuxt-link to="/contact">CONTACT</nuxt-link>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="one-sixth"></div>
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
.five-sixth {
    width: 81.333%;
    float: left;
    margin: 0 1% 40px;
}
.column {
    transition: all .4s ease-in-out;
    border: 1px solid;
    text-align: left;
}
.home_page_box {
    height: 290px;
    background-color: #fff;
}
.grey_line_wrapper {
    margin: 0 5%;
    height: 290px;
}
.left_align {
    float: left;
}
.right_align {
    float: right;
}
.grey_line {
    width: 55px;
    background-color: rgba(84, 76, 98, 0.54);
    height: 290px;
    display: inline-block;
}
.text_wrapper {
    text-align: center;
    position: relative;
    height: 290px;
    display: table;
}
.text_wrapper a {
    font-size: 79px;
    font-family: rock;
    text-align: center;
    color: #393839;
    display: table-cell;
    vertical-align: middle;
}
.text_wrapper a:hover {
    text-decoration: none;
}
.column:hover {
    -webkit-transform: scale(1.1);
    -ms-transform: scale(1.1);
    transform: scale(1.1);
    -webkit-box-shadow: 4px 4px 5px 0px rgba(204, 204, 204, 1);
    -moz-box-shadow: 4px 4px 5px 0px rgba(204, 204, 204, 1);
    box-shadow: 4px 4px 5px 0px rgba(204, 204, 204, 1);
}
</style>
