<template>
    <!-- 사이드바 -->
    <div class="offcanvas offcanvas-start show" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1"
        id="offcanvasNavbar" aria-labelledby="offcanvasNavbarLabel" style="width: 240px; border-right: none;">
        <div class="offcanvas-header p-2">
            <!-- 닫는 버튼 -->
            <button type="button" class="navbar-toggler" data-bs-dismiss="offcanvas" aria-label="Toggle navigation"
                @click="store.isSidebarOpen = !store.isSidebarOpen">
                <span class="navbar-toggler-icon">??</span>
            </button>
            <RouterLink to="/">
                <img class="" src="/image/nav_logo.webp" alt="" style="height: 50px;">
            </RouterLink>
        </div>
        <div class="offcanvas-body">
            <!-- :class="{ 'with-sidebar': isSidebarOpen }"> -->
            <ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
                <!-- 추가 기능 -->
                <hr>
                <span>구독</span>
                <!-- n명까지만 표기하고 나머지 더보기 버튼 -->
                <li calss="nav-item" v-for="star in visibleStarInfo" :key="star.name">
                    <RouterLink :to="`/profile/${star.username}/home`" class="text-decoration-none text-dark">
                        <div class="star-card">
                            <div class="star-image">
                                <img :src="star.image" alt="not">
                            </div>
                            {{ star.name }}
                        </div>
                    </RouterLink>
                </li>
                <!-- 더보기 -->
                <button v-if="displayCount < store.hotstarinfo.length" @click="showMore" class="p-0"
                    style="background: none; border: none; cursor: pointer;">
                    <div class="star-card">
                        <div class="star-image">
                            <img src="/image/downarrow.png" alt="">
                        </div>
                        <span>더보기</span>
                    </div>
                </button>
                <!-- 간략히 보기 -->
                <button v-if="displayCount > 5" @click="showBrief" class="p-0"
                    style="background: none; border: none; cursor: pointer;">
                    <div class="star-card">
                        <div class="star-image">
                            <img src="/image/uparrow.png" alt="">
                        </div>
                        <span>간략히 보기</span>
                    </div>
                </button>
                <hr>
                <button class="" type="button" data-bs-toggle="modal" data-bs-target="#chatModal"
                    style="height: 50px; width: 50px;">
                    <img src="/image/chat.jpg" alt="" style="height: 30px; width: 30px;">
                </button>
            </ul>
        </div>
    </div>
    <ChatModal />
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import { RouterLink } from 'vue-router'
import { useUserStore } from '@/stores/user';

import ChatModal from '../ChatModal.vue';

const store = useUserStore()

// onMounted(() => {
//     console.log(store.isModalOpen)
// })


// const openModal = function (): void {
//     store.isModalOpen = true;
//     console.log(store.isModalOpen)
// };


// 사이드바 밀어내기
watch(() => store.isSidebarOpen, (newValue) => {
    if (newValue) {
        document.body.classList.add('is-sidebar-open');
        console.log('on')
    } else {
        document.body.classList.remove('is-sidebar-open');
        console.log('off')
    }
});


// 사이드바 더보기
const displayCount = ref(5);
const visibleStarInfo = computed(() => store.hotstarinfo.slice(0, displayCount.value));

const showMore = function (): void {
    displayCount.value = store.hotstarinfo.length;
}

const showBrief = function (): void {
    displayCount.value = 5;
}


</script>

<style></style>