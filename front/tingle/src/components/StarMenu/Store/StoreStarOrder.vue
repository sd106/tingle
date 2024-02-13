<template>
  <main>
    <!-- =====================스타 이름으로(email아님) 스타에게 들어온 주문 목록 모두 조회============================= -->
    <StarMenu :id="starid" class="mb-5" />
    <div>
      <h2>스타 모든 주문 조회</h2>
      <input type="text" v-model="starname" />
      <button @click="getStarOrder">조회하기</button>
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
          <div class="d-flex">
            <div class="col-2">
              <img :src="res.goods.imageUrl[0].url" alt="" style="width: 100px" />
            </div>
            <div class="p-3 col-8 d-flex flex-column">
              <span class="mb-3">{{ res.goods.name }}</span>
              <span>{{ res.goods.content }}</span>
            </div>

            <div class="p-3 col-2">
              <span>{{ res.goods.price }}</span>
              {{ res.fan.id }}
            </div>
          </div>
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
import { ref } from 'vue'
import axios from 'axios'
import type { OrderResponse } from '@/common/types/index.ts'
import StarMenu from '@/components/StarMenu/StarMenu.vue'
import { useUserStore } from '@/stores/user'

const { starState } = useUserStore()
const starid = starState?.id

const responseMessageGetByStarName = ref<OrderResponse[]>([])
const starname = ref('')

const getStarOrder = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/order/getByStarName/${starname.value}`)
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
</style>
