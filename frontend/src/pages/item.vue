<template>
<div class="container">
  <default-layout>
    <div class="input-search">
      <input-search></input-search>
    </div>
    <div class="item-table">
      <el-row type="flex" justify="center">
        <el-col :span="24">
          <div v-html="product.description" class="grid-content bg-purple-dark"></div>
        </el-col>
      </el-row>
      <el-row type="flex" justify="center">
        <el-col :span="24">
          <div class="grid-content bg-purple-dark"><b>{{ item.itemId }}</b></div>
        </el-col>
      </el-row>
      <el-row type="flex" justify="center">
        <el-col :span="24">
          <div class="grid-content bg-purple-dark"><b>{{item.attribute1}} {{item.attribute2}}
            {{item.attribute3}} {{item.attribute4}} {{item.attribute5}} {{product.name}}</b></div>
        </el-col>
      </el-row>
      <el-row type="flex" justify="center">
        <el-col :span="24">
          <div class="grid-content bg-purple-dark">{{ item.name }}</div>
        </el-col>
      </el-row>
      <el-row type="flex" justify="center">
        <el-col :span="24">
          <div class="grid-content bg-purple-dark">{{ item.quantity }} in stock</div>
        </el-col>
      </el-row>
      <el-row type="flex" justify="center">
        <el-col :span="24">
          <div class="grid-content bg-purple-dark">${{ item.listPrice }}</div>
        </el-col>
      </el-row>
      <el-row type="flex" justify="center">
        <el-col :span="24">
          <div class="grid-content bg-purple-dark">
            <el-button type="primary" @click="addToCart()">Add to Cart</el-button>
          </div>
        </el-col>
      </el-row>
    </div>
  </default-layout>
</div>
</template>

<script>
import defaultLayout from '../layouts/default'
import inputSearch from '../components/input-search'
import {getRelativePathAndParams} from '../utils/utils'
export default {
  components: {
    defaultLayout,
    inputSearch
  },
  data() {
    return {
      item: {},
      product:{}
    }
  },
    methods: {
    getData() {
      this.axios.get('item', {
          params: {
            id: this.$route.query.id
          }
        })
        .then(res => {
          this.item = res.data.data.item
          this.product = res.data.data.product
        })
        .catch(err => {
          window.console.error(err);
        })
    },
    addToCart(){
      this.axios.post('/user/cart',{itemId: this.item.itemId})
      .then(res => {
        if(res.data.status){
          this.$message.success('成功添加到购物车')
        }
        window.console.log(res)
        this.$cookies.set("token", res.data.data.token, 60*60*24*7)
      })
      .catch(err => {
        if(err.response.status==400){
          this.$message ('请先登入')
            //跳转到登录页
            this.$router.push('/signin?redirect='+getRelativePathAndParams())
        }
        window.console.error(err); 
      })
    }
  },
  created() {
    this.getData();
  },
  watch: {
    '$route'() {
      this.getData();
    }
  }
}
</script>

<style scoped>
.input-search {
  margin: 10px auto 10px auto;
}
.item-table{
  margin-top: 100px;
  margin-bottom: 150px;
}

.el-table{
  min-height: 350px;
}

.el-row {
  width: 800px;
  margin-bottom: 2px;
  background-color: #f9fafc;
  text-align: center;
  margin: auto auto 2px auto;
}

.el-col div {
  color: #606266
}

.image-text{
  text-align: center;
}
</style>
