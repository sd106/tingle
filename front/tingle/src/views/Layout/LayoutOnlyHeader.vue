<template>
  <ma class="ma">
    <div class="content2" :class="{ 'not-login': !store.isLogin }" style="margin-top: 80px">
      <AppHeader />
      <RouterView />
    </div>
  </ma>
</template>

<script setup lang="ts">
import { RouterView } from 'vue-router'
import { useUserStore } from '@/stores/user'
import AppHeader from '@/components/Layouts/AppHeader.vue'

const store = useUserStore()
</script>

<style>
.content2 {
  background-color: #fff3e2;
  min-height: 100vh;
}

.not-login {
  background: white;
}
</style>
