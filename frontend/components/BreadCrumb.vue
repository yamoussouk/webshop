<template>
  <div class="custom_breadcrumb">
    <div v-if="back" class="back_button">
      <a @click="$router.go(-1)">
        <div class="wrapper">
          <img :src="'/page_assets/arrow.png'" alt="back arrow">
        </div>
        <div class="button_wrapper">
          <span>BACK</span>
        </div>
      </a>
    </div>
    <div class="bc_text">
      <h1>{{ title }}</h1>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: ''
    },
    back: {
      type: Boolean,
      default: true
    }
  }
}
</script>

<style>
@media only screen and (max-width: 1920px){
  .custom_breadcrumb {
      background-color: #fff;
      height: 120px;
      margin-bottom: 2%;
  }
  .custom_breadcrumb .back_button {
      width: 25%;
      position: absolute;
      background-color: #fff;
      height: 120px;
      z-index: 998;
      color: #000;
  }
  .custom_breadcrumb .button_wrapper {
      float: left;
      display: table;
      height: 120px;
      width: 50%;
  }
  .custom_breadcrumb .back_button a {
      height: 120px;
      width: 100%;
      color: #000;
      display: block;
  }
  .custom_breadcrumb .back_button a:hover {
      cursor: pointer;
  }
  .custom_breadcrumb .back_button a .wrapper {
      float: left;
      width: 19%;
      padding-left: 30%;
      margin: auto;
      display: table;
      height: 120px;
  }
  .custom_breadcrumb .back_button img {
      display: table-cell;
      vertical-align: middle;
      margin: auto;
      padding-top: 40%;
  }
  .custom_breadcrumb .back_button span {
      font-family: Daun;
      font-size: 60px;
      display: table-cell;
      vertical-align: middle;
      position: relative;
      padding-left: 10%;
      padding-top: 25px;
  }
  .bc_text {
      width: 100%;
      background-color: #fff;
      float: left;
      display: table;
      height: 120px;
      text-align: center;
  }
  .bc_text h1 {
      font-family: Daun;
      font-size: 60px;
      text-transform: uppercase;
      letter-spacing: 6px;
      position: relative;
      display: table-cell;
      vertical-align: middle;
      padding-top: 25px;
  }
}
@media only screen and (max-width: 1680px){
  .custom_breadcrumb .button_wrapper {
    width: 45%;
  }
  .custom_breadcrumb .back_button span {
    padding-top: 30px;
  }
}
@media only screen and (max-width: 1600px){
  .custom_breadcrumb .button_wrapper {
    width: 42%;
  }
  .custom_breadcrumb .back_button span {
    padding-top: 25px;
  }
}
@media only screen and (max-width: 1440px){
  .custom_breadcrumb .back_button span {
    padding-top: 25px;
  }
  .bc_text h1 {
    font-size: 55px;
  }
  .custom_breadcrumb .back_button span {
    font-size: 55px;
  }
}
@media only screen and (max-width: 1366px){
  .bc_text h1 {
    font-size: 40px;
  }
  .custom_breadcrumb .back_button span {
    font-size: 40px;
  }
  .custom_breadcrumb .back_button img {
    padding-top: 80%;
    width: 60px;
  }
}
@media only screen and (max-width: 1112px){
  .bc_text h1, .custom_breadcrumb .back_button span {
    font-size: 33px;
  }
  .custom_breadcrumb, .bc_text, .custom_breadcrumb .button_wrapper, .custom_breadcrumb .back_button, .custom_breadcrumb .back_button a .wrapper {
    height: 90px;
  }
  .custom_breadcrumb .back_button img {
    padding-top: 50%;
  }
  .custom_breadcrumb .back_button span {
    padding-top: 20px;
  }
  .bc_text h1 {
    padding-top: 20px;
  }
}
@media only screen and (max-width: 1024px){
  .custom_breadcrumb, .bc_text, .custom_breadcrumb .button_wrapper, .custom_breadcrumb .back_button, .custom_breadcrumb .back_button a .wrapper {
    height: 70px;
  }
  .custom_breadcrumb .back_button img {
    padding-top: 40%;
    width: 55px;
  }
  .bc_text h1 {
    padding-top: 10px;
  }
  .custom_breadcrumb .back_button a {
    height: 0;
  }
}
@media only screen and (max-width: 834px){
  .custom_breadcrumb .back_button {
    width: 22%;
  }
  .custom_breadcrumb .back_button a .wrapper {
    padding-left: 25%;
  }
  .custom_breadcrumb .back_button img {
    padding-top: 35%;
  }
  .custom_breadcrumb .back_button span {
    padding-top: 15px;
  }
  .bc_text h1 {
    padding-top: 15px;
  }
}
@media only screen and (max-width: 812px){
  .bc_text h1, .custom_breadcrumb .back_button span {
    font-size: 25px;
  }
  .custom_breadcrumb, .bc_text, .custom_breadcrumb .button_wrapper, .custom_breadcrumb .back_button, .custom_breadcrumb .back_button a .wrapper {
    height: 40px;
  }
  .custom_breadcrumb .back_button img {
    width: 45px;
    padding-top: 20%;
  }
  .bc_text h1 {
    padding-top: 10px;
  }
  .custom_breadcrumb .back_button span {
    padding-top: 8px;
  }
  .custom_breadcrumb {
    margin-bottom: 0%;
  }
}
@media only screen and (max-width: 768px){
  .custom_breadcrumb, .bc_text, .custom_breadcrumb .button_wrapper, .custom_breadcrumb .back_button, .custom_breadcrumb .back_button a .wrapper {
    height: 60px;
  }
  .bc_text h1 {
    padding-top: 10px;
  }
  .custom_breadcrumb .back_button img {
    padding-top: 35%;
  }
  .button_wrapper {
    display: none !important;
  }
}
@media only screen and (max-width: 767px){
}
@media only screen and (max-width: 737px){
}
@media only screen and (max-width: 667px){
  .custom_breadcrumb .button_wrapper {
    width: 40%;
  }
  .custom_breadcrumb .back_button img {
    width: 40px;
    padding-top: 45%;
  }
}
@media only screen and (max-width: 568px){
  .bc_text h1, .custom_breadcrumb .back_button span {
    font-size: 20px;
  }
  .custom_breadcrumb .back_button img {
    width: 30px;
    padding-top: 70%;
  }
  .custom_breadcrumb .back_button span {
    padding-top: 10px;
  }
  .bc_text h1 {
    padding-top: 10px;
  }
}
@media only screen and (max-width: 414px){
  .custom_breadcrumb .back_button a .wrapper {
    padding-left: 20%;
  }
  .custom_breadcrumb .back_button img {
    padding-top: 70%;
  }
  .custom_breadcrumb .back_button {
    width: 10%;
  }
}
@media only screen and (max-width: 375px){
  .custom_breadcrumb .back_button {
    display: none;
  }
}
@media only screen and (max-width: 360px){
}
@media only screen and (max-width: 320px){
}
</style>
