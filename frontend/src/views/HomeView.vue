<template>
  <div class="text-center mt-12">
    <h1 class="text-3xl font-bold mb-6">🎵 Welcome to Music Inventory!</h1>
    <p class="mt-4 text-lg text-gray-400">Your instruments. Organized.</p>
  </div>
  <div class="p-10 text-white">
    <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
      <RouterLink to="/instruments" class="bg-gray-800 hover:bg-gray-700 p-6 rounded shadow-lg transition duration-300">
        <div class="flex flex-col justify-between h-full">
          <div>
            <h2 class="text-xl font-semibold mb-2">🎺 Instruments</h2>
            <p class="text-sm text-gray-300">Manage all instruments, their status, and assignments.</p>
          </div>
        </div>
      </RouterLink>

      <!-- Placeholder for future cards -->
      <!--
      <RouterLink to="/students" class="bg-gray-800 hover:bg-gray-700 p-6 rounded shadow-lg transition duration-300">
        <h2 class="text-xl font-semibold mb-2">🎓 Students</h2>
        <p class="text-sm text-gray-300">Assign instruments and track student info.</p>
      </RouterLink
      -->
    </div>
  </div>
</template>

<script setup lang="ts">
// Nothing needed here yet unless you want dynamic data
</script>
