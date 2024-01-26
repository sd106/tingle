<template>
  <main class="container">
    <h1>스토어</h1>
    <StarMenu :name="name" />
    <StoreMain></StoreMain>
    <StoreCreate></StoreCreate>

  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'

import StarMenu from '@/components/StarMenu/StarMenu.vue';
import StoreMain from '@/components/StarMenu/Store/StoreMain.vue';
import StoreCreate from '@/components/StarMenu/Store/StoreCreate.vue';

const props = defineProps(['username']);
const name = ref(props.username);

</script>