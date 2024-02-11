<template>
  <main class="container">
    <StarMenu :id="id" />
    <div class="container border">
      <RouterLink :to="`/${id}/wish/manage`">
        <button class="tw-btn tw-btn-outline tw-mx-1">
          위시 관리
        </button>
      </RouterLink>
    </div>

    <button class="tw-btn tw-btn-outline tw-mx-1" @click="getSortingWish(0)">최신순</button>
    <button class="tw-btn tw-btn-outline tw-mx-1" @click="getSortingWish(1)">추천순</button>
    <button class="tw-btn tw-btn-outline tw-mx-1" @click="getSortingWish(2)">미션금순</button>

    <!-- 모든 위시들은 누르면 각각 모달 detail로 -->
    <!-- 뭔가 추가 페이지로 결과 확인할 수 있는데 구독자만 볼 수 있다거나 -->
    <div class="container border">
      <!-- NS 리스트 -->
      스타가 완료한 위시
      <ul>
        <li v-for="wish in getCurrentPageItemsDS" :key="wish.id">
          ID: {{ wish.id }} / User ID: {{ wish.userId }}
          <br>
          points: {{ wish.points }}
          <br>
          Contents: {{ wish.contents }}

          <br> 🖤 {{ wish.likedCount }} <br>
        </li>
      </ul>
      <!-- 페이지네이션 UI -->
      <nav v-if="wishDS.length > 4" aria-label="...">
        <ul class="pagination">
          <li class="page-item" :class="{ disabled: currentPageDS === 1 }">
            <a class="page-link" @click="changePage(currentPageDS - 1, 0)" tabindex="-1" href="#" aria-label="Previous">
              <span aria-hidden="true">&laquo;</span>
            </a>
          </li>
          <li v-for="page in totalPagesDS" :key="page" class="page-item" :class="{ active: currentPageDS === page }">
            <a class="page-link" @click="changePage(page, 0)">{{ page }}</a>
          </li>
          <li class="page-item" :class="{ disabled: currentPageDS === totalPagesDS }">
            <a class="page-link" @click="changePage(currentPageDS + 1, 0)" href="#" aria-label="Next">
              <span aria-hidden="true">&raquo;</span>
            </a>
          </li>
        </ul>
      </nav>


    </div>
    <div class="container border">
      <!-- PS 리스트 -->
      스타가 선택한 위시
      <ul>
        <li v-for="wish in getCurrentPageItemsPS" :key="wish.id">
          ID: {{ wish.id }} / User ID: {{ wish.userId }} / Star ID: {{ wish.starId }}
          <br>
          points: {{ wish.points }}
          <br>
          Contents: {{ wish.contents }}

          <br> 🖤 {{ wish.likedCount }} <br>
        </li>
      </ul>
      <!-- 페이지네이션 UI -->
      <nav v-if="wishPS.length > 4" aria-label="...">
        <ul class="pagination">
          <li class="page-item" :class="{ disabled: currentPagePS === 1 }">
            <a class="page-link" @click="changePage(currentPagePS - 1, 0)" tabindex="-1" href="#" aria-label="Previous">
              <span aria-hidden="true">&laquo;</span>
            </a>
          </li>
          <li v-for="page in totalPagesPS" :key="page" class="page-item" :class="{ active: currentPagePS === page }">
            <a class="page-link" @click="changePage(page, 1)">{{ page }}</a>
          </li>
          <li class="page-item" :class="{ disabled: currentPagePS === totalPagesPS }">
            <a class="page-link" @click="changePage(currentPagePS + 1, 0)" href="#" aria-label="Next">
              <span aria-hidden="true">&raquo;</span>
            </a>
          </li>
        </ul>
      </nav>


    </div>
    <div class="container border">
      <div class="container border">
      </div>
      스타가 미선택한 위시
      <!-- DS 리스트 -->
      <ul role="list" class="tw-divide-y tw-divide-gray-300">
        <li v-for="wish in getCurrentPageItemsNS" :key="wish.id" class="tw-flex tw-justify-between tw-gap-x-6 tw-py-5">
          <div class="tw-min-w-0 tw-flex-auto">
            <p class="tw-mt-1 tw-truncate tw-text-xs tw-leading-5 tw-text-gray-500">User ID: {{ wish.userId }}</p>
            <p class="tw-text-sm tw-font-semibold tw-leading-6 tw-text-gray-900">{{ wish.id }}번째 Wish</p>
          </div>
          <div>Contents: {{ wish.contents }}</div>
          <div>
            points: {{ wish.points }}
            🖤 {{ wish.likedCount }}
          </div>
        </li>
      </ul>
      <!-- 페이지네이션 UI -->
      <nav v-if="wishNS.length > 4" aria-label="...">
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

    </div>
    <RouterLink :to="`/${id}/wish/create`">
      <button class="tw-btn tw-btn-outline tw-mx-1">소원 빌기</button>
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
import type { WishInfo } from '@/common/types/index'
import StarMenu from '@/components/StarMenu/StarMenu.vue';

// 스타 본인의 id 정보
const props = defineProps(['id']);
const id = ref(props.id);

// 위시 리스트
const wishNS = ref<WishInfo[]>([]);
const wishPS = ref<WishInfo[]>([]);
const wishDS = ref<WishInfo[]>([]);

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
  if (status == 0) {
    if (page >= 1 && page <= totalPagesDS.value) {
      currentPageDS.value = page;
    }
  }
  else if (status == 1) {
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

// 시작될 때 초기화 및 데이터 가져오기
onMounted(() => {
  getSortingWish(0);
});

</script>