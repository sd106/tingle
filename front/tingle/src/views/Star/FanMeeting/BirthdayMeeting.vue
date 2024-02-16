<template>
  <audio id="congratulationSound" src="/sound/congratulations.mp3"></audio>
  <div class="container row">
    <div class="col-9" id="remote-video-container">
      <video id="remoteVideo" ref="remoteVideoElement" autoplay></video>
    </div>
    <div class="col-3 d-flex flex-column justify-content-between py-5 ps-0" id="local-video-container" @mouseenter="showControls" @mouseleave="hideControls">
        <video id="localVideo" ref="localVideoElement" autoplay></video>
        <div class="px-5 d-flex justify-content-between">
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
          <div v-if="store.isStar">
            <!-- <button class="btn btn-secondary" @click="congratulation"> 생일 축하 반주 시작</button> -->
            <button class="btn btn-secondary" @click="fanfare"> 팡파레 </button>
          </div>
          <div v-else>

          </div>
        </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, watch, watchEffect } from 'vue'
import { useUserStore } from '@/stores/user'

const store = useUserStore()
const props = defineProps<{
  localStream: MediaStream | undefined
  remoteStream: MediaStream | undefined
}>()

const emit = defineEmits(['congratulation', 'fanfare'])

const localVideoElement = ref<HTMLMediaElement>()
const remoteVideoElement = ref<HTMLMediaElement>()
const controlsVisible = ref(false)
const isVideoOn = ref(true)
const isAudioOn = ref(true)

const showControls = () => {
controlsVisible.value = true
}

const hideControls = () => {
controlsVisible.value = false
}

// const congratulation = () => {
//   emit('congratulation')
//   const congratulationSound = document.getElementById('congratulationSound') as HTMLAudioElement;
//   if (congratulationSound) {
//     congratulationSound.play().catch(error => console.error("Audio play failed", error));
//   }
// }

const fanfare = async () => {
  console.log("???")
  emit('fanfare')
}

const toggleVideo = () => {
if (props.localStream) {
  const videoTrack = props.localStream.getVideoTracks()[0]
  if (videoTrack) {
    videoTrack.enabled = !videoTrack.enabled
    isVideoOn.value = videoTrack.enabled
  }
}
}

const toggleAudio = () => {
if (props.localStream) {
  const audioTrack = props.localStream.getAudioTracks()[0]
  if (audioTrack) {
    audioTrack.enabled = !audioTrack.enabled
    isAudioOn.value = audioTrack.enabled
  }
}
}

const loadLocalVideo = async () => {
if (localVideoElement.value && props.localStream) {
  localVideoElement.value.srcObject = props.localStream;
}
console.log("loadLocalVideo@@@@@@@@@@@@@@@2")
}

const loadRemoteVideo = async () => {
if (remoteVideoElement.value && props.remoteStream) {
  remoteVideoElement.value.srcObject = props.remoteStream;
}
console.log("loadRemoteVideo@@@@@@@@@@@@@@@2")
}


watchEffect(async() => {
  await loadLocalVideo()
  await loadRemoteVideo()
});

watch(() => props.remoteStream, (newStream) => {
if (remoteVideoElement.value && newStream) {
  remoteVideoElement.value.srcObject = newStream;
}
}, { immediate: true });

</script>

<style scoped>
.container {
  position: relative;
  width: 100%;
  height: 100%; 
}

#localVideo {
  border: 1px solid #000;
  width: 100%;
  transform: scaleX(-1);
}

#remoteVideo {
  border: 1px solid #000;
  left: 0;
  height: 100%;
  transform: scaleX(-1);
}

.control-icon {
    font-size: 2rem;
    cursor: pointer;
}

#local-video-container:hover .control-container {
  visibility: visible;
}
</style>