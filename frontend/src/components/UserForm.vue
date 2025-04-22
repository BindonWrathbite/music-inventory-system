<template>
  <BaseEntityForm
      :title="'User'"
      :entity="form"
      :fields="fields"
      @submit="submitForm"
      @cancel="emit('cancel')"
  />
</template>

<script setup lang="ts">
import { ref, onMounted, computed, watch } from 'vue'
import axios from '@/plugins/axios'
import BaseEntityForm from './BaseEntityForm.vue'
import type { UserDTO } from '@/types/user'

const props = defineProps<{
  user?: UserDTO | null
}>()

const emit = defineEmits(['submitSuccess', 'cancel'])

// Main form state with fallback values
const form = ref<UserDTO>({
  id: props.user?.id || null,
  username: props.user?.username || '',
  role: props.user?.role || 'USER',
  password: props.user?.password || '',
  locationId: props.user?.locationId || null
})

const locationOptions = ref<{ label: string, value: number }[]>([])
const roleOptions = [
  { label: 'Admin', value: 'ADMIN' },
  { label: 'User', value: 'USER' }
]

// Dynamic fields for the form
const fields = computed(() => {
  const base = [
    { key: 'username', label: 'Username', type: 'text' },
    { key: 'role', label: 'Role', type: 'dropdown', options: roleOptions },
    {
      key: 'locationId',
      label: 'Location',
      type: 'dropdown',
      options: locationOptions.value
    }
  ]

  if (!form.value.id) {
    base.splice(2, 0, {
      key: 'password',
      label: 'Password (auto-generated)',
      type: 'text',
      readonly: true
    })
  }

  return base
})

// Auto-generate password when adding a user
watch(() => form.value.username, (newVal) => {
  if (!form.value.id && newVal) {
    form.value.password = `${newVal}123`
  }
})

// Load available locations for dropdown
onMounted(async () => {
  try {
    const res = await axios.get('/api/v1/locations')
    locationOptions.value = res.data.map((loc: any) => ({
      label: loc.name,
      value: loc.id
    }))
  } catch (error) {
    console.error('Failed to load locations:', error)
    alert('Unable to fetch locations.')
  }
})

// Submit logic
async function submitForm(formData: UserDTO) {
  try {
    const isEdit = !!formData.id
    const url = isEdit
        ? `/api/v1/users/${formData.id}`
        : '/api/v1/users'

    const response = await axios({
      method: isEdit ? 'put' : 'post',
      url,
      data: formData
    })

    emit('submitSuccess', response.data)
  } catch (err) {
    console.error('User save failed:', err)
    alert('An error occurred while saving the user.')
  }
}
</script>
