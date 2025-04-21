<template>
  <BaseEntityForm
      :title="'User'"
      :entity="user"
      :fields="fields"
      @submit="submitForm"
      @cancel="emit('cancel')"
  />
</template>

<script setup lang="ts">
import BaseEntityForm from './BaseEntityForm.vue'
import type { UserDTO } from '@/types/user'

const props = defineProps<{
  user?: UserDTO | null
}>()

const emit = defineEmits(['submitSuccess', 'cancel'])

// Field structure for User management
const fields = [
  { key: 'username', label: 'Username', type: 'text' },
  { key: 'role', label: 'Role', type: 'text' },
  { key: 'password', label: 'Password', type: 'text' },
  { key: 'locationName', label: 'Location', type: 'text' } // matches DTO display field
]

async function submitForm(formData: UserDTO) {
  try {
    const isEdit = !!formData.id
    const url = isEdit
        ? `http://localhost:8080/api/v1/users/${formData.id}`
        : 'http://localhost:8080/api/v1/users'
    const method = isEdit ? 'put' : 'post'

    const response = await fetch(url, {
      method: method.toUpperCase(),
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(formData)
    })

    if (!response.ok) throw new Error('User save failed')
    const result = await response.json()
    emit('submitSuccess', result)
  } catch (error) {
    console.error('User save failed:', error)
    alert('An error occurred while saving the user.')
  }
}
</script>
