<template>
    <main>
        <img :src="order.fan.picture" alt="">
        <p>username : {{ order.fan.username }}</p>
        <p>email : {{ order.fan.email }}</p>
        <p>상품 정보 : {{ order.goods.name }}</p>
        <p>상품 설명 : {{ order.goods.content }}</p>
        <p>상품 가격 : {{ order.goods.price }}</p>
        <hr>
  <br>
  <hr>
  <h1>상품 생성</h1>

  <input type="text" v-model="content">
  <input type="text" v-model="title">

  <form @submit.prevent="submitForm(order.orderId)">
    <input type="file" @change="handleFileUpload" multiple>
    <button type="submit">생성</button>
  </form>

    </main>
</template>

<script setup lang="ts">
import type { OrderResponse } from '@/common/types';
import { ref } from 'vue';
import axios from 'axios';

const files = ref<File[]>([])
const content = ref<string>('')
const title = ref<string>('')

const submitForm = (orderId : Number) => {
  if (files.value && files.value.length > 0) {
    createProduct(orderId, files.value);
  } else {
    alert('파일을 선택해주세요.');
  }
};

const createProduct = async (orderId: Number, fileInputs: File[]) => {

  try {
        const formData = new FormData();
        formData.append('orderId', orderId.toString());
        formData.append('content', content.value);
        formData.append('title', title.value);
        for (let i = 0; i < fileInputs.length; i++) {
            formData.append('files', fileInputs[i]);
        }

    const response = await axios.post('http://i10d106.p.ssafy.io/api/order/sendTouser', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });

    if (response.status === 200) {
      alert('사진이 성공적으로 전달 되었습니다.');
    } else {
      alert('사진 전달에 실패했습니다.');
    }
  } catch (error) {
    console.error('상품 생성 중 오류 발생', error);
  }
};


const handleFileUpload = (event: Event) => {
  const target = event.target as HTMLInputElement;
  if (target.files) {
    files.value = Array.from(target.files);
  }
};

const props = defineProps({
    order: {
        type: Object as () => OrderResponse,
        required: true
        
    }
});

const order = props.order

</script>