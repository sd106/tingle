<template>
    <!-- Nav바 -->
    <nav class="navbar fixed-top custom-navbar">
        <div class="container-fluid">
            <!-- 사이드바 여는 버튼 -->
            <button class="menu-btn" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar"
                aria-controls="offcanvasNavbar" aria-label="Toggle navigation"
                @click="store.isSidebarOpen = !store.isSidebarOpen">
                <img src="/image/ham.png" alt="">
            </button>
            <RouterLink to="/">
                <img class="" src="/image/nav_logo.webp" alt="" style="height: 50px;">
            </RouterLink>
            <!-- 검색창 -->
            <form class="search-box" action="" method="get">
                <input class="search-txt" type="search" placeholder="검색어를 입력하세요" aria-label="Search">
                <button class="search-btn" type="submit">
                    <img src="/image/magnifier.png" alt="">
                </button>
            </form>
            <!-- 로그인 유무로 보이기 -->
            <!-- <RouterLink v-if="!store.isLogin" to="/signUp" class="router-link-custom">Signup</RouterLink>
            <RouterLink v-if="!store.isLogin" to="/logIn" class="router-link-custom">Login</RouterLink>
            <RouterLink v-if="store.isLogin" :to="`/${store.starInfo?.starId}/home`" class="router-link-custom">profile
            </RouterLink> -->
            <!-- <span v-if="store.isLogin" @click="logOut" style="cursor: pointer;">Logout</span> -->
            <!-- 사용자 프로필 사진 > 모달로 메뉴 -->
            <RouterLink v-if="!store.isLogin" to="/logIn" class="router-link-custom">
                <button>Login</button>
            </RouterLink>
            <button class="tw-btn" v-if="store.isLogin" @click="logOut">로그아웃</button>
            <div v-if="store.isLogin" class="dropdown dropstart">
                <div class="dropdown-toggle user-image" href="#" role="button" data-bs-toggle="dropdown"
                    aria-expanded="false">
                    <!-- 사용자 프로필 사진으로 대체 -->
                    <img class="" src="/image/titan.webp" alt="" style="height: 50px;">
                </div>
                <ul class="dropdown-menu">
                    <!-- 간편 메뉴 넣기 -->
                    <li><a class="dropdown-item" href="#">Action</a></li>
                    <li><a class="dropdown-item" href="#">Another action</a></li>
                    <li><a class="dropdown-item" href="#">Something else here</a></li>
                </ul>
            </div>

        </div>
    </nav>
</template>

<script setup lang="ts">
import { watch } from 'vue'
import { useUserStore } from '@/stores/user';
import { RouterLink, useRouter } from 'vue-router'

const store = useUserStore()
const router = useRouter()


const logOut = function (): void {
    window.location.href = 'http://i10d106.p.ssafy.io:8080/api/logout'
    router.push({ name: 'home' })
    store.starState = null
    store.fanState = null
}

watch(() => store.isSidebarOpen, (newValue) => {
    if (newValue) {
        document.body.classList.add('is-sidebar-open');
    } else {
        document.body.classList.remove('is-sidebar-open');
    }
});


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
}

.menu-btn:hover {
    background-color: #dddddd;
}

.menu-btn img {
    width: 100%;
    height: 100%;
    object-fit: scale-down
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