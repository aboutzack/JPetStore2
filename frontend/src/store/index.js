import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    cart: null,
    account: null,
    signed: true,
    //搜索到的所有product
    productList: null
  },
  getters: {
      allCartItems: state=>{
        return state.cart.allCartItems
      },
      subTotal: state=>{
        return state.cart.subTotal
      }
  },
  mutations: {
    updateCart:(state, cart)=>{
      state.cart = cart
    },
    updateSigned:(state, status)=>{
      state.signed = status
    },
    updateAccount: (state, account)=>{
      state.account = account
    }
  }
})