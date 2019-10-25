<template>
  <default-layout>
    <div class="input-search">
      <inputSearch></inputSearch>
    </div>
    <div class="table">
      <el-table :data="itemList">
        <el-table-column align="center" label="Item ID" width="100">
          <template slot-scope="scope">
            <span style="margin-left: 10px">
              <el-link type="primary" :href="'item?id='+scope.row.itemId">{{ scope.row.itemId }}</el-link>
            </span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="Product ID" width="100">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.product.productId }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="Description" width="100">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.product.name }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="In Stock?" width="100">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.product.name }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="Quantity" width="100">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.product.name }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="List Price" width="100">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.listPrice }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="Total Cost" width="100">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.listPrice }}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="button">
      <el-button center type="primary" size="mini" >Proceed to Checkout</el-button>
    </div>
  </default-layout>
</template>

<script>
import defaultLayout from '../layouts/default'
import inputSearch from '../components/input-search'
export default {
  methods: {
    getData(){
      this.axios.get('user/cart')
        .then(res => {
          window.console.log(res.data)
        })
        .catch(err => {
          window.console.error(err);
        })
    }
  },
  components: {
    defaultLayout,
    inputSearch
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

.el-table {
  margin: 10px auto 10px auto;
  width: 700px;
}

.button{
  text-align: center;
}

.el-button{
  margin: 15px auto 15px auto;
}
</style>