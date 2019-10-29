<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app',
  components: {
  },
  created(){
    localStorage.getItem("signed") && this.$store.commit('updateSigned',localStorage.getItem("signed")=='true');
  }
}
</script>

<style>
</style>
