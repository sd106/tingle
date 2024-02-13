<template>
  <main class="container">
    <StarMenu :id="starId" />
    <div class="container border">

      <img :src="starProfile?.banner" alt="사진">
    </div>
    <div class="container border">
      <!-- 프로필 사진 > 누르면 확대-->
      <div>
        <img :src="starProfile?.profileImage" alt="사진">
        <StarProfileUpdateDeleteVue/>
        <div style="display: inline-block;">
          <!-- 닉네임 -->
          <p>닉네임 {{ starProfile?.snsUrl }}</p>
          <!-- sns주소 링크 -->
        </div>
      </div>

      <p>sns 주소 {{ starProfile?.username }} </p>

    </div>

    <div class="container border" v-show="showInputArticle">
      <input type="file" id="image-upload" multiple>
      <input v-model="homeCreateRequest.content" placeholder="입력해주세요">
      <button @click="insertArticle()">완료</button>
    </div>


    <div class="item-wrapper">
      <div v-for="item in article" :key="item.id" class="item-container">
        <div class="item-header">
          <button class="menu-btn" type="button" data-bs-toggle="modal" data-bs-target="#chatModal"
            style="height: 50px; width: 50px;">수정
          </button>
          <button class="item-button" @click="deleteArticle(item.id)">삭제</button>
        </div>
        <p>{{ item.content }}</p>
        <div v-for="picture in item.homePictureDtos" :key="picture.id">
          <img :src="picture.image" alt="사진">
        </div>
        <p>Created At: {{ item.createdAt }}</p>
        <p v-if="item.updatedAt">Updated At: {{ item.updatedAt }}</p>
      </div>
    </div>
    <div v-if="selectedArticleId?.valueOf() !== -1" class="modal">
      <div class="modal-content">
        <h2>게시물 수정</h2>
        <button>수정 확인</button>
        <button @click="selectedArticleId = -1">취소</button>
      </div>
    </div>

  </main>

  <div class="fixed-button">

    <img src="/image/articlePlus.png" @click="IsInputArticle" />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useUserStore } from '@/stores/user';
const store = useUserStore();
let showInputArticle = ref(false);

import StarMenu from '@/components/StarMenu/StarMenu.vue';
import StarProfileUpdateDeleteVue from '@/components/StarMenu/StarHome/StarProfileUpdateDelete.vue'

import type { StarProfile, HomeArticle } from '@/common/types/index'

const starId = store.starState!.id;

const starProfile = ref<StarProfile>();


const article = ref<HomeArticle[]>([]);
let files = ref<File[]>([]);

const selectedArticleId = ref<number>(-1);

const getstarProfile = async () => {
  const response = await axios.get(`${store.API_URL}/home/profile/${starId}`);
  starProfile.value = response.data.data;
  console.log(starProfile.value);
}

const getArticle = async () => {
  axios.get(`${store.API_URL}/home/${starId}`)
    .then(response => {
      console.log(response.data.data);
      article.value = response.data.data;
    })
    .catch(error => {
      console.error(error);
    });
};

const handleFileSelection = (event: any) => {
  files.value = event.target.files;
}

let homeCreateRequest = {
  starId: starId,
  ordering: 3,
  content: "입력해주세요"
};

const insertArticle = async () => {

  let formData = new FormData();
  formData.append('homeCreateRequest', JSON.stringify(homeCreateRequest)); // JSON 문자열로 변환하여 추가

  // 파일이 있을 경우에만 추가
  if (files && files.value.length > 0) {
    for (let i = 0; i < files.value.length; i++) {
      formData.append('files', files.value[i]);
    }
  }

  axios.post(`${store.API_URL}/home/post`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }).then(response => {
    getArticle();
    showInputArticle.value = false;
    console.log(response.data);
  }).catch(error => {
    console.error(error);
  });
}

let homeUpdateRequest = {
  homeId: 1,
  ordering: 3,
  content: "입력해주세요"
};


const deleteArticle = async (homeid: number) => {

  axios.delete(`${store.API_URL}/home/delete/${homeid}`)
    .then(response => {
      getArticle();
      console.log(response.data);
    }).catch(error => {
      console.error(error);
    });

}

const IsInputArticle = () => {
  showInputArticle.value = true;
}



onMounted(() => {
  getstarProfile();
  getArticle();

  const inputElement = document.getElementById('image-upload');

  inputElement!.addEventListener('change', handleFileSelection);

});



</script>

<style>
.item-container {
  border: 1px solid black;
  padding: 10px;
  margin-bottom: 10px;
}


.container {
  margin-bottom: 10px;
}

.border {
  border: 1px solid black;
  padding: 10px;
}

.item-wrapper {
  width: 90%;
  margin: 30px auto;
}


.fixed-button {
  position: fixed;
  bottom: 10px;
  /* 버튼을 화면 밑에서 얼마나 떨어지게 할지 설정 */
  right: 10px;
  /* 버튼을 화면 오른쪽에서 얼마나 떨어지게 할지 설정 */
}

.fixed-button img {
  width: 100px;
  height: auto;
}

.item-container {
  position: relative;
}

.item-header {
  position: absolute;
  top: 0;
  right: 0;
}

.item-button {
  margin: 0 5px;
}
</style>