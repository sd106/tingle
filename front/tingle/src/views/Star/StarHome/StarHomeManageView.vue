<template>
  <main class="container">
    <StarMenu :id="id" />
    <h1>관리하는 페이지인데 어케해야할 지 모르겠음</h1>
  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue';

import StarMenu from '@/components/StarMenu/StarMenu.vue';

const props = defineProps(['id']);
const id = ref(props.id);

</script>
