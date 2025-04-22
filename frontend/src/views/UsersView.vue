<template>
  <div class="p-6">
    <div v-if="showForm">
      <UserForm
          :user="editingUser"
          @submitSuccess="handleSubmitSuccess"
          @cancel="handleCancel"
      />
    </div>

    <div v-else>
      <div class="flex justify-between items-center mb-4">
        <h1 class="text-2xl font-bold">User Management</h1>
        <button
            class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
            @click="handleAddUser"
        >
          Add User
        </button>
      </div>

      <BaseDataTable
          :items="users"
          :columns="userColumns"
          :currentUsername="auth.username"
          @edit="handleEdit"
          @delete="handleDelete"
      />

    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from '@/plugins/axios'
import BaseDataTable from '@/components/BaseDataTable.vue'
import UserForm from '@/components/UserForm.vue'
import { useAuthStore } from '@/stores/auth'
const auth = useAuthStore()

// State
const users = ref([])
const showForm = ref(false)
const editingUser = ref(null)

// Columns for table
const userColumns = [
  { key: 'username', label: 'Username' },
  { key: 'role', label: 'Role' },
  { key: 'locationName', label: 'Location' }
]

// Load all users on mount
onMounted(async () => {
  try {
    const response = await axios.get('/api/v1/users')
    users.value = response.data
  } catch (error) {
    console.error('Failed to load users:', error)
    alert('Unable to load users. Are you logged in as admin?')
  }
})

// CRUD Event Handlers
function handleAddUser() {
  editingUser.value = null
  showForm.value = true
}

function handleEdit(user) {
  editingUser.value = user
  showForm.value = true
}

async function handleDelete(user) {
  const confirmDelete = confirm(`Are you sure you want to delete user "${user.username}"?`)
  if (!confirmDelete) return

  try {
    await axios.delete(`/api/v1/users/${user.id}`)
    users.value = users.value.filter(u => u.id !== user.id)
  } catch (error) {
    console.error('Failed to delete user:', error)
    alert('An error occurred while deleting the user.')
  }
}

function handleSubmitSuccess(savedUser) {
  const index = users.value.findIndex(u => u.id === savedUser.id)
  if (index !== -1) {
    users.value[index] = savedUser
  } else {
    users.value.push(savedUser)
  }
  editingUser.value = null
  showForm.value = false
}

function handleCancel() {
  editingUser.value = null
  showForm.value = false
}
</script>
