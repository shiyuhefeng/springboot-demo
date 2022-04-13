<template>
  <div id="login">
    <div class="login-center">
      <div class="welcome">欢迎登陆</div>
      <el-form ref="form" :model="form" :rules="rules">
        <el-form-item prop="username">
          <el-input prefix-icon="Avatar" v-model="form.username">
            <template #prefix>
              <el-icon style="top: 10px; padding-left: 3px; font-size: 20px"
                ><avatar
              /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="Lock" v-model="form.password" show-password>
            <template #prefix>
              <el-icon style="top: 10px; padding-left: 3px; font-size: 20px"
                ><lock
              /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <div style="display: flex">
            <el-input
              prefix-icon="el-icon-key"
              v-model="form.validCode"
              style="width: 50%"
              placeholder="请输入验证码"
            ></el-input>
            <ValidCode @input="createValidCode" />
          </div>
        </el-form-item>

        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="login()">
            登 录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { Avatar, Lock } from '@element-plus/icons-vue'
import request from '../utils/request'

import ValidCode from "../components/ValidCode.vue"

export default {
  name: 'login',
  components: {
    Avatar,
    Lock,
    ValidCode
  },
  data() {
    return {
      form: {},
      rules: {
        username: [
          {
            required: true,
            message: '请输入用户名',
            trigger: 'blur'
          }
        ],
        password: [
          {
            required: true,
            message: '请输入密码',
            trigger: 'blur'
          }
        ],
        validCode: ''
      }
    }
  },
  methods: {
    login() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (!this.form.validCode) {
            this.$message.error("请输入验证码")
            return
          }
          if (this.form.validCode.toLowerCase() != this.validate.toLowerCase()) {
            this.$message.error("验证码错误")
            return
          }
          // 验证合法  发送请求
          request.post('/user/login', this.form).then(res => {
            if (res.code == '0') {
              this.$message({
                type: 'success',
                message: '登录成功'
              })
              // 存储登录信息  存储用户的信息
              console.log(res)
              sessionStorage.setItem('user', JSON.stringify(res.data))
              // 登录成功之后  进行页面的跳转  跳转到主页
              this.$router.push("/")
            } else {
              this.$message({
                type: 'error',
                message: res.msg
              })
            }
          })
        }
      })
    },
    // 接受验证码组件提交的 4 位验证码
    createValidCode(data) {
      // console.log(data);
      this.validate = data
    }
  }
}
</script>

<style scoped>
#login {
  background-color: rgb(109, 48, 48);
  width: 100%;
  height: 100vh;
  overflow: hidden;
}
.login-center {
  width: 400px;
  margin: 150px auto;
}
.welcome {
  color: #ccc;
  font-size: 30px;
  text-align: center;
  font-family: "微软雅黑";
  margin-bottom: 30px;
}
</style>