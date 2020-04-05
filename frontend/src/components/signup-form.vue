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
          <template v-if="!isIdAndPassValid">
            <el-form-item style="margin-top:50px" label="账号" prop="id">
              <el-input type="text" v-model="ruleForm.id" autocomplete="off" clearable></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="pass">
              <el-input type="password" v-model="ruleForm.pass" autocomplete="off" show-password></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="checkPass">
              <el-input
                type="password"
                v-model="ruleForm.checkPass"
                autocomplete="off"
                show-password
              ></el-input>
            </el-form-item>
            <el-form-item label="验证码" prop="captcha">
              <div :class="['input-captcha', captchaing?'loading':'']">
              <div class="clickButton"><span @click="handleCaptchaClick"></span></div>
              <div :class="['info', captchaStatus?'success':'']">{{captchainfo}}</div>
            </div>
            </el-form-item>
            <div class="button">
              <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
            </div>
          </template>
          <template v-if="isIdAndPassValid">
            <el-divider>User Information</el-divider>
            <el-form-item style="margin-top:50px" label="First name" prop="firstName">
              <el-input type="text" v-model="ruleForm.firstName" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="Last name" prop="lastName">
              <el-input type="text" v-model="ruleForm.lastName" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="Email" prop="email">
              <el-input type="text" v-model="ruleForm.email" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="Phone" prop="phone">
              <el-input type="text" v-model="ruleForm.phone" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="Address1" prop="addr1">
              <el-input type="text" v-model="ruleForm.addr1" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="Address2" prop="addr2">
              <el-input type="text" v-model="ruleForm.addr2" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="Country" prop="country">
              <el-input type="text" v-model="ruleForm.country" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="City" prop="city">
              <el-input type="text" v-model="ruleForm.city" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="State" prop="state">
              <el-input type="text" v-model="ruleForm.state" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="Zip" prop="zip">
              <el-input type="text" v-model="ruleForm.zip" autocomplete="off"></el-input>
            </el-form-item>
            <el-divider>Profile Information</el-divider>
            <el-form-item label="Language Preference" prop="languagePreference">
              <el-select v-model="ruleForm.languagePreference">
                <el-option label="中文" value="chinese"></el-option>
                <el-option label="English" value="english"></el-option>
                <el-option label="Japanese" value="japanese"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="Favourite Category">
              <el-select v-model="ruleForm.favouriteCategory">
                <el-option label="BIRDS" value="BIRDS"></el-option>
                <el-option label="CATS" value="CATS"></el-option>
                <el-option label="DOGS" value="DOGS"></el-option>
                <el-option label="FISH" value="FISH"></el-option>
                <el-option label="REPTILES" value="REPTILES"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-checkbox v-model="ruleForm.list">Enable MyList</el-checkbox>
            </el-form-item>
            <el-form-item>
              <el-checkbox v-model="ruleForm.banner">Enable MyBanner</el-checkbox>
            </el-form-item>
            <div class="button">
              <el-button type="primary" @click="confirmForm('ruleForm')">注册</el-button>
            </div>
          </template>
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
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
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
        checkPass: "",
        captcha: "",
        firstName: "",
        lastName: "",
        email: "",
        phone: "",
        addr1: "",
        addr2: "",
        country: "",
        city: "",
        state: "",
        zip: "",
        languagePreference: "",
        favouriteCategory: "",
        list: false,
        banner: false
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
        checkPass: [
          {
            validator: validatePass2,
            trigger: "blur"
          }
        ],
        captcha: [
          {
            required: true,
            message: "请输入验证码",
            trigger: "blur"
          }
        ],
        firstName: [
          {
            required: true,
            message: "Please enter your firstname",
            trigger: "blur"
          }
        ],
        lastName: [
          {
            required: true,
            message: "Please enter your lastname",
            trigger: "blur"
          }
        ],
        email: [
          {
            required: true,
            message: "Please enter your email",
            trigger: "blur"
          }
        ],
        phone: [
          {
            required: true,
            message: "Please enter your phone",
            trigger: "blur"
          }
        ],
        addr1: [
          {
            required: true,
            message: "Please enter your address",
            trigger: "blur"
          }
        ],
        city: [
          {
            required: true,
            message: "Please enter your city",
            trigger: "blur"
          }
        ],
        state: [
          {
            required: true,
            message: "Please enter your state",
            trigger: "blur"
          }
        ],
        zip: [
          {
            required: true,
            message: "Please enter your zip",
            trigger: "blur"
          }
        ],
        country: [
          {
            required: true,
            message: "Please enter your country",
            trigger: "blur"
          }
        ],
        languagePreference: [
          {
            required: true,
            message: "Please pick your preferent language",
            trigger: "change"
          }
        ]
      },
      isIdAndPassValid: false
    };
  },
  methods: {
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
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.axios
            .get("/user", {
              params: {
                username: this.ruleForm.id,
                cToken: this.cToken,
                captcha: this.ruleForm.captcha
              }
            })
            .then(res => {
              if (res.data.status) {
                //不存在此用户
                this.isIdAndPassValid = true;
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
    confirmForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          let account = {
            username: this.ruleForm.id,
            password: this.ruleForm.pass,
            email: this.ruleForm.email,
            firstName: this.ruleForm.firstName,
            lastName: this.ruleForm.lastName,
            addr1: this.ruleForm.addr1,
            addr2: this.ruleForm.addr2,
            city: this.ruleForm.city,
            state: this.ruleForm.state,
            zip: this.ruleForm.zip,
            country: this.ruleForm.country,
            phone: this.ruleForm.phone,
            favouriteCategoryId: this.ruleForm.favouriteCategory,
            languagePreference: this.ruleForm.languagePreference,
            listOption: this.ruleForm.list,
            bannerOption: this.ruleForm.banner,
            bannerName: this.ruleForm.banner
          };
          this.axios.post("/user", account).then(res => {
            if (res.data.status) {
              this.$message.success("注册成功");
              this.$store.commit("setToken", res.data.data.token);
              localStorage.setItem('token', res.data.data.token)
              this.$store.commit("updateSigned", true);
              window.location.href = "/";
            } else {
              this.$message.error("用户名重复");
              this.isIdAndPassValid = false;
            }
          });
        }
      });
    },
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
