<template>
  <main class="container">
    <StarMenu :id="id" />
    <div class="container border">
      <h2>상품목록</h2>
      <RouterLink :to="`/${id}/store/manage`">
        설정
      </RouterLink>
      <div v-if="products" class="d-flex row">
        <!-- //////정렬기준 수정할 수 있게 row 하나 추가해서 변경 가능하게 -->
        <!-- //////available = false면 비활성화하고 회색처리  -->
        <div v-for="product in products" :key="product.productId" class="border product-card col-3 p-3">
          <RouterLink :to="`/${id}/store/${product.productId}`">
            <img :src="product.imageUrl[0].url" alt="">
            <div class="product-info">
              <p>상품명: {{ product.name }}</p>
              <p>가격: {{ product.price }}</p>
            </div>
          </RouterLink>
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
import { RouterLink } from 'vue-router'
import axios from 'axios';

import StarMenu from '@/components/StarMenu/StarMenu.vue';
import StoreCreate from '@/components/StarMenu/Store/StoreCreate.vue';

// 상속
const props = defineProps(['id']);
const id = ref(props.id);


// 전체 상품 출력
import type { Goods } from '@/common/types'
const products = ref<Goods[] | null>(null);
////// props로 받은거 나오게 설정해야함
const starName = 'l2esm24'

const getAllProducts = async (starName: string) => {
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
  getAllProducts(starName);
})


// const selectedProduct = ref<Goods | null>(null);

</script>

<style scoped>
img {
  max-width: 100%;
  margin-bottom: 1rem;
}

.product-card {
  display: flex;
  flex-direction: column;
}

.product-info {
  padding-left: 3px;
  display: flex;
  flex-direction: column;
}

.product-info p {
  margin: 0.5rem 0;
  text-decoration: none;
}
</style>