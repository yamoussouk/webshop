<template>
  <div class="instagram_feed">
    <h1>Recent posts</h1>
    <transition name="welcome">
      <vue-instagram :key="componentKey" :count="counter" token="288735132.1677ed0.ab1ad3a0c04d4a1b85c1edd79ee56f1e" media-type="image" class="columns is-multiline is-mobile">
        <template slot="feeds" slot-scope="props">
          <li class="fancy-list">
            <a :href="props.feed.link"><img :src="props.feed.images.standard_resolution.url"></a>
          </li>
          <input :value="feedUrl(props)" type="hidden">
        </template>
        <template slot="error" slot-scope="props">
          <div class="fancy-alert">
            {{ props.error.error_message }}
          </div>
        </template>
      </vue-instagram>
    </transition>
    <div class="fix" />
    <div class="feed_buttons">
      <div class="left_wrapper">
        <a :href="followUrl()" class="follow_button" target="_blank"><span>Follow</span></a>
      </div>
      <div class="right_wrapper">
        <span @click="increaseCounter" class="show_more"><span>Show more</span></span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      username: '',
      counter: 5,
      componentKey: 0
    }
  },
  methods: {
    feedUrl (props) {
      this.username = props.feed.user.username
    },
    followUrl () {
      return 'https://instagram.com/' + this.username
    },
    increaseCounter () {
      this.componentKey += 1
      this.counter += 5
    }
  }
}
</script>
<style>
@media only screen and (max-width: 2560px){
  .fancy-list {
      list-style: none;
      float: left;
      width: 24.8%;
      margin-left: 0.2%;
      margin-bottom: 0.2%;
  }
  .fancy-list a {
      width: 468px;
      height: 468px;
  }
  .fancy-list a img {
      width: 100%;
  }
  .fix {
      clear: both;
  }
  .columns {
      width: 100%;
      height: 493px;
      padding: 0 5%;
  }
  .instagram_feed {
      padding-top: 4%;
      padding-bottom: 4%;
      text-align: center;
  }
  .instagram_feed h1 {
      font-family: Daun;
      font-size: 60px;
      color: #000;
      margin-bottom: 2%;
      letter-spacing: 6px;
  }
  .follow_button {
      background-color: rgba(31,89,97, 0.77);
      color: #fff;
      width: 100%;
      font-size: 60px;
      font-family: Daun;
      height: 80px;
      opacity: 0.77;
      line-height: 50px;
      border-radius: 10px;
      cursor: pointer;
      float: right;
      display: table;
      letter-spacing: 3px;
  }
  .follow_button span {
      display: table-cell;
      vertical-align: bottom;
  }
  .welcome-enter {
    opacity: 0;
  }
  .welcome-enter-active {
    transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
  }
  .welcome-leave-active,
  .welcome-leave-to {
    opacity: 0;
  }
  .show_more {
      background-color: #fff;
      color: #1f5961;
      width: 100%;
      font-size: 60px;
      font-family: Daun;
      height: 80px;
      opacity: 0.77;
      line-height: 50px;
      border-radius: 10px;
      cursor: pointer;
      border: 1px solid #1f5961;
      float: left;
      display: table;
  }
  .show_more span {
      display: table-cell;
      vertical-align: bottom;
  }
  .left_wrapper {
      margin-right: 0.5%;
      margin-left: 19.75%;
  }
  .right_wrapper {
      margin-left: 0.5%;
  }
  .left_wrapper, .right_wrapper {
      float: left;
      width: 30%;
  }
  .feed_buttons {
      max-width: 1140px;
      text-align: center;
      margin: 0 auto;
  }
}
@media only screen and (max-width: 1920px){
  .fancy-list {
      list-style: none;
      float: left;
      width: 24.8%;
      margin-left: 0.2%;
      margin-bottom: 0.2%;
  }
  .fancy-list a {
      width: 468px;
      height: 468px;
  }
  .fancy-list a img {
      width: 100%;
  }
  .fix {
      clear: both;
  }
  .columns {
      width: 100%;
      height: 493px;
      padding: 0 5%;
  }
  .instagram_feed {
      padding-top: 4%;
      padding-bottom: 4%;
      text-align: center;
  }
  .instagram_feed h1 {
      font-family: Daun;
      font-size: 60px;
      color: #000;
      margin-bottom: 2%;
      letter-spacing: 6px;
  }
  .follow_button {
      background-color: rgba(31,89,97, 0.77);
      color: #fff;
      width: 100%;
      font-size: 60px;
      font-family: Daun;
      height: 80px;
      opacity: 0.77;
      line-height: 50px;
      border-radius: 10px;
      cursor: pointer;
      float: right;
      display: table;
      letter-spacing: 3px;
  }
  .follow_button span {
      display: table-cell;
      vertical-align: bottom;
  }
  .welcome-enter {
    opacity: 0;
  }
  .welcome-enter-active {
    transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
  }
  .welcome-leave-active,
  .welcome-leave-to {
    opacity: 0;
  }
  .show_more {
      background-color: #fff;
      color: #1f5961;
      width: 100%;
      font-size: 60px;
      font-family: Daun;
      height: 80px;
      opacity: 0.77;
      line-height: 50px;
      border-radius: 10px;
      cursor: pointer;
      border: 1px solid #1f5961;
      float: left;
      display: table;
  }
  .show_more span {
      display: table-cell;
      vertical-align: bottom;
  }
  .left_wrapper {
      margin-right: 0.5%;
      margin-left: 19.75%;
  }
  .right_wrapper {
      margin-left: 0.5%;
  }
  .left_wrapper, .right_wrapper {
      float: left;
      width: 30%;
  }
  .feed_buttons {
      max-width: 1140px;
      text-align: center;
      margin: 0 auto;
  }
}
@media only screen and (max-width: 1680px){
  .columns {
    height: 400px;
  }
  .instagram_feed {
    padding-top: 3%;
  }
  .instagram_feed h1 {
      margin-bottom: 1%;
  }
}
@media only screen and (max-width: 1440px){
  .instagram_feed h1, .sign_up_form h1 {
    font-size: 55px;
  }
  .columns {
    height: 340px;
  }
}
@media only screen and (max-width: 1366px){
}
@media only screen and (max-width: 1112px){
  .instagram_feed h1, .sign_up_form h1 {
    font-size: 45px;
  }
  .columns {
    height: 270px;
  }
  .follow_button, .show_more {
    width: 80%;
    font-size: 50px;
    height: 60px;
    line-height: 40px;
  }
}
@media only screen and (max-width: 1024px){
  .instagram_feed h1, .sign_up_form h1 {
    font-size: 35px;
  }
  .follow_button, .show_more {
    width: 70%;
    font-size: 40px;
    height: 50px;
    line-height: 30px;
  }
  .columns {
    height: 240px;
  }
}
@media only screen and (max-width: 834px){
  .follow_button, .show_more {
    width: 60%;
    font-size: 30px;
    height: 40px;
  }
  .columns {
    height: 200px;
  }
}
@media only screen and (max-width: 812px){
}
@media only screen and (max-width: 768px){
  .columns {
    height: 180px;
  }
}
@media only screen and (max-width: 767px){
  .instagram_feed h1, .sign_up_form h1 {
    font-size: 30px;
  }
  .follow_button, .show_more {
    width: 100%;
    font-size: 30px;
    height: 40px;
  }
  .left_wrapper, .right_wrapper, form input {
    float: initial;
    width: 100%;
  }
  .left_wrapper, .right_wrapper {
    margin-right: 0;
    margin-left: 0;
  }
  .feed_buttons {
    max-width: 90%;
  }
  .left_wrapper {
    padding-bottom: 50px;
  }
  .instagram_feed {
    padding-bottom: 5%;
  }
}
@media only screen and (max-width: 736px){
}
@media only screen and (max-width: 667px){
  .instagram_feed h1, .sign_up_form h1 {
    font-size: 25px;
  }
  .instagram_feed {
    padding-bottom: 6%;
  }
}
@media only screen and (max-width: 568px){
  .fancy-list {
    width: 49.6%;
  }
  .columns {
    height: 520px;
  }
}
@media only screen and (max-width: 414px){
  .fancy-list {
    width: 100%;
    margin-left: 0;
    margin-bottom: 0;
  }
  .feed_buttons {
    margin-top: 10px;
  }
  .instagram_feed {
    padding-bottom: 10%;
  }
}
@media only screen and (max-width: 375px){
  .instagram_feed h1, .sign_up_form h1 {
    font-size: 23px;
  }
  .follow_button, .show_more {
    font-size: 25px;
  }
}
@media only screen and (max-width: 360px){
  .instagram_feed h1, .sign_up_form h1 {
    font-size: 21px;
  }
}
@media only screen and (max-width: 320px){
  .instagram_feed h1, .sign_up_form h1 {
    font-size: 20px;
    letter-spacing: 2px;
    margin-top: 10px;
  }
}
</style>
