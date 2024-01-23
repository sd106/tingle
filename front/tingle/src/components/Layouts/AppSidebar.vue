<template>
    <!-- 사이드바 -->
    <div class="offcanvas offcanvas-start show" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1"
        id="offcanvasNavbar" aria-labelledby="offcanvasNavbarLabel" style="width: 240px; border-right: none;">
        <div class="offcanvas-header p-2">
            <!-- 닫는 버튼 -->
            <button type="button" class="menu-btn" data-bs-dismiss="offcanvas" aria-label="Toggle navigation"
                @click="store.isSidebarOpen = !store.isSidebarOpen">
                <img src="/image/ham.png" alt="">
            </button>
            <RouterLink to="/">
                <img class="" src="/image/nav_logo.webp" alt="" style="height: 50px;">
            </RouterLink>
        </div>
        <div class="offcanvas-body">
            <ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
                <!-- 추가 기능 -->
                <hr>
                <span>&nbsp;구독</span>
                <!-- n명까지만 표기하고 나머지 더보기 버튼 -->
                <li calss="nav-item" v-for="star in visibleStarInfo" :key="star.name">
                    <RouterLink :to="`/profile/${star.username}/home`" class="text-decoration-none text-dark">
                        <div class="star-card">
                            <div class="star-image">
                                <img :src="star.image" alt="not">
                            </div>
                            <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                            {{ star.name }}
                        </div>
                        <!-- 최근 활동 있으면 점으로 표시 -->
                    </RouterLink>
                </li>
                <!-- 더보기 -->
                <button v-if="displayCount < store.hotstarinfo.length" @click="showMore" class="p-0"
                    style="background: none; border: none; cursor: pointer;">
                    <div class="star-card">
                        <div class="arrow-image">
                            <img src="/image/downarrow.png" alt="">
                        </div>
                        <span>&nbsp;&nbsp;&nbsp;&nbsp;{{ store.hotstarinfo.length - 5 }}명 더보기</span>
                    </div>
                </button>
                <!-- 간략히 보기 -->
                <button v-if="displayCount > 5" @click="showBrief" class="p-0"
                    style="background: none; border: none; cursor: pointer;">
                    <div class="star-card">
                        <div class="arrow-image">
                            <img src="/image/uparrow.png" alt="">
                        </div>
                        <span>&nbsp;&nbsp;&nbsp;&nbsp;간략히 보기</span>
                    </div>
                </button>
                <hr>
                <button class="menu-btn" type="button" data-bs-toggle="modal" data-bs-target="#chatModal"
                    style="height: 50px; width: 50px;">
                    <img src="/image/chat.jpg" alt="" style="height: 30px; width: 30px;">
                </button>
            </ul>
        </div>
    </div>
    <ChatModal />
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { RouterLink } from 'vue-router'
import { useUserStore } from '@/stores/user';

import ChatModal from '../ChatModal.vue';

const store = useUserStore()


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