<template>
  <header style>
      <nav class="navbarBox">
        <div class="navbar-container">
          <router-link to="/" class="logo">
            <span class="navbar-sitename">JPetStore</span>
          </router-link>
          <div class="navbar navbar-left">
            <router-link to="/" class="navbar-link" exact>首页</router-link>
            <router-link to="/category?id=BIRDS" class="navbar-link" exact>Birds</router-link>
            <router-link to="/category?id=CATS" class="navbar-link" exact>Cats</router-link>
            <router-link to="/category?id=DOGS" class="navbar-link" exact>Dogs</router-link>
            <router-link to="/category?id=FISH" class="navbar-link" exact>Fish</router-link>
            <router-link to="/category?id=REPTILES" class="navbar-link" exact>Reptiles</router-link>
          </div>
          <div class="navbar-right-open">
            <el-button type="text" @click="showuserinfo=true">
              <i class="el-icon-more"></i>
            </el-button>
            <el-drawer title="OIKii" :visible.sync="showuserinfo" direction="rtl" size="50%">
              <div class="drawerList">
                <div class="drawerListItem">
                  <el-button type="primary" icon="el-icon-edit" size="small" style="width:100%;text-align:center;" round
                    @click="$router.push('/creator/new/article') ;showuserinfo=false">写文章
                  </el-button>
                </div>
                <div class="drawerListItem"
                  @click="$router.push('/people/123456789012345678901234');showuserinfo=false">
                  <i class="el-icon-user-solid"></i> 个人中心
                </div>
                <div class="drawerListItem" @click="$router.push('/setting');showuserinfo=false">
                  <i class="el-icon-s-tools"></i> 设置
                </div>
                <div class="drawerListItem">
                  <i class="el-icon-moon-night"></i> 登出
                </div>
              </div>
            </el-drawer>
          </div>
          <div class="navbar navbar-right">
            <div class="navbar-link userinfo">
              <div v-if="$store.state.signed">
                <div  class="message">
                    <i slot="reference" class="el-icon-shopping-cart-2" style="font-size:1rem;"
                    @click="$router.push('/cart')"></i>
                </div>
                <el-popover placement="bottom" width="150" trigger="click" style="padding:0!important">
                  <div class="userinfo-popout">
                    <div class="username">zack</div>
                    <div class="funclist">
                      <div class="funcitem" @click="$router.push('detailinfo')">
                        <i class="el-icon-user-solid"></i> 我的主页
                      </div>
                      <div class="funcitem" @click="signout()">
                        <i class="el-icon-moon-night"></i> 登出
                      </div>
                    </div>
                  </div>
                  <img :src="userinfo.avatar" slot="reference" :alt="userinfo.name" class="avatar" />
                </el-popover>
              </div>
              <div v-else>
                <router-link :to="'/signup?redirect='+getFullUrl()">注册</router-link> | 
                <router-link :to="'signin?redirect='+getFullUrl()">登入</router-link>
              </div>
            </div>
          </div>
        </div>
      </nav>
    </header>
</template>

<script>
import {getRelativePathAndParams} from '../utils/utils.js'
export default {
  data(){
    return {
      headerFixable: false,
      token: "",
      showuserinfo: false,
      loadingMessage: true,
      userinfo: {
        avatar: "",
        name: "",
        id: ""
    }
  }
},
  methods: {
    getFullUrl(){
      return getRelativePathAndParams()
    },
    signout(){
      this.$cookies.remove('token')
      this.$message.success('登出成功')
      this.$store.commit('updateSigned',false)
    }
  },  
}
</script>

<style lang="scss" scoped>
  $header-height: 4rem;
// $main-color: #5e81ac;
$main-color: rgb(226, 174, 174);
html {
  font-family: "Source Sans Pro", -apple-system, BlinkMacSystemFont, "Segoe UI",
    Roboto, "Helvetica Neue", Arial, sans-serif;
  font-size: 16px;
  word-spacing: 1px;
  -ms-text-size-adjust: 100%;
  -webkit-text-size-adjust: 100%;
  -moz-osx-font-smoothing: grayscale;
  -webkit-font-smoothing: antialiased;
  box-sizing: border-box;
  background: #f7f8fb;
}
body {
  background: #f7f8fb;
}
*,
*:before,
*:after {
  box-sizing: border-box;
  margin: 0;
}

.button--green {
  display: inline-block;
  border-radius: 4px;
  border: 1px solid #3b8070;
  color: #3b8070;
  text-decoration: none;
  padding: 10px 30px;
}

.button--green:hover {
  color: #fff;
  background-color: #3b8070;
}

.button--grey {
  display: inline-block;
  border-radius: 4px;
  border: 1px solid #35495e;
  color: #35495e;
  text-decoration: none;
  padding: 10px 30px;
  margin-left: 15px;
}

.button--grey:hover {
  color: #fff;
  background-color: #35495e;
}
.Header {
  height: $header-height;
  position: fixed;
  .nav-container {
    .nav-left {
      .logo {
      }
    }
    .nav-right {
    }
  }
}
.HeaderFixable {
}

.container {
  // margin-top: $header-height;
  // background: #35495e;
  background: #f7f8fb;
}
@media screen and(max-width: 600px) {
  header {
    .navbarBox {
      .navbar-container {
        .navbar-right {
          display: none;
        }
        .navbar-right-open {
          display: inline !important;
          float: right;
          button {
            color: #434c5e !important;
            font-size: 1rem;
            line-height: 0.5rem;
            font-weight: 700;
          }
        }
      }
    }
  }
}
header {
  .navbarBox {
    box-shadow: 0 1px 1px 0 rgba(0, 0, 0, 0.07);
    position: relative;
    height: 3.6rem;
    z-index: 20;
    top: 0;
    left: 0;
    right: 0;
    box-sizing: border-box;
    line-height: 2.2rem;
    padding: 0.7rem 1.5rem;
    border-bottom: 1px solid #eaecef;
    color: #2c3e50;
    background-color: #fff;
    transition: all 0.5s ease-out;
    overflow: hidden;
    .navbar-container {
      max-width: 1000px;
      margin: 0 auto;
      padding: 0 1rem;
      a {
        font-weight: 545;
        color: $main-color;
        text-decoration: none;
        .navbar-sitename {
          font-size: 1.6rem;
        }
      }
      .logo {
        float: left;
        padding-right: 0.5rem;
      }
      .navbar {
        margin: 0;
        padding: 0;
        line-height: 2.2rem;
        list-style: none;
        .navbar-link {
          display: inline-block;
          height: 100%;
          padding: 0 0.25rem;
          margin-left: 1rem;
          margin-bottom: -10px;
          border-bottom: 2px solid transparent;
          -webkit-transition: all 0.3s;
          transition: all 0.3s;
          color: #2c3e50;
          &:hover {
            border-bottom: 2px solid $main-color;
          }
          &.nuxt-link-active {
            border-bottom: 2px solid $main-color;
          }
        }
        .userinfo {
          &:hover {
            border-bottom: none;
          }
          &.nuxt-link-active {
            border-bottom: none;
          }
          a {
            color: #2c3e50;
          }
          .avatar {
            display: inline-block;
            // position: fixed;
            margin-left: 1.5rem;
            margin-bottom: -10px;
            width: 2.2rem;
            height: 2.2rem;
            max-width: 100%;
            border-radius: 0.3rem;
            background: #2c3e50;
            cursor:pointer;
            &:hover {
              border-bottom: none;
            }
          }
          .message {
            font-weight: 545;
            display: inline-block;
            margin-bottom: -10px;
            line-height: 2.2rem;
            text-align: center;
            cursor: pointer;
            .messagelist {
            }
          }
          .fubao {
            display: inline-block;
            text-align: center;
            font-weight: 545;
            margin-right: 1rem;
            &:hover {
              border-bottom: none;
            }
          }
        }
      }
      .navbar-right-open {
        display: none;
      }
      .navbar-left {
        float: left;
      }
      .navbar-right {
        float: right;
      }
    }
  }
}
.messagelist {
  min-height: 100px;
}
.userinfo-popout {
  padding: 0px;
  .username {
    font-weight: 545;
    font-size: 1.2rem;
    text-align: center;
    line-height: 2.2rem;
  }
  .funclist {
    margin-top: 5px;
    .funcitem {
      font-size: 1rem;
      font-weight: 545;
      padding: 0.5rem 0.5rem;
      transition: all 0.2s all;
      text-decoration: none;
      cursor:pointer;
      &:hover {
        background-color: #b4b0c921;
        transition: all 0.2s ease-out;
      }
    }
  }
}
.el-popover {
  padding: 0 !important;
}
.drawerList {
  width: 100%;
  .drawerListItem {
    height: 3rem;
    line-height: 2rem;
    font-size: 1rem;
    color: #4c566a;
    padding: 0.5rem 2rem;
    &:hover {
      background: #35495e15;
    }
  }
}
</style>