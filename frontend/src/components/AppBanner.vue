<template>
  <div class="w-full bg-white text-gray-900 flex items-center justify-between p-4 shadow-md">
    <!-- Logo -->
    <div class="flex items-center gap-2">
      <img src="/images/dps-logo.jpg" alt="Dearborn Public Schools" class="h-12 w-auto" />
    </div>

    <!-- Title -->
    <div class="text-center flex-1">
      <h1 class="text-xl md:text-2xl font-bold tracking-wide">
        Dearborn Music Inventory System
      </h1>
    </div>

    <!-- Music Notes -->
    <div class="text-2xl md:text-3xl pr-2 hidden sm:flex gap-1">
      <span class="inline-block animate-bounce">🎵</span>
      <span class="inline-block animate-bounce [animation-delay:0.2s]">🎶</span>
      <span class="inline-block animate-bounce [animation-delay:0.4s]">🎵</span>
    </div>
  </div>
</template>

<script setup lang="ts">
// No logic needed—pure visual component
</script>
