<template>
  <div class="w-full bg-white text-gray-900 flex items-center justify-between p-4 shadow-md">
    <!-- Logo -->
    <div class="flex items-center gap-2">
      <img @click="router.push({ name: 'home' })" src="/images/dps-logo.jpg" alt="Dearborn Public Schools" class="h-12 w-auto" />
    </div>

    <!-- Title -->
    <div class="text-center flex-1">
      <h1 class="text-xl md:text-2xl font-bold tracking-wide">
        Dearborn Music Inventory System
      </h1>
    </div>

    <!-- Right section: Animated notes always show, login info only if logged in -->
    <div class="hidden sm:flex items-center gap-3 pr-2 text-2xl md:text-3xl">
      <span class="inline-block animate-bounce">🎵</span>
      <span class="inline-block animate-bounce [animation-delay:0.2s]">🎶</span>
      <span class="inline-block animate-bounce [animation-delay:0.4s]">🎵</span>

      <!-- Only show when user is logged in -->
      <template v-if="auth.isLoggedIn">
        <div class="text-sm md:text-base font-medium text-gray-700 ml-3">
          Hello, {{ auth.username }}
        </div>
        <div>
        <!-- Home Button -->
        <button
            @click="router.push({ name: 'home' })"
            class="ml-2 px-3 py-1 text-sm bg-green-600 text-white rounded-md hover:bg-green-700 transition"
        >
          Home
        </button>

        <!-- Change Password Button (for all logged-in users) -->
        <button
            @click="router.push({ name: 'change-password' })"
            class="ml-2 px-3 py-1 text-sm bg-blue-600 text-white rounded-md hover:bg-blue-700 transition"
        >
          Change Password
        </button>

        <!-- Logout Button -->
        <button
            @click="handleLogout"
            class="ml-2 px-3 py-1 text-sm bg-red-600 text-white rounded-md hover:bg-red-700 transition"
        >
          Logout
        </button>

      </template>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from '@/stores/auth'
import { useRouter } from 'vue-router'

const auth = useAuthStore()
const router = useRouter()

// Clear login state and redirect to /login
const handleLogout = () => {
  auth.logout()
  router.push({ name: 'login' })
}
</script>
