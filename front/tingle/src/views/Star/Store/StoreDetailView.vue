<template>
    <link href="/dist/output.css" rel="stylesheet">
    <main>
        <StarMenu :id="starid" />
        <div v-if="product" class="d-flex row">
            <!-- 왼쪽 -->
            {{ product }}
            <div class="col-6 border tw-border-y-green-800">
                왼쪽
                <button class="tw-btn tw-btn-square">
                    <span class="tw-loading tw-loading-spinner">아오</span>
                </button>
                <input type="checkbox" value="synthwave" class="tw-toggle tw-theme-controller" />
                <!-- <div v-for="image in product.imageUrl" :key="product" class="">
                    <img :src="image.url" alt="상품 이미지" class="goods-image">
                </div> -->
            </div>
            <!-- 오른쪽 -->
            <div class="col-6 border">
                <div>
                    <p>상품명 : {{ product.name }}</p>
                    <p>가격 : {{ product.price }}</p>
                    <p>수량 : {{ product.amount }}</p>
                    <p>설명 : {{ product.content }}</p>
                </div>
                <div>
                    <!-- //////주문 생성이랑 연결 -->
                    <button>구매 버튼</button>
                </div>
            </div>
        </div>
    </main>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import type { Goods } from '@/common/types';
import axios from 'axios'

import StarMenu from '@/components/StarMenu/StarMenu.vue';

const props = defineProps({
    starid: String,
    productId: String, // URL에서 받은 값은 문자열이므로 String으로 받음
});


const starid = ref(props.starid);
const productIdNumber = computed(() => Number(props.productId)); // 숫자로 변환
const product = ref<Goods>()

const getProduct = async (productId: number) => {
    try {
        const response = await axios.get(`http://localhost:8080/product/getById/${productId}`);
        if (response.data.resultCode === 'SUCCESS') {
            product.value = response.data.data;
        } else {
            console.error(response.data.message);
            console.log(response.data.data)
            product.value = response.data.data
        }
    } catch (error) {
        console.error('상품 조회 중 오류 발생', error);
    }
};

onMounted(() => {
    if (productIdNumber.value !== undefined) {
        getProduct(productIdNumber.value);
    }
});


// 사진들 카로셀



</script>

<style scoped>
.close-button {
    float: right;
}

.goods-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
</style>