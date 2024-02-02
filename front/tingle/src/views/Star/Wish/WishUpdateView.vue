<template>
  <main class="container">
    <StarMenu :id="id" />
    일단 디테일에서 본인만 올 수 있는데
    지분 절반 안되면 수정 삭제 불가능하게
  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'

import StarMenu from '@/components/StarMenu/StarMenu.vue';

const props = defineProps(['id']);
const id = ref(props.id);

</script>
