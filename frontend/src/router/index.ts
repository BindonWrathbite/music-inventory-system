import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/auth' // Import the auth store

import HomeView from '@/views/HomeView.vue'
import InstrumentsView from '@/views/InstrumentsView.vue'
import LoginView from '@/views/LoginView.vue'
import UsersView from '@/views/UsersView.vue'
import ChangePasswordView from '@/views/ChangePasswordView.vue'

    const routes = [
        {
        path: '/',
        name: 'home',
        component: HomeView,
        meta: { requiresAuth: true }, // 🔐 Protected route
        },

        {
        path: '/instruments',
        name: 'instruments',
        component: InstrumentsView,
        meta: { requiresAuth: true }, // 🔐 Protected route
        },

        {
            path: '/login',
            name: 'login',
            component: LoginView,
            meta: { requiresAuth: false }, // 🚪 Public route
        },

        {
            path: '/users',
            name: 'users',
            component: UsersView,
            meta: { requiresAuth: true } // 🔒 Protected route
        },

        {
            path: '/change-password',
            name: 'change-password',
            component: ChangePasswordView,
            meta: { requiresAuth: true },
        },
    ]

    const router = createRouter({
      history: createWebHistory(), // Use HTML5 history mode
      routes,
    })

    // Navigation guard: blocks access to protected routes if not logged in
    router.beforeEach((to, from, next) => {
        const auth = useAuthStore()
        if (to.meta.requiresAuth && !auth.isLoggedIn) {
            next({ name: 'login' })
        } else {
            next()
        }
    })

    export default router;