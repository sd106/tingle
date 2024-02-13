<template>
    <div class="modal" tabindex="-1" data-bs-backdrop="static" id="chatModal">
      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content">
          <!-- ChatMain 컴포넌트 -->
          <ChatMain v-if="status==='chatmain'" :chatRoomInfo="chatRoomInfo"></ChatMain>
          
          <!-- ChatRoom 컴포넌트 -->
          <ChatRoom v-if="status==='chatroom'" :selectedStarInfo="selectedStarInfo"></ChatRoom>
        </div>
      </div>
    </div>
  </template>
  <!-- emits('selectBack', room); -->
  <script setup lang="ts">
  import { ref, computed, onMounted } from 'vue';
import { useUserStore } from '@/stores/user';
import type { ChatMessageInfo, ChatRoomInfo } from '@/common/types/index'
import axios from "axios";
import ChatAddStar from './ChatAddStar.vue';
import { defineProps, defineEmits } from 'vue';
  import ChatMain from '@/components/ChatMain.vue';
  import ChatRoom from '@/components/ChatRoom.vue';
  
  const store = useUserStore()

  const myid = 1; //store.fanState!.id;

  // 현재 모달의 상태
  const status = ref<string>('chatmain');

  // 채팅방 정보 (ChatMessageInfo 리스트를 포함)
const chatRoomInfo = ref<ChatRoomInfo[]>([]);
const selectedStarInfo = ref<ChatRoomInfo>();

//   // ChatMain에서 채팅방을 선택하면 호출되는 함수
//   const openChatRoom = (room: ChatRoomInfo) => {
//     selectedStarInfo.value = room;
//     status.value = 'chatroom'; // ChatRoom으로 진입 상태 변경
//   };

//   // ChatMain에서 채팅방을 선택하면 호출되는 함수
//   const backChatMain = () => {
//     status.value = 'chatmain'; // ChatMain으로 진입 상태 변경
//   };

// 데이터 가져오기 함수
const getChatRooms = async (): Promise<void> => {
    try {
    const res = await axios.get(`${store.API_URL}/chatRoom/readRooms/${myid}`);
    chatRoomInfo.value = res.data.data;

    console.log(`${res.data.message}: ${res.data.code}`);

    } catch (error) {
    console.error(`Error fetching getChatRooms: `, error);
    }
};

  // 시작될 때 초기화 및 데이터 가져오기
onMounted(() => {
  getChatRooms();
});

</script>

<style scoped>
</style>