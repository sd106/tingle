<template>
  <main class="container">
    <StarMenu :id="starId" />
    <div class="container border">

      <img :src="starProfile.banner" alt="사진">
    </div>
    <div class="container border">
      <!-- 프로필 사진 > 누르면 확대-->
      <div>
        <img :src="starProfile.profileImage" alt="사진">
        <div style="display: inline-block;">
          <!-- 닉네임 -->
          <p>닉네임 {{starProfile.snsUrl}}</p>
          <!-- sns주소 링크 -->
        </div>
      </div>

      <p>sns 주소 {{starProfile.username}} </p>

    </div>
    <div class="item-wrapper">
      <div v-for="item in article" :key="item.id" class="item-container">
        <div class="item-header">
          <button class="item-button" @click="updateArticle(item.id)">수정</button>
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

      <div class="item-header">
        <button class="item-button">저장</button>
      </div>
      <input type="file" id="image-upload" multiple>
      <input v-model="homeCreateRequest.content" placeholder="입력해주세요">


  </main>
  <div class="fixed-button">
    <img src="/public/image/articlePlus.png" @click="insertArticle" />
  </div>


</template>

<script setup lang="ts">
import { ref,onMounted } from 'vue';
import { RouterLink } from 'vue-router'
import axios from 'axios';

import StarMenu from '@/components/StarMenu/StarMenu.vue';
import type { StarProfile } from '@/common/types/index'

const props = defineProps(['id']);
const starId = ref(props.id);

const starProfile= ref<StarProfile[]>([]);

let isGood = ref();
const buttonText = ref('');

const article= ref([]);

let files= ref([]);
let newArticle: false;

const getstarProfile = async () => {
  const response = await axios.get(`http://localhost:8080/home/profile/1`);
  starProfile.value = response.data.data;
  console.log(starProfile.value);
}

const getArticle = async () => {
  axios.get('http://localhost:8080/home/1')
    .then(response => {
      article.value = response.data.data;
    })
    .catch(error => {
      console.error(error);
    });
};

const handleFileSelection = (event) => {
  files = event.target.files;
}

let homeCreateRequest = {
  starId: starId.value,
  ordering: 3,
  content: "입력해주세요"
};

const insertArticle = async () => {

  let formData = new FormData();
  formData.append('homeCreateRequest', JSON.stringify(homeCreateRequest)); // JSON 문자열로 변환하여 추가

  // 파일이 있을 경우에만 추가
  if (files && files.length > 0) {
    for (let i = 0; i < files.length; i++) {
      formData.append('files', files[i]);
    }
  }

  axios.post('http://localhost:8080/home/post', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }).then(response => {
    getArticle();
    console.log(response.data);
  }).catch(error => {
    console.error(error);
  });
}

const clickAddButton= () => {

  newArticle= true;

};

let homeUpdateRequest = {
  homeId: 1,
  ordering: 3,
  content: "입력해주세요"
};

const updateArticle = async (Long: item.id) => {

  let formData = new FormData();
  formData.append('homeUpdateRequest', JSON.stringify(homeUpdateRequest)); // JSON 문자열로 변환하여 추가

// 파일이 여러 개인 경우, 각각의 파일을 추가
  for (let i = 0; i < files.length; i++) {
    formData.append('files', files[i]);
  }

  axios.post('http://localhost:8080/home/update', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }).then(response => {
    console.log(response.data);
  }).catch(error => {
    console.error(error);
  });
}

const deleteArticle = async (homeid: number) => {

  axios.delete(`http://localhost:8080/home/delete/${homeid}`)
    .then(response => {
      getArticle();
    console.log(response.data);
  }).catch(error => {
    console.error(error);
  });

}



onMounted(() => {
  getstarProfile();
  getArticle();

  const inputElement = document.getElementById('image-upload');
  inputElement.addEventListener('change', handleFileSelection);

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
  bottom: 10px; /* 버튼을 화면 밑에서 얼마나 떨어지게 할지 설정 */
  right: 10px; /* 버튼을 화면 오른쪽에서 얼마나 떨어지게 할지 설정 */
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