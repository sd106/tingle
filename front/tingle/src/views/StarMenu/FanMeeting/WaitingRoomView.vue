<template>
    <div class="row">
        <div class="col-7">
            <div class="chat-thumbnail p-3">
                <img src="/image/fan-meeting-img.webp" alt="채팅방 이미지"/>
            </div>
        </div>
        <div class="col-5">
            <div>
                {{ localUserName }} 님 안녕하세요
            </div>
            <div id="chat-room">
                <ul id="message-list">
                    <li v-for="message in messages" :key="message.id">
                        <div v-if="message.sender !== localUserName" class="message-content other-message-content">
                            <div class="profile-image">
                                <img src="/image/fan-meeting-img.webp" alt="프로필 이미지">
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

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useUserStore } from '@/stores/user'
import InviteCard from '@/components/starMenu/FanMeeting/InviteCard.vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const store = useUserStore()

const starName = ref(route.params.username.toString())
const localUserName = ref(store.userName)
localUserName.value = '나나나'


// 화면 구성 관련
const invited = ref(true)

const messages = ref([{
    sender: '가가가',
    text: '채팅방에 오신 것을 환영합니다.'
},
{
    sender: '나나나',
    text: '직이네'
},
{
    sender: '다다다',
    text: '역시 릅신 ㅇㅅㄽ!!!'
},
{
    sender: '라라라',
    text: '불끄면 안보임'
}
])
const newMessage = ref('')



// 소켓 관련
const socket = ref(null)

const initializeWebSocket = () => {
    socket.value = new WebSocket("ws://localhost:8080/signal")
    
    socket.value.onmessage = (msg) => {
        const message = JSON.parse(msg.data)
        messages.value.push(message)
    };
    
    socket.value.onopen = () => {
        console.log('채팅방에 연결되었습니다.')
    };
    
    socket.value.onclose = () => {
        console.log('채팅방 연결이 종료되었습니다.')
    };
    
    socket.value.onerror = (error) => {
        console.error('WebSocket 에러: ', error)
    };
}

const sendMessage = () => {
    if (newMessage.value.trim() !== '') {
        const message = {
            sender: localUserName.value,
            text: newMessage.value
        }
        socket.value.send(JSON.stringify(message))
        newMessage.value = ''
    }
}

onMounted(initializeWebSocket)
onUnmounted(() => {
  if (socket.value) {
    socket.value.close()
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
    border-color: transparent #e47b7b transparent transparent  ;
   
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