<template>
  <div class="custom_breadcrumb">
    <div class="back_button">
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
    }
  }
}
</script>

<style>
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
    z-index: 999;
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
</style>
