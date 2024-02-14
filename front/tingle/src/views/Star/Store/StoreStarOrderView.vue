<template>
  <main class="container">
    <StarMenu :id="starid" class="mb-5" />
    <div>
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
        <div class="border-bottom container p-0 justify-content-between" v-for="res in responseMessageGetByStarName"
          :key="res.fan.id" @click="expandProduct(res)">
          <div class="d-flex row">
            <div class="col-3">
              <div class="tw-flex tw-flex-col img-container"
                :style="`background-image: url('${res.goods.imageUrl[0].url}');`">
                <!-- <img :src="res.goods.imageUrl[0].url" alt="" /> -->
              </div>
            </div>

            <div class="p-3 col-7 d-flex flex-column">
              <h2 class="my-3 fw-bold">{{ res.goods.name }}</h2>
              <span>{{ truncateText(removeHtmlTags(res.goods.content), 30) }}</span>
            </div>

            <div class="p-3 col-2 centered-content p-3 col-2">
              <h2 class="fw-bold">₩ {{ res.goods.price }}</h2>
            </div>
          </div>
          <div v-if="expandedProduct === res" @click.stop="">
            <StoreOrderDetail :order="res" />
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
import StarMenu from '@/components/StarMenu/StarMenu.vue'

const { starState } = useUserStore()
const responseMessageGetByStarName = ref<OrderResponse[]>([])
const starname = starState!.username
const starid = starState!.id

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
    const response = await axios.get(`https://i10d106.p.ssafy.io/api/order/getByStarName/${starname}`)
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
</script>

<style>
.custom-hr {
  border-top: 3px solid #000000;
  /* 굵기를 3px로 조정하고 색상은 검정으로 설정 */
}

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
