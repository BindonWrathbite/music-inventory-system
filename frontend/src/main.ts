import { createApp } from 'vue'
import App from './App.vue'

import router from './router'
import './style.css'
import PrimeVue from 'primevue/config'
import Aura from '@primeuix/themes/aura';
import ToastService from 'primevue/toastservice'


import { createPinia } from 'pinia' // Import Pinia

const app = createApp(App)
const pinia = createPinia() // Create Pinia instance
app.use(pinia) // Use Pinia in the app
app.use(router)
app.use(PrimeVue, {
    theme: {
        preset: Aura
    }
})
app.use(ToastService)

app.mount('#app')
