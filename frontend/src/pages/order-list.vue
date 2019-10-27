<template>
<div>
  <default-layout>
    <div class="input-search">
      <inputSearch></inputSearch>
    </div>
    <el-divider>My Orders</el-divider>
    <el-table :data="orderList">
      <el-table-column align="center" label="Order ID" width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">
            <el-link type="primary" @click="$router.push('/user/order?id='+scope.row.orderId)">{{ scope.row.orderId }}</el-link>
          </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="Date" width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.orderDate.substr(0,10) }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="Total Price" width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">${{ scope.row.totalPrice }}</span>
        </template>
      </el-table-column>
    </el-table>
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
      orderList: []
    }
  },
  methods: {
    getData() {
      this.axios.get('/user/orders')
        .then(res => {
          if (res.data.status) {
            this.$cookies.set("token", res.data.data.token, 60 * 60 * 24 * 7)
            this.orderList = res.data.data.orderList
          } else {
            this.$message('请先登入')
            //跳转到登录页
            this.$router.push('/signin?redirect=' + getRelativePathAndParams())
          }
        })
        .catch(err => {
          if (err.response.status == 400) {
            this.$message('请先登入')
            //跳转到登录页
            this.$router.push('/signin?redirect=' + getRelativePathAndParams())
          }
          window.console.error(err);
        })
    }
  },
  created() {
    this.getData();
  },
}
</script>

<style scoped>
.input-search {
  margin: 10px auto 10px auto;
}

.el-divider {
  margin: 20px auto 20px auto;
  width: 400px;
}

.el-table {
  width: 540px;
  margin: 0 auto;
  min-height: 350px;
}
</style>
