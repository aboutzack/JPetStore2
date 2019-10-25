<template>
<default-layout>
  <div class="input-search">
    <inputSearch></inputSearch>
    <div class="el-table">
      <el-table :data="productList" style="width: 100%">
        <el-table-column align="center" label="Description" width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px" v-html="scope.row.description">
              <!-- <div v-html="scope.row.description"> -->
            </span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="Product ID" width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">
              <el-link type="primary" @click="$router.push('/product?id='+scope.row.productId)" >{{ scope.row.productId }}</el-link>
            </span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="Category ID" width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">
              <el-link type="primary" @click="$router.push('/category?id='+scope.row.categoryId)" >{{ scope.row.categoryId }}</el-link>
            </span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="Name" width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.name }}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</default-layout>
</template>

<script>
import defaultLayout from '../layouts/default'
import inputSearch from '../components/input-search'
export default {
  data() {
    return {
      productList: []
    }
  },
  components: {
    defaultLayout,
    inputSearch,
  },
  created() {
    this.getData()
  },
  methods:{
    getData(){
      let keyword = this.$route.query.keyword=='null'?null:this.$route.query.keyword
      this.axios.get('/product/searches', {
        params: {
          keyword: keyword
        }
      })
      .then(res => {
        this.productList = res.data.data
        // window.console.log(this.productList)
      })
      .catch(err => {
        window.console.error(err);
      })
    }
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
  width: 720px;
  margin: 0 auto;
}
</style>
