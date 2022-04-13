<template>
  <div id="aside">
    <el-col :span="12">
      <el-menu
        style="width: 200px; min-height: calc(100vh - 50px)"
        :default-active="path"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
        router
      >
        <el-sub-menu index="1" v-if="data === 1">
          <template #title>系统管理</template>
          <el-menu-item index="/user" default-active>用户管理</el-menu-item>
          <el-menu-item index="/category" default-active>图书分类</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/books" default-active>图书管理</el-menu-item>
        <el-menu-item index="/news" default-active>新闻管理</el-menu-item>
      </el-menu>
    </el-col>
  </div>
</template>

<script>
import request from "../utils/request";

export default ({
  name: 'Aside',
  data() {
    return {
      // 设置默认高亮的菜单
      path: this.$route.path,
      data: 0
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user") || '{}'
    this.user = JSON.parse(userStr)

    // 请求服务端  确认当前登录用户的合法信息
    request.get('/user/' + this.user.id).then(res => {
      console.log(res);
      if (res.code === '0') {
        this.data = res.data
      }
    })
  }

});
</script>

<style scoped>
</style>