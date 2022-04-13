<template>
  <div id="login">
    <div class="login-center">
      <div class="welcome">欢迎注册</div>
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
        <el-form-item prop="confirm">
          <el-input prefix-icon="Lock" v-model="form.confirm" show-password>
            <template #prefix>
              <el-icon style="top: 10px; padding-left: 3px; font-size: 20px"
                ><lock
              /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="register()">
            注 册
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { Avatar, Lock } from '@element-plus/icons-vue'
import request from '../utils/request'

export default {
  name: 'login',
  components: {
    Avatar,
    Lock
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
        confirm: [
          {
            required: true,
            message: '请输入确认密码',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    register() {
      if (this.form.password != this.form.confirm) {
        this.$message({
          type: 'error',
          message: '两次输入密码不一致'
        })
        return
      }
      this.$refs['form'].validate((valid) => {
        // 验证合法  发送请求
        if (valid) {
          request.post('/user/register', this.form).then(res => {
            if (res.code == '0') {
              this.$message({
                type: 'success',
                message: '注册成功'
              })
              // 注册成功后  进行页面的跳转  跳转到登录
              this.$router.push("/login")
            } else {
              this.$message({
                type: 'error',
                message: res.msg
              })
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
#login {
  background-color: green;
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