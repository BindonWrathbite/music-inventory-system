<template>
  <div class="p-6 space-y-4">
    <h2 class="text-2xl font-bold text-white">User Management</h2>

    <BaseDataTable
        title="Users"
        :items="users"
        :visible-columns="['username', 'role', 'locationName']"
        @add="startAdd"
        @edit="startEdit"
        @delete="deleteUser"
    />

    <UserForm
        v-if="editingUser"
        :user="editingUser"
        @submitSuccess="onSubmitSuccess"
        @cancel="cancelEdit"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'
import BaseDataTable from '@/components/BaseDataTable.vue'
import UserForm from '@/components/UserForm.vue'

const users = ref([])
const showForm = ref(false)
const editingUser = ref(null)

const userColumns = [
  { key: 'username', label: 'Username' },
  { key: 'role', label: 'Role' },
  { key: 'locationName', label: 'Location' },
]

// Edit handler
function handleEdit(user) {
  editingUser.value = user
  showForm.value = true
}

// Delete handler
async function handleDelete(user) {
  const confirmDelete = confirm(`Are you sure you want to delete user "${user.username}"?`)
  if (!confirmDelete) return

  try {
    await axios.delete(`/api/v1/users/${user.id}`, {
      auth: {
        username: localStorage.getItem('auth_username') || '',
        password: localStorage.getItem('auth_password') || '',
      }
    })
    users.value = users.value.filter(u => u.id !== user.id)
  } catch (error) {
    console.error('Failed to delete user:', error)
    alert('An error occurred while deleting the user.')
  }
}

// Add handler
function handleAddUser() {
  editingUser.value = null
  showForm.value = true
}

// Save handler
function handleSubmitSuccess(newUser) {
  const index = users.value.findIndex(u => u.id === newUser.id)
  if (index !== -1) {
    users.value[index] = newUser
  } else {
    users.value.push(newUser)
  }
  editingUser.value = null
  showForm.value = false
}

function handleCancel() {
  editingUser.value = null
  showForm.value = false
}

// Load users
onMounted(async () => {
  try {
    const response = await axios.get('/api/v1/users', {
      auth: {
        username: localStorage.getItem('auth_username') || '',
        password: localStorage.getItem('auth_password') || '',
      }
    })
    users.value = response.data
  } catch (error) {
    console.error('Failed to load users:', error)
    alert('Unable to load users. Are you logged in as an admin?')
  }
})
</script>
