<template>
     <div v-for="follow in folloingInfo" :key="follow.id">
      <div class="card mt-3 mb-3" style="max-width: 540px;">
        <div class="row g-0">
          <div class="col-md-4">
            <img src="/image/yoo.png" class="img-fluid rounded-start" alt="">
          </div>
          <div class="col-md-8">
            <div class="card-body">
              <h5 class="card-title">{{ follow.picture }}</h5>
              <p class="card-text">{{ follow.username }}</p>
              <button type="button" class="btn btn-primary" @click="addStarChating(follow)">채팅하기</button>
            </div>
          </div>
        </div>
      </div>
     </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useUserStore } from '@/stores/user';

import type { ChatRoomInfo, StarState } from '@/common/types/index'
import axios from "axios";

const store = useUserStore()

// 구독 스타 리스트
const folloingInfo = ref<StarState[]>([]);

// 구독 & 추가 스타 리스트
const chatStarsInfo = ref<ChatRoomInfo[]>([]);


// 구독 스타 가져오기
const getFolloings = async () => {
  try {
    const response = await axios.get(`${store.API_URL}/follow/${store.fanState!.id}`);
    folloingInfo.value = response.data.data;

  } catch (error) {
    console.error(`Error fetching getFolloings: `, error);
  }
}

// 구독 & 추가 스타 가져오기
const getChatStars = async (): Promise<void> => {
  try {
  const res = await axios.get(`${store.API_URL}/chat/read/addingRooms/${store.fanState!.id}`);
  chatStarsInfo.value = res.data.data;

  } catch (error) {
    console.error(`Error fetching getChatStars: `, error);
  }
};

// 스타 추가하기
const addStarChating = async (follow: StarState): Promise<void> => {
try {
  const res = await axios.get(`${store.API_URL}/chat/read/addingRooms/${store.fanState!.id}`);
  chatStarsInfo.value = res.data.data;

  } catch (error) {
    console.error(`Error fetching getChatStars: `, error);
  }
};

// 시작될 때 초기화 및 데이터 가져오기
onMounted(() => {
  getFolloings();
  getChatStars();
});
  
</script>

<style>
</style>