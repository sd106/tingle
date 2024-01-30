<template>
  <main class="container">
    <StarMenu :id="id" />
    <h1>여긴 다 페이지네이션으로</h1>
    <div class="container border">
      <RouterLink :to="`/${id}/wish/manage`">
        스타만 볼 수 있는스타의 위시 관리
      </RouterLink>
    </div>
    <!-- 모든 위시들은 누르면 각각 모달 detail로 -->
    <div class="container border">
      <!-- v-for -->
      <!-- 뭔가 추가 페이지로 결과 확인할 수 있는데 구독자만 볼 수 있다거나 -->
      스타가 완료한 위시
    </div>
    <div class="container border">
      <!-- v-for -->
      스타가 선택한 위시
    </div>
    <div class="container border">
      <div class="container border">
        <button>좋아요순</button>
        <button>신규순</button>
      </div>
      <!-- v-for -->
      다양한 위시 좋아요 순 정렬/ 신규순 정렬
    </div>
    <RouterLink :to="`/${id}/wish/create`">
      소원 빌기
    </RouterLink>
  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { RouterLink } from 'vue-router'


import StarMenu from '@/components/StarMenu/StarMenu.vue';

const props = defineProps(['id']);
const id = ref(props.id);

</script>
