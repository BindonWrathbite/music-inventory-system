<template>
  <div class="min-h-screen bg-gray-900 text-white">
    <AppBanner />
    <Toast /> <!-- ✅ Add this line -->
    <RouterView />
  </div>
</template>

<script setup lang="ts">
import AppBanner from '@/components/AppBanner.vue'
</script>
