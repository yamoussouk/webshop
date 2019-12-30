<template>
  <div class="checkout_wrapper_inner">
    <div v-for="(item, index) in cart" :key="item.id" class="checkout_row" :class="{ alone : isAlone}">
      <div class="index_wrapper">
        <span>{{ index + 1 }}</span>
      </div>
      <div class="description_wrapper">
        <div class="image_wrapper">
          <img v-if="item.image" class="image" alt="product-image" :src="'/uploaded/' + item.id + '/' + item.image.imageUrl">
        </div>
        <div class="description">
          <span class="name">{{ item.name }}</span>
          <span v-if="item.size" class="size">{{ item.size }}</span>
          <span v-if="item.startingDay" class="starting_day">{{ item.startingDay }}</span>
          <span v-if="item.logoText" class="logo_text" :title="item.logoText">{{ item.logoText }}</span>
        </div>
      </div>
      <div class="price_wrapper">
        <div class="price">
          <span>$ {{ ('' + item.price).replace('.', ',') }}</span>
        </div>
      </div>
      <div class="close_wrapper">
        <div class="close">
          <span @click="removeFromCart(index)">x</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    cart: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  computed: {
    isAlone () {
      return this.$store.state.localStorage.localCart.length === 1
    }
  },
  methods: {
    removeFromCart (index) {
      this.$store.commit('localStorage/removeFromLocalCartByIndex', index)
    }
  }
}
</script>

<style scoped>
@media only screen and (max-width: 2560px){
  .checkout_row, .checkout_subtotal_row {
    background: #fff;
  }
  .checkout_row .index_wrapper {
    width: 5%;
    float: left;
    /* border-right: 1px solid #000; */
    height: 140px;
    text-align: center;
    position: relative;
  }
  .checkout_row:nth-child(1) .index_wrapper:before {
    content : "";
    position: absolute;
    right   : 0;
    bottom  : 0;
    height  : 85%;
    width   : 1px;
    border-left:1px solid #000;
  }
  .checkout_row:nth-last-child(1) .index_wrapper:before {
    content : "";
    position: absolute;
    right   : 0;
    top     : 0;
    height  : 85%;
    width   : 1px;
    border-left:1px solid #000;
  }
  .checkout_row:not(:nth-last-child(1)):not(:nth-child(1)) .index_wrapper:before {
    content : "";
    position: absolute;
    right   : 0;
    top     : 0;
    height  : 100%;
    width   : 1px;
    border-left:1px solid #000;
  }
  .checkout_row:not(:nth-last-child(1)) .index_wrapper:after {
    content : "";
    position: absolute;
    right   : 0;
    bottom  : 0;
    height  : 1px;
    width   : 85%;
    border-bottom:1px solid #000;
  }
  .checkout_row:not(:nth-last-child(1)) .description_wrapper:before {
    content : "";
    position: absolute;
    right   : 0;
    bottom  : 0;
    height  : 1px;
    width   : 100%;
    border-bottom:1px solid #000;
  }
  .checkout_row:not(:nth-last-child(1)):not(:nth-child(1)) .description_wrapper:after {
    content : "";
    position: absolute;
    right   : 0;
    bottom  : 0;
    height  : 100%;
    width   : 1px;
    border-right:1px solid #000;
  }
  .checkout_row:nth-child(1) .description_wrapper:after {
    content : "";
    position: absolute;
    right   : 0;
    bottom  : 0;
    height  : 85%;
    width   : 1px;
    border-right:1px solid #000;
  }
  .checkout_row:nth-last-child(1) .description_wrapper:after {
    content : "";
    position: absolute;
    right   : 0;
    top     : 0;
    height  : 85%;
    width   : 1px;
    border-right:1px solid #000;
  }
  .checkout_row:not(:nth-last-child(1)) .price_wrapper:before {
    content : "";
    position: absolute;
    right   : 0;
    bottom  : 0;
    height  : 1px;
    width   : 100%;
    border-bottom:1px solid #000;
  }
  .checkout_row:not(:nth-last-child(1)):not(:nth-child(1)) .price_wrapper:after {
    content : "";
    position: absolute;
    right   : 0;
    bottom  : 0;
    height  : 100%;
    width   : 1px;
    border-right:1px solid #000;
  }
  .checkout_row:nth-child(1) .price_wrapper:after {
    content : "";
    position: absolute;
    right   : 0;
    bottom  : 0;
    height  : 85%;
    width   : 1px;
    border-right:1px solid #000;
  }
  .checkout_row:nth-last-child(1) .price_wrapper:after {
    content : "";
    position: absolute;
    right   : 0;
    top     : 0;
    height  : 85%;
    width   : 1px;
    border-right:1px solid #000;
  }
  .checkout_row:not(:nth-child(1)) .close_wrapper:before {
    content : "";
    position: absolute;
    left    : 0;
    top     : 0;
    height  : 1px;
    width   : 85%;
    border-bottom:1px solid #000;
  }
  /*
  if there is only one row
  */
  .checkout_row.alone .index_wrapper:after, .checkout_row.alone .description_wrapper:before, .checkout_row.alone .price_wrapper:before {
    display: none;
  }
  .checkout_row.alone .index_wrapper:before, .checkout_row.alone .description_wrapper:after, .checkout_row.alone .price_wrapper:after {
    top: 7.5%;
  }
  .checkout_row.alone .close {
    top: 40px;
  }
  .checkout_row .index_wrapper span {
    font-size: 60px;
    height: 140px;
    position: relative;
    top: 40px;
  }
  .image {
    width: 180px;
    height: 100px;
    float: left;
  }
  .image_wrapper {
    margin-left: 2%;
    margin-right: 10%;
    width: 16.7%;
    padding-top: 2%;
  }
  .image_wrapper, .description {
    float: left;
    line-height: 40px;
    padding-top: 2%;
  }
  .description {
    width: 71%;
  }
  .description span {
    float: left;
    width: 100%;
  }
  .description .name {
    font-size: 50px;
  }
  .description .size, .description .starting_day, .description .logo_text {
    font-size: 45px;
    color: #bbb;
  }
  .checkout_row .description_wrapper {
    width: 65%;
    float: left;
    height: 140px;
    position: relative;
  }
  .checkout_row .price_wrapper {
    width: 20%;
    float: left;
    height: 140px;
    position: relative;
  }
  .checkout_row .close_wrapper {
    width: 9%;
    float: left;
    height: 140px;
    position: relative;
  }
  .checkout_row {
    width: 100%;
    height: 140px;
  }
  .price, .close {
    text-align: center;
    position: relative;
  }
  .price {
    top: 40px;
  }
  .price span, .close span {
    font-size: 50px;
  }
  .close span {
    color: #bbb;
    cursor: pointer;
    font-family: cursive !important;
  }
  .close {
    float: none;
    top: 40px;
  }
  #checkout_wrapper span {
    font-family: Daun;
  }
  .description_wrapper .logo_text {
    text-overflow: ellipsis;
    overflow: hidden;
    width: 360px;
    height: 1.2em;
    white-space: nowrap;
  }
}
@media only screen and (max-width: 1920px){
  .checkout_row, .checkout_subtotal_row {
    background: #fff;
  }
  .checkout_row .index_wrapper {
    width: 5%;
    float: left;
    /* border-right: 1px solid #000; */
    height: 140px;
    text-align: center;
    position: relative;
  }
  .checkout_row:nth-child(1) .index_wrapper:before {
    content : "";
    position: absolute;
    right   : 0;
    bottom  : 0;
    height  : 85%;
    width   : 1px;
    border-left:1px solid #000;
  }
  .checkout_row:nth-last-child(1) .index_wrapper:before {
    content : "";
    position: absolute;
    right   : 0;
    top     : 0;
    height  : 85%;
    width   : 1px;
    border-left:1px solid #000;
  }
  .checkout_row:not(:nth-last-child(1)):not(:nth-child(1)) .index_wrapper:before {
    content : "";
    position: absolute;
    right   : 0;
    top     : 0;
    height  : 100%;
    width   : 1px;
    border-left:1px solid #000;
  }
  .checkout_row:not(:nth-last-child(1)) .index_wrapper:after {
    content : "";
    position: absolute;
    right   : 0;
    bottom  : 0;
    height  : 1px;
    width   : 85%;
    border-bottom:1px solid #000;
  }
  .checkout_row:not(:nth-last-child(1)) .description_wrapper:before {
    content : "";
    position: absolute;
    right   : 0;
    bottom  : 0;
    height  : 1px;
    width   : 100%;
    border-bottom:1px solid #000;
  }
  .checkout_row:not(:nth-last-child(1)):not(:nth-child(1)) .description_wrapper:after {
    content : "";
    position: absolute;
    right   : 0;
    bottom  : 0;
    height  : 100%;
    width   : 1px;
    border-right:1px solid #000;
  }
  .checkout_row:nth-child(1) .description_wrapper:after {
    content : "";
    position: absolute;
    right   : 0;
    bottom  : 0;
    height  : 85%;
    width   : 1px;
    border-right:1px solid #000;
  }
  .checkout_row:nth-last-child(1) .description_wrapper:after {
    content : "";
    position: absolute;
    right   : 0;
    top     : 0;
    height  : 85%;
    width   : 1px;
    border-right:1px solid #000;
  }
  .checkout_row:not(:nth-last-child(1)) .price_wrapper:before {
    content : "";
    position: absolute;
    right   : 0;
    bottom  : 0;
    height  : 1px;
    width   : 100%;
    border-bottom:1px solid #000;
  }
  .checkout_row:not(:nth-last-child(1)):not(:nth-child(1)) .price_wrapper:after {
    content : "";
    position: absolute;
    right   : 0;
    bottom  : 0;
    height  : 100%;
    width   : 1px;
    border-right:1px solid #000;
  }
  .checkout_row:nth-child(1) .price_wrapper:after {
    content : "";
    position: absolute;
    right   : 0;
    bottom  : 0;
    height  : 85%;
    width   : 1px;
    border-right:1px solid #000;
  }
  .checkout_row:nth-last-child(1) .price_wrapper:after {
    content : "";
    position: absolute;
    right   : 0;
    top     : 0;
    height  : 85%;
    width   : 1px;
    border-right:1px solid #000;
  }
  .checkout_row:not(:nth-child(1)) .close_wrapper:before {
    content : "";
    position: absolute;
    left    : 0;
    top     : 0;
    height  : 1px;
    width   : 85%;
    border-bottom:1px solid #000;
  }
  /*
  if there is only one row
  */
  .checkout_row.alone .index_wrapper:after, .checkout_row.alone .description_wrapper:before, .checkout_row.alone .price_wrapper:before {
    display: none;
  }
  .checkout_row.alone .index_wrapper:before, .checkout_row.alone .description_wrapper:after, .checkout_row.alone .price_wrapper:after {
    top: 7.5%;
  }
  .checkout_row.alone .close {
    top: 40px;
  }
  .checkout_row .index_wrapper span {
    font-size: 60px;
    height: 140px;
    position: relative;
    top: 40px;
  }
  .image {
    width: 180px;
    height: 100px;
    float: left;
  }
  .image_wrapper {
    margin-left: 2%;
    margin-right: 10%;
    width: 16.7%;
    padding-top: 2%;
  }
  .image_wrapper, .description {
    float: left;
    line-height: 40px;
    padding-top: 2%;
  }
  .description {
    width: 71%;
  }
  .description span {
    float: left;
    width: 100%;
  }
  .description .name {
    font-size: 50px;
  }
  .description .size, .description .starting_day, .description .logo_text {
    font-size: 45px;
    color: #bbb;
  }
  .checkout_row .description_wrapper {
    width: 65%;
    float: left;
    height: 140px;
    position: relative;
  }
  .checkout_row .price_wrapper {
    width: 20%;
    float: left;
    height: 140px;
    position: relative;
  }
  .checkout_row .close_wrapper {
    width: 9%;
    float: left;
    height: 140px;
    position: relative;
  }
  .checkout_row {
    width: 100%;
    height: 140px;
  }
  .price, .close {
    text-align: center;
    position: relative;
  }
  .price {
    top: 40px;
  }
  .price span, .close span {
    font-size: 50px;
  }
  .close span {
    color: #bbb;
    cursor: pointer;
    font-family: cursive !important;
  }
  .close {
    float: none;
    top: 40px;
  }
  #checkout_wrapper span {
    font-family: Daun;
  }
  .description_wrapper .logo_text {
    text-overflow: ellipsis;
    overflow: hidden;
    width: 360px;
    height: 1.2em;
    white-space: nowrap;
  }
}
@media only screen and (max-width: 1366px){
  .checkout_row.alone .image_wrapper {
    padding-top: 3%;
  }
  .checkout_row.alone .description {
    padding-top: 3%;
  }
  .checkout_row .image_wrapper {
    padding-top: 0%;
  }
  .checkout_row .description {
    padding-top: 3%;
  }
  .checkout_row .close_wrapper {
    width: 10%;
  }
}
@media only screen and (max-width: 1112px){
  .image_wrapper {
    margin-right: 14%;
  }
  .description {
    width: 67%;
  }
  .description .name {
    font-size: 40px;
  }
  .description .size, .description .starting_day, .description .logo_text {
    font-size: 35px;
  }
  .price, .checkout_row.alone .close {
    top: 50px;
  }
  .price span, .close span {
    font-size: 40px;
  }
  .checkout_row .image_wrapper {
    padding-top: 1%;
  }
}
@media only screen and (max-width: 1024px){
}
@media only screen and (max-width: 834px){
  .checkout_wrapper_inner {
    display: none;
  }
}
@media only screen and (max-width: 812px){
}
@media only screen and (max-width: 768px){
}
@media only screen and (max-width: 767px){
}
@media only screen and (max-width: 737px){
}
@media only screen and (max-width: 667px){
}
@media only screen and (max-width: 568px){
}
@media only screen and (max-width: 414px){
}
@media only screen and (max-width: 375px){
}
@media only screen and (max-width: 360px){
}
@media only screen and (max-width: 320px){
}
</style>
