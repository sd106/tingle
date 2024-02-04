<template>
  <main class="container">
    <StarMenu :id="id" />
    
    메인에거 다 나오는데
    다중선택
    드래그로 옮길 수 있어야하고
    삭제

    <button @click="getSortingWish(0)">최신순</button>
    <button @click="getSortingWish(1)">추천순</button>
    <button @click="getSortingWish(2)">미션금순</button>

    <!-- 모든 위시들은 누르면 각각 모달 detail로 -->
    <!-- 뭔가 추가 페이지로 결과 확인할 수 있는데 구독자만 볼 수 있다거나 -->
    <div class="container border">
      <!-- NS 리스트 -->
      스타가 완료한 위시
      <ul>
        <li v-for="wish in wishDS" :key="wish.id">
          ID: {{ wish.id }} / User ID: {{ wish.userId }} / Star ID: {{ wish.starId }}
          <br>
          points: {{ wish.points }}
          <br>
          Contents: {{ wish.contents }}

          <br> 🖤 {{ wish.likedCount }} <br>
        </li>
      </ul>
    </div>
    <div class="container border">
      <!-- PS 리스트 -->
      스타가 선택한 위시
      <ul>
        <li v-for="wish in wishPS" :key="wish.id">
          ID: {{ wish.id }} / User ID: {{ wish.userId }} / Star ID: {{ wish.starId }}
          <br>
          points: {{ wish.points }}
          <br>
          Contents: {{ wish.contents }}
        
          <br> 🖤 {{ wish.likedCount }} <br>

          <!-- 미채택 버튼 -->
          <button @click="updateWishStatus(wish.id, 0)">미채택</button>
        </li>
      </ul>
    </div>
    <div class="container border">
      <div class="container border">
      </div>
      <!-- DS 리스트 -->
      스타가 미선택한 위시
      <ul>
        <li v-for="wish in wishNS" :key="wish.id">
          ID: {{ wish.id }} / User ID: {{ wish.userId }} / Star ID: {{ wish.starId }}
          <br>
          points: {{ wish.points }}
          <br>
          Contents: {{ wish.contents }}
        
          <br> 🖤 {{ wish.likedCount }} <br>
          
          <!-- 채택 버튼 -->
          <button @click="updateWishStatus(wish.id, 1)">채택</button>

        </li>
      </ul>
    </div>
    <RouterLink :to="`/${id}/wish/create`">
      소원 빌기
    </RouterLink>
  </main>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { RouterLink } from 'vue-router'
import axios from 'axios'
import { useUserStore } from '@/stores/user'

const store = useUserStore()
// NotStatus, ProgressStatus, DoneStatus
import type { WishInfo} from '@/common/types/index'
import StarMenu from '@/components/StarMenu/StarMenu.vue';

// 스타 본인의 id 정보
const props = defineProps(['id']);
const id = ref(props.id);

// 위시 리스트
const wishNS = ref<WishInfo[]>([]);
const wishPS = ref<WishInfo[]>([]);
const wishDS = ref<WishInfo[]>([]);

// 페이지네이션
const sorting = ref(0);

// 데이터 가져오기 함수
const fetchData = async (status: number, sorting: number): Promise<void> => {

  try {
    /* test */
    id.value = parseInt('1');

    const res = await axios.get(`${store.API_URL}/wish/read/${id.value}/${sorting}/${status}`);
    
    if (status === 0) {
      wishNS.value = res.data.data;
    }
    else if (status === 1) {
      wishPS.value = res.data.data;
    }
    else {
      wishDS.value = res.data.data;
    } 

  } catch (error) {
    console.error(`Error fetching wishes (${status}): `, error);
  }
};

// 최신순/추천순/미션금순 정렬
const getSortingWish = function (sorting: number): void {
  fetchData(0, sorting);
  fetchData(1, sorting);
  fetchData(2, sorting);
};

// 해당 위시를 미채택/채택 상태로 변경
const updateWishStatus = async (wishId:number, wishStatus: number): Promise<void> => {
  try {
    const res = await axios.post(`${store.API_URL}/wish/update/status/star/${id.value}/${wishId}/${wishStatus}`)

    console.log('Wish status updated:', res);

    // 상태를 업데이트한 후, 다시 위시 목록을 불러올 수 있도록
    getSortingWish(0);

  } catch (error) {
    console.error('Error updating wish status:', error);
  }
};

//   // 해당 위시 수정 (미구현)
//   const updateWish = async function (payload:wishInfo): Promise<void> {
//   try {
//     const res = await axios.get(`${store.API_URL}/likes/update`)
//     console.log('res')
//   } catch (error) {
//     console.error('Error fetching updateWish: ', error);
//   }
// }

// // 해당 위시 삭제
//   const deleteWish = async function (wishId:number): Promise<void> {
//   try {
//     const res = await axios.get(`${store.API_URL}/likes/delete/${wishId}`)
//     console.log('res')
//   } catch (error) {
//     console.error('Error fetching deleteWish: ', error);
//   }
// }

// 시작될 때 초기화 및 데이터 가져오기
onMounted(() => {
  getSortingWish(0);
});

</script>