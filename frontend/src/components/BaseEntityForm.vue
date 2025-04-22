<script setup lang="ts">
import InputText from 'primevue/inputtext'
import Calendar from 'primevue/calendar'
import Textarea from 'primevue/textarea'
import Button from 'primevue/button'
import Dropdown from 'primevue/dropdown'

// Accept `form` from parent and mutate it directly
const props = defineProps<{
  entity: Record<string, any>
  fields: { key: string; label: string; type: string; readonly?: boolean; options?: any[] }[]
  title?: string
}>()

const emit = defineEmits(['submit', 'cancel'])

function submitForm() {
  emit('submit', { ...props.entity }) // Use props.entity directly
}
</script>

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
            v-model="entity[field.key]"
            :type="field.type"
            :placeholder="entity[field.key] || field.label"
            :readonly="field.readonly || false"
            class="w-full"
        />

        <Textarea
            v-else-if="field.type === 'textarea'"
            v-model="entity[field.key]"
            :placeholder="field.label"
            autoResize
        />

        <Calendar
            v-else-if="field.type === 'date'"
            v-model="entity[field.key]"
            showIcon
            dateFormat="yy-mm-dd"
            :placeholder="field.label"
        />

        <Dropdown
            v-else-if="field.type === 'dropdown'"
            :options="field.options"
            optionLabel="label"
            optionValue="value"
            v-model="entity[field.key]"
            class="w-full"
        />
      </template>

      <div class="flex justify-end gap-3 pt-2">
        <Button label="Cancel" class="p-button-secondary" @click="emit('cancel')" />
        <Button label="Save" type="submit" class="p-button-success" />
      </div>
    </form>
  </div>
</template>
