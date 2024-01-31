<template>
  <main class="container">
    <StarMenu :id="id" />
    <div class="container border">
      <!-- v-for -->
      <!-- 누르면 detail 모달 -->
      상품목록
      <div v-if="products">
        <!-- 정렬기준 수정할 수 있게 row 하나 추가해서 변경 가능하게 -->
        <!-- 이 div를 누르면 detail 뜨게 -->
        <div v-for="product in products" :key="product">
          <p>{{ product }}</p>
        </div>
      </div>
      <div v-else>
        스타의 상품 목록이 없습니다.
      </div>
    </div>
    <StoreCreate />
  </main>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios';

import StarMenu from '@/components/StarMenu/StarMenu.vue';
import StoreCreate from '@/components/StarMenu/Store/StoreCreate.vue';

const props = defineProps(['id']);
const id = ref(props.id);

const products = ref(null);
// props로 받은거 나오게 설정해야함
const starName = 'mo'

const getProducts = async (starName: string) => {
  try {
    const response = await axios.get(`http://localhost:8080/product/getByStarName/${starName}`);
    if (response.data.resultCode === 'SUCCESS') {
      products.value = response.data.data;
    }
  } catch (error) {
    console.error('스타의 상품 목록 조회 중 오류 발생', error);
  }
};


onMounted(() => {
  getProducts(starName);
})


</script>