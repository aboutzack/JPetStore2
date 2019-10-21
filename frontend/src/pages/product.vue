<template>
  <div>
    <default-layout>
      <div class="input-search"><inputSearch></inputSearch></div>
      <div class="el-table"><el-table
    :data="tableData"
    style="width: 100%">
    <el-table-column
      align="center"
      label="Item ID"
      width="180">
      <template slot-scope="scope">
        <span style="margin-left: 10px"><el-link type="primary" href="/item">{{ scope.row.itemId }}</el-link></span>
      </template>
    </el-table-column>
    <el-table-column
      align="center"
      label="Product ID"
      width="180">
      <template slot-scope="scope">
        <span style="margin-left: 10px">{{ scope.row.productId }}</span>
      </template>
    </el-table-column>
    <el-table-column
      align="center"
      label="Description"
      width="180">
      <template slot-scope="scope">
        <span style="margin-left: 10px">{{ scope.row.description }}</span>
      </template>
    </el-table-column>
    <el-table-column
      align="center"
      label="List Price"
      width="180">
      <template slot-scope="scope">
        <span style="margin-left: 10px">{{ scope.row.listPrice }}</span>
      </template>
    </el-table-column>
    <el-table-column
      align="center"
      label="Operation"
      width="180">
      <template slot-scope="scope">
        <el-button
          type="primary"
          size="mini"
          @click="handleEdit(scope.$index, scope.row)">Add to Cart</el-button>
      </template>
    </el-table-column>
  </el-table></div>
    </default-layout>
  </div>
</template>

<script>
import defaultLayout from '../layouts/default'
import inputSearch from '../components/input-search'
export default {
  components: {
    defaultLayout,
    inputSearch
  },
  data() {
    return {
      tableData: [{
          itemId: 'EST-26',
          productId: 'FL-DLH-02',
          name: 'Persian',
        }, {
          productId: 'FL-DLH-02',
          name: 'Persian',
        }]
    }
  }
}
</script>

<style scoped>
.input-search {
  margin: 10px auto 10px auto;
}
.el-table{
  width: 900px;
  margin: 0 auto;
}
</style>