<template>
  <section v-if="fanMeetingReservation?.fanMeetingType == '자유대화'">
    <NormalMeeting :localStream="localStream" :remoteStream="remoteStream"/>
  </section>
  <section v-else-if="fanMeetingReservation?.fanMeetingType == '인생네컷'">
    <LifeFourCutMeeting :localStream="localStream" :remoteStream="remoteStream"/>
  </section>
  <section v-else-if="fanMeetingReservation?.fanMeetingType == '생일축하'">
    <BirthdayMeeting :localStream="localStream" :remoteStream="remoteStream"/>
  </section>
  <section v-else>
    <h1>연결중입니다...</h1>
  </section>
</template>

<script lang="ts" setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { useRoute } from 'vue-router'
import type { SocketMessage, FanMeetingReservation, SenderState } from '@/common/types/index'
import NormalMeeting from '@/views/Star/FanMeeting/NormalMeeting.vue'
import LifeFourCutMeeting from '@/views/Star/FanMeeting/LifeFourCutMeeting.vue'
import BirthdayMeeting from '@/views/Star/FanMeeting/BirthdayMeeting.vue'
import router from '@/router'
import axios from 'axios'

const route = useRoute()

const starid = ref(route.params.starid.toString())
const store = useUserStore()
const localUser = ref<SenderState>({
  id: store.fanState?.id,
  username: store.fanState?.username,
  picture: store.fanState?.picture
})

const fanMeetingReservation = ref<FanMeetingReservation>()

const loadReservation = async () => {
  try {
    const { data } = await axios.get(`https://i10d106.p.ssafy.io/api/fanMeeting/fanMeetingReservation/${localUser.value.id}/${starid.value}`)
    fanMeetingReservation.value = data
    console.log(fanMeetingReservation.value)
    console.log(data)
  } catch (error) {
    console.error(error)
  }
}
// 주소로 연결할 웹소켓
let socket: WebSocket | undefined

// UI elements
const localVideo = ref()
const remoteVideo = ref()

// WebRTC STUN servers
const peerConnectionConfig = {
  iceServers: [
    { urls: 'stun:stun.stunprotocol.org:3478' },
    { urls: 'stun:stun.l.google.com:19302' }
  ]
}

// WebRTC media 설정
const mediaConstraints = {
  audio: true,
  video: true
}

// WebRTC 에 사용할 변수
const localStream = ref<MediaStream>()
const remoteStream = ref<MediaStream>()
let myPeerConnection: RTCPeerConnection

// 서버에게 메시지 전송 메서드
const sendToServer = (msg: SocketMessage) => {
  if (socket && socket.readyState === WebSocket.OPEN) {
    socket.send(JSON.stringify(msg))
  }
}

// WebSocket
const initializeWebSocket = () => {
  // 소켓 초기화
  socket = new WebSocket('wss://i10d106.p.ssafy.io/api/signal')

  // 소켓이 message를 받을 때 이벤트 함수
  socket.onmessage = (msg) => {
    let message = JSON.parse(msg.data)
    console.log('서버로부터 메시지가 도착했습니다!')
    console.log(message)
    // data type별 처리 메서드가 있음
    switch (message.signalType) {
      case 'text':
        console.log('Text message from ' + message.sender + ' received: ' + message.data)
        break

      case 'Offer':
        console.log('Signal OFFER received')
        handleOfferMessage(message)
        break

      case 'Answer':
        console.log('Signal ANSWER received')
        handleAnswerMessage(message)
        break

      case 'Ice':
        console.log('Signal ICE Candidate received')
        handleICEMessage(message)
        break

      case 'Join':
        console.log(
          'Client is starting to ' + (message.data === 'true') ? 'negotiate' : 'wait for a peer'
        )

        handleJoinMessage(message)
        break

      case 'FinishFan':
        console.log('Fan Meeting is finished')
        handleFinishFanMessage(message)
        break

      default:
        console.log('Error: ', message)
        handleErrorMessage(message)
    }
  }

  // 소켓이 열리면 이벤트 함수
  socket.onopen = () => {
    console.log('소켓 열렸는디요')
    sendToServer({
      sender: localUser.value,
      signalType: 'Join',
      data: starid.value,
      roomType: 'Meeting'
    })
  }

  // 소켓이 닫히면 이벤트 함수
  socket.onclose = () => {
    console.log('소켓 닫혔는디요')
  }

  // 소켓에 에러나면 이벤트 함수
  socket.onerror = (error) => {
    console.error(error)
  }
}

// WebRTC
const initializeWebRTC = async () => {
  console.log('handling joing message!')
  // 내 media 출력
  localStream.value = await navigator.mediaDevices.getUserMedia(mediaConstraints)
  // if (localVideo.value) {
  //   const localVideoElement = localVideo.value as HTMLVideoElement
  //   localVideoElement.srcObject = localStream.value; 
  //   (localVideo.value as HTMLVideoElement).play()
  // }
  console.log('야호')

  // 다른 peer들을 위한 RTCPeerConnection을 만듬
  myPeerConnection = new RTCPeerConnection(peerConnectionConfig)

  //
  myPeerConnection.onicecandidate = (event) => {
    // ICE Candidate 정보를 서버로 보냄
    if (event.candidate) {
      sendToServer({
        sender: localUser.value,
        signalType: 'Ice',
        iceCandidate: event.candidate
      })
      console.log('ICE Candidate Event: ICE candidate sent')
    }
  }
  // 원격 스트림을 받을 때 처리
  myPeerConnection.ontrack = (event) => {
    console.log('Track Event: set stream to remote video element')
    console.log('remoteVideo: ', event.streams[0])
    // if (remoteVideo.value) {
    //   const remoteVideoElement = remoteVideo.value as HTMLVideoElement
    //   remoteVideoElement.srcObject = event.streams[0]
      remoteStream.value = event.streams[0]; 
    //   (remoteVideo.value as HTMLVideoElement).play()
    // }
  }

  // ICE 연결 상태 변경되면 로깅
  myPeerConnection.oniceconnectionstatechange = (event) => {
    if (myPeerConnection) {
      console.log('ICE Connection State:', myPeerConnection.iceConnectionState)
    }
  }
}

// socket event 별 처리 메서드

const handleOfferMessage = async (message: SocketMessage) => {
  try {
    if (message.sdp) {
      const remoteDescription = new RTCSessionDescription(message.sdp as RTCSessionDescriptionInit)
      await myPeerConnection.setRemoteDescription(remoteDescription)
    }

    const answer = await myPeerConnection.createAnswer()
    await myPeerConnection.setLocalDescription(answer)
    sendToServer({
      sender: localUser.value,
      signalType: 'Answer',
      sdp: myPeerConnection.localDescription ? myPeerConnection.localDescription : undefined
    })
  } catch (error) {
    console.error('Error handling offer message: ', error)
  }
}

const handleAnswerMessage = (message: SocketMessage) => {
  if (message.sdp) {
    const remoteDescription = new RTCSessionDescription(message.sdp as RTCSessionDescriptionInit)
    myPeerConnection.setRemoteDescription(remoteDescription)
  }
}

const handleICEMessage = (message: SocketMessage) => {
  const candidate = new RTCIceCandidate(message.iceCandidate)
  myPeerConnection.addIceCandidate(candidate)
}

const handleJoinMessage = async (message: SocketMessage) => {
    console.log('11')
    myPeerConnection.onnegotiationneeded = async () => {
      try {
        console.log('22')

        const offer = await myPeerConnection.createOffer()
        await myPeerConnection.setLocalDescription(offer)
        sendToServer({
          sender: localUser.value,
          signalType: 'Offer',
          sdp: myPeerConnection.localDescription ? myPeerConnection.localDescription : undefined
        })
        console.log('Negotiation Needed Event: SDP offer sent')
      } catch (reason) {
        // 연결 실패 시 오류 처리
        console.error('failure to connect error: ', reason)
      }
    }
  

  // 내 media를 RTCPeerConnection에 추가
  if (localStream.value) {
    localStream.value.getTracks().forEach(track => myPeerConnection.addTrack(track, localStream.value as MediaStream));
  }
}

const handleFinishFanMessage =async (message: SocketMessage) => {
  try {
    sendToServer({
            sender: localUser.value,
            signalType: 'leave',
            sdp: myPeerConnection.localDescription ? myPeerConnection.localDescription : undefined
          })
    
    router.replace({ name: 'WaitingRoomView' })
    alert("팬미팅이 종료되었습니다.")
  } catch (error) {
    console.log(error)
  }
}

const handleErrorMessage = (message: SocketMessage) => {
  console.error('에러발생!: ', message)
}

onMounted(async () => {
  await loadReservation()
  await console.log("이아아아아")
  await initializeWebRTC()
  await console.log("오오오오")

  initializeWebSocket()
  await console.log("으으응")

})

onUnmounted(() => {
  if (socket) {
    socket.close()
  }
})
</script>

<style scoped>
.container {

    position: relative;
    width: 100%;
    height: 90vh; 
    /* border: 1px solid black;  */
}

#local-video-container {
  position: absolute;
  width: 20%;
  height: auto;
  right: 10px;
  bottom: 10px;
  z-index: 2;
}

#localVideo {
  width: 100%;
  height: auto;
  border: 1px solid black;
}

#remote-video-container {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1;
  background-color: grey;
}

#remoteVideo {
  width: 100%;
  height: auto;
}

.control-container {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  gap: 10px;
  visibility: hidden;
}

.control-icon {
  font-size: 2rem;
  cursor: pointer;
}

.control-label-container {
  background-color: rgba(128, 128, 128, 0.5);
  border-radius: 5px;
}
.control-label {
  font-size: 0.8rem;
  font-weight: bold;
  cursor: pointer;
  color: black;
}

#local-video-container:hover .control-container {
  visibility: visible;
}
</style>
