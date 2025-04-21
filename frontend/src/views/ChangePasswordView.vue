<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-100 p-4">
    <div class="bg-white shadow-lg rounded-2xl p-6 w-full max-w-sm space-y-4">
      <h2 class="text-2xl font-bold text-center text-gray-800">Change Password</h2>

      <div>
        <label for="oldPassword" class="block text-sm font-medium text-gray-700">Old Password</label>
        <input
            id="oldPassword"
            v-model="oldPassword"
            type="password"
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:ring-blue-500 focus:border-blue-500"
        />
      </div>

      <div>
        <label for="newPassword" class="block text-sm font-medium text-gray-700">New Password</label>
        <input
            id="newPassword"
            v-model="newPassword"
            type="password"
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:ring-blue-500 focus:border-blue-500"
        />
      </div>

      <button
          @click="changePassword"
          class="w-full bg-green-600 text-white py-2 rounded-md hover:bg-green-700 transition duration-150"
      >
        Save Changes
      </button>

      <p v-if="message" :class="isSuccess ? 'text-green-600' : 'text-red-600'" class="text-sm text-center">
        {{ message }}
      </p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'
import { useAuthStore } from '@/stores/auth'
import { useToast } from 'primevue/usetoast'

const auth = useAuthStore()
const toast = useToast()

const oldPassword = ref('')
const newPassword = ref('')
const message = ref('')
const isSuccess = ref(false)

async function changePassword() {
  try {
    const response = await axios.put(`/api/v1/users/${auth.username}/change-password`, {
      oldPassword: oldPassword.value,
      newPassword: newPassword.value
    })

    toast.add({
      severity: 'success',
      summary: 'Password Updated',
      detail: 'Your password was changed successfully.',
      life: 3000
    })

    oldPassword.value = ''
    newPassword.value = ''
    message.value = ''
    isSuccess.value = true
  } catch (error: any) {
    toast.add({
      severity: 'error',
      summary: 'Password Change Failed',
      detail: 'Please check your old password and try again.',
      life: 3000
    })

    isSuccess.value = false
    console.error(error)
  }
}
</script>
