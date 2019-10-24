<template>
  <div class="container">
    <div v-if="!isIdAndPassValid" class="form-container">
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="账号" prop="id">
          <el-input type="text" v-model="ruleForm.id" autocomplete="off" clearable></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="pass" >
          <el-input type="password" v-model="ruleForm.pass" autocomplete="off" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div v-if="isIdAndPassValid" class="form-container">
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="First name" prop="firstName">
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
        <el-form-item label="Address1" prop="address1">
          <el-input type="text" v-model="ruleForm.addr1" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Address2" prop="address2">
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
        <el-form-item label="Language Preference" prop="languagePreference">
          <el-select v-model="ruleForm.lang" placeholder="中文" prop="Language Preference">
            <el-option label="中文" value="Chinese"></el-option>
            <el-option label="English" value="English"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Favourite Category" prop="favouriteCategory">
          <el-select v-model="ruleForm.category" placeholder="小鸟" prop="favouriteCategory">
            <el-option label="小鸟" value="Birds"></el-option>
            <el-option label="小猫" value="Cats"></el-option>
            <el-option label="小狗" value="Dogs"></el-option>
            <el-option label="鱼" value="Fish"></el-option>
            <el-option label="Reptiles" value="Reptiles"></el-option>
          </el-select>
        </el-form-item>
<!--        <el-form-item label="Language Preference" prop="languagePreference">-->
<!--          <el-input type="text" v-model="ruleForm.lang" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="Favourite Category" prop="favouriteCategory">-->
<!--          <el-input type="text" v-model="ruleForm.category" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->
        <el-form-item label="Enable MyList" prop="enableMylist">
          <el-input type="text" v-model="ruleForm.list" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Enable MyBanner" prop="enableMyBanner">
          <el-input type="text" v-model="ruleForm.banner" autocomplete="off"></el-input>
          <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
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
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm.pass) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        ruleForm: {
          id: '',
          pass: '',
          checkPass: '',
          firstName: '',
          lastName: '',
          email: '',
          phone: '',
          addr1: '',
          addr2: '',
          country: '',
          city: '',
          state: '',
          zip: '',
          lang: '',
          category: '',
          banner: '',
        },
        rules: {
          id: [
            { validator: validateId, trigger: 'blur' }
          ],
          pass: [
            { validator: validatePass, trigger: 'blur' }
          ],
          checkPass: [
            { validator: validatePass2, trigger: 'blur' }
          ],
        },
        isIdAndPassValid: false
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.isIdAndPassValid = true
          }
        });
      },
    }
  }
</script>

<style lang="scss" scoped>
  .form-container {
    margin: 150px calc(50% - 150px) 150px auto;
    width: 400px;
  }

  .el-button {
    width: 300px;
  }
</style>