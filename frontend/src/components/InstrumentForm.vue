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
import BaseEntityForm from './BaseEntityForm.vue'
import type { InstrumentDTO } from '@/types/instrument'

const props = defineProps<{
  instrument?: InstrumentDTO | null
}>()

const emit = defineEmits(['submitSuccess', 'cancel'])

// Define the fields to render in the form
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
  // These will be view-only for now
  { key: 'locationName', label: 'Assigned Location', type: 'text' },
  { key: 'assignedStudentName', label: 'Assigned Student', type: 'text' }
]

async function submitForm(formData: InstrumentDTO) {
  try {
    const isEdit = !!formData.id
    const url = isEdit
        ? `http://localhost:8080/api/v1/instruments/${formData.id}`
        : 'http://localhost:8080/api/v1/instruments'
    const method = isEdit ? 'put' : 'post'

    const response = await fetch(url, {
      method: method.toUpperCase(),
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(formData)
    })

    if (!response.ok) throw new Error('Failed to submit instrument')
    const result = await response.json()
    emit('submitSuccess', result)
  } catch (error) {
    console.error(error)
    alert('An error occurred. Please try again.')
  }
}
</script>
