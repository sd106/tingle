<template>
  <main class="container">
    <StarMenu :id="props.id" class="mb-5" />
    <div class="container">
      <div class="d-flex justify-content-between my-2">
        <!-- 원래 상품 목록이라는 글 있던 자리 -->
        <h1 class="fw-bold"></h1>
        <div class="mb-4">
          <RouterLink :to="`/store/orders`">
            <button class="tw-btn tw-btn-outline mx-2">주문 목록</button>
          </RouterLink>
          <RouterLink :to="`/${id}/store/create`">
            <button class="tw-btn tw-btn-outline mx-2">상품 등록</button>
          </RouterLink>
        </div>
      </div>

      <!-- //////정렬기준 수정할 수 있게 row 하나 추가해서 변경 가능하게 -->
      <!-- //////available = false면 비활성화하고 회색처리  -->
      <div v-if="altProducts" class="row">
        <div
          v-for="product in altProducts"
          :key="product.productId"
          class="col-xl-3 col-lg-6 col-md-6 col-sm-12 mb-4"
        >
          <RouterLink :to="`/${id}/store/star/${product.productId}`" class="tw-flex tw-flex-col">
            <div
              class="product-card tw-rounded-lg tw-transition tw-mb-5 border p-1"
              v-if="product.available === true && product.amount > 0"
            >
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
            <div
              class="product-card tw-rounded-lg tw-transition tw-mb-5 border p-1"
              v-else
              style="background-color: rgb(177, 171, 171)"
            >
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
import { useUserStore } from '@/stores/user'
import StarMenu from '@/components/StarMenu/StarMenu.vue'

// 상속
const props = defineProps(['id'])
const id = ref(props.id)
const { starState } = useUserStore()

// 전체 상품 출력
import type { Goods } from '@/common/types'
const products = ref<Goods[] | null>(null)
////// props로 받은거 나오게 설정해야함
const starName = starState?.username

const getAllProducts = async (starName: string) => {
  try {
    const response = await axios.get(`http://localhost:8080/product/getByStarName/${starName}`)
    // const response = await axios.get(`http://localhost:8080/product/getByStarName/${starName}`);
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
  getAllProducts(starName!)
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
