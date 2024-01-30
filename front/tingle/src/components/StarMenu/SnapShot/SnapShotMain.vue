<template>
    <main>
        <div class="container d-flex flex-wrap" ref="containerRef" @scroll="handleScroll"
            style="height: 480px; overflow-y: auto;">
            <div v-for="star in display" :key="star.name" class="p-2 d-flex flex-column align-items-center"
                style="width: 18%;">
                <div>
                    <img :src="star.image" alt="not">
                </div>
                <div class="star-name">{{ star.name }}</div>
            </div>
        </div>
    </main>
</template>
  
<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/stores/user';
import type { Star } from '@/common/types';

const store = useUserStore()

const display = ref<Star[]>([]);
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