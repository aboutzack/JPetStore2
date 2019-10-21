<template>
  <div class="container">
    <my-header></my-header>
    <slot></slot>
    <el-backtop :visibility-height="10"></el-backtop>
    <my-footer></my-footer>
  </div>
</template>

<script>
import myFooter from '../components/my-footer'
import myHeader from '../components/my-header'
export default {
  components: {
    myHeader,
    myFooter
  }
} 
</script>

<style scoped>
</style>