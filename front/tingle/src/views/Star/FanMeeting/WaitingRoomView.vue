<template>
  <div class="row">
    <div class="col-7">
      <div class="slider p-3">
        <img v-if="currentImageUrl" :src="currentImageUrl" alt="Image slide" />
        <p v-else class="center">스타를 기다리는 중입니다...</p>
      </div>
    </div>
    <div class="col-5 mt-4">
      <div>{{ localUser.username }} 님 안녕하세요</div>
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
    </div>
  </div>
  <InviteCard v-if="invited" @enter-meeting-room="enterMeetingRoom"></InviteCard>
</template>

<script lang="ts" setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useUserStore } from '@/stores/user'
import InviteCard from '@/components/StarMenu/FanMeeting/InviteCard.vue'
import { useRoute } from 'vue-router'
import axios from 'axios'
import type {
  FanMeetingMessage,
  SocketMessage,
  SenderState,
  FanMeetingReservation,
  SnapshotType
} from '@/common/types/index'

const route = useRoute()
const store = useUserStore()

const starid = ref(String(route.params.starid))
const roomType = 'Waiting'

const fanMeetingReservation = ref<FanMeetingReservation>()
const loadReservation = async () => {
  try {
    const response = await axios.get(
      `${store.API_URL}/fanMeeting/reservation/${store.fanState?.id}`
    )
    fanMeetingReservation.value = response.data
    console.log(response)
  } catch (error) {
    console.log(error)
  }
}

// 스타 스냅샷 이미지
const snapshots = ref<SnapshotType[]>([])

const loadSnapshots = async (): Promise<void> => {
  try {
    const response = await axios.get(`http://localhost:8080/snapshot/star/${starid.value}/created`)
    snapshots.value = response.data.AllSnapShot
    console.log('최신순')
  } catch (error) {
    console.error(error)
  }
}

// 현재 슬라이드의 imageUrl을 저장할 ref
const currentImageUrl = ref<string | null>(null)
// 현재 슬라이드의 인덱스를 저장할 ref
let currentIndex = 0

const rotateImages = () => {
  if (snapshots.value.length > 0) {
    currentImageUrl.value = snapshots.value[currentIndex % snapshots.value.length].imageUrl
    currentIndex++
  }
}

// 메시지 관련
const invited = ref(false)

const messages = ref<FanMeetingMessage[]>([])
const newMessage = ref('')

const localUser = ref<SenderState>({
  id: store.fanState?.id,
  username: store.fanState?.username,
  picture: store.fanState?.picture
})

const sendMessage = () => {
  if (newMessage.value.trim() !== '') {
    sendToServer({
      sender: localUser.value,
      signalType: 'Text',
      data: newMessage.value,
      roomType: roomType
    })
    newMessage.value = ''
  }
}

const enterMeetingRoom = () => {
  console.log('??')
  sendToServer({
    sender: localUser.value,
    signalType: 'Accept',
    data: starid.value,
    roomType: roomType
  })
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

    sendToServer({
      sender: localUser.value,
      signalType: 'Join',
      data: starid.value,
      sdp: store.fanState,
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

onMounted(async () => {
  initializeWebSocket()
  loadReservation()
  loadSnapshots()
  rotateImages() // 초기 이미지 설정
  const intervalId = setInterval(rotateImages, 3000) // 3초마다 이미지 변경
  onUnmounted(() => {
    clearInterval(intervalId)
  })
})
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
  min-width: 80px;
}

.send-message-button:hover {
  background-color: #4cae4c;
}

.slider img {
  width: 100%;
  max-width: 600px;
  display: block;
  margin: 0 auto;
}
</style>
