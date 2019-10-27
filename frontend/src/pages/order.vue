<template>
<div>
  <default-layout>
    <div class="form-table-container">
      <el-form :disabled="true" :model="ruleForm" ref="ruleForm" label-width="150px" class="demo-ruleForm">
        <el-row :gutter="40" type="flex" justify="center">
          <el-col :span="10">
            <div>
              <el-divider>Payment Details</el-divider>
              <el-form-item label="Card Type">
                <el-select v-model="order.cardType">
                  <el-option label="Visa" value="Visa"></el-option>
                  <el-option label="Mastercard" value="Mastercard"></el-option>
                  <el-option label="American Express" value="American Express"></el-option>
                  <el-option label="Discover" value="Discover"></el-option>
                  <el-option label="JCB" value="JCB"></el-option>
                  <el-option label="UnionPay" value="UnionPay"></el-option>
                  <el-option label="Maestro" value="Maestro"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="Card Number">
                <el-input v-model="order.creditCard"></el-input>
              </el-form-item>
              <el-form-item label="Expiry Date">
                <el-form-item>
                  <el-date-picker type="month" v-model="order.expiryDate" style="width: 100%;"></el-date-picker>
                </el-form-item>
              </el-form-item>
              <el-divider>Billing Address</el-divider>
              <el-form-item label="First Name">
                <el-input v-model="order.billToFirstName"></el-input>
              </el-form-item>
              <el-form-item label="Last Name">
                <el-input v-model="order.billToLastName"></el-input>
              </el-form-item>
              <el-form-item label="Address 1">
                <el-input v-model="order.billAddress1"></el-input>
              </el-form-item>
              <el-form-item label="Address 2">
                <el-input v-model="order.billAddress2"></el-input>
              </el-form-item>
              <el-form-item label="City">
                <el-input v-model="order.billCity"></el-input>
              </el-form-item>
              <el-form-item label="State">
                <el-input v-model="order.billState"></el-input>
              </el-form-item>
              <el-form-item label="Zip">
                <el-input v-model="order.billZip"></el-input>
              </el-form-item>
              <el-form-item label="Country">
                <el-input v-model="order.billCountry"></el-input>
              </el-form-item>
            </div>
          </el-col>
            <el-col :span="10">
              <div>
                <el-divider>Shipping Address</el-divider>
                <el-form-item label="First Name">
                  <el-input v-model="order.shipToFirstName"></el-input>
                </el-form-item>
                <el-form-item label="Last Name">
                  <el-input v-model="order.shipToLastName"></el-input>
                </el-form-item>
                <el-form-item label="Address 1">
                  <el-input v-model="order.shipAddress1"></el-input>
                </el-form-item>
                <el-form-item label="Address 2">
                  <el-input v-model="order.shipAddress2"></el-input>
                </el-form-item>
                <el-form-item label="City">
                  <el-input v-model="order.shipCity"></el-input>
                </el-form-item>
                <el-form-item label="State">
                  <el-input v-model="order.shipState"></el-input>
                </el-form-item>
                <el-form-item label="Zip">
                  <el-input v-model="order.shipZip"></el-input>
                </el-form-item>
                <el-form-item label="Country">
                  <el-input v-model="order.shipCountry"></el-input>
                </el-form-item>
              </div>
            </el-col>
        </el-row>
      </el-form>
        <el-divider>Your Pets</el-divider>
        <el-table :data="lineItems">
          <el-table-column align="center" label="Item ID" width="180">
            <template slot-scope="scope">
              <span style="margin-left: 10px">
                <el-link type="primary" @click="$router.push('/item?id='+scope.row.itemId)">{{ scope.row.itemId }}</el-link>
              </span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="Description" width="180">
            <template slot-scope="scope">
              <span v-html="scope.row.item.product.description" style="margin-left: 10px"></span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="Quantity" width="180">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.quantity }}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="Price" width="180">
            <template slot-scope="scope">
              <span style="margin-left: 10px">${{ scope.row.unitPrice }}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="Total Cost" width="180">
            <template slot-scope="scope">
              <span style="margin-left: 10px">${{ scope.row.total }}</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
  </default-layout>
</div>
</template>

<script>
import defaultLayout from '../layouts/default'
import {
  getRelativePathAndParams
} from '../utils/utils'
export default {
  components: {
    defaultLayout,
  },
  data() {
    return {
      order: null,
      lineItems: null,
      ruleForm: {
        cardType: '',
        cardNumber: '',
        expiryTime: '',
        firstName: '',
        lastName: '',
        addr1: '',
        addr2: '',
        city: '',
        state: '',
        zip: '',
        country: '',
        shipToDifferentAddr: false,
        shipFirstName: '',
        shipLastName: '',
        shipAddr1: '',
        shipAddr2: '',
        shipCity: '',
        shipState: '',
        shipZip: '',
        shipCountry: '',
      },
    }
  },
  methods: {
    getData() {
      this.axios.get('/user/order', {
          params: {
            id: this.$route.query.id
          }
        })
        .then(res => {
          if (res.data.status) {
            this.$cookies.set("token", res.data.data.token, 60 * 60 * 24 * 7)
            this.order = res.data.data.order
            this.lineItems = this.order.lineItems
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
    },
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
.form-table-container{
  margin: 50px auto 100px auto;
  width: 1000px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)
}
.el-divider {
  width: 400px;
  margin: 30px auto;
}
.el-table {
  width: 900px;
  margin: 0 auto;
  padding-bottom: 50px;
}
</style>
