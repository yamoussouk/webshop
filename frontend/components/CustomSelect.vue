<template>
  <div :ref="refs" :class="{ __disabled: disabled }" class="select">
    <div @click="toggle" class="select_label">
      <span>{{ getLabel(value) }}</span>
      <img :src="'/page_assets/arrow-02.png'" :class="{opened : opened}" alt="select arrow">
    </div>
    <div v-if="opened" class="select_options">
      <div
        v-for="o in options"
        :key="o.id"
        :value="getVal(value)"
        :class="{__active: getVal(o) == getVal(value)}"
        @click="change(o)"
        class="select_option"
      >
        {{ getLabel(o) }}
      </div>
    </div>
    <div v-if="opened" @click="toggle" class="select_overlay" />
  </div>
</template>

<script>
export default {
  props: {
    value: {
      required: true
    },
    options: {
      type: Array,
      required: true
    },
    valueKey: {
      type: String,
      required: false
    },
    labelKey: {
      type: String,
      required: false
    },
    onChange: {
      type: Function,
      required: false
    },
    disabled: {
      type: Boolean
    },
    refs: {
      type: String,
      required: false
    }
  },
  data () {
    return {
      opened: false
    }
  },
  methods: {
    getVal (opt) {
      return !this.valueKey ? opt : opt[this.valueKey]
    },
    getLabel (opt) {
      return !this.labelKey ? opt : opt[this.labelKey]
    },
    change (opt) {
      this.$emit('input', opt)
      this.opened = false

      if (this.onChange !== undefined) {
        this.onChange(this.value)
      }
    },
    toggle () {
      if (this.disabled) {
        return
      }
      this.opened = !this.opened
    }
  }
}
</script>

<style scoped>
@media only screen and (max-width: 2560px){
  .select {
      background-color: #fff;
      width: 80%;
      margin: 0 auto;
      font-family: Daun;
      font-size: 50px;
      color: #d7d7c1;
      text-align: center;
      padding-top: 25px;
  }
  .select_label img {
      float: right;
      position: relative;
      right: 5%;
      top: 20px;
      transition: all .3s ease;
      width: 4%;
  }
  .select_label img.opened {
      transform: rotate(180deg);
  }
  .select_label span {
      padding-left: 30px;
  }
}
@media only screen and (max-width: 1920px){
  .select {
      background-color: #fff;
      width: 80%;
      margin: 0 auto;
      font-family: Daun;
      font-size: 50px;
      color: #d7d7c1;
      text-align: center;
      padding-top: 25px;
  }
  .select_label img {
      float: right;
      position: relative;
      right: 5%;
      top: 20px;
      transition: all .3s ease;
      width: 4%;
  }
  .select_label img.opened {
      transform: rotate(180deg);
  }
  .select_label span {
      padding-left: 30px;
  }
}
@media only screen and (max-width: 1366px){
  .select {
    font-size: 45px;
  }
}
@media only screen and (max-width: 1112px){
  .select {
    font-size: 35px;
    padding-top: 20px;
  }
  .select_label img {
    top: 13px;
  }
}
@media only screen and (max-width: 834px){
  .select {
    font-size: 25px;
    padding-top: 15px;
  }
  .select_label img {
    top: 8px;
  }
}
@media only screen and (max-width: 568px){
  .select {
    font-size: 20px;
    padding-top: 12px;
  }
}
@media only screen and (max-width: 414px){
  .select {
    font-size: 15px;
    padding-top: 8px;
  }
  .select_label span {
    padding-left: 10px;
  }
  .select_label img {
    top: 5px;
  }
}
@media only screen and (max-width: 320px){
  .select {
    font-size: 13px;
  }
}
</style>
