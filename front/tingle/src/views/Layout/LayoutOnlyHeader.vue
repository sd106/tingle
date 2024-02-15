<template>
  <ma class="ma">
    <div class="content2" style="margin-top: 80px">
      <AppHeader />
      <RouterView />
    </div>
  </ma>
</template>

<script setup lang="ts">
import { RouterView } from 'vue-router'

import AppHeader from '@/components/Layouts/AppHeader.vue'
</script>

<style>
.content2 {
  background-color: #FFF3E2;
}
</style>
