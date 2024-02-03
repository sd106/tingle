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
        <div v-for="hotstar in hotStarsInfo" :key="hotstar.id" class="p-2 star-card" style="width: 18%;">
          <RouterLink class="router-link-custom" :to="`/${hotstar.id}/home`">
            <div class="hotstar-image">
              <img :src="hotstar.picture" alt="not" class="hotpic">
            </div>
            <div class="hotstar-nickName">{{ hotstar.username }}</div>
          </RouterLink>
        </div>
      </div>
    </div>
    <!-- 카테고리별 인기순 정렬 -->
    <div class="section-title">카테고리별 상위 10명!</div>
    <div class="container d-flex justify-content-around m-2">
      <!-- 카테고리 버튼 > 누르면 v-for에 들어갈 displaystarinfo 기준이 바뀜 -->
      <button class="category-btn" @click="selectCategory(0)">일상/토크</button>
      <button class="category-btn" @click="selectCategory(1)">동물</button>
      <button class="category-btn" @click="selectCategory(2)">게임/스포츠</button>
      <button class="category-btn" @click="selectCategory(3)">미술/음악</button>
      <button class="category-btn" @click="selectCategory(4)">뷰티/패션</button>
      <button class="category-btn" @click="selectCategory(5)">기타</button>
    </div>
    <div class="container d-flex flex-wrap"
         style="height: 360px; background-color: lightgoldenrodyellow; border-radius: 20px;">
      <div v-if="StarsByCategory.length === 0">
        아직 이 분야 스타가 존재하지 않습니다.
      </div>
      <div v-else v-for="star in StarsByCategory" :key="star.id" class="p-2 star-card" style="width: 18%;">
        <RouterLink class="router-link-custom" :to="`/${star.id}/home`">
          <div class="star-image">
            <img :src="star.picture" alt="not">
          </div>
          <div class="star-nickName">{{ star.username }}</div>
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
import axios from 'axios';

import type { StarByCategory, HotStarInfo } from '@/common/types/index'


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
const StarsByCategory = ref<StarByCategory[]>([]);

const loadInitialData = () => {
  getStarsByCategory(0);
};

const selectCategory = (category: number): void => {
  getStarsByCategory(category);
};

const hotStarsInfo= ref<HotStarInfo[]>([]);

//최근 인기상승 핫스타 가져오기
const getHotStars = async () => {
  const response = await axios.get('http://localhost:8080/follow/hotStars');
  hotStarsInfo.value = response.data.data;
  if(hotStarsInfo.value.length ===0) //만약 핫스타가 없으면 구독자수가 많은 스타를 가져온다
    getTop10Stars();
  console.log(response);
}

getHotStars();

//상위 10명의 스타 가져오기
const getTop10Stars = async () => {
  const response = await axios.get('http://localhost:8080/star/Top10Stars');
  hotStarsInfo.value = response.data.data;
  console.log(response);
}


const getStarsByCategory = async (category:number) => {
  const response = await axios.get(`http://localhost:8080/star/${category}`);
  StarsByCategory.value = response.data.data;
  console.log(response);
}

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

.hotpic {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  overflow: hidden;
  margin-bottom: 5px;
}
.hotpic img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.hotstar-nickName{
  width: 100%;
  height: 100%;
}
</style>