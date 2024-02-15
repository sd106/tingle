<template>
  <div class="chat-list-items scrollable-container">
    <div class="scrollable-content">
     <div v-for="follow in folloingInfo" :key="follow.starId">
      <div class="card mt-3 mb-3" style="max-width: 540px;">
        <div class="row g-0">
          <div class="col-md-4">
      <!-- ////// follow.picture 넣기 -->
            <img :src="follow.picture" class="img-fluid rounded-start" alt="not">
            <!-- <h5 class="card-title">{{ follow.picture }}</h5> -->
              <p class="card-text">{{follow.userName}} ({{ follow.starId }})</p>
              <button type="button" class="btn btn-primary" @click="addStarChating(follow.starId)">채팅하기</button>
          </div>
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

const myid = 1;// store.fanState!.id;

// 구독 스타 가져오기
const getFolloings = async () => {
  try {
    const response = await axios.get(`${store.API_URL}/follow/${store.fanState!.id}`);
    folloingInfo.value = response.data.data;

  } catch (error) {
    console.error(`Error fetching getFolloings: `, error);
  }
}

// 구독 & 추가 스타 조회
const getChatStars = async (): Promise<void> => {
  try {
  const res = await axios.get(`${store.API_URL}/chatRoom/readRooms/${myid}`);
  chatStarsInfo.value = res.data.data;

  } catch (error) {
    console.error(`Error fetching getChatStars: `, error);
  }
};

// a 리스트에서 b 리스트와 중복되는 아이템 제거
const setStarsList = async (): Promise<void> => {
  if(chatStarsInfo.value == null) return;

  folloingInfo.value = folloingInfo.value.filter(itemA => {
    return !chatStarsInfo.value.some(itemB => itemB.starId === itemA.starId);
  });
};

// 스타 추가하기
const addStarChating = async (follow: StarState): Promise<void> => {
try {
  const res = await axios.get(`${store.API_URL}/chat/read/addingRooms/${store.fanState!.id}`);
  chatStarsInfo.value = res.data.data;

  } catch (error) {
    console.error(`Error fetching addStarChating: `, error);
  }
};

// 시작될 때 초기화 및 데이터 조회
onMounted(() => {
  getFolloings();
  getChatStars();
});
</script>

<style scoped>
    .scrollable-container {
    /* width: 300px; /* 네모의 너비 */
    height: 100px; /* 네모의 높이 */
    overflow-y: auto; /* 세로 스크롤이 필요할 때만 표시 */
    border: 1px solid #ccc; /* 테두리 스타일 */
    }

    .scrollable-content {
    padding: 10px; /* 스크롤 영역의 여백 */
    }

    .scrollable-content p {
    margin: 0; /* 단락 사이의 여백 제거 */
    }
</style>