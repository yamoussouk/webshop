<template>
  <div class="sign_up_form">
    <h1>Sign up for my NewsLetter</h1>
    <div class="emaillist">
      <form @submit="signUp" action="#" method="post">
        <input v-model="email" placeholder="e-mail">
        <button type="submit">
          Sign Up
        </button>
      </form>
      <p v-if="errors.length" class="emaillist_error">
        <b>Please correct the following error(s):</b>
        <ul>
          <li v-for="error in errors" :key="error">
            {{ error }}
          </li>
        </ul>
      </p>
      <p v-if="answers.length" class="emaillist_answer">
        <ul>
          <li v-for="answer in answers" :key="answer">
            {{ answer }}
          </li>
        </ul>
      </p>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      email: null,
      errors: [],
      answers: []
    }
  },
  methods: {
    signUp (event) {
      if (!this.email) {
        this.errors.push('Email required.')
      } else if (!this.validEmail(this.email)) {
        this.errors.push('Valid email required.')
      } else {
        this.errors = []
      }
      if (!this.errors.length) {
        this.answers = []
        const formData = new FormData()
        formData.append('email', this.email)
        axios.post('http://localhost:8083/subscribe', formData, {}
        ).then((response) => {
          this.answers.push(response.data)
        })
          .catch(function (error) {
            console.log(error)
          })
      }
      event.preventDefault()
    },
    validEmail (email) {
      const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      return re.test(email)
    }
  }
}
</script>
<style>
@media only screen and (max-width: 2560px){
  .sign_up_form {
      max-width: 1140px;
      text-align: center;
      margin: 0 auto;
      padding-top: 3%;
  }
  .sign_up_form h1 {
      font-family: Daun;
      font-size: 60px;
      color: #000;
      margin-bottom: 5%;
      letter-spacing: 6px;
  }
  form input {
      width: 65%;
      float: left;
      height: 80px;
      font-family: Daun;
      font-size: 60px;
      color: #bcbec0;
      font-style: italic;
      border-radius: 10px;
      border-color: #000;
      letter-spacing: 3px;
      padding: 25px 0 0 20px;
  }
  form input::placeholder {
    color: #F0F0F0;
  }
  .emaillist form button {
      background-color: rgba(31,89,97, 0.77);
      color: #fff;
      width: 264px;
      font-size: 60px;
      font-family: Daun;
      height: 80px;
      opacity: 0.77;
      line-height: 95px;
      border-radius: 10px;
      letter-spacing: 3px;
      width: 30%;
      border: none;
  }
  .emaillist_error, .emaillist_answer {
      text-align: left;
      font-size: 20px;
      margin-top: 1%;
  }
  .emaillist_error b, .emaillist_answer b {
      float: left;
  }
  .emaillist_error ul, .emaillist_answer ul {
      list-style: none;
      padding: 0;
      margin-left: 2%;
      float: left;
  }
}
@media only screen and (max-width: 1920px){
  .sign_up_form {
      max-width: 1140px;
      text-align: center;
      margin: 0 auto;
      padding-top: 3%;
  }
  .sign_up_form h1 {
      font-family: Daun;
      font-size: 60px;
      color: #000;
      margin-bottom: 5%;
      letter-spacing: 6px;
  }
  form input {
      width: 65%;
      float: left;
      height: 80px;
      font-family: Daun;
      font-size: 60px;
      color: #bcbec0;
      font-style: italic;
      border-radius: 10px;
      border-color: #000;
      letter-spacing: 3px;
      padding: 25px 0 0 20px;
  }
  form input::placeholder {
    color: #F0F0F0;
  }
  .emaillist form button {
      background-color: rgba(31,89,97, 0.77);
      color: #fff;
      width: 264px;
      font-size: 60px;
      font-family: Daun;
      height: 80px;
      opacity: 0.77;
      line-height: 95px;
      border-radius: 10px;
      letter-spacing: 3px;
      width: 30%;
      border: none;
  }
  .emaillist_error, .emaillist_answer {
      text-align: left;
      font-size: 20px;
      margin-top: 1%;
  }
  .emaillist_error b, .emaillist_answer b {
      float: left;
  }
  .emaillist_error ul, .emaillist_answer ul {
      list-style: none;
      padding: 0;
      margin-left: 2%;
      float: left;
  }
}
@media only screen and (max-width: 1440px){
  .sign_up_form {
    max-width: 1000px;
    padding-top: 5%;
  }
  .sign_up_form h1 {
    margin-bottom: 2%;
  }
}
@media only screen and (max-width: 1366px){
}
@media only screen and (max-width: 1112px){
  .emaillist form button {
    font-size: 50px;
    height: 60px;
    line-height: 70px;
    width: 32%;
  }
  form input {
    width: 55%;
    height: 60px;
    font-size: 50px;
  }
  .sign_up_form {
    max-width: 850px;
  }
}
@media only screen and (max-width: 1024px){
  .sign_up_form {
    max-width: 700px;
  }
  .emaillist form button {
    font-size: 40px;
    height: 50px;
    line-height: 60px;
    width: 31%;
  }
  form input {
    width: 60%;
    height: 50px;
    font-size: 40px;
    padding: 15px 0 0 20px;
  }
}
@media only screen and (max-width: 834px){
  .sign_up_form {
    padding-top: 3%;
    max-width: 600px;
  }
  .emaillist form button {
    font-size: 30px;
    height: 40px;
    line-height: 50px;
    width: 25.5%;
  }
  form input {
    height: 40px;
    font-size: 30px;
  }
}
@media only screen and (max-width: 812px){
  .sign_up_form h1 {
    width: 100%;
    text-align: center;
    float: left;
  }
  .sign_up_form .emaillist {
    float: left;
    width: 100%;
  }
}
@media only screen and (max-width: 768px){
}
@media only screen and (max-width: 767px){
  .emaillist form button {
    margin-top: 2%;
  }
  .sign_up_form {
    max-width: 90%;
  }
  form input, .emaillist form button {
    float: initial;
    width: 100%;
  }
  .icon:nth-child(1) img {
    width: 2%;
  }
  .icon:nth-child(2) img {
    width: 2.3%;
  }
  .icon img {
    width: 2.5%;
  }
  form input {
    margin-bottom: -10px;
    text-align: center;
  }
}
@media only screen and (max-width: 736px){
}
@media only screen and (max-width: 667px){
}
@media only screen and (max-width: 568px){
}
@media only screen and (max-width: 414px){
  form input {
    margin-bottom: 0px;
  }
}
@media only screen and (max-width: 375px){
  .emaillist form button {
    font-size: 25px;
  }
  form input {
    font-size: 25px;
    padding: 10px 0 0 10px;
  }
}
@media only screen and (max-width: 320px){
}
</style>
