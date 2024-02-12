<template>
  <main class="container">
    <StarMenu :id="props.id" />
    <div class="container">
      <div class="d-flex justify-content-between my-2">
        <h1 class="fw-bold">상품 목록</h1>
      </div>
      <div v-if="altProducts" class="row">
        <div
          v-for="product in altProducts"
          :key="product.productId"
          class="col-xl-3 col-lg-6 col-md-6 col-sm-12 mb-4"
        >
          <RouterLink :to="`/${id}/store/user/${product.productId}`" class="d-flex flex-column">
            <div class="border p-1" v-if="product.available === true && product.amount > 0">
              <img
                :src="product.imageUrl[0]?.url"
                alt=""
                class="tw-w-full tw-h-72 tw-object-cover"
              />
              <div class="product-info tw-text-left tw-py-1">
                <p class="tw-text-md ms-2 tw-font-semibold tw-truncate">{{ product.name }}</p>
                <p class="tw-text-lg ms-2 tw-font-bold tw-text-gray-800">
                  {{ product.formattedPrice }}
                </p>
              </div>
            </div>
            <div v-else style="background-color: rgb(177, 171, 171)">
              <img
                :src="product.imageUrl[0]?.url"
                alt=""
                class="tw-w-full tw-h-72 tw-object-cover"
              />
              <div class="product-info tw-text-left tw-py-1">
                <p class="tw-text-md ms-2 tw-font-semibold tw-truncate">{{ product.name }}</p>
                <p class="tw-text-lg ms-2 tw-font-bold tw-text-gray-800">
                  {{ product.formattedPrice }}
                </p>
              </div>
            </div>
          </RouterLink>
        </div>
      </div>
      <div v-else>스타의 상품 목록이 없습니다.</div>
    </div>
  </main>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { RouterLink } from 'vue-router'
import axios from 'axios'

import StarMenu from '@/components/StarMenu/StarMenu.vue'

// 상속
const props = defineProps(['id'])
const id = ref(props.id)

// 전체 상품 출력
import type { Goods } from '@/common/types'
const products = ref<Goods[] | null>(null)
////// props로 받은거 나오게 설정해야함

const getAllProducts = async (id: number) => {
  try {
    const response = await axios.get(`https://i10d106.p.ssafy.io/product/getByStarId/${id}`)
    if (response.data.resultCode === 'SUCCESS') {
      products.value = response.data.data
    }
  } catch (error) {
    console.error('스타의 상품 목록 조회 중 오류 발생', error)
  }
}

const altProducts = computed(() => {
  return products.value?.map((product) => ({
    ...product,
    formattedPrice: new Intl.NumberFormat('ko-KR', { style: 'currency', currency: 'KRW' }).format(
      product.price
    )
  }))
})

onMounted(() => {
  getAllProducts(props.id)
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
