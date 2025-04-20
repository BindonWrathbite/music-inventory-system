<template>
  <div class="min-h-screen flex items-center justify-center bg-surface-50 px-4">
    <!-- Login Card -->
    <Card class="w-full max-w-sm">
      <template #title>Log In</template>

      <template #content>
        <div class="flex flex-col gap-4">
          <!-- Username Field -->
          <div>
            <label for="username" class="block text-sm font-medium text-color">Username</label>
            <InputText
                id="username"
                v-model="username"
                placeholder="Enter your username"
                class="w-full"
            />
          </div>

          <!-- Password Field -->
          <div>
            <label for="password" class="block text-sm font-medium text-color">Password</label>
            <Password
                id="password"
                v-model="password"
                placeholder="Enter your password"
                :feedback="false"
                toggleMask
                class="w-full"
            />
          </div>

          <!-- Error Message -->
          <Message v-if="errorMessage" severity="error" class="text-sm">
            {{ errorMessage }}
          </Message>

          <!-- Login Button -->
          <Button label="Log In" class="w-full" @click="handleLogin" />
        </div>
      </template>
    </Card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

// PrimeVue components
import Card from 'primevue/card'
import InputText from 'primevue/inputtext'
import Password from 'primevue/password'
import Button from 'primevue/button'
import Message from 'primevue/message'

const router = useRouter()
const auth = useAuthStore()

const username = ref('')
const password = ref('')
const errorMessage = ref('')

// Handle login with credentials
const handleLogin = async () => {
  const success = await auth.login(username.value, password.value)

  if (success) {
    localStorage.setItem('auth_username', username.value)
    localStorage.setItem('auth_password', password.value)
    router.push({ name: 'instruments' })
  } else {
    errorMessage.value = 'Invalid username or password.'
  }
}
</script>
