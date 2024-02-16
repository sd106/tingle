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
const folloingInfo = ref<{ starId: number; picture: string; userName: string }[]>([]);

// 구독 & 추가 스타 리스트
const chatStarsInfo = ref<ChatRoomInfo[]>([]);

const myid = store.fanState!.id;
const myname = store.fanState!.username;

// 구독 스타 조회
const getFolloings = async (): Promise<void> => {

  try {
    const res = await axios.get(`${store.API_URL}/follow/${myid}`);
    folloingInfo.value = res.data.data;

    console.log(`${res.data.message}: ${res.data.code}`);


  } catch (error) {
    console.error(`Error fetching getFolloings: `, error);
  }
}

// 구독 & 추가 스타 조회
const getChatStars = async (): Promise<void> => {
  try {
  const res = await axios.get(`${store.API_URL}/chatRoom/readRooms/${myid}`);
  chatStarsInfo.value = res.data.data;

  console.log(`${res.data.message}: ${res.data.code}`);
  

  } catch (error) {
    console.error(`Error fetching getChatStars: `, error);
  }
};

// a 리스트에서 b 리스트와 중복되는 아이템 제거
// const setStarsList = async (): Promise<void> => {
//   if(chatStarsInfo.value == null) return;

//   folloingInfo.value = folloingInfo.value.filter(itemA => {
//     return !chatStarsInfo.value.some(itemB => itemB.starId === itemA.starId);
//   });
// };

// 스타 추가
const addStarChating = async (starId: number): Promise<void> => {
try {

  if(folloingInfo?.value.length == 5) {
    window.alert("스타를 더 이상 추가할 수 없습니다. (최대 5명)");
    return;
  }
  else {
    const res = await axios.post(`${store.API_URL}/chatRoom/enterTheRoom/${myid}/${starId}`);
    console.log(`${res.data.message}: ${res.data.code}`);

    const res2 = await axios.get(`${store.API_URL}/chatRoom/readTheRoom/${starId}`);
    console.log(`${res2.data.message}: ${res2.data.code}`);

    // 시작 메시지 DB 에 저장
    addStarChatingMessages(res2.data.data.id);

    getFolloings();
    getChatStars();
    //setStarsList();
  }

  } catch (error) {
    console.error(`Error fetching addStarChating: `, error);
  }

};

// 시작 메시지 DB 에 저장
const addStarChatingMessages = async (roomId: number): Promise<void> => {

try {
  const dataToSend = {
    userId: myid,
    direction: 1,
    message: myname + " 님, 채팅 고마워요.",
    roomId: roomId
  };

    const res = await axios.post(`${store.API_URL}/chatMessage/saveMessages`, dataToSend);

    console.log(`${res.data.message}: ${res.data.code}`);

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
    /* 스크롤 가능한 컨테이너 스타일 */
.scrollable-container {
  height: 800px; /* 높이를 이전과 동일하게 조정 */
}

/* 스크롤 가능한 내용 스타일 */
.scrollable-content {
  padding: 0; /* 여백을 이전과 다르게 조정 */
}

/* 스타 카드 스타일 */
.card {
  margin: 10px 0; /* 카드 사이 여백을 조정하여 보다 조화로운 레이아웃 생성 */
}

.card img {
  max-width: 100%; /* 이미지를 카드 너비에 맞게 조정 */
}

.card-text {
  margin: 10px 0; /* 텍스트 사이 여백을 조정 */
}

.btn-primary {
  margin-top: 10px; /* 버튼 상단 여백을 추가하여 레이아웃을 보다 균형있게 조정 */
}
</style>