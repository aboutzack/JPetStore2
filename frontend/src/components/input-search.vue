<template>
<div>
  <el-row gutter="15" type="flex" class="row-bg" justify="center">
    <el-col :span="16"><el-autocomplete
  v-model="state"
  :fetch-suggestions="querySearchAsync"
  placeholder="搜索任何宠物"
  @select="handleSelect"
></el-autocomplete></el-col>
    <el-col :span="1"><el-button type="primary">搜索</el-button></el-col>
  </el-row>
</div>

</template>

<script>
  export default {
    data() {
      return {
        restaurants: [],
        state: '',
        timeout:  null
      };
    },
    methods: {
      loadAll() {
        return [
          { "value": "三全鲜食（北新泾店）", "address": "长宁区新渔路144号" },
        ];
      },
      querySearchAsync(queryString, cb) {
        var restaurants = this.restaurants;
        var results = queryString ? restaurants.filter(this.createStateFilter(queryString)) : restaurants;

        clearTimeout(this.timeout);
        this.timeout = setTimeout(() => {
          cb(results);
        }, 3000 * Math.random());
      },
      createStateFilter(queryString) {
        return (state) => {
          return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
      },
      handleSelect(item) {
        // console.log(item);
        alert(item);
      }
    },
    mounted() {
      this.restaurants = this.loadAll();
    }
  };
</script>

<style>
.el-autocomplete {
  width: 100%;
}
</style>