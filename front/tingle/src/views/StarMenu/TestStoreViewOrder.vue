<template>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.css"/>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick-theme.css"/>
    <main class="container">
      <h1>스토어OrderTest</h1>
      <div>
        <StoreStarOrder/>
        <br>
        <hr>
        <StoreUserOrder/>
        <br>
        <br>
        <UserStorePicutre/>
        <br>
        <hr>
      </div>
      <h1>밑에부터 아님</h1>
      <!-- =====================주문 생성============================= -->

      <div>
        <h2>주문 생성</h2>
        <input v-model="userId" type="number" placeholder="사용자 ID">
        <input v-model="productId" type="number" placeholder="상품 ID">
        <button @click="createOrder">주문 생성</button>
        <p v-if="responseMessageCreate">{{ responseMessageCreate }}</p>
      </div>

      <!-- =====================주문 생성============================= -->

      <br>
      <hr>
      <br>

      <!-- =====================주문 삭제============================= -->

      <div>
        <h2>주문 삭제</h2>
        <input v-model="productId" type="number" placeholder="상품 ID">
        <button @click="deleteOrder">주문 삭제</button>
        <p v-if="responseMessageDelete">{{ responseMessageDelete }}</p>
      </div>


      <!-- =====================주문 삭제============================= -->

      <br>
      <hr>
      <br>

      <!-- =====================스타 이름으로(email아님) 스타에게 들어온 주문 목록 모두 조회============================= -->

      <div>
        <h2>스타 모든 주문 조회</h2>
        <input type="text" v-model = "starname">
        <button @click="getStarOrder">조회하기</button>
        <p v-if="responseMessageGetByStarName">
        <span v-for="res in responseMessageGetByStarName" :key="res">
          {{ res }}
        </span>
        </p>
      </div>

      <!-- =====================스타 이름으로(email아님) 스타에게 들어온 주문 목록 모두 조회============================= -->
      
      <br>
      <hr>
      <br>

      <!-- =====================주문 pk로 주문 디테일 조회============================= -->

      <div>
        <h2>주문 디테일</h2>
        <input type="number" v-model="productId">
        <button @click="getOrderDetail">조회하기</button>
        <p v-if="responseMessageDetail"> {{ responseMessageDetail }} </p>
      </div>




      <!-- =====================주문 pk로 주문 디테일 조회============================= -->

      <br>
      <hr>
      <br>

      <!-- =====================유저의 모든 주문 결과 조회============================= -->
      
      <div>
        <h2>유저의 모든 주문 결과 조회</h2>
        <input type="number" v-model="userId">
        <button @click="getOrderByUserId">조회하기</button>
        <p v-if="responseMessageGetByUserId"> {{ responseMessageGetByUserId }} </p>
      </div>


      <!-- =====================유저의 모든 주문 결과 조회============================= -->


    </main>
  </template>
  
  <script setup lang="ts">
  import StoreStarOrder from '@/components/StarMenu/Store/StoreStarOrder.vue';
  import StoreUserOrder from '@/components/StarMenu/Store/StoreUserOrder.vue';
  import UserStorePicutre from '@/components/StarMenu/Store/UserStorePicture.vue'

  import { ref } from 'vue'
  import axios from 'axios';
  
  const userId = ref(0);
  const productId = ref(0);
  const responseMessageCreate = ref('');
  const responseMessageDelete = ref('');
  const responseMessageGetByStarName = ref('');
  const responseMessageDetail = ref('');
  const responseMessageGetByUserId = ref('');

  const starname = ref('');

// ==================주문 생성 =============================

  const createOrder = async () => {
    try {
      const response = await axios.post(`http://i10d106.p.ssafy.io/api/order/create/${userId.value}/${productId.value}`);
      responseMessageCreate.value = response.data;
    } catch (error) {
      console.error(error);
      responseMessageCreate.value = '주문 생성 실패';
    }
  };
// ==================주문 생성 =============================
  



// ==================주문 삭제 =============================

  const deleteOrder =async () => {
    try {
      const response = await axios.post(`http://i10d106.p.ssafy.io/api/order/delete/${productId.value}`);
      responseMessageDelete.value = response.data;
    } catch (error) {
      console.log(error);
      responseMessageDelete.value = "주문 삭제 실패";
    } 
  }

// ==================주문 삭제 =============================


// ==================스타 이름으로(email아님) 스타에게 들어온 주문 목록 모두 조회 =============================

const getStarOrder =async () => {
  try {
    const response = await axios.get(`http://i10d106.p.ssafy.io/api/order/getByStarName/${starname.value}`)
    responseMessageGetByStarName.value = response.data
  } catch (error) {
    console.log(error)
  }
}

// ==================스타 이름으로(email아님) 스타에게 들어온 주문 목록 모두 조회 =============================


// ===================주문 pk로 주문 디테일 조회 =============================

const getOrderDetail = async () => {
  try {
    const response = await axios.get(`http://i10d106.p.ssafy.io/api/order/getById/${productId.value}`);
    responseMessageDetail.value = response.data;
    console.log(response.data)
    
  } catch (error) {
    
    console.log(error)
  }
  
}

// ===================주문 pk로 주문 디테일 조회 =============================



// ===================유저의 모든 주문 조회=============================

const getOrderByUserId =async () => {
  try {

    const response = await axios.get(`http://i10d106.p.ssafy.io/api/order/getUserOrders/${userId.value}`);
    responseMessageGetByUserId.value = response.data
    console.log(response.data)
  } catch (error) {
    console.log(error)
  }
  
}




// ===================유저의 모든 주문 조회=============================



  </script>


<style>

</style>