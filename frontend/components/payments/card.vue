<template>
  <div>
    <b-container fluid>
      <b-row class="my-1">
        <b-col sm="12">
          <label for="name">Name:</label>
        </b-col>
      </b-row>
      <b-row class="my-1">
        <b-col sm="12">
          <b-form-input id="name" type="text" />
        </b-col>
      </b-row>
      <b-row class="my-1">
        <b-col sm="12">
          <label for="card">Card number:</label>
        </b-col>
      </b-row>
      <b-row class="my-1">
        <b-col sm="12">
          <b-form-input id="card" type="number" />
        </b-col>
      </b-row>
      <b-row class="my-1">
        <b-col sm="12">
          <label>Expiration:</label>
        </b-col>
      </b-row>
      <b-row class="my-1">
        <b-col sm="2">
          <b-form-input
            id="month"
            v-model="form.expMonth"
            type="number"
            oninput="javascript: if (this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength);"
            maxlength="2"
            min="1"
            max="12"
          />
        </b-col>
        <b-col sm="2">
          <b-form-input
            id="year"
            v-model="form.expYear"
            type="number"
            oninput="javascript: if (this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength);"
            maxlength="2"
          />
        </b-col>
      </b-row>
      <b-row class="my-1">
        <b-col sm="2">
          <label for="cvv">CCV:</label>
        </b-col>
      </b-row>
      <b-row class="my-1">
        <b-col sm="3">
          <b-form-input
            id="cvv"
            v-model="form.cvv"
            type="number"
            oninput="javascript: if (this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength);"
            maxlength="3"
          />
        </b-col>
      </b-row>
      <b-row>
        <b-button type="submit" class="pay_button">
          Pay
        </b-button>
      </b-row>
    </b-container>
  </div>
</template>

<script>
export default {
  data () {
    return {
      form: {
        expMonth: '',
        expYear: '',
        cvv: ''
      }
    }
  }
}
</script>
<style>
.container-fluid label {
  font-family: Daun;
  font-size: 40px;
  margin-bottom: -15px;
}
.container-fluid input {
  height: 80px;
  font-family: Daun;
  font-size: 40px;
  padding: 15px 0 0 20px;
}
.pay_button {
    background: #222;
    display: block;
    color: #fff;
    font-size: 30px;
    font-weight: 400;
    line-height: 22px;
    letter-spacing: normal;
    text-transform: capitalize;
    width: 100%;
    height: 60px;
    margin-top: 4%;
}
</style>
