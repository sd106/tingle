<template>
  <main class="container">
    <h1>팬미팅</h1>
    <StarMenu :name="name" />
  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'

import StarMenu from '@/components/StarMenu/StarMenu.vue';

const props = defineProps(['username']);
const name = ref(props.username);
</script>
