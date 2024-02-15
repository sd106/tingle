<template>
    <div class="modal" tabindex="-1" data-bs-backdrop="static" id="chatModal">
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
            <div class="modal-content">
                
                <!-- 네비바 -->
                <div>
                <nav class="navbar navbar-expand-lg navbar-light bg-light">
                    <div class="container-fluid">
                        <a class="navbar-brand" href="#">Chat ({{chatRoomInfo.length}}/5)</a>
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
                        <div v-if="chatRoomInfo.length == 0">
                            채팅할 스타를 하단의 버튼으로 추가해 보세요.
                        </div>
                        <div v-else>
                            <div v-for="room in chatRoomInfo" :key="room.id" :roomInfo="chatRoomInfo">
                                <div class="card mt-3 mb-3" style="max-width: 540px;">
                                    <div class="row g-0">
                                        <div class="col-md-4">
                                            <img src="/image/yoo.png" class="img-fluid rounded-start" alt="">
                                        </div>
                                        <div class="col-md-8">
                                            <div class="card-body">
                                                <h5 class="card-title">{{ room.starId }}</h5>
                                                <p class="card-text">{{ room.chatMessages[room.chatMessages.length-1].message }}</p>
                                                <p class="card-text"><small class="text-muted">{{ room.chatMessages[room.chatMessages.length-1].createDate }} </small></p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 채팅 관련 버튼 -->
                <div>
                <button type="button" class="btn btn-primary" @click="openModal">Add</button>
                    <div>
                        <!-- 중첩 모달 => 스타 추가 페이지 -->
                        <div v-if="isModalOpen">
                            <ChatAddStar></ChatAddStar>

                            <!-- 중첩 모달 닫기 버튼 -->
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" @click="closeModal">X</button>
                        </div>
                    </div>
                <button type="button" class="btn btn-primary m-3">Delete</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { useUserStore } from '@/stores/user';
  import type { ChatMessageInfo, ChatRoomInfo } from '@/common/types/index'
import axios from "axios";
import ChatAddStar from './ChatAddStar.vue';

//const myid = store.fanState!.id;
// NotStatus, ProgressStatus, DoneStatus
const store = useUserStore()

// 모달 열기 
const isModalOpen = ref(false);

// 채팅방 리스트 (ChatMessageInfo 리스트를 포함)
const chatRoomInfo = ref<ChatRoomInfo[]>([]);

// 데이터 가져오기 함수
const fetchData = async (): Promise<void> => {

try {
  const res = await axios.get(`${store.API_URL}/chat/read/rooms/${store.fanState!.id}`);

  chatRoomInfo.value = res.data.data;

  } catch (error) {
    console.error(`Error fetching chatRoom: `, error);
  }
};

// 중첩 모달을 열기
const openModal = () => {
  isModalOpen.value = true;
};

// 중첩 모달을 닫기
const closeModal = () => {
  isModalOpen.value = false;

  // 상태 invalidate
  fetchData();
};

  // 시작될 때 초기화 및 데이터 가져오기
  onMounted(() => {
    fetchData();
  });
  
  </script>

<style scoped>
</style>