<template>
  <main class="container">
    <StarMenu :id="id" />
    메인에거 다 나오는데
    다중선택
    드래그로 옮길 수 있어야하고
    삭제
  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'

import StarMenu from '@/components/StarMenu/StarMenu.vue';

const props = defineProps(['id']);
const id = ref(props.id);

</script>
