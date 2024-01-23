<template>
  <main class="container">
    <h1>스냅샷</h1>
    <div>
      <RouterLink :to="`/profile/${username}/home`">Home</RouterLink> |
      <RouterLink :to="`/profile/${username}/snapshot`">Snapshot</RouterLink> |
      <RouterLink :to="`/profile/${username}/wish`">Wish</RouterLink> |
      <RouterLink :to="`/profile/${username}/store`">Store</RouterLink> |
      <RouterLink :to="`/profile/${username}/fanmeeting`">Fanmeeting</RouterLink>
    </div>
    <SnapShotMain />
  </main>
  <RouterView />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { RouterLink, RouterView } from 'vue-router'

import SnapShotMain from '@/components/StarMenu/SnapShot/SnapShotMain.vue';

const props = defineProps(['username']);
const name = ref('')
name.value = props.username

</script>