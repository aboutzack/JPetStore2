<template>
<div>
  <default-layout>
    <div class="input-search">
      <inputSearch></inputSearch>
    </div>
    <div class="el-table">
      <el-table :data="itemList">
        <el-table-column align="center" label="Item ID" width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">
              <el-link type="primary" @click="$router.push('/item?id='+scope.row.itemId)">{{ scope.row.itemId }}</el-link>
            </span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="Product ID" width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.product.productId }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="Description" width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.product.name }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="List Price" width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.listPrice }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="Operation" width="180">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="handleEdit(scope.$index, scope.row)">Add to Cart</el-button>
          </template>
        </el-table-column>
      </el-table>
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
      tableData: {},
      product: {},
      itemList: []
    }
  },
  methods: {
    getData() {
      this.axios.get('product', {
          params: {
            id: this.$route.query.id
          }
        })
        .then(res => {
          this.tableData = res.data.data
          this.product = this.tableData.product
          this.itemList = this.tableData.itemList
        })
        .catch(err => {
          window.console.error(err);
        })
    },
    handleEdit(index){
      this.axios.post('/user/cart',{itemId: this.itemList[index].itemId})
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

.el-table {
  width: 900px;
  margin: 0 auto;
  min-height: 350px;
}
</style>
