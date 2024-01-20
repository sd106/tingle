<template>
  <!-- Nav바 -->
  <nav class="navbar bg-body-white">
    <div class="container-fluid">

      <!-- 사이드바 여는 버튼 -->
      <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar"
        aria-controls="offcanvasNavbar" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <RouterLink to="/">
        <img class="" src="/image/nav_logo.webp" alt="" style="height: 50px;">
      </RouterLink>
      <!-- 검색창 -->
      <form class="d-flex">
        <input class="form-control me-2" type="search" placeholder="검색어를 입력하세요" aria-label="Search">
        <button class="btn btn-outline-success" type="submit">Search</button>
      </form>
      <!-- 로그인 유무로 보이기 -->
      <RouterLink to="/signUp">Signup</RouterLink> |
      <RouterLink to="/logIn">Login</RouterLink> |
<<<<<<< HEAD
      <span @click="logOut">Logout</span>
      <!-- 사용자 프로필 사진 > 모달로 메뉴 -->
      <img class="" src="/image/nav_logo.webp" alt="" style="height: 50px;">
=======
      <button @click="logOut">Logout</button>
>>>>>>> bc66e294088c69f5dd6d604705f19afbf054cf29

      <!-- 사이드바 -->
      <div class="offcanvas offcanvas-start show" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1"
        id="offcanvasNavbar" aria-labelledby="offcanvasNavbarLabel" style="width: 240px; border-right: none;">
        <div class="offcanvas-header p-2">
          <!-- 닫는 버튼 -->
          <button type="button" class="navbar-toggler" data-bs-dismiss="offcanvas" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <RouterLink to="/">
            <img class="" src="/image/nav_logo.webp" alt="" style="height: 50px;">
          </RouterLink>
        </div>
        <div class="offcanvas-body">
          <ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
            <!-- 추가 기능 -->
            <hr>
            <span>구독</span>
            <!-- n명까지만 표기하고 나머지 더보기 버튼 -->
            <li calss="nav-item" v-for="star in store.starinfo" :key="star.name">
              <RouterLink :to="`/profile/${star.username}/home`" class="text-decoration-none text-dark">
                <div class="star-card">
                  <div class="star-image">
                    <img :src="star.image" alt="not">
                  </div>
                  {{ star.name }}
                </div>
              </RouterLink>
            </li>
            <hr>
            <button class="" type="button" style="height: 50px; width: 50px;">
              <img src="/image/chat.jpg" alt="" style="height: 30px; width: 30px;">
            </button>

          </ul>
        </div>
      </div>
    </div>
  </nav>


  <RouterView />
</template>

<script setup lang="ts">
import { RouterLink, RouterView, useRouter } from 'vue-router'
import { useUserStore } from './stores/user';


const router = useRouter()
const store = useUserStore()

<<<<<<< HEAD
const logOut = function (): void {
  router.push({ name: 'home' })
=======

function logOut(): void {
  store.logOut()
>>>>>>> bc66e294088c69f5dd6d604705f19afbf054cf29
}


</script>



<style>
.star-card {
  display: flex;
  align-items: center;
  margin: 1px;
  border-radius: 10px;
  /* background-color: blueviolet; */
}

.star-card:hover {
  background-color: rgb(230, 230, 230);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0.3);
}

.star-image {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  overflow: hidden;
  margin: 5px;
}

.star-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.offcanvas {
  transition: none;
  /* 애니메이션을 없애는 스타일 */
}
</style>
