<template>
  <default-layout>
    <div class="input-search">
      <inputSearch></inputSearch>
    </div>
    <div class="table">
      <el-table :data="allCartItems">
        <el-table-column align="center" label="Item ID" width="100">
          <template slot-scope="scope">
            <span style="margin-left: 10px">
              <el-link type="primary" :href="'item?id='+scope.row.itemId">{{ scope.row.item.itemId }}</el-link>
            </span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="Product ID" width="100">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.item.productId }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="Description" width="150">
          <template slot-scope="scope">
            <span v-html="scope.row.item.product.description" style="margin-left: 10px"></span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="In Stock?" width="100">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.inStock }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="Quantity" width="150">
          <template slot-scope="scope">
            <!-- <span style="margin-left: 10px">{{ scope.row.item.quantity }}</span> -->
            <!-- <el-input-number size="mini" v-model="scope.row.item.quantity" @change="handleChange(scope.row, scope.row.item.quantity)" :min="0"></el-input-number> -->
            <el-input-number size="mini" v-model="scope.row.item.quantity" @change="handleChange(scope.row)" :min="0"></el-input-number>
          </template>
        </el-table-column>
        <el-table-column align="center" label="List Price" width="100">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.item.listPrice }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="Total Cost" width="100">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.total }}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div v-if="cart" class="button">
      <el-button center type="primary" size="mini" >Proceed to Checkout</el-button>
    </div>
  </default-layout>
</template>

<script>
import defaultLayout from '../layouts/default'
import inputSearch from '../components/input-search'
export default {
  data(){
    return {
      cart: null,
      allCartItems: null,
      subTotal: 0
    }
  },
  methods: {
    getData(){
      this.axios.get('user/cart')
        .then(res => {
          if(res.data.status){
            let token = res.data.data.token
            this.$cookies.set("token", token, 60*60*24*7)
            this.cart = res.data.data.cart
            this.allCartItems = this.cart.allCartItems
            this.subTotal = this.cart.subTotal
          }else{
            this.$message ('请先登入')
            //跳转到登录页
            this.$router.push('/signin?redirect=cart')
          }
        })
        .catch(() => {
          this.$message ('请先登入')
          //跳转到登录页
          this.$router.push('/signin?redirect=cart')
        })
    },
    handleChange(row, data){
      window.console.log(row, data)
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
  width: 800px;
  min-height: 300px;
}

.button{
  text-align: center;
}

.el-button{
  margin: 15px auto 15px auto;
}
</style>