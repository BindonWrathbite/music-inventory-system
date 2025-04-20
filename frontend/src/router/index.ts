import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/auth' // Import the auth store

import HomeView from '@/views/HomeView.vue'
import InstrumentsView from '@/views/InstrumentsView.vue'
import LoginView from '@/views/LoginView.vue'

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