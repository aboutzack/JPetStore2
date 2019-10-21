<template>
  <div class="Footer">
      <div class="footerContainer">
        <el-row>
          <el-col :xs="24" :sm="6">
            <h1 class="logo">JPetStore <small>2019</small></h1>
            <p>🏫 Central South University</p>
            <p></p>
            <p>👨‍💻 GuanYu-Cai LuJun-Zhang</p>
            <p>👩‍💻 JiaQi-Chen</p>
          </el-col>
          <el-col :xs="24" :sm="6"></el-col>
          <el-col :xs="24" :sm="6"></el-col>
          <el-col :xs="24" :sm="6"></el-col>
        </el-row>
      </div>
    </div>
</template>

<script>
export default {

}
</script>
  
<style lang="scss" scoped>
  .Footer {
  height: 16rem;
  background: rgb(238, 235, 235);
  .footerContainer {
    max-width: 1000px;
    margin: 0 auto;
    padding: 2rem 2rem;
    .logo{
      color: #4C566A;
      font-size: 2rem;
      line-height: 5rem;
      font-weight: 400;
    }
    p{
      font-size: .8rem;
      font-weight: 500;
      color: #4C566A;
      line-height: 2rem;
    }
  }
}
*,*:before,*:after {
  box-sizing: border-box;
  margin: 0;
}
</style>