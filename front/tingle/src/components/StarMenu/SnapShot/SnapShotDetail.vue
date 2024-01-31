<template>
    <main>
        <div class="왼쪽 전부">
            <!-- 사진 -->
            <img src="" alt="">
            <!-- 클릭하면 확대 -->
        </div>
        <div class="오른쪽">
            <div class="오른쪽 상단">
                <!-- 본인이라면 수정으로 이동 -->
                <!-- <RouterLink :to="`/${id}/home`">
                        수정
                    </RouterLink> -->
                <!-- 제목 -->
                <h1>제목</h1>
                <!-- 태그 -->
                <!-- 작성자, 작성 시간 -->
                <h4>작성자 , 작성 시간</h4>
            </div>
            <div class="오른쪽 중단">
                <!-- 댓글 총 개수-->
                <p>댓글 && 개</p>
                <!-- v-for 작성자, 댓글 내용 -->
            </div>
            <div class="오른쪽 하단">
                <input type="text">
                <!-- 댓글 입력창 -->
            </div>
        </div>
    </main>
</template>
<script setup lang="ts">
// import { RouterLink } from 'vue-router'


</script>
  