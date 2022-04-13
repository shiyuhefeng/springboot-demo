import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// 引入element-plus框架
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import 'element-plus/dist/index.css'

createApp(App).use(store).use(router).use(ElementPlus, {
  locale: zhCn,
}).mount('#app')
