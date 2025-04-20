import { defineStore } from 'pinia'
import axios from 'axios'

export const useAuthStore = defineStore('auth', {
    state: () => ({
        username: localStorage.getItem('auth_username') || '',
        password: localStorage.getItem('auth_password') || '',
        isLoggedIn: !!localStorage.getItem('auth_username'), // True if saved login
    }),

    actions: {
        // Log in with username/password
        async login(username: string, password: string) {
            // Construct Basic Auth header
            const basicAuth = 'Basic ' + btoa(`${username}:${password}`)

            try {
                // Make a simple request to verify credentials (any protected route works)
                await axios.get('/api/instruments', {
                    headers: {
                        Authorization: basicAuth,
                    },
                })

                // If successful, store credentials and mark as logged in
                this.username = username
                this.password = password
                this.isLoggedIn = true

                // Set Axios default auth header globally
                axios.defaults.headers.common['Authorization'] = basicAuth

                return true
            } catch (error) {
                // Login failed — likely 401
                this.logout()
                return false
            }
        },

        // Log out and clear state
        logout() {
            this.username = ''
            this.password = ''
            this.isLoggedIn = false
            delete axios.defaults.headers.common['Authorization']
        },
    },
})

// 📌 Immediately re-apply stored auth header on app start
const storedUsername = localStorage.getItem('auth_username')
const storedPassword = localStorage.getItem('auth_password')

if (storedUsername && storedPassword) {
    axios.defaults.headers.common['Authorization'] = 'Basic ' + btoa(`${storedUsername}:${storedPassword}`)
}