<template>
  <main>
    <!-- 배너사진 -->
    <div style="height: 250px;">
      <img class="img-fluid w-100 h-100" src="/image/logo.webp" alt="">
    </div>
    <!-- HOT STAR -->
    <div class="container d-flex" style="height: 250px; background-color: bisque;">
      <div v-for="star in store.starinfo" :key="star.name" class="p-2 d-flex flex-column align-items-center">
        <RouterLink :to="`/profile/${star.username}/home`">
          <img :src="star.image" alt="not">
        </RouterLink>
        {{ star.name }}
      </div>
    </div>
  </main>
  <RouterView />
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { RouterLink, RouterView } from 'vue-router'
import { useUserStore } from '@/stores/user';

const store = useUserStore()



onMounted(() => {
  store.getStarInfo()
})
</script>