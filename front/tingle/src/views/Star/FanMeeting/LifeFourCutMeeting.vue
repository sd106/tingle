<template>
  <div class="d-flex container">
    <!-- 팬일때 -->
    <div>

      <div class="d-flex mt-3">
      <h2 @click="captureVideo" class="fw-bold hover-text m-3">캡처</h2>
      <h2 @click="mergeAndDownloadImages" class="fw-bold hover-text m-3">다운로드</h2>
    </div>
      <div v-if="!isStar" style="width: 750px;">
        <div class="d-flex m-3">
          <div class="remote-video-container">
            <video class="remoteVideo" ref="remoteVideoElement" autoplay></video>
          </div>
          <!-- 내 카메라 -->
          <div class="local-video-container" @mouseenter="showControls" @mouseleave="hideControls">
            <video class="localVideo" ref="localVideoElement" autoplay></video>
          </div>
          <!-- 내 카메라 -->
        </div>
      </div>

      <!-- 스타일때 -->
      <div v-if="isStar">
      <div class="d-flex m-3">
        <!-- 내 카메라 -->
        <div class="local-video-container" @mouseenter="showControls" @mouseleave="hideControls">
          <video class="localVideo" ref="localVideoElement" autoplay></video>
        </div>
        <!-- 내 카메라 -->
        <div class="remote-video-container">
          <video class="remoteVideo" ref="remoteVideoElement" autoplay></video>
        </div>
      </div>
    </div>
    <div class="d-flex">

    <div v-if="!isStar" class="d-flex m-3" style="width: 715px;">
      <div  v-if="capturedRemoteImageUrl" class="remote-video-container remoteVideo">
        <img :src="capturedRemoteImageUrl" alt="Captured Remote Video" />
        </div>
      <div v-if="capturedLocalImageUrl" class="local-video-container localVideo">
        <img :src="capturedLocalImageUrl" alt="Captured Local Video" />
      </div>
    </div>

    <div v-if="isStar" class="d-flex">
      <div v-if="capturedLocalImageUrl" class="local-video-container localVideo">
        <img :src="capturedLocalImageUrl" alt="Captured Local Video" />
      </div>
      <div  v-if="capturedRemoteImageUrl" class="remote-video-container remoteVideo">
        <img :src="capturedRemoteImageUrl" alt="Captured Remote Video" />
        </div>
    </div>
  </div>
  </div>

  <div class="m-4 tw-carousel tw-carousel-vertical tw-rounded-box ">
  <div class="tw-carousel-item tw-h-full">
    <img src="/image/4cut-1.jpg" />
  </div> 
  <div class="tw-carousel-item tw-h-full">
    <img src="/image/4cut-2.jpg" />
  </div> 
  <div class="tw-carousel-item tw-h-full">
    <img src="/image/4cut-3.jpg" />
  </div> 
  <div class="tw-carousel-item tw-h-full">
    <img src="/image/4cut-4.jpg" />
  </div> 
  <div class="tw-carousel-item tw-h-full">
    <img src="/image/4cut-5.jpg" />
  </div> 
  <div class="tw-carousel-item tw-h-full">
    <img src="/image/4cut-6.jpg" />
  </div> 
  <div class="tw-carousel-item tw-h-full">
    <img src="/image/4cut-7.jpg" />
  </div>
</div>

  </div>

  <!-- <button @click="bb">ddd</button> -->
</template>

<script lang="ts" setup>
import { ref, watch, watchEffect } from 'vue'
import { useUserStore } from '@/stores/user'
import type {Ref} from 'vue'

const props = defineProps<{
  localStream: MediaStream | undefined
  remoteStream: MediaStream | undefined
}>()

const localVideoElement = ref<HTMLVideoElement>()
const remoteVideoElement = ref<HTMLVideoElement>()
const controlsVisible = ref(false)
const isVideoOn = ref(true)
const isAudioOn = ref(true)
const store = useUserStore()
const isStar = store.isStar

const capturedLocalImageUrl = ref<string | null>(null)
const capturedRemoteImageUrl = ref<string | null>(null)

const captureVideo = () => {
  capture(localVideoElement.value ?? null, capturedLocalImageUrl)
  capture(remoteVideoElement.value ?? null, capturedRemoteImageUrl)
}

const capture = (videoElement: HTMLVideoElement | null, imageUrlRef: Ref<string | null>) => {
  const canvas = document.createElement('canvas')
  const context = canvas.getContext('2d')
  if (videoElement && context) {
    canvas.width = videoElement.videoWidth
    canvas.height = videoElement.videoHeight
    context.drawImage(videoElement, 0, 0, canvas.width, canvas.height)
    const imageDataUrl = canvas.toDataURL('image/png')
    imageUrlRef.value = imageDataUrl
  }
}



// 이미지를 로컬에 저장하는 함수
const downloadImage = (imageUrl: string, fileName: string) => {
  const a = document.createElement('a')
  a.href = imageUrl
  a.download = fileName || 'download'
  document.body.appendChild(a)
  a.click()
  document.body.removeChild(a)
}




const drawImageOnCanvas = (
  context: CanvasRenderingContext2D,
  imageUrl: string,
  x: number,
  y: number,
  width: number,
  height: number,
  isLocal: boolean = false // 로컬 이미지인지 여부
): Promise<void> => {
  return new Promise((resolve, reject) => {
    const image: HTMLImageElement = new Image();
    image.onload = () => {
      if (isLocal) {
        // 좌우 반전 처리
        context.save(); // 현재 상태 저장
        context.scale(-1, 1); // x 축 방향으로 반전
        context.drawImage(image, -x-width, y, width, height); // 반전된 위치에 이미지 그리기
        context.restore(); // 저장된 상태로 복원
      } else {
        // 일반 이미지 처리
        context.drawImage(image, x, y, width, height);
      }
      resolve();
    };
    image.onerror = reject;
    image.src = imageUrl;
  });
};

// 두 이미지를 합치고 다운로드하는 함수
const mergeAndDownloadImages = async () => {
  const canvas: HTMLCanvasElement = document.createElement('canvas');
  const context: CanvasRenderingContext2D | null = canvas.getContext('2d');
  
  if (!context) return;

  // 캔버스 크기 설정
  const imageWidth: number = 640; // 예시값, 실제에 맞게 조정
  const imageHeight: number = 480; // 예시값, 실제에 맞게 조정
  canvas.width = imageWidth * 2;
  canvas.height = imageHeight;

  // 로컬 비디오 이미지 캡처
  if (!isStar)
  {
  if (capturedLocalImageUrl.value) {
    await drawImageOnCanvas(context, capturedLocalImageUrl.value, 0, 0, imageWidth, imageHeight,true); // isLocal을 true로 설정
  }

  // 리모트 비디오 이미지 캡처
  if (capturedRemoteImageUrl.value) {
    await drawImageOnCanvas(context, capturedRemoteImageUrl.value, imageWidth, 0, imageWidth, imageHeight,true);
  }
}

if (isStar)
  {
  if (capturedLocalImageUrl.value) {
    await drawImageOnCanvas(context, capturedLocalImageUrl.value, 0, 0, imageWidth, imageHeight,true); // isLocal을 true로 설정
  }

  // 리모트 비디오 이미지 캡처
  if (capturedRemoteImageUrl.value) {
    await drawImageOnCanvas(context, capturedRemoteImageUrl.value, imageWidth, 0, imageWidth, imageHeight,true);
  }
}

  // 합쳐진 이미지 다운로드
  const finalImageUrl: string = canvas.toDataURL('image/png');
  downloadImage(finalImageUrl, 'combined-video-capture');
};

// 이미지 다운로드 함수
// const downloadImage = (imageUrl: string, filename: string) => {
//   const downloadLink: HTMLAnchorElement = document.createElement('a');
//   downloadLink.href = imageUrl;
//   downloadLink.download = `${filename}.png`;
//   document.body.appendChild(downloadLink);
//   downloadLink.click();
//   document.body.removeChild(downloadLink);
// };





const bb = () => {
  console.log(props.localStream)
  console.log(props.remoteStream)
}
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
    localVideoElement.value.srcObject = props.localStream
  }
  console.log('loadLocalVideo@@@@@@@@@@@@@@@2')
}

const loadRemoteVideo = async () => {
  if (remoteVideoElement.value && props.remoteStream) {
    remoteVideoElement.value.srcObject = props.remoteStream
  }
  console.log('loadRemoteVideo@@@@@@@@@@@@@@@2')
}

watchEffect(async () => {
  await loadLocalVideo()
  await loadRemoteVideo()
})

watch(
  () => props.remoteStream,
  (newStream) => {
    if (remoteVideoElement.value && newStream) {
      remoteVideoElement.value.srcObject = newStream
    }
  },
  { immediate: true }
)
</script>

<style scoped>

.image-group {
  display: flex;
  flex-direction: row;
  margin-bottom: 10px; /* 각 그룹간의 마진 */
}

.captured-image {
  margin-right: 5px; /* 이미지간의 마진 */
}


.container {
  /* position: relative; */
  width: 100%;
  height: 100vh;
}

/* #local-video-container {
position: absolute;
width: 20%;
height: auto;
right: 10px; 
bottom: 10px;
z-index: 2;
} */

.local-video-container {
}

.localVideo {
  transform: scaleX(-1);
}

/* #remote-video-container {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1; 
  background-color: grey;
} */

.remote-video-container {
}

.remoteVideo {
  transform: scaleX(-1);
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

.hover-text {
  transition: transform 0.3s ease-in-out; /* transform 속성에 대한 전환 효과 적용 */
}

.hover-text:hover {
  transform: translateX(10px); /* 호버 시 글자를 오른쪽으로 10픽셀 이동 */
  cursor: pointer;
}

</style>
