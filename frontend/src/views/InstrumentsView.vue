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
      <table class="w-full table-auto border border-gray-700 rounded text-white">
        <BaseDataTable :items="instruments" :columns="instrumentColumns" />
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
import BaseDataTable from "@/components/BaseDataTable.vue";

const instruments = ref([])
const showAddForm = ref(false)
const instrumentColumns = [
  { key: 'type', label: 'Type' },
  { key: 'brand', label: 'Brand' },
  { key: 'inventoryNumber', label: 'Inventory #' },
  { key: 'condition', label: 'Condition' },
  { key: 'locationName', label: 'Location' },
  { key: 'assignedStudentName', label: 'Assigned To' }
]
onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/v1/instruments')
    instruments.value = response.data
  } catch (error) {
    console.error('Failed to load instruments:', error)
  }
})
</script>
