<template>
  <div class="sign_up_form">
    <h1>Sign up for my NewsLetter</h1>
    <div class="emaillist">
      <form action="#" method="post" @submit="signUp">
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
</style>
