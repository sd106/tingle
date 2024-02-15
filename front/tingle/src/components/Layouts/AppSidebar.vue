<template>
  <!--사이드바 -->
  <div
    v-if="store.isSidebarOpen"
    class="offcanvas offcanvas-start show"
    data-bs-scroll="true"
    data-bs-backdrop="false"
    tabindex="-1"
    id="offcanvasNavbar"
    aria-labelledby="offcanvasNavbarLabel"
    style="width: 240px; border-right: none;"
  >
    <div class="offcanvas-header p-2">
      <!-- 닫는 버튼 -->
      <button
        type="button"
        class="menu-btn"
        data-bs-dismiss="offcanvas"
        aria-label="Toggle navigation"
        @click="store.isSidebarOpen = !store.isSidebarOpen"
      >
        <img src="/image/ham.png" alt="" />
      </button>
      <RouterLink to="/">
        <img v-if="isStar" class="logo" src="/image/star-logo.gif" alt="" style="height: 60px" />
        <img v-else class="logo" src="/image/boat-logo.gif" alt="" style="height: 60px" />
      </RouterLink>
    </div>
    <div class="offcanvas-body">
      <ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
        <!-- 추가 기능 -->
        <hr />
        <!-- n명까지만 표기하고 나머지 더보기 버튼 -->
        <li calss="nav-item" v-for="star in folloingInfo.slice(0, displayCount)" :key="star.starId">
          <RouterLink :to="`/${star.starId}/home`" class="text-decoration-none text-dark">
            <div class="star-card">
              <div class="star-image">
                <img :src="star.picture" alt="not" />
              </div>
              <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
              {{ star.userName }}
            </div>
            <!-- 최근 활동 있으면 점으로 표시 -->
          </RouterLink>
        </li>
        <!-- 더보기 -->
        <button
          v-if="displayCount < folloingInfo.length"
          @click="showMore"
          class="p-0"
          style="background: none; border: none; cursor: pointer"
        >
          <div class="star-card">
            <div class="arrow-image">
              <img src="/image/downarrow.png" alt="" />
            </div>
            <span>&nbsp;&nbsp;&nbsp;&nbsp;{{ folloingInfo.length - displayCount }}명 더보기</span>
          </div>
        </button>
        <!-- 간략히 보기 -->
        <button
          v-if="displayCount > 7"
          @click="showBrief"
          class="p-0"
          style="background: none; border: none; cursor: pointer"
        >
          <div class="star-card">
            <div class="arrow-image">
              <img src="/image/uparrow.png" alt="" />
            </div>
            <span>&nbsp;&nbsp;&nbsp;&nbsp;간략히 보기</span>
          </div>
        </button>
        <hr />
        <button
          class="menu-btn"
          type="button"
          data-bs-toggle="modal"
          data-bs-target="#chatModal"
          style="height: 50px; width: 50px"
        >
          <img src="/image/chat.jpg" alt="" style="height: 30px; width: 30px" />
        </button>
      </ul>
    </div>
  </div>
  <ChatModal />
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { RouterLink } from 'vue-router'
import { useUserStore } from '@/stores/user'
import axios from 'axios'
import ChatModal from '@/components/StarMenu/Chat/ChatModal.vue'

const store = useUserStore()
const isStar = store.isStar
// 사이드바 더보기
const displayCount = ref(7)

const showMore = function (): void {
  displayCount.value = folloingInfo.value.length
}

const showBrief = function (): void {
  displayCount.value = 7
}

const folloingInfo = ref<{ starId: number; picture: string; userName: string }[]>([])
// const hotStarInfo= ref<{id: number, picture: string, userName: string}[]>([]);

//구독한 스타 가져오기
const getFolloings = async () => {
  const response = await axios.get(`http://localhost:8080/follow/${store.fanState?.id}`)
  folloingInfo.value = response.data.data
  console.log(response)
}

getFolloings()

onMounted(() => {
  store.isSidebarOpen = false
})
</script>

<style>
.logo {
  position: fixed;
  top: 10px;
  left: 140px;
  right: 0;
}
</style>
