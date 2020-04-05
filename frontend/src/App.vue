<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "app",
  components: {},
  created() {
    let token = localStorage.getItem("token");
    if (token) {
      this.$store.commit("setToken", token);
      this.$store.commit("updateSigned", true);
    }
  }
};
</script>

<style>
</style>
