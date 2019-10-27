<template>
  <div class="contact_wrapper">
    <span id="email_label">YOUR EMAIL</span>
    <input type="email" class="email_input" v-model="email" />
    <span id="message_label">MESSAGE</span>
    <textarea class="message_input" v-model="text" />
    <span @click="sendEmail" id="send">send</span>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data () {
    return {
      email: '',
      text: ''
    }
  },
  methods: {
    sendEmail () {
      const formData = new FormData()
      formData.append('from', this.email)
      formData.append('text', this.text)
      axios.post('http://localhost:8083/default/contact', formData, {}
      ).then((response) => {
        console.log(response.data)
        // this.answers.push(response.data)
      })
        .catch(function (error) {
          console.log(error)
        })
    }
  }
}
</script>

<style>
.contact_wrapper {
    width: 1328px;
    margin: 3% auto;
    background: #fff;
    height: 700px;
    padding-left: 2%;
}
.contact_wrapper span {
    width: 100%;
    font-family: Daun;
    font-size: 50px;
    display: block;
}
#email_label {
    margin-bottom: 2%;
    padding-top: 2%;
    height: 61px;
}
#message_label {
    margin-bottom: 3%;
    height: 41px;
}
input.email_input {
    width: 40%;
    height: 60px;
    margin-bottom: 2%;
    background-color: rgba(31,89,97, 0.07);
    border-radius: 10px;
    border: none;
    font-size: 40px;
    font-family: Daun;
    padding: 1% 0 0 1%;
}
.contact_wrapper textarea {
    width: 98%;
    height: 300px;
    background-color: rgba(31,89,97, 0.07);
    border-radius: 10px;
    border: none;
    margin-bottom: 2%;
    font-size: 40px;
    font-family: Daun;
    padding: 1% 0 0 1%;
}
#send {
    background-color: #5ea99e;
    color: #fff;
    font-size: 60px;
    font-family: Daun;
    height: 80px;
    line-height: 100px;
    letter-spacing: 3px;
    width: 30%;
    float: right;
    text-align: center;
    position: relative;
    right: 2%;
}
</style>
