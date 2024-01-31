<template>
  <main class="container">
    <StarMenu :id="id" />
    <div class="container border">
      <!-- v-for -->
      <!-- 누르면 detail 모달 -->
      상품목록
      <!-- 상품마다 수정, 삭제 버튼 있어서 수정은 수정 모달 -->
      <!-- create 모달 -->
    </div>
  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'

import StarMenu from '@/components/StarMenu/StarMenu.vue';


const props = defineProps(['id']);
const id = ref(props.id);

</script>