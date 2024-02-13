<template>
  <main class="container">
    <ul class="mb-5 d-flex justify-content-around nav nav-underline">
      <li class="nav-item">
        <RouterLink :to="`/profile/userinfo`" class="nav-link router-link-custom"
          :class="{ active: isActive('/userInfo') }">회원 정보</RouterLink>
      </li>
      <li class="nav-item">
        <RouterLink :to="`/profile/storage`" class="nav-link router-link-custom"
          :class="{ active: isActive('/storage') }">보관함</RouterLink>
      </li>
      <li class="nav-item">
        <RouterLink :to="`/profile/orders`" class="nav-link router-link-custom" :class="{ active: isActive('/orders') }">
          주문 목록</RouterLink>
      </li>
      <li></li>
      <li></li>
      <li></li>
    </ul>
    <div>
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
        <div class="border-bottom container p-0 justify-content-between" v-for="res in responseMessageGetByUserId"
          :key="res.fanId" @click="expandProduct(res)">
          <div class="d-flex row">
            <div class="col-3">
              <RouterLink :to="`/${res.goods.starId}/profile/orders/detail/${res.goods.productId}`"
                class="tw-flex tw-flex-col img-container"
                :style="`background-image: url('${res.goods.imageUrl[0].url}');`">
              </RouterLink>
            </div>

            <div class="p-3 col-7 d-flex flex-column">
              <RouterLink :to="`/${res.goods.starId}/profile/orders/detail/${res.goods.productId}`"
                class="tw-flex tw-flex-col d-flex">
                <h2 class="my-3 fw-bold">{{ res.goods.name }}</h2>
                <span>{{ truncateText(removeHtmlTags(res.goods.content), 30) }}</span>
              </RouterLink>
            </div>

            <div class="centered-content p-3 col-2">
              <h2 class="fw-bold">₩ {{ res.goods.price }}</h2>
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
import { useRoute } from 'vue-router'

const route = useRoute()
const responseMessageGetByUserId = ref<OrderResponse2[]>([])
const { fanState } = useUserStore()
const userId = fanState!.id

const isActive = (path: string) => {
  return route.path.includes(path)
}

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

const expandedProduct = ref<OrderResponse2 | null>(null)

const expandProduct = (order: OrderResponse2) => {
  expandedProduct.value = expandedProduct.value === order ? null : order
}
</script>

<style>
.centered-content {
  display: flex;
  /* Flex 컨테이너 설정 */
  justify-content: center;
  /* 가로 축에서 중앙 정렬 */
  align-items: center;
  /* 세로 축에서 중앙 정렬 */
}

.img-container {
  width: 300px;
  /* 이미지 컨테이너의 너비 */
  height: 250px;
  /* 이미지 컨테이너의 높이 */
  background-position: center;
  /* 이미지를 중앙에 위치 */
  background-size: contain;
  /* 이미지가 컨테이너를 벗어나지 않도록 함 */
  background-repeat: no-repeat;
  /* 이미지 반복 없음 */
}
</style>
