<template>
  <!-- Nav바 -->
  <nav class="navbar fixed-top p-0">
    <div
      :class="{ 'user-background': !isStar, 'star-background': isStar }"
      class="container-fluid"
      style="height: 80px"
    >
      <!-- 사이드바 여는 버튼 -->
      <button
        v-if="store.isLogin"
        class="menu-btn"
        type="button"
        data-bs-toggle="offcanvas"
        data-bs-target="#offcanvasNavbar"
        aria-controls="offcanvasNavbar"
        aria-label="Toggle navigation"
        @click="store.isSidebarOpen = !store.isSidebarOpen"
      >
        <img src="/image/ham.png" alt="" />
      </button>
      <RouterLink to="/">
        <img v-if="isStar" class="logo" src="/image/star-logo.gif" alt="" style="height: 60px" />
        <img
          v-if="store.isLogin && !isStar"
          class="logo"
          src="/image/boat-logo.gif"
          alt=""
          style="height: 60px"
        />
        <h1 class="logo-title" style="margin-left: 10%" v-if="!store.isLogin">TINGLE</h1>
      </RouterLink>
      <!-- 검색창 -->
      <div class="search-box" v-if="!isStar">
        <input
          class="search-txt"
          placeholder="검색어를 입력하세요"
          v-model="searchQuery"
          @input="fetchStars"
          @keyup.enter="goStarHome"
        />
        <ul class="ul-search">
          <li
            class="li-search"
            v-for="(star, index) in stars"
            :key="star.id"
            :class="{ selected: index === selectedStarIndex }"
            @click="selectStar(star)"
          >
            {{ star.name }}
          </li>
        </ul>
        <button class="search-btn">
          <img src="/image/magnifier.png" alt="" />
        </button>
      </div>

      <div v-if="!store.isLogin" class="d-flex">
        <img @click="redirectToGoogle" class="menu-btn mx-1" src="/image/Logo_Google.png" alt="" />
        <img @click="redirectToNaver" class="menu-btn mx-1" src="/image/Logo_Naver.png" alt="" />
      </div>

      <div v-if="store.isLogin" class="dropdown dropstart">
        <div
          class="dropdown-toggle user-image"
          href="#"
          role="button"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          <img
            v-if="store.isStar"
            class=""
            :src="`${store.starState?.picture}`"
            alt="starimage"
            style="height: 50px"
          />
          <img
            v-if="!store.isStar"
            class=""
            :src="`${store.fanState?.picture}`"
            alt="userimage"
            style="height: 50px"
          />
        </div>
        <ul class="dropdown-menu">
          <li>
            <RouterLink :to="`/profile/userinfo`" class="dropdown-item">회원 정보</RouterLink>
          </li>
          <li>
            <RouterLink :to="`/profile/storage`" class="dropdown-item">보관함</RouterLink>
          </li>
          <li>
            <RouterLink :to="`/profile/orders`" class="dropdown-item">주문 목록</RouterLink>
          </li>
          <li><a class="dropdown-item" href="#" @click="logOut">로그아웃</a></li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script setup lang="ts">
import { watch, ref, onMounted, onUnmounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { RouterLink, useRouter, useRoute } from 'vue-router'
import axios from 'axios'

const store = useUserStore()
const router = useRouter()
const route = useRoute()
const isStar = store.isStar
//=============search================
const searchQuery = ref<String>('')
const stars = ref<{ id: number; name: String }[]>([])
const selectedStarIndex = ref<number>(-1)

const fetchStars = async () => {
  if (searchQuery.value.length >= 1) {
    // 최소 2글자 이상 입력시 검색 실행
    const response = await axios.get(
      `https://i10d106.p.ssafy.io/api/star/search/${searchQuery.value}`
    )
    stars.value = response.data.data
    console.log(stars.value)
  } else {
    stars.value = []
  }
}

const goStarHome = async () => {
  if (stars.value) {
    const goStarHomeResults = stars.value.filter((star) => star.name === searchQuery.value)
    if (goStarHomeResults.length > 0) {
      const goStarHomeId = goStarHomeResults[0].id // 첫 번째 매칭 결과의 ID
      router.push(`/${goStarHomeId}/home`)
      stars.value = []
    }
  } else {
    alert('없는 사람임;')
    stars.value = []
  }
}

const handleKeyDown = (e: KeyboardEvent) => {
  switch (e.key) {
    case 'ArrowDown':
      selectedStarIndex.value = (selectedStarIndex.value + 1) % stars.value.length
      searchQuery.value = stars.value[selectedStarIndex.value].name
      break
    case 'ArrowUp':
      selectedStarIndex.value =
        (selectedStarIndex.value - 1 + stars.value.length) % stars.value.length
      searchQuery.value = stars.value[selectedStarIndex.value].name
      break
    case 'Enter':
      if (selectedStarIndex.value !== -1) {
        selectStar(stars.value[selectedStarIndex.value])
      }
      break
  }
}

const selectStar = (star: { id: number; name: String }) => {
  // 선택된 star에 대한 로직 구현, 예: 라우터로 이동
  console.log('Selected star:', star)
  router.push(`/${star.id}/home`)
  stars.value = []
  searchQuery.value = ''
}

watch(
  () => route.params,
  () => {
    // 라우트 파라미터가 변경될 때 페이지 새로고침
    window.location.reload()
  },
  {
    deep: true, // 객체 내부까지 감시하기 위한 옵션
    immediate: false // 컴포넌트가 마운트될 때는 실행하지 않음
  }
)

onMounted(() => {
  window.addEventListener('keydown', handleKeyDown)
})

onUnmounted(() => {
  window.removeEventListener('keydown', handleKeyDown)
})

//================search============//
const logOut = function (): void {
  window.location.href = 'https://i10d106.p.ssafy.io/api/logout'
  router.push({ name: 'home' })
  store.starState = null
  store.fanState = null
  store.isStar = false
}

watch(
  () => store.isSidebarOpen,
  (newValue) => {
    if (newValue) {
      document.body.classList.add('is-sidebar-open')
    } else {
      document.body.classList.remove('is-sidebar-open')
    }
  }
)

// 로그인
const redirectToOAuthProvider = async (provider: string) => {
  try {
    window.location.href = `https://i10d106.p.ssafy.io/api/oauth2/authorization/${provider}`
    store.provider = provider
  } catch (error) {
    console.log('error', error)
  }
}

const redirectToGoogle = () => {
  redirectToOAuthProvider('google')
}

const redirectToNaver = () => {
  redirectToOAuthProvider('naver')
}
</script>

<style>
.logo {
  position: fixed;
  top: 10px;
  left: 140px;
  right: 0;
}

.user-background {
  /* background-color: #DCBFFF; */
  background: linear-gradient(to bottom, #dcbfff, #f1eaff);
  /* background: linear-gradient(to bottom, rgba(220, 191, 255, 0.3), rgba(241, 234, 255, 0.2)); */
}

.star-background {
  background: linear-gradient(to bottom, #ffc7c7, #fff3e2);
}

.dropdown-toggle::before {
  display: none !important;
}

.user-image {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  overflow: hidden;
  margin-bottom: 5px;
}

.user-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.menu-btn {
  width: 54px;
  height: 54px;
  border: none;
  background: none;
  border-radius: 50px;
  cursor: pointer;
  transition: transform 0.3s ease;
}

.menu-btn:hover {
  scale: 1.1;
  box-shadow: none;
  background-color: #dddddd;
}

.menu-btn img {
  width: 100%;
  height: 100%;
  object-fit: scale-down;
}

/* 링크 꾸미기 제거 */
.router-link-custom {
  color: inherit;
  text-decoration: none;
}

.search-box {
  position: relative; /* 오버레이를 위한 기준점 */
  width: 300px; /* 검색바의 너비 */
  margin: auto; /* 중앙 정렬 */
}

.ul-search {
  position: absolute; /* 오버레이 위치 */
  width: 100%; /* 검색바 너비와 동일하게 설정 */
  top: 100%; /* 검색바 바로 아래에 위치하도록 설정 */
  left: 0; /* 컨테이너 왼쪽 정렬 */
  z-index: 1000; /* 다른 요소 위에 오도록 z-index 설정 */
  background-color: white;
  /* 기타 스타일 */
}

.search-txt {
  width: 100%;
  padding: 10px 20px;
  font-size: 16px; /* 텍스트 크기 */
  border: 1px solid #ddd; /* 테두리 설정 */
  border-radius: 20px; /* 둥근 모서리 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* 그림자 효과 */
}

.search-txt:focus {
  outline: none; /* 입력 필드 선택 시 테두리 제거 */
}

.search-btn {
  position: absolute;
  right: 15px;
  top: 23px;
  transform: translateY(-50%);
  background: none;
  border: none;
  cursor: pointer;
}

.search-btn img {
  width: 24px; /* 검색 버튼 이미지 크기 */
  height: auto;
}

.li-search {
  padding: 10px 20px;
  border-bottom: 1px solid #ddd; /* 항목 사이의 구분선 */
}

.li-search:last-child {
  border-bottom: none; /* 마지막 항목의 구분선 제거 */
}

.li-search:hover {
  background-color: #f5f5f5; /* 항목 호버 시 배경 색상 변경 */
}

.li-search.selected {
  background-color: #eee; /* 선택된 항목의 배경색 */
  /* 추가 스타일링 */
}
</style>
