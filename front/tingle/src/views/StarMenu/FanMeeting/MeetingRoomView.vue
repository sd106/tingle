<template>
    <div class="container">
    <div id="local-video-container" @mouseenter="showControls" @mouseleave="hideControls">
        <video id="localVideo" ref="localVideo" autoplay></video>
        <div class="control-container">
            <div v-if="isVideoOn" class="text-center" @click="toggleVideo">
                <div class="control-icon">🎥</div>
                <div class="control-label-container">
                    <div class="control-label">카메라</div>
                    <div class="control-label">켜짐</div>
                </div>
            </div>
            <div v-else class="text-center" @click="toggleVideo">
                <div class="control-icon">🚫</div>
                <div class="control-label-container">
                    <div class="control-label">카메라</div>
                    <div class="control-label">꺼짐</div>
                </div>
            </div>

            <div v-if="isAudioOn" class="text-center" @click="toggleAudio">
                <div class="control-icon">🔊</div>
                <div class="control-label-container">
                    <div class="control-label">마이크</div>
                    <div class="control-label">켜짐</div>                
                </div>
            </div>
            <div v-else class="text-center" @click="toggleAudio">
                <div class="control-icon">🚫</div>
                <div class="control-label-container">
                    <div class="control-label">마이크</div>
                    <div class="control-label">꺼짐</div>
                </div>
            </div>

        </div>
    </div>
    <div id="remote-video-container">
        <video id="remoteVideo" ref="remoteVideo" autoplay></video>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useUserStore } from '@/stores/user'

const store = useUserStore()

// 화면 구성 관련
const controlsVisible = ref(false)
const isVideoOn = ref(true)
const isAudioOn = ref(true)

const showControls = () => {
  controlsVisible.value = true
}

const hideControls = () => {
  controlsVisible.value = false
}

const toggleVideo = () => {
  if (localStream) {
    const videoTrack = localStream.getVideoTracks()[0]
    if (videoTrack) {
      videoTrack.enabled = !videoTrack.enabled
      isVideoOn.value = videoTrack.enabled
    }
  }
}

const toggleAudio = () => {
  if (localStream) {
    const audioTrack = localStream.getAudioTracks()[0]
    if (audioTrack) {
      audioTrack.enabled = !audioTrack.enabled
      isAudioOn.value = audioTrack.enabled
    }
  }
}

// 주소로 연결할 웹소켓
let socket

// UI elements
const RoomNumber = ref('')
const localVideo = ref(null)
const localUserName = ref(store.userName)
const localRoom = ref(1)
const remoteVideo = ref(null)

// WebRTC STUN servers 
const peerConnectionConfig = {
    'iceServers': [
        {'urls': 'stun:stun.stunprotocol.org:3478'},
        {'urls': 'stun:stun.l.google.com:19302'},
    ]
}

// WebRTC media 설정
const mediaConstraints = {
    audio: true,
    video: true
}

// WebRTC 에 사용할 변수
let localStream;
let localVideoTracks;
let myPeerConnection;


// 서버에게 메시지 전송 메서드
const sendToServer = (msg) => {
    if (socket && socket.readyState === WebSocket.OPEN) {
        socket.send(JSON.stringify(msg))
    }
}

// WebSocket
const initializeWebSocket = () => {
    // 소켓 초기화
    socket = new WebSocket("ws://localhost:8080/signal")

    // 소켓이 message를 받을 때 이벤트 함수
    socket.onmessage = (msg) => {
        let message = JSON.parse(msg.data)
        console.log("서버로부터 메시지가 도착했습니다!")
        console.log(message)
        // data type별 처리 메서드가 있음
        switch (message.signalType) {
            case "text":
                console.log('Text message from ' + message.sender + ' received: ' + message.data)
                break

            case "Offer":
                console.log('Signal OFFER received')
                handleOfferMessage(message)
                break;

            case "Answer":
                console.log('Signal ANSWER received')
                handleAnswerMessage(message)
                break;

            case "Ice":
                console.log('Signal ICE Candidate received')
                handleICEMessage(message)
                break;

            case "Join":
                console.log('Client is starting to ' + (message.data === "true)" ? 'negotiate' : 'wait for a peer'))
                handleJoinMessage(message)
                break;

            default:
                handleErrorMessage('Wrong type message received from server')
        }
    };

    // 소켓이 열리면 이벤트 함수
    socket.onopen = () => {
        console.log('소켓 열렸는디요')
        sendToServer({
            sender: localUserName.value,
            signalType: 'Join',
            data: localRoom.value

        });
    };

    // 소켓이 닫히면 이벤트 함수
    socket.onclose = () => {
        console.log('소켓 닫혔는디요')
    };

    
    // 소켓에 에러나면 이벤트 함수
    socket.onerror = (error) => {
        console.errorr(error)
    };
}

// WebRTC
const initializeWebRTC = async () => {
    console.log("handling joing message!")
    // 내 media 출력
    localStream = await navigator.mediaDevices.getUserMedia(mediaConstraints)
    localVideo.value.srcObject = localStream
    localVideo.value.play()
    console.log("야호")

    // 다른 peer들을 위한 RTCPeerConnection을 만듬
    myPeerConnection = new RTCPeerConnection(peerConnectionConfig)
    
    // 
    myPeerConnection.onicecandidate = (event) =>{
        // ICE Candidate 정보를 서버로 보냄
        if (event.candidate) {
            sendToServer({
                sender: localUserName.value,
                signalType: 'Ice',
                iceCandidate: event.candidate
            })
            console.log('ICE Candidate Event: ICE candidate sent')
        }
    }
    // 원격 스트림을 받을 때 처리
    myPeerConnection.ontrack = (event) => {
        console.log('Track Event: set stream to remote video element')
        console.log('remoteVideo: ',event.streams[0])
        remoteVideo.value.srcObject = event.streams[0]
        remoteVideo.value.play()
    }

    // ICE 연결 상태 변경되면 로깅
    myPeerConnection.oniceconnectionstatechange = (event) => {
        console.log('ICE Connection State:', myPeerConnection.iceConnectionState)
    }

}

// socket event 별 처리 메서드

const handleOfferMessage = async (message) => {
    try {
        const remoteDescription = new RTCSessionDescription(message.sdp)
        await myPeerConnection.setRemoteDescription(remoteDescription)

        const answer = await myPeerConnection.createAnswer()
        await myPeerConnection.setLocalDescription(answer)
        sendToServer({
            sender: localUserName.value,
            signalType: 'Answer',
            sdp: myPeerConnection.localDescription
        })
    } catch (error) {
        console.error('Error handling offer message: ', error)
    }
}

const handleAnswerMessage = (message) => {
    const remoteDescription = new RTCSessionDescription(message.sdp)
    myPeerConnection.setRemoteDescription(remoteDescription)
}

const handleICEMessage = (message) => {
    const candidate = new RTCIceCandidate(message.iceCandidate)
    myPeerConnection.addIceCandidate(candidate)
}

const handleJoinMessage = async (message) => {
    if (message.data === "true") {
        console.log("11")
        myPeerConnection.onnegotiationneeded = async () => {
            try {   
                console.log("22")

                const offer = await myPeerConnection.createOffer()
                await myPeerConnection.setLocalDescription(offer)
                sendToServer({
                    sender: localUserName.value,
                    signalType: 'Offer',
                    sdp: myPeerConnection.localDescription
                })
                console.log('Negotiation Needed Event: SDP offer sent')
            } catch (reason) {
                // 연결 실패 시 오류 처리
                console.error('failure to connect error: ', reason)
            }
        }
    }

    // 내 media를 RTCPeerConnection에 추가
    localStream.getTracks().forEach(track => myPeerConnection.addTrack(track, localStream))

}


const handleErrorMessage = (message) => {
    console.error("에러발생!: ", message)
}


onMounted(async() => {
    await initializeWebRTC()
    initializeWebSocket()
})

onUnmounted(() => {
  if (socket) {
    socket.close();
  }
})


</script>

<style scoped>
.container {
    position: relative;
    width: 100%;
    height: 90vh; 
    border: 1px solid black; 
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
    background-color: rgba(128,128,128,0.5) ;
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