<template>
  <!-- 네비바 -->
  <div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <div class="container-fluid">
        <a v-if="chatRoomInfo" class="navbar-brand" href="#">Chat {{ chatRoomInfo.length }}/5</a>
        <a v-else class="navbar-brand" href="#">Chat 0/5</a>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">X</button>
      </div>
    </nav>
  </div>

  <!-- 배너 -->
  <div class="card bg-dark text-white">
    <img src="" class="card-img" alt="" height="100">
    <div class="card-img-overlay">
      <h5 class="card-title">대충 광고</h5>
    </div>
  </div>

  <!-- 채팅 목록 -->
  <div class="chat-list-items scrollable-container">
    <div class="scrollable-content">
      <div v-if="!chatRoomInfo">
        채팅할 스타를 하단의 버튼으로 추가해 보세요.
      </div>
      <div v-else>
        <button v-for="room in chatRoomInfo" :key="room.id" @click="selectRoom(room)">
          <div class="card mt-3 mb-3" style="max-width: 540px;">
            <div class="row g-0">
              <div class="col-md-4">
                <img src="/image/yoo.png" class="img-fluid rounded-start" alt="">
              </div>
              <div class="col-md-8">
                <div class="card-body">
                  <h5 class="card-title">{{ room.starId }}</h5>
                  <!--<p class="card-text">{{ room.chatMessages[room.chatMessages.length - 1].message }}</p>
                  <p class="card-text"><small class="text-muted">{{
                    room.chatMessages[room.chatMessages.length - 1].createDate }} </small></p>
                    -->
                </div>
              </div>
            </div>
          </div>
        </button>
      </div>
    </div>
  </div>

  <!-- 채팅 관련 버튼 -->
  <div>
    <button type="button" class="btn btn-primary" @click="openModal">Add</button>
    <button type="button" class="btn btn-primary m-3">Delete</button>
  </div>

  <!-- 중첩 모달 => 스타 추가 페이지 -->
  <div v-if="isModalOpen" class="black-bg">
    <div class="white-bg">
      <ChatAddStar></ChatAddStar>
      <!-- 중첩 모달 닫기 버튼 -->
      <button type="button" class="btn-close " data-bs-dismiss="modal" aria-label="Close" @click="closeModal">X</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { useUserStore } from '@/stores/user';
import type { ChatMessageInfo, ChatRoomInfo } from '@/common/types/index'
import axios from "axios";
import ChatAddStar from './ChatAddStar.vue';
import { defineProps, defineEmits } from 'vue';

// NotStatus, ProgressStatus, DoneStatus
const store = useUserStore()

const myid = 1; //store.fanState!.id;

const chatRoomInfo = ref<ChatRoomInfo[]>([]);

const emits = defineEmits(['update:selectedRoom']);

// 채팅방 조회 (자식 컴포넌트에서 ref 값을 변경)
const getChatRooms = async (): Promise<void> => {
    try {
    const res = await axios.get(`${store.API_URL}/chatRoom/readRooms/${myid}`);
    chatRoomInfo.value = res.data.data;

    console.log(`${res.data.message}: ${res.data.code}`);

    } catch (error) {
    console.error(`Error fetching getChatRooms: `, error);
    }
};

// 데이터 변경
const selectRoom = (room: ChatRoomInfo) => {
  emits('update:selectedRoom', room);
}

// 모달 열기 
const isModalOpen = ref(false);

// 중첩 모달을 열기
const openModal = () => {
  isModalOpen.value = true;
};

// 중첩 모달을 닫기
const closeModal = () => {
  isModalOpen.value = false;

  // 상태 invalidate
  getChatRooms();
};

onMounted(() => {
  getChatRooms();
});

</script>

<style scoped>
.scrollable-container {
  /* width: 300px; /* 네모의 너비 */
  height: 800px;
  /* 네모의 높이 */
  overflow-y: auto;
  /* 세로 스크롤이 필요할 때만 표시 */
  border: 1px solid #ccc;
  /* 테두리 스타일 */
}

.scrollable-content {
  padding: 10px;
  /* 스크롤 영역의 여백 */
}

.scrollable-content p {
  margin: 0;
  /* 단락 사이의 여백 제거 */
}

.black-bg {
  display: flex;
  align-items: center;
  width: 500px;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.432);
  position: fixed;
  padding: 20px;
}

.white-bg {
  width: 100%;
  background-color: white;
  padding: 20px;
  border-radius: 8px;
}

.modal-exit-btn {
  margin-top: 30px;
}

.modal-exit-btn:hover {
  cursor: pointer;
}</style>