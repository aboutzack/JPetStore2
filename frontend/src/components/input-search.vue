<template>
<div>
  <el-row gutter="15" type="flex" class="row-bg" justify="center">
    <el-col :span="16"><el-autocomplete
  v-model="state"
  :fetch-suggestions="querySearchAsync"
  placeholder="搜索任何宠物"
  value-key="name"
  @keyup.enter.native="search"
></el-autocomplete></el-col>
    <el-col :span="1"><el-button type="primary" @click="search">搜索</el-button></el-col>
  </el-row>
</div>

</template>

<script>
  export default {
    data() {
      return {
        state: null,
        timeout:  null,
        productList: [],
        queryString: ''
      };
    },
    methods: {
      getData(){
        this.axios.get('product/searches')
        .then(res => {
          this.productList = res.data.data
        })
        .catch(err => {
          window.console.error(err)
        })
      },
      querySearchAsync(queryString, cb) {
        this.queryString = queryString;
        var productList = this.productList;
        var results = queryString ? productList.filter(this.createStateFilter(queryString)) : productList;
        clearTimeout(this.timeout);
        this.timeout = setTimeout(() => {
          cb(results);
        }, 500 * Math.random());
      },
      createStateFilter(queryString) {
        return (state) => {
          return (state.name.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
      },
      search(){
        this.$router.push('/product/searches?keyword='+this.state)
      }
    },
    created(){
       this.getData();
    },
  };
</script>

<style>
.el-autocomplete {
  width: 100%;
}
</style>