<template>
  <main>
    <!-- 배너사진 -->
    <div class="container p-0" style="height: 250px;">
      <img class="img-fluid w-100 h-100" src="/image/logo.webp" alt="">
    </div>
    <!-- HOT STAR -->
    <div class="container d-flex" style="height: 180px; background-color: bisque;">
      <div v-for="star in store.hotstarinfo" :key="star.name" class="p-2 d-flex flex-column align-items-center star-card"
        style="width: 18%;">
        <RouterLink :to="`/profile/${star.username}/home`">
          <div class="star-image">
            <img :src="star.image" alt="not">
          </div>
        </RouterLink>
        <div class="star-name">{{ star.name }}</div>
      </div>
    </div>
    <!-- 나머지 인기순 정렬 -->
    <div class="container d-flex flex-wrap" style="height: 360px; background-color: sandybrown;">
      <div v-for="star in displayedStarInfo" :key="star.name" class="p-2 d-flex flex-column align-items-center star-card"
        style="width: 18%;">
        <RouterLink :to="`/profile/${star.username}/home`">
          <div class="star-image">
            <img :src="star.image" alt="not">
          </div>
        </RouterLink>
        <div class="star-name">{{ star.name }}</div>
      </div>
    </div>
  </main>
  <RouterView />
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { RouterLink, RouterView } from 'vue-router'
import { useUserStore } from '@/stores/user';

import type { Star } from '@/common/types/index'

const store = useUserStore()


const displayedStarInfo = ref<Star[]>([]);

// 초기 데이터 로딩
const loadInitialData = () => {
  displayedStarInfo.value = store.allstarinfo.slice(0, 10);
};


// 컴포넌트가 마운트될 때 초기 데이터 로딩 및 스크롤 이벤트 핸들러 등록
onMounted(() => {
  loadInitialData();
});

</script>

<style scoped>
.star-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 10px;
  background-color: blueviolet;
}

.star-image {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  overflow: hidden;
  margin-bottom: 5px;
}

.star-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.star-name {
  font-weight: bold;
  text-align: center;
}
</style>