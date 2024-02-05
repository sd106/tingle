<template>
    <main>
        <div>
        <h2>유저의 모든 주문 결과 조회</h2>
        <input type="number" v-model="userId">
        <button @click="getOrderByUserId">조회하기</button>
        <div v-if="responseMessageGetByUserId">
            <p class="fw-bold mt-3 mb-1">주문 상품</p>
            <hr class="m-0 mb-3" style="border:0; height:3px; background: #000;">
            <div class="d-flex">
              <div class="col-2">
              </div>
              <div class="col-8 text-center">
                <p class="fw-bold">상품 정보</p>
              </div>
              <div class="col-2 text-center">
                <p class="fw-bold">상품 금액</p>
              </div>
            </div>
            <hr>
            <div class="border-bottom container p-0 justify-content-between" v-for="res in responseMessageGetByUserId" :key="res.fanId" @click="expandProduct(res)">
              <div class="d-flex">  
              <div class="col-2">
                    <img  :src="res.goods.imageUrl[0].url" alt="" style="width: 100px;">
                </div>

                <div class=" p-3 col-8 d-flex flex-column">
                    <span class="mb-3">{{ res.goods.name }}</span>
                    <span>{{ res.goods.content }}</span>
                </div>

                <div class=" p-3 col-2">
                    <span>{{ res.goods.price }}</span>
                </div>
              </div>
                <div v-if="expandedProduct === res">
                  <StoreOrderDetail2 :order="res"/>
                  <button @click="deleteOrder(res.orderId)">주문 삭제</button>
                </div>
            </div>
        </div>
        </div>
    </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios';
import type { OrderResponse2 } from '@/common/types';
const responseMessageGetByUserId = ref<OrderResponse2[]>([]);
const userId = ref<number>();
  
const getOrderByUserId =async () => {
  try {
    const response = await axios.get(`http://localhost:8080/order/getUserOrders/${userId.value}`);
    responseMessageGetByUserId.value = response.data.data
    console.log(response.data.data)
  } catch (error) {
    console.log(error)
  }
}

const deleteOrder =async (orderId : number) => {
    try {
      const response = await axios.post(`http://localhost:8080/order/delete/${orderId}`);
      console.log(response.data)
      getOrderByUserId();
    } catch (error) {
      console.log(error);
    } 
  }

import StoreOrderDetail2 from '@/components/StarMenu/Store/StoreOrderDetailUser.vue'

  const expandedProduct = ref<OrderResponse2 | null>(null)

  const expandProduct = (order: OrderResponse2) => {
    expandedProduct.value = expandedProduct.value === order ? null : order
  }

</script>
