<template>
    <div :class="{ __disabled: disabled }" class="select" :ref="refs">
      <div @click="toggle" class="select_label">
        <span>{{ getLabel(value) }}</span>
        <img :src="'/page_assets/arrow-02.png'" alt="select arrow" :class="{opened : opened}"/>
      </div>
      <div v-if="opened" class="select_options">
        <div
            v-for="o in options"
            :key="o.id"
            :value="getVal(value)"
            @click="change(o)"
            :class="{__active: getVal(o) == getVal(value)}"
        class="select_option">{{ getLabel(o) }}</div>
      </div>
      <div v-if="opened" @click="toggle" class="select_overlay"></div>
    </div>
</template>

<script>
export default {
  data () {
    return {
      opened: false
    }
  },
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
</style>
