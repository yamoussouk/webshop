<template>
  <div class="card-carousel">
    <div class="card-img">
      <img :src="currentImage" alt="">
      <div class="actions">
        <span @click="prevImage" class="prev">
          <img id="prev-arrow" :src="'/page_assets/arrow-02.png'" alt="left-arrow">
        </span>
        <span @click="nextImage" class="next">
          <img id="next-arrow" :src="'/page_assets/arrow-02.png'" alt="right-arrow">
        </span>
      </div>
    </div>
    <div class="thumbnails">
      <div
        v-for="(image, index) in images"
        :key="image.id"
        :class="['thumbnail-image', (activeImage == index) ? 'active' : '']"
        @click="activateImage(index)"
      >
        <img :src="'/uploaded/' + id + '/' + image.imageUrl">
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Carousel',
  props: {
    images: {
      type: Array,
      default: () => {
        return []
      }
    },
    id: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      activeImage: 0
    }
  },
  computed: {
    currentImage () {
      return '/uploaded/' + this.id + '/' + this.images[this.activeImage].imageUrl
    }
  },
  methods: {
    nextImage () {
      let active = this.activeImage + 1
      if (active >= this.images.length) {
        active = 0
      }
      this.activateImage(active)
      // execute set height when the image is rendered
      this.$nextTick(() => {
        this.$emit('setHeight')
      })
    },
    prevImage () {
      let active = this.activeImage - 1
      if (active < 0) {
        active = this.images.length - 1
      }
      this.activateImage(active)
      this.$nextTick(() => {
        this.$emit('setHeight')
      })
    },
    activateImage (imageIndex) {
      this.activeImage = imageIndex
    }
  }
}
</script>

<style scoped>
@media only screen and (max-width: 2560px){
  .card-carousel {
      user-select: none;
      position: relative;
  }
  .thumbnails {
      display: flex;
      justify-content: space-evenly;
      flex-direction: row;
  }
  .thumbnail-image {
      display: flex;
      align-items: center;
      cursor: pointer;
      padding: 2px;
      height: 150px;
      overflow: hidden;
      margin-top: 10px;
  }
  .thumbnail-image > img {
      width: 100%;
      height: auto;
      transition: all 250ms;
  }
  .thumbnail-image:hover > img,
  .thumbnail-image.active > img {
      opacity: 0.5;
      box-shadow: 2px 2px 6px 1px rgba(0,0,0, 0.5);
  }
  .card-img {
      position: relative;
      margin-bottom: 15px;
  }
  .card-img > img {
      width: 100%;
      display: block;
      margin: 0 auto;
  }
  .actions {
      font-size: 1.5em;
      height: 40px;
      position: absolute;
      top: 50%;
      margin-top: -20px;
      width: 112%;
      display: flex;
      align-items: center;
      justify-content: space-between;
      color: #585858;
      left: -6%;
  }
  .actions > span {
      cursor: pointer;
      transition: all 250ms;
      font-size: 45px;
  }
  .actions > span.prev {
      margin-left: 5px;
  }
  .actions > span.next {
      margin-right: 5px;
  }
  .actions > span:hover {
      color: #eee;
  }
  #prev-arrow {
      transform: rotate(90deg);
  }
  #next-arrow {
      transform: rotate(270deg);
  }
}
@media only screen and (max-width: 1920px){
  .card-carousel {
      user-select: none;
      position: relative;
  }
  .thumbnails {
      display: flex;
      justify-content: space-evenly;
      flex-direction: row;
  }
  .thumbnail-image {
      display: flex;
      align-items: center;
      cursor: pointer;
      padding: 2px;
      height: 150px;
      overflow: hidden;
      margin-top: 10px;
  }
  .thumbnail-image > img {
      width: 100%;
      height: auto;
      transition: all 250ms;
  }
  .thumbnail-image:hover > img,
  .thumbnail-image.active > img {
      opacity: 0.5;
      box-shadow: 2px 2px 6px 1px rgba(0,0,0, 0.5);
  }
  .card-img {
      position: relative;
      margin-bottom: 15px;
  }
  .card-img > img {
      display: block;
      margin: 0 auto;
  }
  .actions {
      font-size: 1.5em;
      height: 40px;
      position: absolute;
      top: 50%;
      margin-top: -20px;
      width: 112%;
      display: flex;
      align-items: center;
      justify-content: space-between;
      color: #585858;
      left: -6%;
  }
  .actions > span {
      cursor: pointer;
      transition: all 250ms;
      font-size: 45px;
  }
  .actions > span.prev {
      margin-left: 5px;
  }
  .actions > span.next {
      margin-right: 5px;
  }
  .actions > span:hover {
      color: #eee;
  }
  #prev-arrow {
      transform: rotate(90deg);
  }
  #next-arrow {
      transform: rotate(270deg);
  }
}
@media only screen and (max-width: 1112px){
  .actions > span.next {
    margin-right: -5px;
  }
  .actions > span.prev {
    margin-left: -5px;
  }
}
@media only screen and (max-width: 834px){
  .card-img > img {
      width: 90%;
  }
  .thumbnail-image {
    height: 100px;
    margin-top: 5px;
  }
}
@media only screen and (max-width: 812px){
  .actions > span.prev {
    margin-left: 5px;
  }
  .actions > span.next {
    margin-right: 0;
  }
  .thumbnail-image {
    height: 50px;
  }
  #next-arrow, #prev-arrow {
    width: 80%;
  }
}
@media only screen and (max-width: 767px){
  #next-arrow, #prev-arrow {
    width: 70%;
  }
  .actions > span.prev {
    margin-left: 10px;
  }
}
@media only screen and (max-width: 667px){
  .thumbnail-image {
    height: 45px;
  }
}
@media only screen and (max-width: 568px){
  .card-img > img {
    width: 80%;
  }
  #next-arrow, #prev-arrow {
    width: 70%;
  }
  .actions > span.prev {
    margin-left: 10px;
  }
  .actions > span.next {
    margin-right: 5px;
  }
}
@media only screen and (max-width: 414px){
  .actions > span.next {
    margin-right: 0px;
  }
}
@media only screen and (max-width: 320px){
  .actions > span.next {
    margin-right: -5px;
  }
  .actions > span.prev {
    margin-left: 5px;
  }
}
</style>
