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

<script lang="ts" setup>
import { ref } from 'vue'

const props = defineProps<{
  localStream: MediaStream | null
}>()
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