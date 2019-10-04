<template>
  <div>
    <h1>This is secret page.</h1>
    <NuxtLink to="/">
      Back home
    </NuxtLink>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  middleware: 'authenticated',
  created () {
    axios.get('http://localhost:8083/admin/products/all'
    ).then((response) => {
      console.log(response.data)
    })
      .catch(function (error) {
        console.log(error)
      })
  }
}
</script>
