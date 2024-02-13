<template>
  <main>
    <h1>사진 목록</h1>
    <div class="d-flex flex-wrap">
      <div class="storage-item col-3 mx-2" v-for="storage in userStoreStorages" :key="storage.id">
        <div class="storage-image-container">
          <img class="storage-image" :src="storage.url" :alt="storage.title">
        </div>
        <div class="storage-info">
          <!-- <p class="fw-bold">{{ storage.title }}</p> -->
          <!-- <p>{{ storage.content }}</p> -->
          <p>from {{ storage.starname }}</p>
          <div>
            <button class="delete-button">삭제하기</button> <!-- 삭제 버튼 추가 -->
          </div>
        </div>
      </div>
    </div>
  </main>
</template>
  
<script setup lang="ts">
import type { UserStoreStorageResponse } from '@/common/types';
import { ref, onMounted } from 'vue'
import axios from 'axios';
import { useUserStore } from '@/stores/user'

const { fanState } = useUserStore()
const userId = fanState?.id

const userStoreStorages = ref<UserStoreStorageResponse[]>([]);

const fetchData = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/order/getStarsPicture/${userId}`);
    console.log(response.data.resultCode)
    if (response.data.resultCode === 'SUCCESS') {
      userStoreStorages.value = response.data.data;
      //   console.log(userStoreStorages)
    } else {
      console.error('데이터를 불러오는 데 실패했습니다.');
    }
  } catch (error) {
    console.error('API 요청 중 오류가 발생했습니다.', error);
  }
};


onMounted(() => {
  fetchData();
});


</script>

<style scoped>
.storage-item {
  flex: 0 1 300px;
  height: 550px;
  /* 고정 높이 설정 */
  margin-bottom: 20px;
  background-color: #f9f9f9;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  /* 세로 방향 배치 */
  position: relative;
}

.storage-image-container {
  flex: 1;
  /* 이미지 컨테이너가 사용 가능한 공간을 차지하되, */
  display: flex;
  /* Flex 컨테이너 설정 */
  justify-content: center;
  /* 가로 방향 중앙 정렬 */
  align-items: center;
  /* 세로 방향 중앙 정렬 */
  background-color: white;
  /* 배경색을 하얀색으로 설정 */
  overflow: hidden;
  /* 이미지가 컨테이너를 벗어나지 않도록 함 */
}

.storage-image {
  max-width: 100%;
  /* 너비를 100%로 설정하여 컨테이너에 맞게 조정 */
  height: auto;
  /* 높이를 auto로 설정하여 비율을 유지 */
  object-fit: cover;
  /* 컨테이너에 맞추되, 비율 유지 */
  margin: auto;
  /* 이미지를 컨테이너 내에서 중앙에 배치 */
}

.storage-info {
  padding: 15px;
  padding-bottom: 50px;
  /* 삭제 버튼 공간 확보를 위한 하단 패딩 추가 */
  margin-top: auto;
  /* 정보 영역을 하단에 고정 */
  background: #f9f9f9;
  /* 배경색 설정 */
  position: relative;
  /* 상대 위치 설정 */
}

.storage-info p {
  color: #666;
  margin: 5px 0;
  line-height: 1.4;
  overflow: hidden;
  /* 내용이 넘칠 경우 숨김 처리 */
  text-overflow: ellipsis;
  /* 넘치는 텍스트를 말줄임표로 표시 */
  white-space: nowrap;
  /* 텍스트를 한 줄로 표시 */
}

.storage-info p:first-child {
  color: #333;
  font-weight: bold;
}

.delete-button {
  position: absolute;
  /* 버튼을 상위 컨테이너에 대해 절대 위치 */
  bottom: 10px;
  /* 상단에서부터의 거리 */
  right: 10px;
  /* 오른쪽에서부터의 거리 */
  padding: 5px 10px;
  /* 내부 여백 */
  background-color: #ff4d4d;
  /* 배경색 */
  color: white;
  /* 글자색 */
  border: none;
  /* 테두리 제거 */
  border-radius: 5px;
  /* 경계 모서리 둥글게 */
  cursor: pointer;
  /* 마우스 오버 시 커서 변경 */
  position: absolute;
  /* 버튼을 상위 컨테이너에 대해 절대 위치 */
  bottom: 15px;
  /* 하단에서부터의 거리 조정 */
  right: 15px;
  /* 오른쪽에서부터의 거리 조정 */
  transition: background-color 0.3s;
  /* 배경색 변경에 대한 전환 효과 */
}

.delete-button:hover {
  background-color: #ff3333;
  /* 마우스 오버 시 배경색 변경 */
}
</style>
