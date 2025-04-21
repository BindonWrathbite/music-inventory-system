<template>
  <div class="p-10 text-white">
    <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
      <!-- Instruments card (any logged in user) -->
      <RouterLink
          v-if="auth.isLoggedIn"
          to="/instruments"
          class="bg-gray-800 hover:bg-gray-700 p-6 rounded shadow-lg transition duration-300"
      >
        <h2 class="text-xl font-semibold mb-2">🎺 Instruments</h2>
        <p class="text-sm text-gray-300">Manage instruments and assignments.</p>
      </RouterLink>

      <!-- Users card (admin only) -->
      <RouterLink
          v-if="auth.role === 'ADMIN'"
          to="/users"
          class="bg-gray-800 hover:bg-gray-700 p-6 rounded shadow-lg transition duration-300"
      >
        <h2 class="text-xl font-semibold mb-2">🧑‍💼 Users</h2>
        <p class="text-sm text-gray-300">Manage user roles and permissions.</p>
      </RouterLink>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from '@/stores/auth'

const auth = useAuthStore()
</script>
