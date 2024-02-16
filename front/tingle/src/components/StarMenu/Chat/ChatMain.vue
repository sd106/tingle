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

  <!-- 채팅 목록 -->
  <div class="chat-list-items scrollable-container">
    <div class="scrollable-content">
      <div v-if="!chatRoomInfo">
        채팅할 스타를 하단의 버튼으로 추가해 보세요.
      </div>
      <div v-else>
        <button v-for="(room, index) in chatRoomInfo" :key="index" @click="selectRoom(room)">
          <div class="card mt-3 mb-3" style="max-width: 550px;">
            <div class="row g-0">
              <div class="col-md-4">
                <img :src="chatUserInfo[index].picture" class="img-fluid rounded-start" alt="">
              </div>
              <div class="col-md-8">
                <div class="card-body">
                  <h5 class="card-title">{{ chatUserInfo[index].username }}</h5>

                  <!-- <div v-if="room.chatMessages.length != null && room.chatMessages.length >= 1">
                    <p class="card-text">{{ room.chatMessages[room.chatMessages.length - 1].message }}</p>
                    <p class="card-text"><small class="text-muted">{{ room.chatMessages[room.chatMessages.length - 1].createDate }} </small></p>
                  </div> -->
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
    <button type="button" class="btn" @click="openModal">스타 추가</button>
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
import type { ChatMessageInfo, ChatRoomInfo, SenderState } from '@/common/types/index'
import axios from "axios";
import ChatAddStar from './ChatAddStar.vue';
import { defineProps, defineEmits } from 'vue';

// NotStatus, ProgressStatus, DoneStatus
const store = useUserStore()
const myid = 1; //store.fanState!.id;

const chatRoomInfo = ref<ChatRoomInfo[]>([]);
const chatUserInfo = ref<SenderState[]>([]);

const emits = defineEmits(['update:selectedRoom']);

// 채팅방 조회 (자식 컴포넌트에서 ref 값을 변경)
const getChatRooms = async (): Promise<void> => {
    try {
    const res = await axios.get(`${store.API_URL}/chatRoom/readRooms/${myid}`);
    chatRoomInfo.value = res.data.data;
    console.log(`${res.data.message}: ${res.data.code}`);

    const res2 = await axios.get(`${store.API_URL}/chatRoom/readRooms/starInfo/${myid}`);
    chatUserInfo.value = res2.data.data;
    console.log(`${res2.data.message}: ${res2.data.code}`);

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
};

onMounted(() => {
  getChatRooms();
});

</script>

<style scoped>
/* 네비바 스타일 */
.navbar {
  background-color: #f8f9fa; /* 네비바 배경색 */
  border-bottom: 1px solid #dee2e6; /* 네비바 하단 테두리 */
}

.navbar-brand {
  font-weight: bold; /* 네비바 로고 폰트 굵기 */
  color: #007bff; /* 로고 색상 */
}

/* 채팅 목록 스타일 */
.chat-list-items {
  margin-top: 20px; /* 채팅 목록과 버튼 사이 여백 */
}

/* 스크롤 가능한 컨테이너 스타일 */
.scrollable-container {
  height: 600px; /* 스크롤 가능한 컨테이너 높이 */
  overflow-y: auto; /* 세로 스크롤만 표시 */
  border: 1px solid #ccc; /* 테두리 스타일 */
  border-radius: 5px; /* 컨테이너 테두리 둥글게 */
}

.scrollable-content {
  padding: 10px; /* 내용과 테두리 사이 여백 */
}

/* 채팅방 카드 스타일 */
.card {
  margin-bottom: 20px; /* 채팅방 카드 사이 여백 */
  border: 1px solid #dee2e6; /* 카드 테두리 스타일 */
  border-radius: 8px; /* 카드 테두리 둥글게 */
}

.card img {
  max-width: 100%; /* 이미지 최대 너비 */
  border-radius: 8px; /* 이미지 테두리 둥글게 */
}

.card-body {
  padding: 10px; /* 카드 내용과 테두리 사이 여백 */
}

/* 채팅 관련 버튼 스타일 */
.btn {
  margin-top: 20px; /* 버튼 상단 여백 */
}

/* 중첩 모달 스타일 */
.black-bg {
  display: flex;
  align-items: center;
  justify-content: center;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5); /* 배경색과 투명도 조절 */
}

.white-bg {
  background-color: #fff; /* 모달 배경색 */
  padding: 20px; /* 내부 여백 */
  border-radius: 8px; /* 모달 테두리 둥글게 */
}

.modal-exit-btn {
  margin-top: 20px; /* 닫기 버튼 상단 여백 */
}

.modal-exit-btn:hover {
  cursor: pointer; /* 호버 시 커서 변경 */
}

</style>