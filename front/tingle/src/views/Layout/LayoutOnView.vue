<template>
  <mai :class="{ 'not-login': !store.isLogin }">
    <AppHeader />
    <AppSidebar />
    <div
      class="content"
      style="margin-top: 55px"
      :class="{ 'is-sidebar-open': store.isSidebarOpen }"
    >
      <RouterView />
    </div>
  </mai>
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
<style>
.content {
  background-color: #f1eaff;
  min-height: 100vh;
}

.not-login {
  background: white;
}

main {
  background-color: white;
}
</style>
