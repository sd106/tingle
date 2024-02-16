<template>
    <div>
      <div class="d-flex justify-content-between px-3">
        <!-- <div>
          <button class="btn border btn-secondary" @click="finishFan">팬 내보내기</button>
        </div> -->
        <div>
          <button class="btn border btn-secondary" @click="finishFan">팬 내보내기</button>
          <button class="btn border btn-secondary" @click="showFanListModal = true">대기방 팬 목록 보기</button>
          <button class="btn border btn-secondary" @click="finishMeeting">팬미팅 끝내기</button>
          <div v-if="showFanListModal" class="fan-list-modal" @click.self="showFanListModal = false">
            <div class="fan-list-modal-content">
              <div class="fan-list">
                <div v-for="fan in fans" :key="fan.id" class="fan-item" @click.stop>
                  <div class="fan-name" @click="selectFan(fan.username)">{{ fan.username }}</div>
                  <button v-if="selectedFanName === fan.username" class="invite-btn" @click="invite(fan.username)">
                    초대하기
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div id="chat-room">
      <ul id="message-list">
        <li v-for="message in messages" :key="message.sender?.id">
          <div
            v-if="message.sender?.username !== localUser.username"
            class="message-content other-message-content"
          >
            <div class="profile-image">
              <img :src="message.sender?.picture" alt="프로필 이미지" />
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
    <!-- <div>
        <button @click="finishMeeting">팬미팅 끝내기</button>
    </div> -->
</template>

<script lang="ts" setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useUserStore } from '@/stores/user'
import type { FanMeetingMessage, SocketMessage, SenderState } from '@/common/types/index'

const store = useUserStore()
const emit = defineEmits(['finish-fan', 'finish-meeting'])
const props = defineProps(['finishedFans'])

const localUser = ref<SenderState>({
  id: store.starState?.id,
  username: store.starState?.username,
  picture: store.starState?.picture
})

const finishFan = () => {
  emit('finish-fan')
}

const finishMeeting = () => {
  emit('finish-meeting')
}

const selectedFanName = ref('')
const showFanListModal = ref(false)

const selectFan = (fanName: string | undefined) => {
  if (fanName === selectedFanName.value) {
    selectedFanName.value = ''
  } else {
    selectedFanName.value = fanName as string
  }
}

// 메시지 관련
const invited = ref(false)

const messages = ref<FanMeetingMessage[]>([])
const newMessage = ref('')

const fans = ref<SenderState[]>([])

const invite = (fanName: string) => {
  sendToServer({
    sender: localUser.value,
    signalType: 'Invite',
    data: fanName,
    roomType: 'Waiting'
  })
}

const sendMessage = () => {
  if (newMessage.value.trim() !== '') {
    sendToServer({
      sender: localUser.value,
      signalType: 'Text',
      data: newMessage.value,
      roomType: 'Waiting'
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
  socket = new WebSocket('wss://i10d106.p.ssafy.io/api/signal')

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

      case 'Join2': // 다른 사용자가 입장했을 떄 이 메시지를 받고 fan list에 추가
        console.log('Join message received')
        if (!fans.value.some((fan) => fan.username === message.sender?.username)) {
          fans.value.push(message.sender)
        }
        break
      default:
        handleErrorMessage(message)
    }
  }

  socket.onopen = () => {
    console.log('소켓 열렸는디요.')

    sendToServer({
      sender: localUser.value,
      signalType: 'Join',
      data: String(store.starState?.id),
      roomType: 'Waiting'
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

/* 팬 리스트 관련 */
.fan-list-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.fan-list-modal-content {
  width: 15rem;
  min-height: 10rem;
  background-color: white;
  border-radius: 10px;
  border: 1px solid grey;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  padding: 2rem 1rem;
}

.fan-list {
  display: flex;
  flex-direction: column;
}

.fan-item {
  position: relative;
  margin-bottom: 10px;
}

.fan-name {
  cursor: pointer;
}

.invite-btn {
  position: absolute;
  right: 0;
  top: 0;
  background-color: #4caf50;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 5px;
}

.invite-btn:hover {
  background-color: #45a049;
}
</style>
