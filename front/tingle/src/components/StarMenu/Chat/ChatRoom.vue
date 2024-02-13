<template>
  <!-- 네비바 -->
  <div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <button class="btn btn-sm btn-outline-secondary" type="button" @click="selectRoom()">이전</button>
      <a class="navbar-brand" href="#">{{ props.selectedRoom.starId }}</a>
      <button class="btn btn-sm btn-outline-secondary" type="button" @click="exitTheRoom()">나가기</button> 
    </nav>
  </div>
  <div class="chat-list-items scrollable-container">
    <div class="scrollable-content">

      <!-- 채팅방 중간 direction 1 == 스타, direction == 2 팬 -->
      <div v-for="msg in newSelectedRoom" :key="msg.id">
          <!-- 받은 메시지 -->
          <!-- 스타 x, 스타 (=1) -->
          <!-- 스타 o, 팬 (=2) -->
          <span v-if="(!store.isStar && msg.direction == 1)
                   || (store.isStar && msg.direction == 2)">
            <div class="star-card">
              <div class="star-image">
                <img src="/image/yoo.png" class="img-fluid rounded-start" alt="">
              </div>
              <div> 상대: </div>
              <div>{{ msg.message }}</div>
              <div>{{ msg.createDate }}</div>
            </div>
          </span>

          <!-- 보낸 메시지 -->
          <!-- 스타 x, 팬 (=2) -->
          <!-- 스타 o, 스타 (=1) -->
          <span v-if="(!store.isStar && msg.direction == 2)
                   || (store.isStar && msg.direction == 1)">
            <div class="star-card">
              <div> 나: </div>
              <div class="justify-content-end">{{ msg.message }}</div>
            </div>
          </span>
      </div>
    </div>
  </div>

  <!-- 채팅방 하단 -->
  <div class="input-group">
    <input type="text" class="form-control" v-model="inputValue" placeholder="Recipient's username" aria-label="Recipient's username with two button addons">
    <button class="btn btn-outline-secondary" type="button">^.^</button>
    <button class="btn btn-outline-secondary" type="button" @click="sendMessage">전송</button>
  </div>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router';
import { useUserStore } from '@/stores/user';
import { ref, computed, onMounted } from 'vue';
import type { ChatMessageInfo, ChatRoomInfo } from '@/common/types/index'
import axios from "axios";
import { defineProps } from 'vue';
import { Client } from '@stomp/stompjs';

import Stomp from 'stompjs';
// import SockJS from 'sockjs-client';

import SockJS from "sockjs-client/dist/sockjs";

// import * as Stomp from 'stompjs/lib/stomp.js';
// import SockJs from 'sockjs-client/dist/sockjs.min.js'

//import SockJS from '@/common/modules/sockjs';

//import SockJS from 'sockjs-client';

//const myid = store.fanState!.id;
// NotStatus, ProgressStatus, DoneStatus
const store = useUserStore();

// route 를 생성한다.
const route = useRoute();

const myid = 1; //store.fanState!.id;

// ChatMain 컴포넌트로부터 전달된 스타 정보를 props로 받음
const props = defineProps<{
  selectedRoom: ChatRoomInfo
}>();

const inputValue = ref<string>('');
const newSelectedRoom = ref<ChatMessageInfo[]>([]);

/////////////////////////////////////////////////
// Stomp over WebSocket 인스턴스 생성
	
// WebSocket 인스턴스 생성
// const socket = new WebSocket("localhost:8080/chat");
// const stompClient = new Stomp.over(socket);

let stompClient: Stomp.Client | null = null;

//처음 채팅방에 접속 시 SockJS 연결
function connect(): void {
    const socket = new SockJS('http://localhost:8080/chat');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected);
}

// 웹소켓 연결 시 onConnected 함수 실행
function onConnected(): void {
      stompClient?.subscribe(`/sub/room/${props.selectedRoom.id}`, (message) => {
        const msg = JSON.parse(message.body);

        newSelectedRoom.value.push(msg);
      });
    }

  // stompClient.subscribe(`/sub/room/${props.selectedRoom.id}`, {
  //     id: 0,
  //     userId: myid,
  //     direction: store.isStar ? 1 : 2,
  //     message: message,
  //     createDate: new Date(),
  //     roomId: props.selectedRoom.id
  // });
  // stompClient.send('/pub/message', {}, JSON.stringify({ 
  //     id: 0,
  //     userId: myid,
  //     direction: store.isStar ? 1 : 2,
  //     message: message,
  //     createDate: new Date(),
  //     roomId: props.selectedRoom.id
  //  }));

  // connectingElement.classList.add('hidden');


// 웹소켓 연결 에러 발생 시 onError함수 실행
function onError(error: any): void {
      console.error('WebSocket connection error', error);
    }

// 메세지 전송 시 실행되는 함수
function sendMessage(): void {
  if (inputValue.value.trim() != '' && stompClient) {
    const chatMessage = {
      id: 0,
      userId: myid,
      direction: store.isStar ? 1 : 2,
      message: inputValue.value,
      createDate: new Date(),
      roomId: props.selectedRoom.id
    };
    stompClient.send('/pub/message', {}, JSON.stringify(chatMessage));
    
    addStarChatingMessages(props.selectedRoom.id, inputValue.value);
    newSelectedRoom.value.push(chatMessage);
    inputValue.value = '';
  }
}

const emits = defineEmits(['update:selectedBack']);

// 채팅 메인으로 돌아가는 함수
const selectRoom = () => {
  emits('update:selectedBack');
}

// 메시지 조회
const getChatMessages = async (): Promise<void> => {

try {
  const res = await axios.get(`${store.API_URL}/chatMessage/readMessages/${props.selectedRoom.id}`);
  newSelectedRoom.value = res.data.data;
  
  console.log(`${res.data.message}: ${res.data.code}`);

  } catch (error) {
    console.error(`Error fetching getChatMessages: `, error);
  }
};

// 메시지 DB 에 저장
const addStarChatingMessages = async (roomId: number, message: string): Promise<void> => {
try {
  const dataToSend = {
    userId: myid,
    direction: store.isStar ? 1 : 2,
    message: message,
    roomId: roomId
  };

    const res = await axios.post(`${store.API_URL}/chatMessage/saveMessages`, dataToSend);

    console.log(`${res.data.message}: ${res.data.code}`);

  } catch (error) {
    console.error(`Error fetching addStarChating: `, error);
  }
};

// 채팅방 퇴장
const exitTheRoom = async (): Promise<void> => {

try {
  const res = await axios.post(`${store.API_URL}/chatRoom/exitTheRoom/${myid}/${props.selectedRoom.starId}`);
  
  console.log(`${res.data.message}: ${res.data.code}`);

  selectRoom();

  } catch (error) {
    console.error(`Error fetching exitTheRoom: `, error);
  }
};

 // 시작될 때 초기화 및 데이터 조회
 onMounted(() => {
  connect();
  getChatMessages();
  });

</script>

<style>
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
</style>