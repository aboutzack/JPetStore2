<template>
  <div class="container">
    <el-form
      :model="ruleForm"
      status-icon
      :rules="rules"
      ref="ruleForm"
      label-width="142px"
      class="demo-ruleForm"
    >
      <el-row :gutter="40" type="flex" justify="center">
        <el-col :span="10">
          <el-form-item style="margin-top:50px" label="账号" prop="id">
            <el-input type="text" v-model="ruleForm.id" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="pass">
            <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="captcha">
            <div :class="['input-captcha', captchaing?'loading':'']">
              <div class="clickButton"><span @click="handleCaptchaClick"></span></div>
              <div :class="['info', captchaStatus?'success':'']">{{captchainfo}}</div>
            </div>
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
      if (value === "") {
        callback(new Error("请输入账号"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkId");
        }
        callback();
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    return {
      cToken: "",
      img: "",
      captchainfo: "点击验证",
      captchaStatus: false,
      captchaing: false,
      ruleForm: {
        id: "",
        pass: "",
        captcha: ""
      },
      rules: {
        id: [
          {
            validator: validateId,
            trigger: "blur"
          }
        ],
        pass: [
          {
            validator: validatePass,
            trigger: "blur"
          }
        ],
        captcha: [
          {
            required: true,
            message: "请进行人机验证",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.axios
            .post("/session", {
              username: this.ruleForm.id,
              password: this.ruleForm.pass,
              cToken: this.cToken,
              captcha: this.ruleForm.captcha
            })
            .then(res => {
              if (res.data.status) {
                let token = res.data.data.token;
                this.$store.commit("setToken", token);
                this.$store.commit("updateSigned", true);
                this.$store.commit("updateAccount", res.data.data.account);
                localStorage.setItem("token", token)
                this.axios.onRequest(config => {
                  config.headers.authorization = "Bearer " + token;
                });
                let redirect =
                  this.$route.query.redirect.substr(0, 7) == "/signup"
                    ? "/"
                    : this.$route.query.redirect;
                this.$router.push(redirect);
              } else {
                this.$message.error(res.data.msg);
                this.getCaptcha();
              }
            })
            .catch(err => {
              window.console.error(err);
            });
        }
      });
    },
    handleCaptchaClick(){
      if (this.captchaing ===true){
        return;
      }
      if (this.captchaStatus == true){
        return;
      }
      this.captchaing = true;
      this.captchainfo = "正在验证...";
      setTimeout(() => {
        this.captchainfo = "验证成功"
        this.captchaStatus = true
        this.captchaing = false
        this.ruleForm.captcha = true
      }, 2000);
    }
  },
  created() {
    this.getCaptcha();
  }
};
</script>

<style lang="scss" scoped>
.el-form {
  margin: 50px auto 100px auto;
  width: 1000px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}

.el-button {
  margin-bottom: 30px;
}

.button {
  text-align: center;
}
img {
  width: 100px;
  cursor: pointer;
  margin-left: 5px;
}
.input-captcha {
  // display: flex;
  position: relative;
  height: 2.5rem;
  box-shadow: 0 0 5px -1px rgba(0,0,0,0.3);
  border-radius: .3rem;
  &.loading{
    filter: brightness(130%);
  }
  .clickButton{
    position: absolute;
    left: .5rem;
    top: .5rem;
    display: block;
    width: 1.5rem;
    height: 1.5rem;
    span{
      display: block;
      width: 1.5rem;
      height: 1.5rem;
      border-radius: 1.5rem;
      background: #dab0af;
      cursor: pointer;
      transition: all .3s;
      &:hover{
        transform: scale(1.3);
        filter: brightness(130%);
      }
    }
  }
  .info{
    font-size: .9rem;
    color: #777;
    font-weight: 500;
    line-height: 2.5rem;
    width: 100%;
    padding-left: 3rem;
    &.success{
      color: #A3BE8C;
    }
  }
}
</style>
