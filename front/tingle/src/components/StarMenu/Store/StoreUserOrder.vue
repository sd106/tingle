<template>
  <main>
    <div>
      <!-- <input type="number" v-model="userId"> -->
      <!-- <button @click="getOrderByUserId">조회하기</button> -->
      <div v-if="responseMessageGetByUserId">
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
          v-for="res in responseMessageGetByUserId"
          :key="res.fanId"
          @click="expandProduct(res)"
        >
          <div class="d-flex row">
            <div class="col-2">
              <!-- <img  :src="res.goods.imageUrl[0].url" alt="" style="width: 100px;"> -->
              <RouterLink
                :to="`/${res.goods.starId}/store/${res.goods.productId}`"
                class="tw-flex tw-flex-col"
              >
                <img :src="res.goods.imageUrl[0].url" alt="" />
              </RouterLink>
            </div>

            <div class="p-3 col-8 d-flex flex-column">
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
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { RouterLink } from 'vue-router'
import { useUserStore } from '@/stores/user'
import type { OrderResponse2 } from '@/common/types'

const responseMessageGetByUserId = ref<OrderResponse2[]>([])
const { fanState } = useUserStore()
const userId = fanState?.id

const getOrderByUserId = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/order/getUserOrders/${userId}`)
    responseMessageGetByUserId.value = response.data.data
    console.log(response.data.data)
    // var textOnly = responseMessageGetByUserId.replace(/<[^>]*>/g, '');
    // console.log(textOnly); // "예시" 를 출력합니다.
  } catch (error) {
    console.log(error)
  }
}

function removeHtmlTags(html: string): string {
  const div = document.createElement('div')
  div.innerHTML = html
  return div.textContent || div.innerText || ''
}

onMounted(() => {
  getOrderByUserId()
})

function truncateText(text: string, maxLength: number): string {
  if (text.length > maxLength) {
    return text.substring(0, maxLength) + '...'
  } else {
    return text
  }
}

const deleteOrder = async (orderId: number) => {
  try {
    const response = await axios.post(`http://localhost:8080/order/delete/${orderId}`)
    console.log(response.data)
    getOrderByUserId()
  } catch (error) {
    console.log(error)
  }
}

import StoreOrderDetail2 from '@/components/StarMenu/Store/StoreOrderDetailUser.vue'

const expandedProduct = ref<OrderResponse2 | null>(null)

const expandProduct = (order: OrderResponse2) => {
  expandedProduct.value = expandedProduct.value === order ? null : order
}
</script>

<style>
.centered-content {
  display: flex; /* Flex 컨테이너 설정 */
  justify-content: center; /* 가로 축에서 중앙 정렬 */
  align-items: center; /* 세로 축에서 중앙 정렬 */
}
</style>
