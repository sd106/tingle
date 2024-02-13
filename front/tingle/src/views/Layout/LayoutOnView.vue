<template>
  <AppHeader />
  <AppSidebar />
  <div class="content" :class="{ 'is-sidebar-open': store.isSidebarOpen }">
    <RouterView />
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { RouterView } from 'vue-router'
import { useUserStore } from '@/stores/user'

import AppHeader from '@/components/Layouts/AppHeader.vue'
import AppSidebar from '@/components/Layouts/AppSidebar.vue'

const store = useUserStore()

onMounted(() => {
  if (store.isSidebarOpen) {
    document.body.classList.add('is-sidebar-open')
  }
})
</script>
