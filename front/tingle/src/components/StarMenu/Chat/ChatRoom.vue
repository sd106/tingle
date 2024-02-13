<template>
  <!-- 네비바 -->
  <div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <button class="btn btn-sm btn-outline-secondary" type="button" @click="selectback()">이전</button>
      <a class="navbar-brand" href="#">{{ starInfo.name }}</a>
      <button class="btn btn-sm btn-outline-secondary" type="button">나가기</button> 
    </nav>
  </div>

  <!-- 채팅방 중간 -->
  <div v-for="msg in msgList" :key="msg.id">
      <!-- 받은 메시지 -->
      <span v-if="msg.starid == msg.senderid">
        <div class="star-card">
          <div class="star-image">
            <img src="/image/yoo.png" class="img-fluid rounded-start" alt="">
          </div>
          <div>{{ msg.contents }}</div>
          <div>{{ msg.sendTime }}</div>
        </div>
      </span>

      <!-- 보낸 메시지 -->
      <span v-else>
        <div class="star-card">
          <div class="justify-content-end">{{ msg.contents }}</div>
          <div class="justify-content-end">{{ msg.sendTime }}</div>
        </div>
      </span>
  </div>

  <!-- 채팅방 하단 -->
  <div class="input-group">
    <input type="text" class="form-control" placeholder="Recipient's username" aria-label="Recipient's username with two button addons">
    <button class="btn btn-outline-secondary" type="button">^.^</button>
    <button class="btn btn-outline-secondary" type="button">전송</button>
  </div>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router';
import { useUserStore } from '@/stores/user';
import { ref, computed, onMounted } from 'vue';
import type { ChatMessageInfo, ChatRoomInfo } from '@/common/types/index'
import axios from "axios";
import { defineProps } from 'vue';

//const myid = store.fanState!.id;
// NotStatus, ProgressStatus, DoneStatus
const store = useUserStore();

// route 를 생성한다.
const route = useRoute();

const myid = 1; //store.fanState!.id;

// ChatMain 컴포넌트로부터 전달된 스타 정보를 props로 받음
const props = defineProps<{
  selectedStarInfo: ChatRoomInfo,
  status: string;
}>();

// 자식 컴포넌트에서 사용할 ref 변수를 선언
const selectedStarInfo = ref(props.selectedStarInfo);
const status = ref(props.status);

  //stomp와 sockJS관련 임포트 혹은 html에서 스크립트를 넣어야한다
//   let socket = new SockJS('/ws');
// let stompClient = Stomp.over(socket);
// stompClient.connect({},onConnected,onError);
// function onConnected() {
//   ...
// }
// function onError() {
//   ...
// }

// 채팅 메인으로 돌아가는 함수
const selectBack = () => {
  status.value = "chatmain";
};

// 데이터 가져오기 함수
const getChatMessages = async (): Promise<void> => {

try {
  const res = await axios.get(`${store.API_URL}/chatMessage/readMessages/${selectedStarInfo.value.starId}`);
  selectedStarInfo.value = res.data.data;
  
  console.log(`${res.data.message}: ${res.data.code}`);

  } catch (error) {
    console.error(`Error fetching getChatRooms: `, error);
  }
};

 // 시작될 때 초기화 및 데이터 가져오기
 onMounted(() => {
    roomInfo.value = route.params.roomInfo;
    getChatMessages();
  });

</script>

<style>

</style>