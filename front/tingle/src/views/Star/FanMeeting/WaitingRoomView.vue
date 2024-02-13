<template>
  <div class="row">
    <div class="col-7">
      <div class="chat-thumbnail p-3">
        <img src="/image/fan-meeting-img.webp" alt="채팅방 이미지" />
      </div>
    </div>
    <div class="col-5">
      <div>{{ localUserName }} 님 안녕하세요</div>
      <div id="chat-room">
        <ul id="message-list">
          <li v-for="message in messages" :key="message.sender">
            <div
              v-if="message.sender !== localUserName"
              class="message-content other-message-content"
            >
              <div class="profile-image">
                <img src="/image/fan-meeting-img.webp" alt="프로필 이미지" />
              </div>
              <div class="message other-message">
                {{ message.text }}
              </div>
            </div>
            <div v-else class="message-content my-message-content">
              <div class="message my-message">
                {{ message.text }}
              </div>
            </div>
          </li>
        </ul>
        <div class="message-input">
          <input v-model="newMessage" @keyup.enter="sendMessage" placeholder="메시지 입력" />
          <button class="send-message-button" @click="sendMessage">보내기</button>
        </div>
      </div>
    </div>
  </div>
  <InviteCard v-if="invited" :starName="starName"></InviteCard>
</template>

<script lang="ts" setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useUserStore } from '@/stores/user'
import InviteCard from '@/components/StarMenu/FanMeeting/InviteCard.vue'
import { useRoute } from 'vue-router'
import type { FanMeetingMessage, SocketMessage } from '@/common/types/index'

const route = useRoute()
const store = useUserStore()

const starName = ref(route.params.starid.toString())
const localUserName = ref(store.fanState?.username)
const roomType = 'Waiting'

// 메시지 관련
const invited = ref(false)

const messages = ref<FanMeetingMessage[]>([])
const newMessage = ref('')

const sendMessage = () => {
  if (newMessage.value.trim() !== '') {
    sendToServer({
      sender: localUserName.value,
      signalType: 'Text',
      data: newMessage.value,
      roomType: roomType
    })
    newMessage.value = ''
  }
}

// 소켓 관련
let socket: WebSocket | undefined

// 서버에게 메시지 전송 메서드
const sendToServer = (msg: SocketMessage) => {
  if (socket && socket.readyState === WebSocket.OPEN) {
    socket.send(JSON.stringify(msg))
  }
}

const initializeWebSocket = () => {
  socket = new WebSocket('ws://localhost:8080/signal')

  socket.onmessage = (msg) => {
    let message = JSON.parse(msg.data)
    console.log('서버로부터 메시지가 도착했습니다!')
    console.log(message)
    // data type별 처리 메서드가 있음
    switch (message.signalType) {
      case 'Text':
        handleTextMessage(message)
        break

      case 'Invite':
        console.log('Invite message received')
        handleInviteMessage(message)
        break

      default:
        handleErrorMessage(message)
    }
  }

  socket.onopen = () => {
    console.log('소켓 열렸는디요.')
    const sendToServer = (msg: SocketMessage) => {
      if (socket && socket.readyState === WebSocket.OPEN) {
        socket.send(JSON.stringify(msg))
      }
    }

    sendToServer({
      sender: localUserName.value,
      signalType: 'Join',
      data: starName.value,
      roomType: roomType
    })
  }

  socket.onclose = () => {
    console.log('소켓 닫혔는디요.')
  }

  socket.onerror = (error) => {
    console.error(error)
  }
}

const handleTextMessage = (message: SocketMessage) => {
  messages.value.push({
    sender: message.sender,
    text: message.data
  })
  console.log('Text message from ' + message.sender + ' received: ' + message.data)
}

const handleInviteMessage = (message: SocketMessage) => {
  invited.value = true
  console.log('초대왔땅')
}
const handleErrorMessage = (message: SocketMessage) => {
  console.error('에러발생!: ', message)
}

onMounted(initializeWebSocket)
onUnmounted(() => {
  if (socket) {
    socket.close()
  }
})
</script>

<style scoped>
.chat-thumbnail img {
  width: 100%;
  height: auto;
  border-radius: 10px;
}

#chat-room {
  margin-top: 20px;
  border: 1px solid #ddd;
  background-color: #f9f9f9;
  height: 500px;
  display: flex;
  flex-direction: column;
}

#message-list {
  flex-grow: 1;
  overflow-y: auto;
  padding: 10px;
  list-style-type: none;
}

.message-content {
  display: flex;
  margin: 0.7rem;
}

.message {
  position: relative;
  padding: 0.5rem 1rem;
}

.message::after {
  content: '';
  position: absolute;
  border-style: solid;
  width: 0;
  height: 0;
}

.other-message-content {
  justify-content: flex-start;
}

.other-message {
  background-color: #e47b7b;
  border-radius: 0 3px 3px 3px;
}

.other-message::after {
  right: 100%;
  top: 0%;
  border-width: 0 10px 10px 0;
  border-color: transparent #e47b7b transparent transparent;
}

.my-message-content {
  justify-content: flex-end;
}

.my-message {
  background-color: #007bff;
  color: white;
  border-radius: 3px 0 3px 3px;
}

.my-message::after {
  left: 100%;
  top: 0%;
  border-width: 0 0 10px 10px;
  border-color: transparent transparent transparent #007bff;
}

.profile-image img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.message-input {
  display: flex;
  padding: 10px;
}

input {
  flex-grow: 1;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.send-message-button {
  padding: 10px 15px;
  background-color: #5cb85c;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-left: 10px;
}

.send-message-button:hover {
  background-color: #4cae4c;
}
</style>
