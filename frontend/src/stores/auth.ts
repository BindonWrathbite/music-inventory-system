import { defineStore } from 'pinia'
import axios from 'axios'

export const useAuthStore = defineStore('auth', {
    state: () => ({
        username: localStorage.getItem('auth_username') || '',
        password: localStorage.getItem('auth_password') || '',
        isLoggedIn: !!(localStorage.getItem('auth_username') && localStorage.getItem('auth_password'))
    }),

    actions: {
        async login(username: string, password: string) {
            const basicAuth = 'Basic ' + btoa(`${username}:${password}`)

            try {
                await axios.get('/api/instruments', {
                    headers: {
                        Authorization: basicAuth
                    }
                })

                this.username = username
                this.password = password
                this.isLoggedIn = true

                localStorage.setItem('auth_username', username)
                localStorage.setItem('auth_password', password)
                axios.defaults.headers.common['Authorization'] = basicAuth

                return true
            } catch (error) {
                this.logout()
                return false
            }
        },

        logout() {
            this.username = ''
            this.password = ''
            this.isLoggedIn = false
            localStorage.removeItem('auth_username')
            localStorage.removeItem('auth_password')
            delete axios.defaults.headers.common['Authorization']
        }
    }
})

// Restore credentials only if both username AND password exist
const storedUsername = localStorage.getItem('auth_username')
const storedPassword = localStorage.getItem('auth_password')

if (storedUsername && storedPassword) {
    const basicAuth = 'Basic ' + btoa(`${storedUsername}:${storedPassword}`)
    axios.defaults.headers.common['Authorization'] = basicAuth
}
