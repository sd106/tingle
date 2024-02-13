<template>
    <div class="modal" tabindex="-1" data-bs-backdrop="static" id="chatModal">
      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content">
          <!-- ChatMain 컴포넌트 -->
          <ChatMain v-if="status==='chatmain'" @update:selectedRoom="receiveRoom($event)"></ChatMain>
          
          <!-- ChatRoom 컴포넌트 -->
          <ChatRoom v-if="status==='chatroom'" :selectedRoom="selectedRoom" @update:selectedBack="receiveBack"></ChatRoom>
        </div>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue';
import type { ChatRoomInfo } from '@/common/types/index'
import ChatMain from './ChatMain.vue';
import ChatRoom from './ChatRoom.vue';  

  // 현재 모달의 상태
  const status = ref<string>('chatmain');

  // 채팅방 정보 (ChatMessageInfo 리스트를 포함)
  const selectedRoom = ref<ChatRoomInfo>({
  id: 0,
  starId: 0,
  chatMessages: []
});

const receiveRoom = (newRoom: ChatRoomInfo) => {
	selectedRoom.value.id = newRoom.id;
	selectedRoom.value.starId = newRoom.starId;
	selectedRoom.value.chatMessages = newRoom.chatMessages;

  status.value = 'chatroom';
}

const receiveBack = () => {
  status.value = 'chatmain';
}

</script>

<style scoped>
</style>