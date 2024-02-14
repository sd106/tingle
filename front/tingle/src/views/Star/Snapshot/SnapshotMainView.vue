<template>
  <main class="container">
    <StarMenu :id="id" />
    <div class="main-layout pt-5">
      <!-- 상단 메뉴 -->
      <!-- 상세 페이지 섹션 (빨간 네모 부분) -->
      <section v-if="wishStore.selectedSnapshot" class="detail-section cont mb-5">
        <SnapShotDetail :selectedSnapshot="wishStore.selectedSnapshot" :starid="id" />
      </section>
    </div>
    <div class="d-flex justify-content-between align-items-center my-4 mx-3">
      <div>
        <button class="btn me-2 fs-5 fw-bold text-secondary" @click="loadSnapshots">
          ✧ 최신순
        </button>
        <button class="btn fs-5 fw-bold text-secondary" @click="loadSnapshotsBylikes">
          🔥 좋아요순
        </button>
      </div>
      <div>
        <RouterLink :to="`/${id}/snapshot/create`" class="btn btn-secondary">글쓰기</RouterLink>
      </div>
    </div>

  <div class="main-layout">
    <!-- 스냅샷 목록 섹션 (파란색 부분) -->
    <section class="snapshot-list-section">
      <div class="snapshot-list-container" ref="containerRef" @scroll="handleScroll">
        <span v-for="snapshot in filteredSnapshot1" :key="snapshot.id" class="snapshot-item">
          <div class="tw-relative">
            <img @click="wishStore.selectSnapshot(snapshot.id)" :src="snapshot.imageUrl" alt="Snapshot Image" class="snapshot-image my-1">
            <span v-if="snapshot.isStarLike" class="tw-absolute tw-top-0 tw-right-0" style="font-size: 24px;"><img src="" alt=""></span>
          </div>
        </span>
        <span v-for="snapshot in filteredSnapshot2" :key="snapshot.id" class="snapshot-item">
          <div class="tw-relative">
            <img @click="wishStore.selectSnapshot(snapshot.id)" :src="snapshot.imageUrl" alt="Snapshot Image" class="snapshot-image my-1">
            <span v-if="snapshot.isStarLike" class="tw-absolute tw-top-0 tw-right-0" style="font-size: 24px;">⭐</span>
          </div>
        </span>
        <span v-for="snapshot in filteredSnapshot3" :key="snapshot.id" class="snapshot-item">
          <div class="tw-relative">
            <img @click="wishStore.selectSnapshot(snapshot.id)" :src="snapshot.imageUrl" alt="Snapshot Image" class="snapshot-image my-1">
            <span v-if="snapshot.isStarLike" class="tw-absolute tw-top-0 tw-right-0" style="font-size: 24px;">⭐</span>
          </div>
        </span>
        <span v-for="snapshot in filteredSnapshot4" :key="snapshot.id" class="snapshot-item">
          <div class="tw-relative">
            <img @click="wishStore.selectSnapshot(snapshot.id)" :src="snapshot.imageUrl" alt="Snapshot Image" class="snapshot-image my-1">
            <span v-if="snapshot.isStarLike" class="tw-absolute tw-top-0 tw-right-0" style="font-size: 24px;">⭐</span>
          </div>
        </span>
        <span v-for="snapshot in filteredSnapshot5" :key="snapshot.id" class="snapshot-item">
          <div class="tw-relative">
            <img @click="wishStore.selectSnapshot(snapshot.id)" :src="snapshot.imageUrl" alt="Snapshot Image" class="snapshot-image my-1">
            <span v-if="snapshot.isStarLike" class="tw-absolute tw-top-0 tw-right-0" style="font-size: 24px;">⭐</span>
          </div>
        </span>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import axios from 'axios'
import { useUserStore } from '@/stores/user'
import { useSnapshotStore } from '@/stores/snapshot'
import StarMenu from '@/components/StarMenu/StarMenu.vue'
import type { Starinfo, SnapshotType } from '@/common/types'
import SnapShotDetail from '../../../components/StarMenu/SnapShot/SnapShotDetail.vue'

const store = useUserStore()
const wishStore = useSnapshotStore()
const props = defineProps(['id'])
const id = ref(props.id)

const snapshots = ref<SnapshotType[]>([])
const display = ref<Starinfo[]>([])
const containerRef = ref<HTMLElement | null>(null)

const loadSnapshots = async (): Promise<void> => {
  try {
    console.log(id)
    const response = await axios.get(`http://localhost:8080/snapshot/star/${id.value}/created`)
    snapshots.value = response.data.AllSnapShot
    console.log('최신순')
  } catch (error) {
    console.error(error)
  }
}

const loadSnapshotsBylikes = async (): Promise<void> => {
  try {
    const response = await axios.get(`http://localhost:8080/snapshot/star/${id.value}/likes`)
    snapshots.value = response.data.AllSnapShot
    console.log('좋아요순')
  } catch (error) {
    console.error(error)
  }
}

// const loadMore = function (): void {
//   // 스크롤 관련 로딩 로직
//   const newData = store.allstarinfo.slice(display.value.length, display.value.length + 10);
//   display.value = [...display.value, ...newData];
// };

const handleScroll = function (): void {
  // 스크롤 이벤트 처리 로직
  const container = containerRef.value
  if (container) {
    // 스크롤이 하단에 도달했을 때 추가 데이터 로딩
    if (container.scrollTop + container.clientHeight >= container.scrollHeight) {
      // loadMore();
    }
  }
}

onMounted(() => {
  wishStore.selectedSnapshot = null
  loadSnapshots()
})

const filteredSnapshot1 = computed(() => {
  return snapshots.value.filter((_, index) => index % 5 === 0)
})
const filteredSnapshot2 = computed(() => {
  return snapshots.value.filter((_, index) => index % 5 === 1)
})
const filteredSnapshot3 = computed(() => {
  return snapshots.value.filter((_, index) => index % 5 === 2)
})
const filteredSnapshot4 = computed(() => {
  return snapshots.value.filter((_, index) => index % 5 === 3)
})
const filteredSnapshot5 = computed(() => {
  return snapshots.value.filter((_, index) => index % 5 === 4)
})
</script>

<style>
.cont {
  background-color: #fff;
  /* 배경색 */
  border-radius: 20px;
  /* 테두리 둥글기 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.4);
  /* 그림자 효과 */
  overflow: hidden;
  /* 내부 요소가 테두리를 넘어가지 않도록 설정 */
  border: 1px solid white;
  /* 테두리 색상 설정 */
}

.container::-webkit-scrollbar {
  display: none;
  /* 스크롤바 숨기기 */
}

.image-container img {
  width: 100%;
  /* 이미지 컨테이너의 너비에 맞춥니다 */
  height: auto;
  /* 모든 이미지의 높이를 150px로 설정합니다 */
  object-fit: cover;
  /* 이미지 비율을 유지하면서 컨테이너를 채웁니다 */
}

.off-canvas {
  /* 오프캔버스 스타일 */

  width: 100%;
  /* 상세 페이지의 너비 */
  height: 100%;
  /* 전체 높이 */
  background: white;
  /* 배경색 */
  z-index: 1000;
  /* 다른 요소 위에 표시 */
  /* 여기에 애니메이션 효과 등을 추가할 수 있습니다 */
}

.main-layout {
  display: flex;
  flex-direction: column;
  /* 수직 정렬 */
  align-items: center;
}

.detail-section {
  width: 100%;
  /* 상세 페이지가 가능한 많은 공간을 차지하게 함 */
  /* 추가 스타일링 */
}

.snapshot-list-container {
  display: flex;
  flex-wrap: wrap;

  align-items: flex-start;
  overflow-x: auto;
}

.snapshot-item {
  flex: 0 0 calc(19% - 10px);
  margin: 5px;
  cursor: pointer;
  width: 100%;
  /* 이미지 컨테이너가 갖는 실제 너비 */
  box-sizing: border-box;
}

.snapshot-image:hover .snapshot-image {
  opacity: 0.6;
  /* 이미지 어두워짐 효과 */
}

.snapshot-image {
  width: 100%;
  /* 이미지가 항목의 너비를 꽉 채우도록 함 */
  height: auto;
  /* 이미지의 높이를 자동으로 설정 */
  object-fit: cover;
  /* 이미지가 비율을 유지하면서 항목을 꽉 채우도록 함 */
  transition: opacity 0.3s ease;
  /* 부드러운 효과를 위한 전환 */
}
</style>
