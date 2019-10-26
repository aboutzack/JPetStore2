<template>
  <default-layout>
    <input-search></input-search>
  </default-layout>
</template>

<script>
import defaultLayout from '../layouts/default'
import inputSearch from '../components/input-search'
export default {
  components:{
    defaultLayout,
    inputSearch
  }
}
</script>

<style scoped>
.input-search {
  margin: 10px auto 10px auto;
}
</style>