<template>
  <main class="container">
    <StarMenu :id="id" />
    <div class="container border">
      배너사진
    </div>
    <div class="container border">
      <!-- v-if 걸어서 본인인지 확인해야할듯? -->
      <RouterLink :to="`/${id}/home/manage`">
        스타면 설정버튼
      </RouterLink>
      <!-- 프로필 사진 > 누르면 확대-->
      <div class="container border">

          <img :src="starProfile.banner" alt="사진">
          <img :src="starProfile.profileImage" alt="사진">
          <!-- 닉네임 -->
          <p>닉네임 {{starProfile.snsUrl}}</p>
          <!-- 구독 여부 창 -->
        <button @click="checkFollow">{{ buttonText }}</button>
          <!-- sns주소 링크 -->
          <p>sns 주소 {{starProfile.username}} </p>

      </div>
    </div>
    <div class=" container border">
      설명
    </div>
    <div class=" container border">
      추가 블록
    </div>
  </main>
</template>

<script setup lang="ts">
import { ref,onMounted } from 'vue';
import { RouterLink } from 'vue-router'
import axios from 'axios';

import StarMenu from '@/components/StarMenu/StarMenu.vue';
import type { StarProfile } from '@/common/types/index'

const props = defineProps(['id']);
const id = ref(props.id);

const starProfile= ref<StarProfile[]>([]);

let isGood = ref();
const buttonText = ref('');

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
    const response = await axios.get('http://localhost:8080/follow/isFollowing', {
      params: {
        userId: 1,
        starId: 1
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
    const response = await axios.delete('http://localhost:8080/follow/delete', {
      data: {
        userId: 1,
        starId: 1
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
    const response = await axios.post('http://localhost:8080/follow/create', {
        userId: 1,
        starId: 1
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
  const response = await axios.get(`http://localhost:8080/home/profile/1`);
  starProfile.value = response.data.data;
  console.log(starProfile.value);
}

const BooleanFollow= ref();
const getBooleanFollow = async () => {
  const response = await axios.get(`http://localhost:8080/follow/create`,
  {
    userId: 1,
    starId: 1
  });
}

onMounted(() => {
  checkFollow();
  getstarProfile();

});



</script>

