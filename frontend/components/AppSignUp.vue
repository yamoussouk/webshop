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
    font-family: Audrey;
    font-size: 43px;
    color: rgba(163, 153, 178, 0.77);
    margin-bottom: 5%;
}
form input {
    width: 60%;
    float: left;
    height: 70px;
    font-family: Audrey;
    font-size: 43px;
    color: #bcbec0;
    font-style: italic;
    border-radius: 10px;
    border-color: rgb(235, 235, 235);
}
.emaillist form button {
    background-color: #544c62;
    color: #fff;
    width: 264px;
    font-size: 43px;
    font-family: Audrey;
    height: 70px;
    opacity: 0.77;
    line-height: 60px;
    border-radius: 10px;
}
.emaillist_error, .emaillist_answer {
    text-align: center;
    font-size: 20px;
}
.emaillist_error ul, .emaillist_answer ul {
    list-style: none;
    padding: 0;
}
</style>
