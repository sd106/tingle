<template>
  <!-- Nav바 -->
  <nav class="navbar fixed-top custom-navbar">
    <div class="container-fluid">
      <!-- 사이드바 여는 버튼 -->
      <button
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
        <img class="" src="/image/nav_logo.webp" alt="" style="height: 50px" />
      </RouterLink>
      <!-- 검색창 -->
      <form class="search-box" action="" method="get">
        <input
          class="search-txt"
          type="search"
          placeholder="검색어를 입력하세요"
          aria-label="Search"
        />
        <button class="search-btn" type="submit">
          <img src="/image/magnifier.png" alt="" />
        </button>
      </form>
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
import { watch } from 'vue'
import { useUserStore } from '@/stores/user'
import { RouterLink, useRouter } from 'vue-router'

const store = useUserStore()
const router = useRouter()

const logOut = function (): void {
  window.location.href = 'http://localhost:8080/logout'
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
    window.location.href = `http://localhost:8080/oauth2/authorization/${provider}`
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
.custom-navbar {
  background-color: white;
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
  height: 36px;
  width: 300px;
  background: white;
  border-radius: 40px;
  padding: 0px;
  border: 1px solid #f9deec;
  display: flex;
}

.search-txt {
  border: none;
  background: none;
  outline: none;
  float: left;
  padding: 10px;
  padding-left: 20px;
  font-size: 14px;
  line-height: 30px;
  width: 200px;
  flex: 1;
}

.search-btn {
  color: #f9deec;
  float: rignt;
  width: 30px;
  height: 100%;
  border-radius: 50%;
  background: white;
  border: none;
  font-size: 16px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 10px;
}

.search-btn:hover {
  background-color: #dddddd;
  border-radius: 100%;
}

.search-btn img {
  height: 20px;
  width: 20px;
  object-fit: contain;
}

</style>
