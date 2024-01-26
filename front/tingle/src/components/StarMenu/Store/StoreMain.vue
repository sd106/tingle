<template>
    <main>
        <div class="m-2 d-flex justify-content-between">

            <div class="border col-1 center-element">
                <input type="checkbox">
                <span class="checkmark"></span>
            </div>
            <div class="border col-2 center-element">
                <img src="" alt="????">
            </div>
            <div class="border col-5 d-flex align-items-center">
                <h2>title</h2>
            </div>
            <div class="border col-2 d-flex align-items-center">
                <h2>price</h2>
            </div>
            <div class="d-flex border col-2 align-items-center justify-content-between">
                <button @click="updateProduct">수정</button>
                <button>삭제</button>
            </div>

        </div>
    </main>
</template>
  
<script setup lang="ts">

import { ref } from 'vue';



const updateProduct = () => {
    console.log('수정 버튼이 클릭되었습니다. axios보낼게염');
}










</script>

<style>
    .center-element {
        display: flex;
        justify-content: center; /* 수평 중앙 정렬 */
        align-items: center; /* 수직 중앙 정렬 */
        height: 20vh; /* 수직 중앙 정렬을 위한 높이 설정 */
    }
</style>