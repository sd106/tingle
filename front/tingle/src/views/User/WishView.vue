<template>
    <main class="container">
      <StarMenu :id="id" />
      <button @click="getSortingWish(0)">최신순</button>
      <button @click="getSortingWish(1)">추천순</button>
      <button @click="getSortingWish(2)">미션금순</button>
  
      <!-- 모든 위시들은 누르면 각각 모달 detail로 -->
      <!-- 뭔가 추가 페이지로 결과 확인할 수 있는데 구독자만 볼 수 있다거나 -->
      <div class="container border">
        <!-- NS 리스트 -->
        스타가 완료한 위시
        <!-- 페이지네이션 UI -->
      <nav aria-label="...">
          <ul class="pagination">
            <li class="page-item" :class="{ disabled: currentPageNS === 1 }">
              <a class="page-link" @click="changePage(currentPageNS - 1, 0)" tabindex="-1" href="#" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
              </a>
            </li>
            <li v-for="page in totalPagesNS" :key="page" class="page-item" :class="{ active: currentPageNS === page }">
              <a class="page-link" @click="changePage(page, 2)">{{ page }}</a>
            </li>
            <li class="page-item" :class="{ disabled: currentPageNS === totalPagesNS }">
              <a class="page-link" @click="changePage(currentPageNS + 1, 0)" href="#" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
              </a>
            </li>
          </ul>
        </nav>
        <ul>
          <li v-for="wish in getCurrentPageItemsNS" :key="wish.id">
            ID: {{ wish.id }} / User ID: {{ wish.userId }} / Star ID: {{ wish.starId }}
            <br>
            points: {{ wish.points }}
            <br>
            Contents: {{ wish.contents }} <br>

            <div v-if="checkIfWishHasLike(wish.id)" @click="updateWishLiked(wish.id)">🖤 {{ wish.likedCount }}</div>
            <div v-else @click="updateWishLiked(wish.id)">🤍 {{ wish.likedCount }} </div> <br>
                
          </li>
        </ul>
      </div>
      <div class="container border">
        <!-- PS 리스트 -->
        스타가 선택한 위시
        <!-- 페이지네이션 UI -->
      <nav aria-label="...">
          <ul class="pagination">
            <li class="page-item" :class="{ disabled: currentPagePS === 1 }">
              <a class="page-link" @click="changePage(currentPagePS - 1, 0)" tabindex="-1" href="#" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
              </a>
            </li>
            <li v-for="page in totalPagesPS" :key="page" class="page-item" :class="{ active: currentPagePS === page }">
              <a class="page-link" @click="changePage(page, 2)">{{ page }}</a>
            </li>
            <li class="page-item" :class="{ disabled: currentPagePS === totalPagesPS }">
              <a class="page-link" @click="changePage(currentPagePS + 1, 0)" href="#" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
              </a>
            </li>
          </ul>
        </nav>
        <ul>
          <li v-for="wish in getCurrentPageItemsPS" :key="wish.id">
            ID: {{ wish.id }} / User ID: {{ wish.userId }} / Star ID: {{ wish.starId }}
            <br>
            points: {{ wish.points }}
            <br>
            Contents: {{ wish.contents }} <br>
          
            <div v-if="checkIfWishHasLike(wish.id)" @click="updateWishLiked(wish.id)">🖤 {{ wish.likedCount }}</div>
            <div v-else @click="updateWishLiked(wish.id)">🤍 {{ wish.likedCount }} </div> <br>
  
            <!-- 완료 버튼 -->
            <button v-if="wish.userId==myid" @click="updateWishStatus(wish.id)">완료</button>
  
          </li>
        </ul>
      </div>
      <div class="container border">
        <div class="container border">
        </div>
        <!-- DS 리스트 -->
        스타가 미선택한 위시
        <!-- 페이지네이션 UI -->
      <nav aria-label="...">
          <ul class="pagination">
            <li class="page-item" :class="{ disabled: currentPageDS === 1 }">
              <a class="page-link" @click="changePage(currentPageDS - 1, 0)" tabindex="-1" href="#" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
              </a>
            </li>
            <li v-for="page in totalPagesDS" :key="page" class="page-item" :class="{ active: currentPageDS === page }">
              <a class="page-link" @click="changePage(page, 2)">{{ page }}</a>
            </li>
            <li class="page-item" :class="{ disabled: currentPageDS === totalPagesDS }">
              <a class="page-link" @click="changePage(currentPageDS + 1, 0)" href="#" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
              </a>
            </li>
          </ul>
        </nav>
        <ul>
          <li v-for="wish in getCurrentPageItemsDS" :key="wish.id">
            ID: {{ wish.id }} / User ID: {{ wish.userId }} / Star ID: {{ wish.starId }}
            <br>
            points: {{ wish.points }}
            <br>
            Contents: {{ wish.contents }} <br>

            Contents: {{ wish.contents }} <br>

            <div v-if="checkIfWishHasLike(wish.id)" @click="updateWishLiked(wish.id)">🖤 {{ wish.likedCount }}</div>
            <div v-else @click="updateWishLiked(wish.id)">🤍 {{ wish.likedCount }} </div> <br>

            <!-- 미션금 추가 버튼 -->
            <button @click="addPoints(wish.id, 1000)">미션금 추가</button>

            <!-- 삭제 버튼 -->
            <button v-if="wish.userId==myid" @click="deleteWish(wish.id)">삭제</button>
          </li>
        </ul>
      </div>
      <RouterLink :to="`/${id}/wish/create`">
        소원 빌기
      </RouterLink>
    </main>
  </template>
  
  <script setup lang="ts">
  import { ref, onMounted, computed } from 'vue'
  import { RouterLink } from 'vue-router'
  import axios from 'axios'
  import { useUserStore } from '@/stores/user'
  
  const store = useUserStore()
  // NotStatus, ProgressStatus, DoneStatus
  import type { WishInfo, LikesInfo } from '@/common/types/index'
  import StarMenu from '@/components/StarMenu/StarMenu.vue';
  
  // 스타 본인의 id 정보
  const props = defineProps(['id']);
  const id = ref(props.id);
  const myid = ref(1);
  
  // 위시 리스트
  const wishNS = ref<WishInfo[]>([]);
  const wishPS = ref<WishInfo[]>([]);
  const wishDS = ref<WishInfo[]>([]);
  const likesList = ref<LikesInfo[]>([]);
  
 // 페이지네이션 관련 변수
const itemsPerPageDS = 5; // 페이지당 아이템 수
const currentPageDS = ref(1); // 현재 페이지
const totalPagesDS = computed(() => Math.ceil(wishDS.value.length / itemsPerPageDS)); // 전체 페이지 수

const itemsPerPagePS = 5; // 페이지당 아이템 수
const currentPagePS = ref(1); // 현재 페이지
const totalPagesPS = computed(() => Math.ceil(wishPS.value.length / itemsPerPagePS)); // 전체 페이지 수

const itemsPerPageNS = 5; // 페이지당 아이템 수
const currentPageNS = ref(1); // 현재 페이지
const totalPagesNS = computed(() => Math.ceil(wishNS.value.length / itemsPerPageNS)); // 전체 페이지 수

// 페이지 변경 함수
const changePage = (page: number, status: number) => {
  if(status == 0) {
    if (page >= 1 && page <= totalPagesDS.value) {
      currentPageDS.value = page;
  }
  }
  else if(status == 1) {
    if (page >= 1 && page <= totalPagesPS.value) {
      currentPagePS.value = page;
  }
  }
  else {
    if (page >= 1 && page <= totalPagesNS.value) {
      currentPageNS.value = page;
  }
  }
};
 
// 현재 페이지의 아이템 가져오기 (DS)
const getCurrentPageItemsDS = computed(() => {
  const startIndex = (currentPageDS.value - 1) * itemsPerPageDS;
  const endIndex = startIndex + itemsPerPageDS;
  return wishDS.value.slice(startIndex, endIndex);
});

// 현재 페이지의 아이템 가져오기 (PS)
const getCurrentPageItemsPS = computed(() => {
  const startIndex = (currentPagePS.value - 1) * itemsPerPagePS;
  const endIndex = startIndex + itemsPerPagePS;
  return wishPS.value.slice(startIndex, endIndex);
});

// 현재 페이지의 아이템 가져오기 (NS)
const getCurrentPageItemsNS = computed(() => {
  const startIndex = (currentPageNS.value - 1) * itemsPerPageNS;
  const endIndex = startIndex + itemsPerPageNS;
  return wishNS.value.slice(startIndex, endIndex);
});

// 데이터 가져오기 함수
const fetchData = async (status: number, sorting: number): Promise<void> => {

try {
  /* test */
  id.value = parseInt('1');

  const res = await axios.get(`${store.API_URL}/wish/read/${id.value}/${sorting}/${status}`);
  
  if (status === 0) {
    wishNS.value = res.data.data;
    currentPageNS.value = 1;
  }
  else if (status === 1) {
    wishPS.value = res.data.data;
    currentPagePS.value = 1;
  }
  else {
    wishDS.value = res.data.data;
    currentPageDS.value = 1;
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
  
    // 해당 위시에 미션금 추가
    const addPoints = async function (wishId:number, points:number): Promise<void> {
    try {
      const res = await axios.get(`${store.API_URL}/likes/points/add/{wishId}/{userId}/{points}`)
      likesList.value = res.data
      console.log('res')
    } catch (error) {
      console.error('Error fetching addPoints: ', error);
    }
  }
  
  // 해당 위시를 완료 상태로 변경
  const updateWishStatus = async (wishId:number): Promise<void> => {
    try {
      const res = await axios.post(`${store.API_URL}/wish/update/status/user/${myid.value}/${wishId}/0`)
  
      console.log('Wish status updated:', res);
  
      // 상태를 업데이트한 후, 다시 위시 목록을 불러올 수 있도록
      getSortingWish(0);
  
    } catch (error) {
      console.error('Error updating wish status:', error);
    }
  };
  
  // 내 추천 리스트 조회
  const getLikesList = async function (): Promise<void> {
    try {
      const res = await axios.get(`${store.API_URL}/likes/read/mylist/${id.value}/${myid.value}`)
      likesList.value = res.data
      console.log('res')
    } catch (error) {
      console.error('Error fetching likeList: ', error);
    }
  }
  
    // 해당 위시에 대한 추천 상태 토글 변경 (create 포함)
    const updateWishLiked = async function (wishId: number): Promise<void> {
    try {
      const res = await axios.get(`${store.API_URL}/likes/update/toggle/${wishId}/${myid.value}`)
  
      getLikesList();
  
      // 서버로 상태를 업데이트할 때 사용하고 싶다면, 여기에 추가 로직을 작성
  
      console.log('likesList:', likesList.value);
    } catch (error) {
      console.error('Error updating wish liked status:', error);
    }
  };
  
//     // 해당 위시 수정 (미구현)
//     const updateWish = async function (payload:wishInfo): Promise<void> {
//     try {
//       const res = await axios.get(`${store.API_URL}/likes/update`)
//       console.log('res')
//     } catch (error) {
//       console.error('Error fetching updateWish: ', error);
//     }
//   }
  
  // 해당 위시 삭제
    const deleteWish = async function (wishId:number): Promise<void> {
    try {
      const res = await axios.get(`${store.API_URL}/likes/delete/${wishId}/${id.value}/${myid.value}`)
      console.log('res')
    } catch (error) {
      console.error('Error fetching deleteWish: ', error);
    }
  }
  
  // likesList에서 해당 wishId를 가진 항목이 있는지 확인
  const checkIfWishHasLike = (wishId: number) => {
    const foundLike = likesList.value.find(like => like.wishId === wishId);
    
    if (foundLike) {
      // 해당 wishId를 가진 항목이 있다면, 해당 항목의 값을 반환
      return foundLike.liked;
    } else {
      // 해당 wishId를 가진 항목이 없다면, 원하는 값을 반환
      return 0; // 또는 다른 값으로 대체 가능
    }
  };

  // 시작될 때 초기화 및 데이터 가져오기
  onMounted(() => {
    getSortingWish(0);
    getLikesList();
  });
  
  </script>