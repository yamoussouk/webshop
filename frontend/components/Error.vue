<template>
  <div :class="{ visible: visible}" class="error-indicator">
    <div class="error-message-box">
        <p>{{ text }}</p>
    </div>
    <div class="error-button-container">
      <div class="left-container">
        <span @click="save()" class="save-button">{{ left }}</span>
      </div>
      <div class="right-container">
        <span @click="cancel()" class="cancel-button">{{ right }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    text: {
      type: String,
      default: ''
    },
    left: {
      type: String,
      default: ''
    },
    right: {
      type: String,
      default: ''
    },
    visible: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    cancel () {
      this.$emit('update:cancel', false)
    },
    save () {
      this.$emit('done', true)
    }
  }
}
</script>

<style scoped>
.error-indicator {
  background: #fff;
  width: 20%;
  position: absolute;
  right: 1%;
  height: 10%;
  border-radius: 10px;
  transition: 1s;
  opacity: 0;
}
.error-indicator.visible {
  opacity: 1;
}
.error-message-box {
  height: 40%;
  padding: 3% 3% 0 3%;
}
.error-message-box p {
  border-bottom: 1px dotted #000;
  text-align: center;
  margin-bottom: 0px;
}
.error-button-container {
  height: 60%;
  padding: 0 2% 0 2%;
}
.error-button-container .left-container {
  width: 49%;
  float: left;
  height: 100%;
  border-right: 1px dotted #000;
  display: flex;
  align-items: center;
  justify-content: center;
}
.error-button-container .right-container {
  width: 50%;
  height: 100%;
  float: left;
  display: flex;
  align-items: center;
  justify-content: center;
}
.error-button-container .save-button {
  cursor: pointer;
  font-size: 16px;
  color: #cf4848;
}
.error-button-container .cancel-button {
  cursor: pointer;
  font-size: 16px;
  color: #cf4848;
}
</style>
