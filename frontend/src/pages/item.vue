<template>
<div class="container">
  <default-layout>
    <div class="input-search">
      <input-search></input-search>
    </div>
    <div class="item-table">
      <el-row type="flex" justify="center">
        <el-col :span="24">
          <div class="grid-content bg-purple-dark">{{ description }}</div>
        </el-col>
      </el-row>
      <el-row type="flex" justify="center">
        <el-col :span="24">
          <div class="grid-content bg-purple-dark"><b>{{ itemId }}</b></div>
        </el-col>
      </el-row>
      <el-row type="flex" justify="center">
        <el-col :span="24">
          <div class="grid-content bg-purple-dark"><b>{{ allAttribute }}</b></div>
        </el-col>
      </el-row>
      <el-row type="flex" justify="center">
        <el-col :span="24">
          <div class="grid-content bg-purple-dark">{{ name }}</div>
        </el-col>
      </el-row>
      <el-row type="flex" justify="center">
        <el-col :span="24">
          <div class="grid-content bg-purple-dark">{{ quantity }} in stock</div>
        </el-col>
      </el-row>
      <el-row type="flex" justify="center">
        <el-col :span="24">
          <div class="grid-content bg-purple-dark">{{listPrice }}</div>
        </el-col>
      </el-row>
      <el-row type="flex" justify="center">
        <el-col :span="24">
          <div class="grid-content bg-purple-dark">
            <el-button type="primary">Add to Cart</el-button>
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
export default {
  components: {
    defaultLayout,
    inputSearch
  },
  data() {
    return {
      itemId: 'EST-26',
      description: `Great companion for up to 75 years`,
      attribute1: 'Adult',
      attribute2: 'Male',
      attribute3: '',
      attribute4: '',
      attribute5: '',
      name: 'Chihuahua',
      quantity: '10000',
      listPrice: '$125.50',
    }
  },
  computed: {
    allAttribute: function () {
      return (this.attribute1 + ' ' + this.attribute2 + ' ' + this.attribute3 + ' ' + this.attribute4 + ' ' + this.attribute5+
      ' '+this.name)
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
</style>
