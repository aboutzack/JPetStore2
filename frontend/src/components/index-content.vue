<template>
  <div class="container">
    <div class="input-search"><input-search></input-search></div>
    <div class="block">
      <el-carousel height="350px">
        <el-carousel-item v-for="item in 4" :key="item">
          <h3 class="small"><img src="https://www.thesprucepets.com/thmb/5qqmdB9NoLp5CVOpUOQiwXRagAQ=/1080x1222/filters:no_upscale()/31170124_355309831644176_4994517524629422080_n-5b3a4d0ec9e77c003771e6a6.jpg"></h3>
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template>

<script>
import inputSearch from './input-search'
export default {
  components: {
    inputSearch
  }
}
</script>

<style scoped>
.container{
  height: 400px;
}
.el-carousel__item h3 {
  text-align: center;
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}
.el-carousel__item:nth-child(2n) {
   background-color: #99a9bf;
}
  
.el-carousel__item:nth-child(2n+1) {
   background-color: #d3dce6;
}

.input-search{
  margin: 10px auto 10px auto;
}

img {
  width: 100%;
  height: 70%;
  /* width: 100%;
	height: auto;
	top:50%;
	-webkit-transform: translateY(-50%);
	-ms-transform: translateY(-50%);
	-moz-transform: translateY(-50%);
	position: relative; */
}
</style>