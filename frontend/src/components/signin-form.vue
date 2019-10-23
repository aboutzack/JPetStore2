<template>
<div class="container">
  <div class="form-container">
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="账号" prop="id">
        <el-input type="text" v-model="ruleForm.id" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="pass">
        <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">登入</el-button>
      </el-form-item>
    </el-form>
  </div>
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
      ruleForm: {
        id: '',
        pass: '',
      },
      rules: {
        id: [{
          validator: validateId,
          trigger: 'blur'
        }],
        pass: [{
          validator: validatePass,
          trigger: 'blur'
        }]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios.post('session',{
              username: this.ruleForm.id,
              password: this.ruleForm.pass
          })
          .then(res => {
            this.$cookies.set("tokne", res.data.data.token, 60*60*24*7)
            this.$router.push(this.$route.query.redirect)
          })
          .catch$(err => {
            window.console.error(err)
          })
        }
      });
    },
  }
}
</script>

<style lang="scss" scoped>
// .container{
//   //  height: 400px;
// }

.form-container {
  margin: 150px calc(50% - 150px) 150px auto;
  width: 400px;
}

.el-button {
  width: 300px;
}
</style>
