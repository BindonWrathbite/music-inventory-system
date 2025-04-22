<template>
  <BaseEntityForm
      title="Instrument"
      :entity="form"
      :fields="fields"
      @submit="submitForm"
      @cancel="emit('cancel')"
  />
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import axios from '@/plugins/axios'
import BaseEntityForm from './BaseEntityForm.vue'

// Props
const props = defineProps<{
  instrument?: any | null
}>()

const emit = defineEmits(['submitSuccess', 'cancel'])

// Form state
const form = ref({
  id: props.instrument?.id || null,
  type: props.instrument?.type || '',
  brand: props.instrument?.brand || '',
  serialNumber: props.instrument?.serialNumber || '',
  inventoryNumber: props.instrument?.inventoryNumber || '',
  condition: props.instrument?.condition || '',
  purchaseDate: props.instrument?.purchaseDate || '',
  purchasePrice: props.instrument?.purchasePrice || '',
  repairs: props.instrument?.repairs || '',
  notes: props.instrument?.notes || '',
  locationId: props.instrument?.locationId || null,
  assignedStudentId: props.instrument?.assignedStudentId || null
})
console.log('📦 Prefilled form:', form.value)


// Dropdown options
const locationOptions = ref<{ label: string, value: number }[]>([])
const studentOptions = ref<{ label: string, value: number }[]>([])

// Dynamic fields with dropdowns
const fields = computed(() => [
  { key: 'type', label: 'Type', type: 'text' },
  { key: 'brand', label: 'Brand', type: 'text' },
  { key: 'serialNumber', label: 'Serial Number', type: 'text' },
  { key: 'inventoryNumber', label: 'Inventory Number', type: 'text' },
  { key: 'condition', label: 'Condition', type: 'text' },
  { key: 'purchaseDate', label: 'Purchase Date', type: 'date' },
  { key: 'purchasePrice', label: 'Purchase Price', type: 'text' },
  { key: 'repairs', label: 'Repairs', type: 'textarea' },
  { key: 'notes', label: 'Notes', type: 'textarea' },
  {
    key: 'locationId',
    label: 'Assigned Location',
    type: 'dropdown',
    options: locationOptions.value
  },
  {
    key: 'assignedStudentId',
    label: 'Assigned Student',
    type: 'dropdown',
    options: studentOptions.value
  }
])

// Fetch dropdown data
onMounted(async () => {
  try {
    const [locRes, stuRes] = await Promise.all([
      axios.get('/api/v1/locations'),
      axios.get('/api/v1/students')
    ])

    locationOptions.value = locRes.data.map((loc: any) => ({
      label: loc.name,
      value: loc.id
    }))

    studentOptions.value = stuRes.data.map((student: any) => ({
      label: `${student.firstName} ${student.lastName}`,
      value: student.id
    }))
  } catch (error) {
    console.error('Failed to load location or student options:', error)
    alert('Error loading dropdowns. Please try again.')
  }
})

// Submit form
async function submitForm(formData: any) {
  try {
    const isEdit = !!formData.id
    const url = isEdit
        ? `/api/v1/instruments/${formData.id}`
        : '/api/v1/instruments'

    const response = await axios({
      method: isEdit ? 'put' : 'post',
      url,
      data: formData
    })

    emit('submitSuccess', response.data)
  } catch (error) {
    console.error('Instrument save failed:', error)
    alert('An error occurred while saving the instrument.')
  }
}
</script>
