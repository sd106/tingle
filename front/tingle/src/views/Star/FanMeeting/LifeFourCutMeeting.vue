<template>
  <div class="d-flex container">
    <!-- 팬일때 -->
    <div>

      <div class="d-flex mt-3">
        <h2 class="fw-bold hover-text m-2" @click="captureAndMergeImages">캡처</h2>
    <h2 class="fw-bold hover-text m-2" @click="downloadAllMergedImages">이미지 다운로드</h2>
      <!-- <h2 @click="captureVideo" class="fw-bold hover-text m-3">캡처</h2>
      <h2 @click="mergeAndDownloadImages" class="fw-bold hover-text m-3">다운로드</h2> -->
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

    <div>
    <!-- 이미지 캡처 및 합치기 버튼 -->

    <!-- 합쳐진 이미지들을 표시하는 영역 -->
    <div v-for="(image, index) in mergedImages" :key="index" class="ms-3 remoteVideo merged-image-container" style="width: 720px;">
      <img :src="image" :alt="'Merged Image ' + index">
    </div>

    <!-- 모든 합쳐진 이미지를 한 장의 이미지로 다운로드하는 버튼 -->
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


const props = defineProps<{
  localStream: MediaStream | undefined
  remoteStream: MediaStream | undefined
}>()

const controlsVisible = ref(false)
const isVideoOn = ref(true)
const isAudioOn = ref(true)
const store = useUserStore()
const isStar = store.isStar





const localVideoElement = ref<HTMLVideoElement | null>(null)
const remoteVideoElement = ref<HTMLVideoElement | null>(null)
const mergedImages = ref<string[]>([])

// imgLoadPromise 함수 추가
const imgLoadPromise = (src: string): Promise<HTMLImageElement> => {
  return new Promise((resolve, reject) => {
    const img = new Image();
    img.onload = () => resolve(img);
    img.onerror = () => reject(new Error(`Failed to load image at ${src}`));
    img.src = src;
  });
}

const captureAndMergeImages = async () => {
  const localImage = await captureVideo(localVideoElement.value)
  const remoteImage = await captureVideo(remoteVideoElement.value)
  if (localImage && remoteImage) {
    await mergeTwoImagesAndSave(localImage, remoteImage)
  }
}

const captureVideo = (videoElement: HTMLVideoElement | null): Promise<string> => {
  return new Promise((resolve, reject) => {
    if (!videoElement) {
      reject('Video element is null')
      return
    }
    const canvas = document.createElement('canvas')
    const context = canvas.getContext('2d')
    canvas.width = videoElement.videoWidth
    canvas.height = videoElement.videoHeight
    context?.drawImage(videoElement, 0, 0, canvas.width, canvas.height)
    resolve(canvas.toDataURL('image/png'))
  })
}

// const mergeTwoImagesAndSave = async (localImage: string, remoteImage: string) => {
//   const [localImg, remoteImg] = await Promise.all([imgLoadPromise(localImage), imgLoadPromise(remoteImage)])
//   const canvas = document.createElement('canvas')
//   const context = canvas.getContext('2d')
//   canvas.width = localImg.width + remoteImg.width
//   canvas.height = Math.max(localImg.height, remoteImg.height)
//   context?.drawImage(localImg, 0, 0)
//   context?.drawImage(remoteImg, localImg.width, 0)
//   mergedImages.value.push(canvas.toDataURL('image/png'))
// }
const mergeTwoImagesAndSave = async (localImage: string, remoteImage: string) => {
  const imgLoadPromise = (src: string): Promise<HTMLImageElement> => {
    return new Promise((resolve, reject) => {
      const img = new Image();
      img.onload = () => resolve(img);
      img.onerror = reject;
      img.src = src;
    });
  };

  const [localImg, remoteImg] = await Promise.all([
    imgLoadPromise(localImage),
    imgLoadPromise(remoteImage)
  ]);

  const canvas = document.createElement('canvas');
  const context = canvas.getContext('2d');
  canvas.width = localImg.width + remoteImg.width;
  canvas.height = Math.max(localImg.height, remoteImg.height);

  context?.drawImage(localImg, 0, 0);
  context?.drawImage(remoteImg, localImg.width, 0);

  // 배열에서 네 개의 이미지만 유지
  if (mergedImages.value.length >= 4) {
    mergedImages.value.shift(); // 배열의 첫 번째 항목 제거
  }
  mergedImages.value.push(canvas.toDataURL('image/png')); // 새 이미지 추가
};

// 모든 합쳐진 이미지를 한 장의 이미지로 다운로드하는 함수
const downloadAllMergedImages = async () => {
  const canvas = document.createElement('canvas');
  const context = canvas.getContext('2d');
  const imgs = await Promise.all(mergedImages.value.map(src => imgLoadPromise(src)))

  const totalHeight = imgs.reduce((acc, img) => acc + img.height, 0)
  const maxWidth = Math.max(...imgs.map(img => img.width))
  canvas.width = maxWidth
  canvas.height = totalHeight

  let yOffset = 0;
  mergedImages.value.forEach((url, index) => {
    const img = new Image();
    img.src = url;
    img.onload = () => {
      // 좌우 반전을 위해 x축 방향으로 -1을 곱함
      context?.save(); // 현재 상태를 저장
      context?.scale(-1, 1); // x축 방향으로 반전
      // 이미지를 반전된 상태로 그리기 위해 x 위치 조정
      context?.drawImage(img, -canvas.width, yOffset, canvas.width, img.height);
      context?.restore(); // 캔버스 상태를 복원

      if (index === mergedImages.value.length - 1) {
        // 마지막 이미지를 그린 후 최종 이미지 처리
        const finalImage = canvas.toDataURL('image/png');
        const link = document.createElement('a');
        link.href = finalImage;
        link.download = 'merged-images-flipped.png';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      }
      yOffset += img.height;
    };
  });
};






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