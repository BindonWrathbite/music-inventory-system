<template>
  <div class="p-6">
    <div v-if="showForm">
      <InstrumentForm
          :instrument="editingInstrument"
          @submitSuccess="handleSubmitSuccess"
          @cancel="handleCancel"
      />
    </div>

    <div v-else>
      <div class="flex justify-between items-center mb-4">
        <h1 class="text-2xl font-bold">Instrument Inventory</h1>
        <button
            class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
            @click="handleAddInstrument"
        >
          Add Instrument
        </button>


      </div>

      <BaseDataTable
          :items="instruments"
          :columns="instrumentColumns"
          @edit="handleEdit"
          @delete="handleDelete"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'
import BaseDataTable from "@/components/BaseDataTable.vue";
import InstrumentForm from '@/components/InstrumentForm.vue'

const instruments = ref([])
const showForm = ref(false)
const editingInstrument = ref<InstrumentDTO | null>(null)

const instrumentColumns = [
  { key: 'type', label: 'Type' },
  { key: 'brand', label: 'Brand' },
  { key: 'inventoryNumber', label: 'Inventory #' },
  { key: 'condition', label: 'Condition' },
  { key: 'locationName', label: 'Location' },
  { key: 'assignedStudentName', label: 'Assigned To' }
]

// handlers for edit and delete
function handleEdit(instrument: InstrumentDTO) {
  editingInstrument.value = instrument
  showForm.value = true
}

async function handleDelete(instrument: any) {
  const confirmDelete = confirm(`Are you sure you want to delete ${instrument.type} (${instrument.inventoryNumber})?`)
  if (!confirmDelete) return

  try {
    await axios.delete(`http://localhost:8080/api/v1/instruments/${instrument.id}`)
    instruments.value = instruments.value.filter(i => i.id !== instrument.id)
    console.log('Instrument deleted.')
  } catch (error) {
    console.error('Failed to delete instrument:', error)
    alert('An error occurred while trying to delete this instrument.')
  }
}

function handleAddInstrument() {
  editingInstrument.value = null
  showForm.value = true
}

function handleSubmitSuccess(newInstrument: InstrumentDTO) {
  const index = instruments.value.findIndex(i => i.id === newInstrument.id)
  if (index !== -1) {
    instruments.value[index] = newInstrument
  } else {
    instruments.value.push(newInstrument)
  }
  editingInstrument.value = null
  showForm.value = false
}

function handleCancel() {
  editingInstrument.value = null
  showForm.value = false
}

onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/v1/instruments')
    instruments.value = response.data
  } catch (error) {
    console.error('Failed to load instruments:', error)
  }
})
</script>
