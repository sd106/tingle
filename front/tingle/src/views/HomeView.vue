<template>
  <main>
    <!-- 배경색을 두고 다른걸 다 흰색으로 두는 것도 괜찮을듯 -->
    <!-- 배너사진 -->
    <div class="container p-0" style="height: 250px;">
      <img class="img-fluid w-100 h-100" src="/image/logo.webp" alt="">
    </div>
    <!-- HOT STAR -->
    <div class="section-title">HOT STAR들을 만나보세요!</div>
    <!-- 슬라이더 좀 더 자연스럽고 부드럽게 회전초밥마냥 라이브러리 쓰든가-->
    <div class="container slider-container">
      <div class="justify-content-between slider-track" ref="sliderTrack">
        <div v-for="star in store.hotstarinfo" :key="star.nickName" class="p-2 star-card" style="width: 18%;">
          <RouterLink class="router-link-custom" :to="`/${star.id}/home`">
            <div class="star-image">
              <img :src="star.image" alt="not">
            </div>
            <div class="star-nickName">{{ star.nickName }}</div>
          </RouterLink>
        </div>
      </div>
    </div>
    <!-- 카테고리별 인기순 정렬 -->
    <div class="section-title">카테고리별 상위 10명!</div>
    <div class="container d-flex justify-content-around m-2">
      <!-- 카테고리 버튼 > 누르면 v-for에 들어갈 displaystarinfo 기준이 바뀜 -->
      <button class="category-btn" @click="selectCategory('유형민')">유형민</button>
      <button class="category-btn" @click="selectCategory('황찬준')">황찬준</button>
      <button class="category-btn" @click="selectCategory('정수빈')">정수빈</button>
      <button class="category-btn" @click="selectCategory('이성모')">이성모</button>
      <button class="category-btn" @click="selectCategory('윤정영')">윤정영</button>
      <button class="category-btn" @click="selectCategory('오은아')">오은아</button>
    </div>
    <div class="container d-flex flex-wrap"
      style="height: 360px; background-color: lightgoldenrodyellow; border-radius: 20px;">
      <div v-for="star in displayedStarInfo" :key="star.id" class="p-2 star-card" style="width: 18%;">
        <RouterLink class="router-link-custom" :to="`/${star.id}/home`">
          <div class="star-image">
            <img :src="star.image" alt="not">
          </div>
          <div class="star-nickName">{{ star.nickName }}</div>
        </RouterLink>
      </div>
    </div>
  </main>
  <RouterView />
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { RouterLink, RouterView } from 'vue-router'
import { useUserStore } from '@/stores/user';

import type { Starinfo } from '@/common/types/index'


const store = useUserStore()

// 슬라이드
const sliderTrack = ref<HTMLDivElement | null>(null);

const moveSlider = () => {
  setInterval(() => {
    if (sliderTrack.value) {
      const firstStar = sliderTrack.value.children[0] as HTMLElement;
      sliderTrack.value.appendChild(firstStar.cloneNode(true));
      sliderTrack.value.removeChild(firstStar);
    }
  }, 3000);
};

// 카테고리 디스플레이
const selectedCategory = ref<string>('유형민'); // 기본값은 첫 번째 카테고리로 설정
const displayedStarInfo = ref<Starinfo[]>([]);

const loadInitialData = () => {
  displayedStarInfo.value = store.categories[selectedCategory.value];
};

const selectCategory = (category: string): void => {
  selectedCategory.value = category;
  // if (!categories[category]) {
  //   // 만약 해당 카테고리의 starinfo가 없다면 요청
  //   categories[category] = /* API 요청 결과 */;
  // }
  displayedStarInfo.value = store.categories[category];
};


onMounted(() => {

  loadInitialData();
  moveSlider();
});


</script>

<style scoped>
.star-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 10px;
  transition: transform 0.3s ease;
  background: none;
  height: 160px;
  width: 110px;
}

.star-card:hover {
  transform: scale(1.1);
  box-shadow: none;
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

.star-nickName {
  font-weight: bold;
  text-align: center;
}

.category-btn {
  width: 100px;
  height: 36px;
  border: none;
  background: whitesmoke;
  border-radius: 30px;
}

.category-btn:hover {
  background-color: #dddddd;
}

.slider-container {
  overflow: hidden;
  width: 100%;
  height: 180px;
  background-color: bisque;
  border-radius: 100px;
}

.slider-track {
  display: flex;
}

.section-title {
  font-size: 1.5em;
  font-weight: bold;
  margin: 10px 0;
}
</style>