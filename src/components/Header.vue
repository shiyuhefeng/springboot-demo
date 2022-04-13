<template>
  <div id="header">
    <div class="left">后台管理</div>
    <div class="content"></div>
    <div class="right">
      <el-dropdown>
        <span class="el-dropdown-link">
          {{ nickName }}
          <el-icon class="el-icon--right">
            <arrow-down />
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>个人信息</el-dropdown-item>
            <el-dropdown-item @click="logout()">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { ArrowDown } from "@element-plus/icons-vue";
export default {
  name: "Header",
  components: {
    ArrowDown
  },
  data() {
    return {
      nickName: ''
    }
  },
  created() {
    let userMes = JSON.parse(sessionStorage.getItem('user')) || '';
    this.nickName = userMes.nickName
  },
  methods: {
    logout() {
      this.$router.push('/login')
    }
  }
};
</script>

<style scoped>
#header {
  height: 50px;
  line-height: 50px;
  border-bottom: 1px solid #ccc;
  display: flex;
}
.left {
  font-size: 20px;
  color: dodgerblue;
  padding-left: 30px;
  cursor: default;
}
.content {
  flex: 1;
}
.right {
  padding-right: 50px;
  font-size: 20px;
  cursor: pointer;
}
</style>