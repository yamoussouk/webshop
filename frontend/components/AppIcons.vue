<template>
  <div class="app-icons">
    <div class="icons-wrapper">
      <a class="icon" href="#">
        <img src="/page_assets/facebook.png" alt="facebook">
      </a>
      <a class="icon" href="#">
        <img src="/page_assets/tumblr.png" alt="tumblr">
      </a>
      <a class="icon" href="#">
        <img src="/page_assets/pinterest.png" alt="pinterest">
      </a>
      <a class="icon" href="#">
        <img src="/page_assets/instagram.png" alt="instagram">
      </a>
      <a class="icon" href="#">
        <img src="/page_assets/etsy.png" alt="etsy">
      </a>
    </div>
  </div>
</template>

<style scoped>
@media only screen and (max-width: 1920px){
  .app-icons {
      text-align: center;
      margin-top: 3%;
  }
  .app-icons .icon {
      margin-right: 2%;
  }
}
@media only screen and (max-width: 1366px){
  .icon img {
    width: 3.8vh;
  }
  .icon:nth-child(1) img {
    width: 3vh;
  }
  .icon:nth-child(2) img {
    width: 3.5vh;
  }
}
@media only screen and (max-width: 812px){
  .app-icons {
      float: left;
      width: 100%;
  }
  .icon img {
    width: 2.5%;
  }
  .icon:nth-child(1) img {
    width: 1.9%;
  }
  .icon:nth-child(2) img {
    width: 2.2%;
  }
}
@media only screen and (max-width: 736px){
  .icon img {
    width: 2.5%;
  }
  .icon:nth-child(1) img {
    width: 2%;
  }
  .icon:nth-child(2) img {
    width: 2.3%;
  }
}
@media only screen and (max-width: 414px){
  .icon img {
    width: 4%;
  }
  .icon:nth-child(1) img {
    width: 3%;
  }
  .icon:nth-child(2) img {
    width: 3.5%;
  }
}
</style>
