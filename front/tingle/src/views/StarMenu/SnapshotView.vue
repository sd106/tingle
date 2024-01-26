<template>
  <main class="container">
    <h1>스냅샷</h1>
    <StarMenu :name="name" />
    <SnapShotMain />
  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'

import StarMenu from '@/components/StarMenu/StarMenu.vue';
import SnapShotMain from '@/components/StarMenu/SnapShot/SnapShotMain.vue';

const props = defineProps(['username']);
const name = ref(props.username);

</script>