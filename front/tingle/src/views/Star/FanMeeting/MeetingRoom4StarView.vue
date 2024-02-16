<template>
    <div class="row m-0 p-0">
        <div class="col-9">
            <section v-if="fanMeetingReservation?.fanMeetingType == '자유대화'">
                <NormalMeeting :localStream="localStream" :remoteStream="remoteStream"/>
            </section>
            <section v-else-if="fanMeetingReservation?.fanMeetingType == '인생네컷'">
                <LifeFourCutMeeting :localStream="localStream" :remoteStream="remoteStream"/>
            </section>
            <section v-else-if="fanMeetingReservation?.fanMeetingType== '생일축하'">
                <BirthdayMeeting :localStream="localStream" :remoteStream="remoteStream"/>
            </section>
            <section v-else>
                <h1>연결중입니다...</h1>
            </section>
        </div>
        <div class="col-3">
            <FanMeetingBoard :finishedFans="finishedFans" @finish-fan="finishFan" @finish-meeting="finishMeeting"/>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useUserStore } from '@/stores/user'
import axios from 'axios'
import type { SocketMessage, FanMeetingReservation, SenderState } from '@/common/types/index'
import NormalMeeting from '@/views/Star/FanMeeting/NormalMeeting.vue'
import LifeFourCutMeeting from '@/views/Star/FanMeeting/LifeFourCutMeeting.vue'
import BirthdayMeeting from '@/views/Star/FanMeeting/BirthdayMeeting.vue'
import FanMeetingBoard from '@/components/StarMenu/FanMeeting/FanMeetingBoard.vue'
import { useRouter } from 'vue-router'

const store = useUserStore()
const router = useRouter()
const localUser = ref<SenderState>({
  id: store.starState?.id,
  username: store.starState?.username,
  picture: store.starState?.picture
})

const fanMeetingReservation = ref<FanMeetingReservation>()

const finishedFans = ref<String[]>([])

const finishFan = () => {
  console.log('finishFan')
  console.log(fanMeetingReservation)
  sendToServer({
    sender: localUser.value,
    signalType: 'FinishFan',
    data: fanMeetingReservation.value?.userName,
    roomType: 'Waiting'
  })
  finishedFans.value.push(fanMeetingReservation.value?.userName ?? '')
  fanMeetingReservation.value = undefined
}

const finishMeeting = async () => {
  console.log('finishing Meeting..')
  try {
    const response = axios.delete(`http://localhost:8080/fanMeeting/finish/${store.starState?.id}`)
    console.log(response)
    router.push({ name: 'starhomemanage' })
  } catch (error) {
    console.log(error)
  }
}

const fanfare = () => {
  console.log("팡파레!!")
  sendToServer({
      sender: localUser.value,
      signalType: 'Fanfare',
      roomType: 'Meeting'
    })
}

const congratulation = () => {
  sendToServer({
      sender: localUser.value,
      signalType: 'Congratulation',
      roomType: 'Meeting'
    })

    
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
  socket = new WebSocket('ws://localhost:8080/signal')

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
          'Client is starting to ' + (message.data === 'true' ? 'negotiate' : 'wait for a peer')
        )
        handleJoinMessage(message)
        break

            case "Accept":
                console.log('Signal ACCEPT received')
                handleAcceptMessage(message)
                break;

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
      roomType: 'Meeting',
      data: String(localUser.value.id)
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
  //     const localVideoElement = localVideo.value as HTMLVideoElement;
  //     localVideoElement.srcObject = localStream.value;
  //     (localVideo.value as HTMLVideoElement).play();
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
    //     const remoteVideoElement = remoteVideo.value as HTMLVideoElement;
    //     remoteVideoElement.srcObject = event.streams[0];
    remoteStream.value = event.streams[0]
    //     (remoteVideo.value as HTMLVideoElement).play();
    console.log(remoteStream.value)
    // }
  }

  // ICE 연결 상태 변경되면 로깅
  myPeerConnection.oniceconnectionstatechange = (event) => {
    if (myPeerConnection) {
      console.log(event)
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
    localStream.value
      .getTracks()
      .forEach((track) => myPeerConnection.addTrack(track, localStream.value as MediaStream))
  }
}

const handleAcceptMessage = (message: SocketMessage) => {
  try {
    console.log(message.sdp)
    fanMeetingReservation.value = message.sdp as FanMeetingReservation
    console.log(fanMeetingReservation.value)
  } catch (error) {
    console.log(error)
  }
}

const handleErrorMessage = (message: SocketMessage) => {
  console.error('에러발생!: ', message)
}

onMounted(async () => {
  await initializeWebRTC()
  initializeWebSocket()
})

onUnmounted(() => {
  if (socket) {
    socket.close()
  }
})
</script>

<style>
.center {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 80vh; /* 뷰포트의 전체 높이 */
  text-align: center;
}
</style>
