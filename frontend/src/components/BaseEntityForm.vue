<template>
  <div class="bg-gray-800 text-white p-6 rounded shadow max-w-xl w-full">
    <h2 class="text-xl font-bold mb-4">
      {{ entity?.id ? 'Edit' : 'Add New' }} {{ title }}
    </h2>

    <form @submit.prevent="submitForm" class="grid grid-cols-1 gap-4">
      <template v-for="field in fields" :key="field.key">
        <label :for="field.key" class="text-sm font-semibold text-gray-300">
          {{ field.label }}
        </label>

        <InputText
            v-if="field.type === 'text' || field.type === 'email' || field.type === 'number'"
            v-model="form[field.key]"
            :type="field.type"
            :placeholder="field.label"
            class="w-full"
        />

        <Textarea
            v-else-if="field.type === 'textarea'"
            v-model="form[field.key]"
            :placeholder="field.label"
            autoResize
        />

        <Calendar
            v-else-if="field.type === 'date'"
            v-model="form[field.key]"
            showIcon
            dateFormat="yy-mm-dd"
            :placeholder="field.label"
        />

        <!-- Add more custom field types as needed -->
      </template>

      <div class="flex justify-end gap-3 pt-2">
        <Button label="Cancel" class="p-button-secondary" @click="emit('cancel')" />
        <Button label="Save" type="submit" class="p-button-success" />
      </div>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import InputText from 'primevue/inputtext'
import Calendar from 'primevue/calendar'
import Textarea from 'primevue/textarea'
import Button from 'primevue/button'

const props = defineProps<{
  entity?: Record<string, any> | null
  fields: { key: string; label: string; type: string }[]
  title?: string
}>()

const emit = defineEmits(['submit', 'cancel'])

const form = ref<Record<string, any>>({})

watch(
    () => props.entity,
    (newVal) => {
      form.value = { ...newVal } || {}
    },
    { immediate: true }
)

function submitForm() {
  emit('submit', { ...form.value })
}
</script>
