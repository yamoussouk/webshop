<template>
  <div class="contact_wrapper">
    <span id="email_label">YOUR EMAIL</span>
    <input v-model="email" type="email" class="email_input">
    <span id="message_label">MESSAGE</span>
    <textarea v-model="text" class="message_input" />
    <div v-show="error" id="error">
      Something went wrong. Try it again!
    </div>
    <span id="send" @click="sendEmail">send</span>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data () {
    return {
      email: '',
      text: '',
      error: false
    }
  },
  methods: {
    sendEmail () {
      this.error = false
      const formData = new FormData()
      formData.append('from', this.email)
      formData.append('text', this.text)
      axios.post('http://localhost:8083/default/contact', formData, {}
      ).then((response) => {
        if (response.data === 'OK') {
          this.email = ''
          this.text = ''
        } else {
          this.error = true
        }
      })
        .catch(function (error) {
          console.log(error)
        })
    }
  }
}
</script>

<style>
@media only screen and (max-width: 1920px){
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
  #error {
      font-family: Daun;
      font-size: 50px;
      color: #ff0000;
      float: left;
  }
}
@media only screen and (max-width: 1680px){
}
@media only screen and (max-width: 1440px){
}
@media only screen and (max-width: 1366px){
  .contact_wrapper {
    width: 1010px;
    height: 660px;
  }
}
@media only screen and (max-width: 1112px){
  .contact_wrapper {
    width: 900px;
    height: 650px;
  }
}
@media only screen and (max-width: 1024px){
  input.email_input {
    width: 50%;
  }
  #send {
    height: 70px;
    line-height: 90px;
    font-size: 55px;
  }
  .contact_wrapper {
    height: 640px;
  }
  .contact_wrapper span {
    font-size: 45px;
  }
}
@media only screen and (max-width: 834px){
  .contact_wrapper {
    width: 760px;
    height: 630px;
  }
}
@media only screen and (max-width: 812px){
}
@media only screen and (max-width: 768px){
  .contact_wrapper {
    width: 700px;
    height: 620px;
  }
}
@media only screen and (max-width: 767px){
  #email_label {
    margin-bottom: 3%;
    padding-top: 3%;
  }
  .contact_wrapper span {
    font-size: 38px;
  }
  input.email_input {
    margin-bottom: 3%;
  }
  #send {
    height: 50px;
    line-height: 70px;
    font-size: 45px;
  }
  .contact_wrapper {
    height: 610px;
  }
}
@media only screen and (max-width: 737px){
}
@media only screen and (max-width: 667px){
  .contact_wrapper {
    width: 90%;
    height: 580px;
  }
  input.email_input {
    height: 40px;
    font-size: 35px;
    padding: 3% 0 0 1%;
    width: 98%;
  }
}
@media only screen and (max-width: 568px){
  .contact_wrapper span {
    font-size: 1.8rem;
    margin-bottom: 0%;
  }
  #email_label {
    margin-bottom: 0;
  }
  #send {
    height: 40px;
    line-height: 55px;
    font-size: 35px;
  }
  .contact_wrapper {
    height: 545px;
  }
}
@media only screen and (max-width: 414px){
  .contact_wrapper {
    height: 535px;
  }
}
@media only screen and (max-width: 375px){
  .contact_wrapper span {
    font-size: 1.5rem;
  }
  #email_label {
    font-size: 1.6rem;
    height: 45px;
  }
  #message_label {
    margin-bottom: 0%;
    height: 40px;
    font-size: 1.6rem;
  }
  .contact_wrapper {
    height: 500px;
  }
}
@media only screen and (max-width: 360px){
  #message_label {
    height: 35px;
  }
  .contact_wrapper {
    height: 495px;
  }
}
@media only screen and (max-width: 320px){
}
</style>
