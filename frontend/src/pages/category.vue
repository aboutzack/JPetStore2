<template>
<div class="container">
  <default-layout>
    <div class="input-search">
      <inputSearch></inputSearch>
    </div>
    <div class="el-table">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column align="center" label="Product ID" width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">
              <el-link type="primary" @click="$router.push('product?id='+scope.row.productId)" >{{ scope.row.name }}</el-link>
            </span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="Name" width="180">
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p>姓名: {{ scope.row.name }}</p>
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">{{ scope.row.name }}</el-tag>
              </div>
            </el-popover>
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
export default {
  components: {
    defaultLayout,
    inputSearch
  },
  data() {
    // return {
    //   tableData: [{
    //     productId: 'FL-DLH-02',
    //     name: 'Persian',
    //   }, {
    //     productId: 'FL-DLH-02',
    //     name: 'Persian',
    //   }]
    // }
    return {
      tableData: {}
    }
  },
  methods: {
    getData() {
      this.axios.get('category', {
          params: {
            id: this.$route.query.id
          }
        })
        .then(res => {
          this.tableData = res.data.data
        })
        .catch(err => {
          window.console.error(err);
        })
    }
  },
  created() {
    this.getData()
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
  width: 400px;
  margin: 0 auto;
  /* height: 500px; */
  min-height: 350px;
}
</style>
