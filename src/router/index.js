import { createRouter, createWebHistory } from 'vue-router'
import Layout from "../layout/Layout.vue"

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: '/user',
    children: [
      {
        path: 'user',
        name: 'User',
        component: () => import("../views/User.vue")
      },
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('../views/Profile.vue')
      },
      {
        path: 'books',
        name: 'Books',
        component: () => import("../views/Books.vue")
      },
      {
        path: 'news',
        name: 'News',
        component: () => import("../views/News.vue")
      },
      {
        path: 'category',
        name: 'Category',
        component: () => import('../views/Category.vue')
      }
    ]
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import("../views/Register.vue")
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import("../views/Login.vue")
  },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
