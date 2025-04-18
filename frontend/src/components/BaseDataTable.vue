<template>
  <div class="overflow-x-auto rounded shadow border border-gray-700">
    <table class="w-full table-auto text-left text-white">
      <thead class="bg-gray-800">
      <tr>
        <th
            v-for="col in columnsToRender"
            :key="col.key"
            class="p-2 font-semibold border-b border-gray-700"
        >
          {{ col.label }}
        </th>
      </tr>
      </thead>
      <tbody>
      <template v-for="(item, index) in items" :key="item[keyField] ?? index">
        <!-- Clickable row -->
        <tr
            @click="toggleExpanded(index)"
            class="border-b border-gray-700 hover:bg-gray-800 cursor-pointer"
        >
          <td
              v-for="col in columnsToRender"
              :key="col.key"
              class="p-2 whitespace-nowrap"
          >
            {{ item[col.key] ?? '—' }}
          </td>
        </tr>

        <!-- Expanded detail row -->
        <tr v-if="expandedIndex === index" class="bg-gray-900 border-b border-gray-700"><td :colspan="columnsToRender.length" class="p-4 text-sm text-gray-300 bg-gray-900">
          <div class="grid grid-cols-2 gap-x-4 gap-y-2">
            <template v-for="(value, key) in getExtraFields(item)" :key="key">
              <div class="font-semibold text-gray-400">{{ formatLabel(key) }}:</div>
              <div class="text-gray-100">{{ value ?? '—' }}</div>
            </template>
          </div>
        </td>
        </tr>
      </template>
      </tbody>
    </table>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'

interface Column {
  key: string
  label: string
}

const props = defineProps<{
  items: any[]
  columns?: Column[]
  keyField?: string
}>()

// Only render columns explicitly listed or inferred (excluding 'id')
const columnsToRender = computed(() => {
  if (props.columns?.length) return props.columns
  const sample = props.items[0] || {}
  return Object.keys(sample)
      .filter((key) => key !== 'id')
      .map((key) => ({
        key,
        label: key.charAt(0).toUpperCase() + key.slice(1)
      }))
})

// Track which row is expanded
const expandedIndex = ref<number | null>(null)

function toggleExpanded(index: number) {
  expandedIndex.value = expandedIndex.value === index ? null : index
}

// Pull in additional fields for expanded row view
function getExtraFields(item: any) {
  const visibleKeys = columnsToRender.value.map(col => col.key)
  const result: Record<string, any> = {}

  Object.entries(item).forEach(([key, value]) => {
    if (!visibleKeys.includes(key) && key !== 'id') {
      result[key] = value
    }
  })

  return result
}

// Format keys into readable labels
function formatLabel(key: string) {
  return key
      .replace(/([A-Z])/g, ' $1') // Add space before capital letters
      .replace(/^./, str => str.toUpperCase()) // Capitalize first letter
}
</script>
