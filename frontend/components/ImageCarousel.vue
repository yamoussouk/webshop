<template>
    <div class="card-carousel">
        <div class="card-img">
            <img :src="currentImage" alt="">
            <div class="actions">
                <span @click="prevImage" class="prev">
                    &#8249;
                </span>
                <span @click="nextImage" class="next">
                    &#8250;
                </span>
            </div>
        </div>
        <div class="thumbnails">
            <div
                v-for="(image, index) in  images"
                :key="image.id"
                :class="['thumbnail-image', (activeImage == index) ? 'active' : '']"
                @click="activateImage(index)"
            >
                <img :src="'images/1/' + image.imageUrl">
            </div>
        </div>
    </div>
</template>

<script>
export default {
  name: 'Carousel',
  data () {
    return {
      activeImage: 0
    }
  },
  computed: {
    currentImage () {
      // eslint-disable-next-line
      this.timeLeft = this.autoSlideInterval
      return 'images/1/' + this.images[this.activeImage].imageUrl
    }
  },
  methods: {
    nextImage () {
      let active = this.activeImage + 1
      if (active >= this.images.length) {
        active = 0
      }
      this.activateImage(active)
    },
    prevImage () {
      let active = this.activeImage - 1
      if (active < 0) {
        active = this.images.length - 1
      }
      this.activateImage(active)
    },
    activateImage (imageIndex) {
      this.activeImage = imageIndex
    }
  },
  created () {
    if (this.startingImage && this.startingImage >= 0 && this.startingImage < this.images.length) {
      this.activeImage = this.startingImage
    }
  },
  props: ['images']
}
</script>

<style scoped>
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
}
.thumbnail-image > img {
    width: 100%;
    height: auto;
    transition: all 250ms;
}
.thumbnail-image:hover > img,
.thumbnail-image.active > img {
    opacity: 0.6;
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
    width: 91.5%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    color: #585858;
    left: 4%;
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
</style>
