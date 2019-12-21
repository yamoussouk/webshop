<template>
  <div class="search_with_icon">
    <span>
      <img :src="'/page_assets/magnifier.png'" alt="magnifier">
    </span>
      <div class="search_field">
        <input id="product_search_field" placeholder="search items" :value="search" @keyup="onChange($event)">
      </div>
  </div>
</template>

<script>
export default {
  props: {
    search: {
      type: String,
      default: ''
    }
  },
  methods: {
    onChange (event) {
      this.$emit('update:search', event.target.value)
    }
  }
}
</script>

<style scoped>
@media only screen and (max-width: 1024px){
  #product_search_field {
    width: 100%;
    border-radius: 6px;
    border: none;
    height: 60px;
    font-family: Daun;
    font-size: 35px;
    padding: 13px 80px 0;
    color: #F0F0F0;
  }
  #product_search_field::placeholder {
    color: #F0F0F0;
  }
  .search_with_icon {
    position: relative;
    margin-bottom: 2%;
    width: 65%;
    float: left;
  }
  .search_with_icon span {
    position: absolute;
    width: 20px;
    left: 20px;
    top: 8px;
  }
}
@media only screen and (max-width: 834px){
    .search_with_icon {
    width: 60%;
  }
}
@media only screen and (max-width: 812px){
  #product_search_field {
    height: 50px;
    font-size: 30px;
  }
  .search_with_icon span img {
    width: 35px;
  }
  .search_with_icon {
    width: 64%;
  }
}
@media only screen and (max-width: 768px) {
  .sidebar_button_wrapper img {
    position: relative;
    top: -1px;
  }
  #product_search_field {
    font-size: 35px;
  }
}
@media only screen and (max-width: 737px) {
  .sidebar_button_wrapper img {
    width: 12%;
  }
}
@media only screen and (max-width: 667px) {
  .search_with_icon span img {
    width: 25px;
  }
  .search_with_icon span {
    top: 13px;
  }
  #product_search_field {
    padding: 13px 60px 0;
  }
}
@media only screen and (max-width: 568px) {
  #product_search_field {
    padding: 8px 60px 0;
    font-size: 25px;
  }
}
@media only screen and (max-width: 414px) {
  .search_with_icon {
    display: table-header-group;
    float: none;
    position: relative;
  }
  .search_field {
    margin-bottom: 3%;
    width: 95%;
    margin-left: 2.5%;
  }
  .search_with_icon span {
    left: 0;
    top: auto;
    margin-top: 8px;
    margin-left: 33px;
  }
  #product_search_field {
    height: 40px;
  }
}
</style>
