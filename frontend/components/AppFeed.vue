<template>
  <div class="instagram_feed">
    <h1>Recent posts</h1>
    <transition name="welcome">
      <vue-instagram token="" :key="componentKey" v-bind:count="counter" mediaType="image" class="columns is-multiline is-mobile">
        <template slot="feeds" slot-scope="props">
          <li class="fancy-list"><a :href="props.feed.link"><img :src=props.feed.images.standard_resolution.url /></a> </li>
          <input type="hidden" :value="feedUrl(props)" />
        </template>
        <template slot="error" slot-scope="props">
          <div class="fancy-alert"> {{ props.error.error_message }} </div>
        </template>
      </vue-instagram>
    </transition>
    <div class="fix"></div>
    <div class="feed_buttons">
      <div class="left_wrapper">
        <a class="follow_button" v-bind:href="followUrl()" target="_blank" ><span>Follow</span></a>
      </div>
      <div class="right_wrapper">
        <span class="show_more" @click=increaseCounter ><span>Show more</span></span>
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
}
.instagram_feed {
    padding-top: 4%;
    padding-bottom: 4%;
    text-align: center;
}
.instagram_feed h1 {
    font-family: Audrey;
    font-size: 43px;
    color: rgba(163, 153, 178, 0.77);
    margin-bottom: 2%;
}
.follow_button {
    background-color: #544c62;
    color: #fff;
    width: 50%;
    font-size: 43px;
    font-family: Audrey;
    height: 70px;
    opacity: 0.77;
    line-height: 60px;
    border-radius: 10px;
    cursor: pointer;
    float: right;
    display: table;
}
.follow_button span {
    display: table-cell;
    vertical-align: middle;
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
    color: #544c62;
    width: 50%;
    font-size: 43px;
    font-family: Audrey;
    height: 70px;
    opacity: 0.77;
    line-height: 60px;
    border-radius: 10px;
    cursor: pointer;
    border: 1px solid #544c62;
    float: left;
    display: table;
}
.show_more span {
    display: table-cell;
    vertical-align: middle;
}
.left_wrapper {
    margin-right: 0.5%;
}
.right_wrapper {
    margin-left: 0.5%;
}
.left_wrapper, .right_wrapper {
    float: left;
    width: 49.5%;
}
</style>
