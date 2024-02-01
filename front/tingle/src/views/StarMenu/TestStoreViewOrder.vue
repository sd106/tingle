<template>
    <main class="container">
      <h1>스토어OrderTest</h1>
      <h1>{{ name }}</h1>
    </main>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue'
  
  const props = defineProps(['username']);
  const name = ref(props.username);
  

  
  </script>