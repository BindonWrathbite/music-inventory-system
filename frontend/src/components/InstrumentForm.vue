<template>
  <BaseEntityForm
      :title="'Instrument'"
      :entity="instrument"
      :fields="fields"
      @submit="submitForm"
      @cancel="emit('cancel')"
  />
</template>

<script setup lang="ts">
import axios from 'axios'
import BaseEntityForm from './BaseEntityForm.vue'
import type { InstrumentDTO } from '@/types/instrument'

const props = defineProps<{
  instrument?: InstrumentDTO | null
}>()

const emit = defineEmits(['submitSuccess', 'cancel'])

// Define editable fields for the instrument form
const fields = [
  { key: 'type', label: 'Type', type: 'text' },
  { key: 'brand', label: 'Brand', type: 'text' },
  { key: 'serialNumber', label: 'Serial Number', type: 'text' },
  { key: 'inventoryNumber', label: 'Inventory Number', type: 'text' },
  { key: 'condition', label: 'Condition', type: 'text' },
  { key: 'purchaseDate', label: 'Purchase Date', type: 'date' },
  { key: 'purchasePrice', label: 'Purchase Price', type: 'text' },
  { key: 'repairs', label: 'Repairs', type: 'textarea' },
  { key: 'notes', label: 'Notes', type: 'textarea' },
  { key: 'locationName', label: 'Assigned Location', type: 'text' },
  { key: 'assignedStudentName', label: 'Assigned Student', type: 'text' }
]

// Submit handler for both add and edit
async function submitForm(formData: InstrumentDTO) {
  try {
    const isEdit = !!formData.id
    const url = isEdit
        ? `/api/v1/instruments/${formData.id}`
        : '/api/v1/instruments'

    const response = isEdit
        ? await axios.put(url, formData)
        : await axios.post(url, formData)

    emit('submitSuccess', response.data)
  } catch (error) {
    console.error('Instrument save failed:', error)
    alert('An error occurred while saving the instrument. Please try again.')
  }
}
</script>
