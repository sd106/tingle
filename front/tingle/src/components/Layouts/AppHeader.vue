<template>
    <!-- Nav바 -->
    <nav class="navbar fixed-top custom-navbar">
        <div class="container-fluid">
            <!-- 사이드바 여는 버튼 -->
            <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar"
                aria-controls="offcanvasNavbar" aria-label="Toggle navigation"
                @click="store.isSidebarOpen = !store.isSidebarOpen">
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
            <span @click="logOut">Logout</span>
            <!-- 사용자 프로필 사진 > 모달로 메뉴 -->
            <img class="" src="/image/nav_logo.webp" alt="" style="height: 50px;">
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
    router.push({ name: 'home' })
}

watch(() => store.isSidebarOpen, (newValue) => {
    if (newValue) {
        document.body.classList.add('is-sidebar-open');
    } else {
        document.body.classList.remove('is-sidebar-open');
    }
});


</script>

<style scoped>
.custom-navbar {
    background-color: white;
}
</style>