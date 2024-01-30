<template>
  <main class="container">
    <StarMenu :id="id" />
    <div class="container border">
      배너사진
    </div>
    <div class="container border">
      <!-- v-if 걸어서 본인인지 확인해야할듯? -->
      <RouterLink :to="`/${id}/home/manage`">
        스타면 설정버튼
      </RouterLink>
      <!-- 프로필 사진 > 누르면 확대-->
      <div class="container border">
        <img src="" alt="사진">
        <!-- 닉네임 -->
        <p>닉네임</p>
        <!-- 구독 여부 창 -->
        <p>구독 여부</p>
        <!-- sns주소 링크 -->
        <p>sns 주소</p>
      </div>
    </div>
    <div class=" container border">
      설명
    </div>
    <div class=" container border">
      추가 블록
    </div>
  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { RouterLink } from 'vue-router'

import StarMenu from '@/components/StarMenu/StarMenu.vue';

const props = defineProps(['id']);
const id = ref(props.id);

</script>

