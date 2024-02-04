<template>
  <main class="container">
    <StarMenu :id="id" />
    <h1>여긴 다 페이지네이션으로</h1>
    <div class="container border">
      <RouterLink :to="`/${id}/wish/manage`">
        스타만 볼 수 있는스타의 위시 관리
      </RouterLink>
    </div>

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

// 시작될 때 초기화 및 데이터 가져오기
onMounted(() => {
  getSortingWish(0);
});

</script>