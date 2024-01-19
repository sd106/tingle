<template>
  <main>
    <h1>위시</h1>
    <div>
      <RouterLink :to="`/profile/${username}/home`">Home</RouterLink> |
      <RouterLink :to="`/profile/${username}/snapshot`">Snapshot</RouterLink> |
      <RouterLink :to="`/profile/${username}/wish`">Wish</RouterLink> |
      <RouterLink :to="`/profile/${username}/store`">Store</RouterLink> |
      <RouterLink :to="`/profile/${username}/fanmeeting`">Fanmeeting</RouterLink>
    </div>
  </main>
  <RouterView />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { RouterLink, RouterView } from 'vue-router'

const props = defineProps(['username']);
const name = ref('')
name.value = props.username

</script>
