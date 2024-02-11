<template>
    <main>
        <h1>상품 생성</h1>
        <form @submit.prevent="submitForm">
            <input type="text" v-model="productDto.name" placeholder="상품 이름">
            <input type="number" v-model.number="productDto.amount" placeholder="수량">
            <input type="number" v-model.number="productDto.price" placeholder="가격">
            <textarea v-model="productDto.content" placeholder="상품 설명"></textarea>
            <input type="file" ref="fileInput" multiple>
            <button type="submit">생성</button>
        </form>
    </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios';

import type { Product } from '@/common/types'


const productDto = ref<Product>({
    starId: 2,
    starName: 'mo',
    name: '',
    amount: 0,
    price: 0,
    content: ''
});
const fileInput = ref();

const createProduct = async (productDto: Product, fileInputs: FileList) => {
    try {
        const formData = new FormData();
        formData.append('productDto', JSON.stringify(productDto));
        for (let i = 0; i < fileInputs.length; i++) { formData.append('files', fileInputs[i]); } const response = await
            axios.post('http://i10d106.p.ssafy.io:8080/api/product/create', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }); if (response.status === 200) { alert('상품이 성공적으로 생성되었습니다.'); } else { alert('상품 생성에 실패했습니다.'); }
    } catch
    (error) { console.error('상품 생성 중 오류 발생', error); }
};

const submitForm = () => {
    if (fileInput.value && fileInput.value.files && fileInput.value.files.length > 0) {
        createProduct(productDto.value, fileInput.value.files);
    } else {
        alert('파일을 선택해주세요.');
    }
};

</script>
