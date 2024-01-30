<template>
  <main class="container">
    <StarMenu :id="id" />
    <h1>스냅샷 생성</h1>
  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'

import StarMenu from '@/components/StarMenu/StarMenu.vue';


const props = defineProps(['id']);
const id = ref(props.id);

</script>