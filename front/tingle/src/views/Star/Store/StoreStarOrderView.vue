<template>
  <main class="container">
    <div>
      <h2>스타 모든 주문 조회</h2>
      <div v-if="responseMessageGetByStarName">
        <p class="fw-bold mt-3 mb-1">주문 상품</p>
        <hr class="m-0 mb-3" style="border: 0; height: 3px; background: #000" />
        <div class="d-flex">
          <div class="col-2"></div>
          <div class="col-8 text-center">
            <p class="fw-bold">상품 정보</p>
          </div>
          <div class="col-2 text-center">
            <p class="fw-bold">상품 금액</p>
          </div>
        </div>
        <hr />
        <div
          class="border-bottom container p-0 justify-content-between"
          v-for="res in responseMessageGetByStarName"
          :key="res.fan.id"
          @click="expandProduct(res)"
        >
          <div class="d-flex row">
            <div class="col-2">
              <img :src="res.goods.imageUrl[0].url" alt="" />
            </div>
            <div class="p-3 col-8 d-flex flex-column">
              <span class="my-3">{{ res.goods.name }}</span>
              <span>{{ truncateText(removeHtmlTags(res.goods.content), 30) }}</span>
            </div>

            <div class="p-3 col-2 centered-content p-3 col-2">
              <h3 calss="fw-bold">{{ res.goods.price }}</h3>
            </div>
          </div>

          <!-- <div class="p-3 col-8 d-flex flex-column">
              <RouterLink
                :to="`/${res.goods.starId}/store/${res.goods.productId}`"
                class="tw-flex tw-flex-col d-flex"
              >
                <h2 class="my-3">{{ res.goods.name }}</h2>
                <span class="">{{ truncateText(removeHtmlTags(res.goods.content), 30) }}</span>
              </RouterLink>
            </div>

            <div class="centered-content p-3 col-2">
              <h3 class="fw-bold">$ {{ res.goods.price }}</h3>
            </div> -->

          <div v-if="expandedProduct === res" @click.stop="">
            <StoreOrderDetail :order="res" />
            <button @click="deleteOrder(res.orderId)">주문 삭제</button>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useUserStore } from '@/stores/user'
import type { OrderResponse } from '@/common/types/index.ts'

const { starState } = useUserStore()
const responseMessageGetByStarName = ref<OrderResponse[]>([])
const starname = starState!.username

onMounted(() => {
  getStarOrder()
})

function removeHtmlTags(html: string): string {
  const div = document.createElement('div')
  div.innerHTML = html
  return div.textContent || div.innerText || ''
}
function truncateText(text: string, maxLength: number): string {
  if (text.length > maxLength) {
    return text.substring(0, maxLength) + '...'
  } else {
    return text
  }
}

const getStarOrder = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/order/getByStarName/${starname}`)
    responseMessageGetByStarName.value = response.data.data
    console.log(response.data.data)
    console.log(response.data)
  } catch (error) {
    console.log(error)
  }
}

import StoreOrderDetail from '@/components/StarMenu/Store/StoreOrderDetail.vue'

const expandedProduct = ref<OrderResponse | null>(null)

const expandProduct = (order: OrderResponse) => {
  expandedProduct.value = expandedProduct.value === order ? null : order
}

const deleteOrder = async (orderId: number) => {
  try {
    const response = await axios.post(`http://localhost:8080/order/delete/${orderId}`)
    console.log(response.data)
    getStarOrder()
  } catch (error) {
    console.log(error)
  }
}
</script>

<style>
.custom-hr {
  border-top: 3px solid #000000; /* 굵기를 3px로 조정하고 색상은 검정으로 설정 */
}

.centered-content {
  display: flex; /* Flex 컨테이너 설정 */
  justify-content: center; /* 가로 축에서 중앙 정렬 */
  align-items: center; /* 세로 축에서 중앙 정렬 */
}
</style>
