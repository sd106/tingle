<!-- <template>
  <div>
    <ul>
      <li v-for="snapshot in snapshots" :key="snapshot.id" @click="goToDetail(snapshot.id)">
        <img :src="snapshot.imageUrl" alt="Snapshot Image">
        <p>{{ snapshot.username }}</p>
      </li>
    </ul>
    <button @click="goToCreate">Create Snapshot</button>
  </div>
</template>



<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';

type SnapshotType = {
  id: number;
  imageUrl: string;
  username: string;
};

const store = useUserStore();

const snapshots = ref<SnapshotType[]>([]);
const router = useRouter();

const loadSnapshots = async (): Promise<void> => {
  try {
    await axios.get('http://localhost:8080/snapshot/')
    .then((res) => {
    
        snapshots.value = res.data.AllSnapShot;
        console.log(snapshots.value);
      
    });
  } catch (error) {
    console.error(error);
  }
};

onMounted(() => {
  loadSnapshots();
});

const goToCreate = () => {
  router.push(`/${store.starInfo?.starId}/snapshot/create`);
};

const goToDetail = (id: number) => {
  router.push(`/snapshot/${id}`);
};


</script>

<style>
.container::-webkit-scrollbar {
  display: none;
}  스크롤바 숨기기
</style> -->

<template>
  <main class="container">
    <StarMenu :id="id" />
    <div class="container border">
      <button>정렬1</button>
      <button>정렬2</button>
    </div>
    <div class="container d-flex flex-wrap" ref="containerRef" @scroll="handleScroll"
      style="height: 480px; overflow-y: auto;">
      <!-- 오프캔버스로 디테일 띄우기 -->
      <div v-for="snapshot in snapshots" :key="snapshot.id" @click="goToDetail(snapshot.id)"
        class="p-2 d-flex flex-column align-items-center image-container" style="width: 20%;">
        <img :src="snapshot.imageUrl" alt="Snapshot Image" class="snapshot-image">
        <div class="star-name">{{ snapshot.username }}</div>
      </div>
    </div>
    <RouterLink :to="`/${store.starInfo?.starId}/snapshot/create`">글쓰기</RouterLink>
  </main>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';
import StarMenu from '@/components/StarMenu/StarMenu.vue';
import type { Starinfo } from '@/common/types';

const store = useUserStore();
const router = useRouter();
const props = defineProps(['id']);
const id = ref(props.id);

type SnapshotType = {
  id: number;
  imageUrl: string;
  username: string;
};
const snapshots = ref<SnapshotType[]>([]);
const display = ref<Starinfo[]>([]);
const containerRef = ref<HTMLElement | null>(null);

const loadSnapshots = async (): Promise<void> => {
  try {
    const response = await axios.get('http://localhost:8080/snapshot/');
    snapshots.value = response.data.AllSnapShot;
    console.log(snapshots.value);
  } catch (error) {
    console.error(error);
  }
};

const loadMore = function (): void {
  // 스크롤 관련 로딩 로직
  // 10씩 더 출력
  const newData = store.allstarinfo.slice(display.value.length, display.value.length + 10);
  display.value = [...display.value, ...newData];
};

const handleScroll = function (): void {
  // 스크롤 이벤트 처리 로직
  const container = containerRef.value;
  if (container) {
    // 스크롤이 하단에 도달했을 때 추가 데이터 로딩
    if (container.scrollTop + container.clientHeight >= container.scrollHeight) {
      loadMore();
    }
  }
};

onMounted(() => {
  loadSnapshots();
});

const goToDetail = (id: number) => {
  router.push(`/snapshot/${id}`);
};
</script>

<style>
.container::-webkit-scrollbar {
  display: none; /* 스크롤바 숨기기 */
}

.image-container img {
  width: 100%;       /* 이미지 컨테이너의 너비에 맞춥니다 */
  height: 150px;     /* 모든 이미지의 높이를 150px로 설정합니다 */
  object-fit: cover; /* 이미지 비율을 유지하면서 컨테이너를 채웁니다 */
}
</style>