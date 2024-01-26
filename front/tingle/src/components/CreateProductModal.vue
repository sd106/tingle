<template>
    <div class="container modal fade" tabindex="-1" data-bs-backdrop="static" id="CreateProductModal">
        <div class="modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable">
            <div class="modal-content">

                <div class="d-flex ">
                
                <div class="modal-header  col-4">
                    <div class="mb-3">
                        <div>
                            <label for="product-image" class="form-label">상품 이미지:</label>
                            <input type="file" id="product-image" name="product_image" class="form-control-file" @change="previewImage">
                        </div>
                        <div>
                            <img :src="imagePreview" class="fixed-image-size" alt="상품 이미지 미리보기" v-if="imagePreview">
                        </div>
                    </div>
                </div>
                <div class="modal-body col-8">
                    <div class="d-flex justify-content-end">
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <form action="/submit_product" method="POST">
                        <div class="mb-3">
                            <label for="product-name" class="form-label">상품 이름:</label>
                            <input type="text" id="product-name" name="product_name" class="form-control" required>
                        </div>
                        <div class="mb-3 d-flex justify-content-between">
                            <div>
                                <label for="product-price" class="form-label">상품 가격:</label>
                                <input type="number" id="product-price" name="product_price" class="form-control" required>
                            </div>                            
                            <div>
                                <label for="product-price" class="form-label">상품 수량:</label>
                                <input type="number" id="product-price" name="product_price" class="form-control" required>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="product-description" class="form-label">상품 설명:</label>
                            <textarea id="product-description" name="product_description" class="form-control" rows="4" required></textarea>
                        </div>
                        <input type="submit" value="등록">
                    </form>

                </div>

            </div>


            </div>
        </div>
    </div>
</template>

<script setup lang="ts">

import { ref } from 'vue';

const imagePreview = ref(""); // 이미지 미리보기를 위한 변수

// 이미지를 미리보기하는 함수
const previewImage = (event) => {
    const file = event.target.files[0]; // 업로드된 파일 가져오기

    if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
            // 이미지 데이터를 변수에 할당하고 미리보기에 표시
            imagePreview.value = e.target.result;
        };
        reader.readAsDataURL(file);
    } else {
        // 파일이 없을 경우 이미지 미리보기 초기화
        imagePreview.value = "";
    }
}

</script>

<style scoped>
.fixed-image-size {
    width: 200px; /* 원하는 가로 크기로 설정 */
    height: auto; /* 자동으로 세로 크기 계산 */
}


</style>