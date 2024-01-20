<template>
  <!-- 사이드바 -->
  <nav class="navbar bg-body-white">
    <div class="container-fluid">

      <!-- 여는 버튼 -->
      <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar"
        aria-controls="offcanvasNavbar" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <img class="" src="/image/nav_logo.webp" alt="" style="height: 50px;">
      <!-- 검색창 -->
      <form class="d-flex">
        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-success" type="submit">Search</button>
      </form>
      <RouterLink to="/">Home</RouterLink> |
      <RouterLink to="/signUp">Signup</RouterLink> |
      <RouterLink to="/logIn">Login</RouterLink> |
      <button @click="logOut">Logout</button>


      <div class="offcanvas offcanvas-start w-25" tabindex="-1" id="offcanvasNavbar"
        aria-labelledby="offcanvasNavbarLabel">
        <div class="offcanvas-header p-2">
          <!-- 닫는 버튼 -->
          <button type="button" class="navbar-toggler" data-bs-dismiss="offcanvas" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <img class="" src="/image/nav_logo.webp" alt="" style="height: 50px;">
        </div>
        <div class="offcanvas-body">
          <ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
            <!-- <li class="nav-item">
              <a class="nav-link active" aria-current="page" href="#">Home</a>
            </li> -->
            <li calss="nav-item" v-for="star in store.starinfo" :key="star.name">
              <img :src="star.image" alt="not">
              {{ star.name }}
            </li>
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


function logOut(): void {
  store.logOut()
}
</script>

<!-- <script lang="ts">
export default {
  data() {
    return {
      isOffcanvasOpen: true, // 페이지 로드 시 Offcanvas를 열도록 기본값 설정
    };
  },
  methods: {
    toggleOffcanvas() {
      // Offcanvas 열기/닫기 토글
      this.isOffcanvasOpen = !this.isOffcanvasOpen;
    },
    closeOffcanvas() {
      // Offcanvas 닫기
      this.isOffcanvasOpen = false;
    },
  },
};
</script> -->



<style scoped>
/* 
header {
  line-height: 1.5;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}
*/
</style>
