<template>
  <main class="container">
    <StarMenu :id="id" />
    <!-- 선택한 녀석만 표시 -->
    <div class="container border">
      <button>
        정렬1
      </button>
      <button>
        정렬2
      </button>
    </div>
    <div class="container d-flex flex-wrap" ref="containerRef" @scroll="handleScroll"
      style="height: 480px; overflow-y: auto;">
      <!-- 오프캔버스로 디테일 띄우기 -->
      <!-- v-if로 스타가 좋아하거나 작성한거라면 추가 스타일 -->
      <div v-for="star in display" :key="star.nickName" class="p-2 d-flex flex-column align-items-center"
        style="width: 18%;">
        <div>
          <img :src="star.image" alt="not">
        </div>
        <!-- 제목과 태그 좋아요-->
        <div class="star-name">{{ star.nickName }}</div>
      </div>
    </div>
    <RouterLink :to="`/${id}/snapshot/create`">
      글쓰기
    </RouterLink>

  </main>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { RouterLink } from 'vue-router'
import { useUserStore } from '@/stores/user';
import type { Starinfo } from '@/common/types';


import StarMenu from '@/components/StarMenu/StarMenu.vue';


const props = defineProps(['id']);
const id = ref(props.id);

const store = useUserStore()

const display = ref<Starinfo[]>([]);
const containerRef = ref<HTMLElement | null>(null);


const loadMore = function (): void {
  // 10씩 더 출력
  const newData = store.allstarinfo.slice(display.value.length, display.value.length + 10);
  display.value = [...display.value, ...newData];
};

const handleScroll = function (): void {
  const container = containerRef.value;
  if (container) {
    // 스크롤이 하단에 도달했을 때 추가 데이터 로딩
    if (container.scrollTop + container.clientHeight >= container.scrollHeight) {
      loadMore();
    }
  }
};

// 초기 20명 출력
const loadInitial = function (): void {
  display.value = store.allstarinfo.slice(0, 20);
}

onMounted(() => {
  loadInitial();
});

</script>
  
<style>
.container::-webkit-scrollbar {
  display: none;
  /* 스크롤바 숨기기 */
}
</style>
