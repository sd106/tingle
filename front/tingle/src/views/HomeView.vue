<template>
  <main class="main-background">
    <!-- 배경색을 두고 다른걸 다 흰색으로 두는 것도 괜찮을듯 -->
    <!-- 배너사진 -->
    <div v-if="hotStarsInfo"></div>
    <div class="container-banner full-width-fixed p-0">
      <img class="star1" src="/image/star1.png" />
      <img class="star2" src="/image/star2.png" />
      <img class="star3" src="/image/star3.png" />
      <img class="star4" src="/image/star4.png" />
      <img class="star5" src="/image/star1.png" />
      <img src="/image/cat-banner.png" style="margin-left: 40%; margin-top: 94px" alt="" />
    </div>
    <!-- HOT STAR -->
    <img src="/image/wood.png" class="container-img" style="margin-left: 39%" alt="" />
    <!-- <img src="/image/cat-banner.png" style="padding-top: 1000px;" alt=""> -->
    <h1 class="container" style="padding-top: 500px">HOT STAR들을 만나보세요!</h1>
    <div class="container slider-container">
      <div class="justify-content-between slider-track" ref="sliderTrack">
        <div v-for="hotstar in hotStarsInfo" :key="hotstar.id" class="p-2 star-card-hotstar">
          <RouterLink :to="`/${hotstar.id}/home`">
            <div class="d-flex">
              <div class="p-0 me-5 align-items center" style="width: 80px">
                <img :src="hotstar.picture" alt="not" style="border-radius: 50%" />
              </div>
              <div class="d-flex justify-content-center align-items-center me-5">
                <span class="fw-bold long-text">{{ truncateText(hotstar.username, 4) }}</span>
              </div>
            </div>
          </RouterLink>
        </div>
      </div>
    </div>
    <!-- 카테고리별 인기순 정렬 -->
    <h1 class="container">카테고리별 상위 10명!</h1>
    <div class="container d-flex">
      <!-- 카테고리 버튼 > 누르면 v-for에 들어갈 displaystarinfo 기준이 바뀜 -->
      <button class="category-btn m-2" @click="selectCategory(0)">일상/토크</button>
      <button class="category-btn m-2" @click="selectCategory(1)">동물</button>
      <button class="category-btn m-2" @click="selectCategory(2)">게임/스포츠</button>
      <button class="category-btn m-2" @click="selectCategory(3)">미술/음악</button>
      <button class="category-btn m-2" @click="selectCategory(4)">뷰티/패션</button>
      <button class="category-btn m-2" @click="selectCategory(5)">기타</button>
    </div>
    
    <div class="container tw-mx-auto">
      <div class="tw-grid tw-grid-cols-5 tw-gap-0" style="margin-bottom: 100px">
        <div v-if="StarsByCategory.length === 0">아직 이 분야 스타가 존재하지 않습니다.</div>
        <div
          v-else
          v-for="star in StarsByCategory"
          :key="star.id"
          class="tw-border tw-rounded-lg star-card"
        >
          <RouterLink
            :to="`/${star.id}/home`"
            class="tw-flex tw-flex-col tw-items-center tw-justify-center"
          >
            <div
              class="tw-basis-4/5 tw-overflow-hidden"
              style="object-fit:; border-top-left-radius: 20px; border-top-right-radius: 20px"
            >
              <img :src="star.picture" alt="not" style="object-fit:" />
            </div>
            <div class="tw-flex tw-text-center tw-items-center tw-basis-1/5">
              <span class="tw-text-xl tw-font-bold long-text">{{ truncateText(star.username, 6) }}</span>
            </div>
          </RouterLink>
        </div>
      </div>
    </div>
  </main>
  <RouterView />
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { RouterLink, RouterView } from 'vue-router'
import { useUserStore } from '@/stores/user'
import axios from 'axios'

import type { HotStarInfo } from '@/common/types/index'
import type { StarByCategory } from '@/common/types/index'

const store = useUserStore()
// 슬라이드
const sliderTrack = ref<HTMLDivElement | null>(null)

const truncateText = (text: string, maxLength: number) => {
  if (text.length > maxLength) {
    return text.substring(0, maxLength) + '...'
  } else {
    return text
  }
}

const moveSlider = () => {
  setInterval(() => {
    if (sliderTrack.value) {
      const firstStar = sliderTrack.value.children[0] as HTMLElement
      sliderTrack.value.appendChild(firstStar.cloneNode(true))
      sliderTrack.value.removeChild(firstStar)
    }
  }, 3000)
}

// 카테고리 디스플레이
const StarsByCategory = ref<StarByCategory[]>([])

const loadInitialData = () => {
  getStarsByCategory(0)
}

const selectCategory = (category: number): void => {
  getStarsByCategory(category)
}

const hotStarsInfo = ref<HotStarInfo[]>([])

//최근 인기상승 핫스타 가져오기
const getHotStars = async () => {
  const response = await axios.get(`${store.API_URL}/follow/hotStars`)
  hotStarsInfo.value = response.data.data
  if (hotStarsInfo.value.length === 0)
    //만약 핫스타가 없으면 구독자수가 많은 스타를 가져온다
    getTop10Stars()
  console.log(response)
}

getHotStars()

//상위 10명의 스타 가져오기
const getTop10Stars = async () => {
  const response = await axios.get(`${store.API_URL}/star/Top10Stars`)
  hotStarsInfo.value = response.data.data
  console.log(response)
}

const getStarsByCategory = async (category: number) => {
  const response = await axios.get(`${store.API_URL}/star/${category}`)
  StarsByCategory.value = response.data.data
  console.log(response)
}

onMounted(() => {
  loadInitialData()
  moveSlider()
})
</script>

<style scoped>
.long-text {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.main-background {
  position: absolute;
  left: 0;
  right: 0;
  background-color: #eef1ff;
}

.full-width-fixed {
  position: absolute;
  left: 0;
  right: 0;
  height: 300px;
}

.container-img {
  position: absolute;
  top: 219px;
  left: 0;
  right: 0;
  height: 240px; /* 필요한 높이 값 */
  z-index: 1000; /* 필요한 경우 z-index 값 설정 */
}

.container-banner {
  overflow: hidden;
  background: midnightblue;
}
.star-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 20px;
  margin: 10px;
  transition: transform 0.3s ease;
  background: white;
  height: 240px;
  width: 200px;
}

.star-card-hotstar {
  display: flex;
  margin: 10px;
  transition: transform 0.3s ease;
  background: white;
  border-radius: 100px;
  padding: 2px;
  height: 100px;
  width: auto;
}

.star-card:hover {
  transform: scale(1.1);
  box-shadow: none;
}

.star-image {
  width: 100px;
  height: 100px;
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
  background: white;

  border-radius: 30px;
}

.category-btn:hover {
  background-color: #ede7e7;
}

.slider-container {
  overflow: hidden;
  width: 100%;
  height: 180px;
  background-color: #eef1ff;
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
  overflow: hidden;
  margin-bottom: 5px;
}

.hotpic img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.hotstar-nickName {
  width: 100%;
  height: 100%;
}

@keyframes star-fall {
  0% {
    transform: translate(80vw, -10vh);
  }
  100% {
    transform: translate(30vw, 40vh);
  }
}

@keyframes star-fall2 {
  0% {
    transform: translate(70vw, -20vh);
  }
  100% {
    transform: translate(20vw, 30vh);
  }
}

@keyframes star-fall3 {
  0% {
    transform: translate(50vw, -20vh);
  }
  100% {
    transform: translate(15vw, 30vh);
  }
}

@keyframes star-fall4 {
  0% {
    transform: translate(40vw, -20vh);
  }
  100% {
    transform: translate(0vw, 30vh);
  }
}

@keyframes star-fall5 {
  0% {
    transform: translate(30vw, -20vh);
  }
  100% {
    transform: translate(-30vw, 30vh);
  }
}

.star1 {
  position: absolute;
  top: 0px;
  animation: star-fall 2.2s linear infinite;
}

.star2 {
  position: absolute;
  top: -10px;
  animation: star-fall2 2.5s linear infinite;
}

.star3 {
  position: absolute;
  top: -3px;
  animation: star-fall3 1.3s linear infinite;
}

.star4 {
  position: absolute;
  top: -8px;
  animation: star-fall4 2.5s linear infinite;
}

.star5 {
  position: absolute;
  top: -8px;
  animation: star-fall5 1.7s linear infinite;
}
</style>
