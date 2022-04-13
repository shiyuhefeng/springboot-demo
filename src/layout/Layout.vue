<template>
  <div id="layout">
    <Header />
    <div class="layout-content">
      <Aside />
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import Header from "../components/Header.vue"
import Aside from "../components/Aside.vue"

export default {
  name: 'Layout',
  components: {
    Header,
    Aside
  }
}
</script>

<style>
.layout-content {
  display: flex;
}
</style>