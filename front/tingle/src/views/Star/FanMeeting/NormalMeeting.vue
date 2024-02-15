<template>
  <div class="container px-0">
    <div v-if="store.isStar" id="local-video-container-star" @mouseenter="showControls" @mouseleave="hideControls">
      <video id="localVideo" ref="localVideoElement" autoplay></video>
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
    <div v-else id="local-video-container-fan" @mouseenter="showControls" @mouseleave="hideControls">
      <video id="localVideo" ref="localVideoElement" autoplay></video>
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
    <div v-if="store.isStar" id="remote-video-container-star">
        <video class="tw-size-full" id="remoteVideo" ref="remoteVideoElement" autoplay></video>
    </div>
    <div v-else id="remote-video-container-fan">
        <video class="tw-size-full" id="remoteVideo" ref="remoteVideoElement" autoplay></video>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, watch, watchEffect } from 'vue'
import { useUserStore } from '@/stores/user'

const store = useUserStore()

const props = defineProps<{
  localStream: MediaStream | undefined
  remoteStream: MediaStream | undefined
}>()

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
    /* position: relative; */
    width: auto;
    height: 100%; 
    /* border: 1px solid black;  */
}

#local-video-container-star {
  position: absolute;
  width: 20%;
  height: auto;
  left: 7%; 
  bottom: 0px;
  z-index: 2;
}


#local-video-container-fan {
  position: absolute;
  width: 20%;
  height: auto;
  right: 0px; 
  bottom: 0px;
  z-index: 2;
}

#localVideo {
    width: 100%;
    height: auto;
    /* border: 1px solid black; */
}

#remote-video-container-star {
    position: absolute;
    top: 0;
    left: 7%;
    width: auto;
    height: 100%;
    z-index: 1; 
    /* background-color: grey; */
}

#remote-video-container-fan {
    position: absolute;
    top: 0;
    left: 0px;
    width: auto;
    height: 100%;
    z-index: 1; 
    /* background-color: grey; */
}
/*
#remoteVideo {
    width: 100%;
    height: auto;
}

 */

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