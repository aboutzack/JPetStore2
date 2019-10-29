<template>
<div class="container">
  <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="142px"  class="demo-ruleForm">
    <el-row :gutter="40" type="flex" justify="center">
      <el-col :span="10">
        <el-form-item style="margin-top:50px" label="账号" prop="id">
          <el-input type="text" v-model="ruleForm.id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="pass">
          <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item  label="验证码" prop="captcha">
          <div class="input-captcha">
            <el-input @keyup.enter.native="submitForm('ruleForm')" type="text" v-model="ruleForm.captcha" autocomplete="off"></el-input>
            <img :src="img"  @click="getCaptcha"></div>
        </el-form-item>
        <div class="button">
          <el-button type="primary" @click="submitForm('ruleForm')">登入</el-button>
        </div>
      </el-col>
    </el-row>
  </el-form>
</div>
</template>

<script>
export default {
  data() {
    var validateId = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入账号'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkId');
        }
        callback();
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    return {
      cToken: '',
      img: '',
      ruleForm: {
        id: '',
        pass: '',
        captcha: ''
      },
      rules: {
        id: [{
          validator: validateId,
          trigger: 'blur'
        }],
        pass: [{
          validator: validatePass,
          trigger: 'blur'
        }],
        captcha: [{
          required: true,
          message: '请输入验证码',
          trigger: 'blur'
        }]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios.post('/session', {
              username: this.ruleForm.id,
              password: this.ruleForm.pass,
              cToken: this.cToken,
              captcha: this.ruleForm.captcha
            })
            .then(res => {
              if (res.data.status) {
                let token = res.data.data.token
                this.$cookies.set("token", token, 60 * 60 * 24 * 7)
                this.$store.commit('updateSigned', true)
                this.$store.commit('updateAccount',res.data.data.account)
                let redirect =  this.$route.query.redirect.substr(0,7)=='/signup'?'/':this.$route.query.redirect
                this.$router.push(redirect)
              } else {
                this.$message.error(res.data.msg)
                this.getCaptcha()
              }
            })
            .catch(err => {
              window.console.error(err)
            })
        }
      });
    },
    getCaptcha(){
        this.axios.get('/captcha')
        .then(res => {
          this.cToken=res.data.token
          this.img='data:image/jpeg;base64,'+res.data.img
        })
        .catch(err => {
          window.console.error(err)
        })
      },
  },
  created(){
    this.getCaptcha()
  }
}
</script>

<style lang="scss" scoped>
.el-form {
  margin: 50px auto 100px auto;
  width: 1000px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)
}

.el-button {
  margin-bottom: 30px;
}

.button {
  text-align: center;
}
img{
  width: 100px;
  cursor: pointer;
  margin-left: 5px;
}
.input-captcha{
  display: flex;
}
</style>
