import axios from 'axios'

// Read from localStorage and attach to each request
axios.interceptors.request.use(config => {
    const username = localStorage.getItem('auth_username')
    const password = localStorage.getItem('auth_password')

    if (username && password) {
        config.auth = {
            username,
            password,
        }
    }

    return config
})

export default axios
