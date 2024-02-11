<template>
    <main>
        <StarMenu :id="starid" />
        <div v-if="product" class="d-flex row justify-content-center tw-space-x-4 tw-mt-2">
            <!-- 왼쪽 -->

            <div class="col-6">
                <div class="tw-carousel tw-w-full">
                    <div v-for="(image, index) in product.imageUrl" :key="image.id"
                        class="tw-carousel-item tw-relative tw-w-full" :class="{ 'tw-hidden': index !== activeIndex }">
                        <img :src="image.url" alt="" class="tw-w-full">
                        <div v-if="product.imageUrl.length > 2" class="tw-absolute tw-flex tw-justify-between tw-transform tw--translate-y-1/2 tw-left-5
                            tw-right-5 tw-top-1/2">
                            <button @click="prevSlide" class="tw-btn tw-btn-circle tw-glass">❮</button>
                            <button @click="nextSlide" class="tw-btn tw-btn-circle tw-glass">❯</button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 오른쪽 -->
            <div class="col-4 tw-ps-6 tw-flex tw-flex-col tw-justify-between">
                <div>
                    <p class="tw-text-3xl tw-font-bold tw-text-gray-900 tw-px-2 tw-py-2">{{ product.name }}</p>
                    <p class="tw-text-xl tw-font-semibold tw-text-gray-900 tw-pt-2 tw-px-2">
                        {{ formattedPrice }}<span class="tw-text-sm">원</span>
                    </p>
                    <div class="tw-flex tw-items-center tw-mb-4 tw-px-1">
                        <p class="tw-text-xs tw-text-gray-500 tw-border tw-px-2 tw-py-1 tw-rounded">{{ product.amount }} 개
                            남음
                        </p>

                    </div>
                    <p class="tw-py-4">{{ product.content }}</p>
                    <hr class="tw-my-4">
                </div>
                <div>
                    <button
                        class="tw-btn tw-btn-active tw-btn-block tw-bg-black tw-text-white tw-py-3 tw-rounded-md tw-mb-2">구매하기</button>
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
        const response = await axios.get(`http://i10d106.p.ssafy.io:8080/api/product/getById/${productId}`);
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
const activeIndex = ref(0);

const prevSlide = () => {
    if (activeIndex.value > 0)
        activeIndex.value -= 1
};

// 다음 슬라이드로 이동
const nextSlide = () => {
    if (activeIndex.value < product.value!.imageUrl.length - 1)
        activeIndex.value += 1
};


const formattedPrice = computed(() => {
    return new Intl.NumberFormat('ko-KR', { style: 'decimal' }).format(product.value!.price)
})


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