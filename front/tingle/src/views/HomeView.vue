<template>
  <main>
    <!-- 배너사진 -->
    <div style="height: 250px;">
      <img class="img-fluid w-100 h-100" src="/image/logo.webp" alt="">
    </div>
    <!-- HOT STAR -->
    <div class="container d-flex" style="height: 250px; background-color: bisque;">
      <div class="p-2 d-flex flex-column align-items-center" v-for="star in store.starinfo" :key="star.name">
        <img :src="star.image" alt="not">
        {{ star.name }}
      </div>

    </div>
  </main>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useUserStore } from '@/stores/user';

const store = useUserStore()

// const check = store.starinfo

onMounted(() => {
  store.getStarInfo()
  console.log('onMounted')
})
</script>