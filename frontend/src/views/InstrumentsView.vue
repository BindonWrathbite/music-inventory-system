<template>
  <div class="p-6">
    <div class="flex justify-between items-center mb-4">
      <h1 class="text-2xl font-bold">Instrument Inventory</h1>
      <button
          class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
          @click="showAddForm = true"
      >
        Add Instrument
      </button>
    </div>

    <!-- Placeholder: Add instrument form modal -->
    <div v-if="showAddForm" class="mb-4">
      <p class="text-gray-600 italic">[Instrument Form Coming Soon]</p>
    </div>

    <div v-if="instruments.length > 0" class="overflow-x-auto">
      <table class="w-full table-auto border border-gray-200 shadow-sm rounded">
        <thead class="bg-gray-100 text-left">
        <tr>
          <th class="p-2">Type</th>
          <th class="p-2">Condition</th>
          <th class="p-2">Inventory #</th>
          <th class="p-2">Location</th>
          <th class="p-2">Assigned To</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="instrument in instruments" :key="instrument.id" class="border-t">
          <td class="p-2">{{ instrument.type }}</td>
          <td class="p-2">{{ instrument.condition }}</td>
          <td class="p-2">{{ instrument.inventoryNumber }}</td>
          <td class="p-2">{{ instrument.locationName || '—' }}</td>
          <td class="p-2">{{ instrument.assignedStudentName || '—' }}</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div v-else>
      <p class="text-gray-600">No instruments found.</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'

const instruments = ref([])
const showAddForm = ref(false)

onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/v1/instruments')
    instruments.value = response.data
  } catch (error) {
    console.error('Failed to load instruments:', error)
  }
})
</script>
