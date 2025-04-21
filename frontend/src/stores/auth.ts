import { defineStore } from 'pinia'
import axios from 'axios'

export const useAuthStore = defineStore('auth', {
    state: () => ({
        isLoggedIn: false,
        username: '',
        role: '',
        credentials: {
            username: '',
            password: '',
        },
    }),

    actions: {
        // Attempt login and fetch role from backend
        async login(username: string, password: string): Promise<boolean> {
            try {
                // Step 1: Ping a protected endpoint to validate credentials
                await axios.get('/api/v1/instruments', {
                    auth: { username, password },
                })

                // Step 2: Ask the backend for the logged-in user's info
                const infoResponse = await axios.get('/api/auth/whoami', {
                    auth: { username, password },
                })

                // Step 3: Update state with user info
                this.isLoggedIn = true
                this.username = infoResponse.data.username
                this.role = infoResponse.data.role
                this.credentials = { username, password }

                return true
            } catch (error) {
                console.error('Login failed:', error)
                this.isLoggedIn = false
                this.username = ''
                this.role = ''
                this.credentials = { username: '', password: '' }
                return false
            }
        },

        // Clear login state
        logout() {
            this.isLoggedIn = false
            this.username = ''
            this.role = ''
            this.credentials = { username: '', password: '' }
            localStorage.clear()
        },
    },
})
