<template>
  <main class="container" >
    <StarMenu :id="starId" />
    <div class="container border">
      <img  :src="starProfile?.banner" alt="사진">
    </div>
    <div class="container border">
      <!-- 프로필 사진 > 누르면 확대-->
      <div>
        <img :src="starProfile?.profileImage" alt="사진">
        <!-- 프로필 수정, 삭제 컴포넌트 -->
        <div style="display: inline-block;">
          <!-- 닉네임 -->
          <p>닉네임 {{starProfile?.snsUrl}}</p>
          <!-- 구독 여부 창 -->
          <button @click="checkFollow">{{ buttonText }}</button>
          <!-- sns주소 링크 -->
        </div>
      </div>

      <p>sns 주소 {{starProfile?.username}} </p>

    </div>
    <div class="item-wrapper">
      <div v-for="item in article" :key="item.id" class="item-container">
        <p>{{ item.content }}</p>
        <div v-for="picture in item.homePictureDtos" :key="picture.image">
          <img :src="picture.image" alt="사진">
        </div>
        <p>Created At: {{ item.createdAt }}</p>
        <p v-if="item.updatedAt">Updated At: {{ item.updatedAt }}</p>
      </div>
    </div>
  </main>

</template>

<script setup lang="ts">
import { ref,onMounted } from 'vue';
import { RouterLink } from 'vue-router'
import axios from 'axios';

import { useUserStore } from '@/stores/user';
const store = useUserStore();

import StarMenu from '@/components/StarMenu/StarMenu.vue';
import type { StarProfile, HomeArticle } from '@/common/types/index'

const props = defineProps(['id']);
const starId = ref(props.id);

const starProfile= ref<StarProfile>();

let isGood = ref();
const buttonText = ref('');

const article= ref<HomeArticle[]>([]);

const checkFollow = async () => { //처음에 팔로잉 여부 체크
  if (isGood.value === undefined) {
  isGood.value = await isFollow();
  console.log(isGood.value);
  buttonText.value = isGood.value ? '구독중' : '구독';
}else {
    if (isGood.value) { //팔로잉을 이미 한 상태였다면
      await unFollowing();
      isGood.value = false;
      buttonText.value = '구독';
    } else { //팔로잉을 안한 상태였다면
      await following();
      isGood.value = true;
      buttonText.value = '구독중';
    }
  }
}



const isFollow = async () => {
  try {
    const response = await axios.get(`${store.API_URL}/follow/isFollowing`, {
      params: {
        userId: 1,
        starId: starId.value
      }
    });
    console.log(response);
    isGood.value= response.data.data;
    return response.data.data;
  } catch (error) {
    console.error(error);
    return false;
  }
}

const unFollowing = async () => {
  try {
    const response = await axios.delete(`${store.API_URL}/follow/delete`, {
      data: {
        userId: 1,
        starId: starId.value
      }
    });
    console.log(response);
    isGood.value= response.data.data;
    return response.data.data;
  } catch (error) {
    console.error(error);
    return false;
  }
}

const following = async () => {
  try {
    const response = await axios.post(`${store.API_URL}/follow/create`, {
        userId: 1,
        starId: starId.value
    });
    console.log(response);
    isGood.value= response.data.data;
    return response.data.data;
  } catch (error) {
    console.error(error);
    return false;
  }
}

const getstarProfile = async () => {
  const response = await axios.get(`${store.API_URL}/home/profile/${starId.value}`);
  starProfile.value = response.data.data;
  console.log(starProfile.value);
}

const getArticle = () => {
  axios.get(`${store.API_URL}/home/${starId.value}`)
    .then(response => {
      article.value = response.data.data;
    })
    .catch(error => {
      console.error(error);
    });
};


onMounted(() => {
  checkFollow();
  getstarProfile();
  getArticle();
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

</style>