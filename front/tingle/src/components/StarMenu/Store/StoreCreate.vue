<template>
    <main class="m-2">
        <div @click="createProduct" class="border text-center">
            <button class="menu-btn" type="button" data-bs-toggle="modal" data-bs-target="#CreateProductModal"
                    style="height: 50px; width: 50px;">
                    <h1 class="fw-bold">+</h1>
            </button>
        </div>
    <CreateProductModal />
    </main>
</template>
  
<script setup lang="ts">
import { ref } from 'vue';

import CreateProductModal from '@/components/CreateProductModal.vue'

const createProduct = () => {
    console.log('생성 버튼이 클릭되었습니다. axios보낼게염');

}

const openModal = () => {
  // 모달 창 열기

}

</script>
  