<template>
  <main class="container">
    <StarMenu :id="id" />
    <!-- /////////////////////////////////////////////////////////// -->
    <!-- ////////// 지금 함수 안먹어서 나중에 되는지 확인해야함 -->
    <!-- /////////////////////////////////////////////////////////// -->
    <div class="container tw-flex tw-justify-between my-4" style="padding-left: 80px; padding-right: 80px; padding-top: 30px;">
      <!-- {{ selectedSort }} -->
      <div>
        <button
          class="btn fs-5 fw-bold tw-text-gray-500 tw-mx-1 menu"
          :class="{ 'tw-text-gray-900': selectedSort === 0 }"
          @click="getSortingWish(0)"
        >
          ✨ 최신순
        </button>
        <button
          class="btn fs-5 fw-bold tw-text-gray-500 tw-mx-1 menu"
          :class="{ 'tw-text-gray-900': selectedSort === 1 }"
          @click="getSortingWish(1)"
        >
          ❤ 좋아요순
        </button>
        <button
          class="btn fs-5 fw-bold tw-text-gray-500 tw-mx-1 menu"
          :class="{ 'tw-text-gray-900': selectedSort === 2 }"
          @click="getSortingWish(2)"
        >
          🌟 포인트순
        </button>
      </div>
      <div>
        <RouterLink :to="`/${id}/wish/create`">
          <button class="btn fs-5 fw-bold tw-text-gray-700 tw-mx-1 menu">🙏 소원 빌기</button>
        </RouterLink>
      </div>
    </div>
    <div class="container" style="padding-left: 80px; padding-right: 80px;">
      <!-- DS 리스트 -->
      <span class="title">별빛 아래 이루어진 꿈</span>
      <!-- /////////////////////////////////////////////////////////// -->
      <!-- 뭔가 추가 페이지로 결과 확인할 수 있어야 할 텐데...? 구독자만 볼 수 있다거나 -->
      <!-- 없으면 그게 없다고 표시 최소 크기 지정 -->
      <!-- /////////////////////////////////////////////////////////// -->
      <ul
        v-if="getCurrentPageItemsDS.length > 0"
        role="list"
        class="tw-divide-y tw-divide-gray-300"
      >
        <li
          v-for="(wish, index) in getCurrentPageItemsDS"
          :key="index"
          class="tw-flex tw-justify-between tw-gap-x-6 tw-py-3 tw-items-center"
        >
          <div class="tw-min-w-0">
            <div class="tw-text-sm tw-font-semibold tw-text-gray-900">{{ wish.id }}번째 Wish</div>
            <div class="tw-mt-1 tw-text-xs tw-text-gray-500">User ID: {{ wish.userId }}</div>
          </div>
          <div class="tw-flex-1 tw-text-sm tw-font-t tw-truncate">
            <span class="tw-block tw-truncate">{{ wish.contents }}</span>
          </div>
          <div v-if="wish.contents.length > 100" class="tw-tooltip" :data-tip="wish.contents">
            <button class="">더보기</button>
          </div>
          <div class="tw-flex tw-gap-x-2 tw-items-center">
            <button
              v-if="checkIfWishHasLike(wish.id)"
              @click="updateWishLiked(wish.id, index, -1, 'DS')"
              class="tw-btn tw-btn-outline dislike"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="tw-h-6 tw-w-6 heart"
                fill="red"
                viewBox="0 0 24 24"
                stroke="black"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"
                />
              </svg>
              {{ wish.likedCount }}
            </button>
            <button
              v-else
              @click="updateWishLiked(wish.id, index, 1, 'DS')"
              class="tw-btn tw-btn-outline like"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="tw-h-6 tw-w-6 heart"
                fill="none"
                viewBox="0 0 24 24"
                stroke="black"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"
                />
              </svg>
              {{ wish.likedCount }}
            </button>
            <div class="points">points: {{ formatNumber(wish.points) }}</div>
          </div>
        </li>
      </ul>
      <div v-else class="tw-text-sm tw-font-semibold tw-text-gray-900 tw-text-center">
        완료된 위시가 없습니다
      </div>
      <!-- 페이지네이션 UI -->
      <nav v-if="wishDS.length > 4" aria-label="..." class="tw-my-1">
        <ul class="pagination tw-flex tw-justify-center">
          <li class="page-item" :class="{ disabled: currentPageDS === 1 }">
            <a
              class="page-link"
              @click="changePage(currentPageDS - 1, 0)"
              tabindex="-1"
              href="#"
              aria-label="Previous"
            >
              <span aria-hidden="true">&lt;</span>
            </a>
          </li>
          <li
            v-for="page in pagesToShowDS"
            :key="page"
            class="page-item"
            :class="{ active: currentPageDS === page }"
          >
            <a class="page-link" @click="changePage(page, 2)">{{ page }}</a>
          </li>
          <li class="page-item" :class="{ disabled: currentPageDS === totalPagesDS }">
            <a
              class="page-link"
              @click="changePage(currentPageDS + 1, 0)"
              href="#"
              aria-label="Next"
            >
              <span aria-hidden="true">&gt;</span>
            </a>
          </li>
        </ul>
      </nav>
    </div>
    <br>
    <br>
    <div class="container" style="padding-left: 80px; padding-right: 80px;">
      <hr />
      <!-- PS 리스트 -->
      <span class="title">별이 선택한 소원</span>
      <ul
        v-if="getCurrentPageItemsPS.length > 0"
        role="list"
        class="tw-divide-y tw-divide-gray-300"
      >
        <li
          v-for="(wish, index) in getCurrentPageItemsPS"
          :key="index"
          class="tw-flex tw-justify-between tw-gap-x-6 tw-py-3 tw-items-center"
        >
          <div class="tw-min-w-0">
            <div class="tw-text-sm tw-font-semibold tw-text-gray-900">{{ wish.id }}번째 Wish</div>
            <div class="tw-mt-1 tw-text-xs tw-text-gray-500">User ID: {{ wish.userId }}</div>
          </div>
          <div class="tw-flex-1 tw-text-sm tw-font-t tw-truncate">
            <span class="tw-block tw-truncate">{{ wish.contents }}</span>
          </div>
          <div v-if="wish.contents.length > 100" class="tw-tooltip" :data-tip="wish.contents">
            <button class="">더보기</button>
          </div>
          <div class="tw-flex tw-gap-x-2 tw-items-center">
            <button
              v-if="checkIfWishHasLike(wish.id)"
              @click="updateWishLiked(wish.id, index, -1, 'PS')"
              class="tw-btn tw-btn-outline dislike"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="tw-h-6 tw-w-6 heart"
                fill="red"
                viewBox="0 0 24 24"
                stroke="black"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"
                />
              </svg>
              {{ wish.likedCount }}
            </button>
            <button
              v-else
              @click="updateWishLiked(wish.id, index, 1, 'PS')"
              class="tw-btn tw-btn-outline like"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="tw-h-6 tw-w-6 heart"
                fill="none"
                viewBox="0 0 24 24"
                stroke="black"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"
                />
              </svg>
              {{ wish.likedCount }}
            </button>
            <button @click="addPoints(wish, index, 1000, `PS`)" class="tw-btn tw-btn-outline point">
              <img src="/image/coin.png" class="tw-h-t tw-w-6 coin" alt="" />
              {{ formatNumber(wish.points) }}
            </button>
            <!-- 완료 버튼 -->
            <button
              class="tw-btn tw-btn-outline tw-btn-success complete"
              v-if="wish.userId == myid"
              @click="updateWishStatus(wish.id)"
            >
              <img src="/image/complete.png" class="tw-h-6 tw-w-6 fin" alt="" />
            </button>
          </div>
        </li>
      </ul>
      <div v-else class="tw-text-sm tw-font-semibold tw-text-gray-900 tw-text-center">
        선택된 위시가 없습니다
      </div>
      <!-- 페이지네이션 UI -->
      <nav v-if="wishPS.length > 4" aria-label="..." class="tw-my-1">
        <ul class="pagination tw-flex tw-justify-center">
          <li class="page-item" :class="{ disabled: currentPagePS === 1 }">
            <a
              class="page-link"
              @click="changePage(currentPagePS - 1, 0)"
              tabindex="-1"
              href="#"
              aria-label="Previous"
            >
              <span aria-hidden="true">&lt;</span>
            </a>
          </li>
          <li
            v-for="page in pagesToShowPS"
            :key="page"
            class="page-item"
            :class="{ active: currentPagePS === page }"
          >
            <a class="page-link" @click="changePage(page, 2)">{{ page }}</a>
          </li>
          <li class="page-item" :class="{ disabled: currentPagePS === totalPagesPS }">
            <a
              class="page-link"
              @click="changePage(currentPagePS + 1, 0)"
              href="#"
              aria-label="Next"
            >
              <span aria-hidden="true">&gt;</span>
            </a>
          </li>
        </ul>
      </nav>
    </div>
    <br>
    <br>
    <div class="container" style="padding-left: 80px; padding-right: 80px;">
      <hr />
      <!-- NS 리스트 -->
      <span class="title">별을 기다리는 소망</span>
      <ul
        v-if="getCurrentPageItemsNS.length > 0"
        role="list"
        class="tw-divide-y tw-divide-gray-300"
      >
        <li
          v-for="(wish, index) in getCurrentPageItemsNS"
          :key="index"
          class="tw-flex tw-justify-between tw-gap-x-6 tw-py-3 tw-items-center"
        >
          <div class="tw-min-w-0">
            <div class="tw-text-sm tw-font-semibold tw-text-gray-900">{{ wish.id }}번째 Wish</div>
            <div class="tw-mt-1 tw-text-xs tw-text-gray-500">User ID: {{ wish.userId }}</div>
          </div>
          <div class="tw-flex-1 tw-text-sm tw-font-t">
            <span class="tw-block tw-truncate">{{ wish.contents }}</span>
          </div>
          <div v-if="wish.contents.length > 100" class="tw-tooltip" :data-tip="wish.contents">
            <button class="">더보기</button>
          </div>
          <div class="tw-flex tw-gap-x-2 tw-items-center">
            <button
              v-if="checkIfWishHasLike(wish.id)"
              @click="updateWishLiked(wish.id, index, -1, 'NS')"
              class="tw-btn tw-btn-outline dislike"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="tw-h-6 tw-w-6 heart"
                fill="red"
                viewBox="0 0 24 24"
                stroke="black"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"
                />
              </svg>
              {{ wish.likedCount }}
            </button>
            <button
              v-else
              @click="updateWishLiked(wish.id, index, 1, 'NS')"
              class="tw-btn tw-btn-outline like"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="tw-h-6 tw-w-6 heart"
                fill="none"
                viewBox="0 0 24 24"
                stroke="black"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"
                />
              </svg>
              {{ wish.likedCount }}
            </button>
            <!-- 포인트 추가 버튼 -->
            <!-- /////////////////////////////////////////////////////////// -->
            <!-- ////// 일단 1000포인트 추가로 고정 -->
            <!-- /////////////////////////////////////////////////////////// -->
            <button @click="addPoints(wish, index, 1000, `NS`)" class="tw-btn tw-btn-outline point">
              <img src="/image/coin.png" class="tw-h-t tw-w-6 coin" alt="" />
              {{ formatNumber(wish.points) }}
            </button>
            <!-- 삭제 버튼 -->
            <button
              class="tw-btn tw-btn-outline tw-btn-error delete"
              v-if="wish.userId == myid"
              @click="deleteWish(wish.id)"
            >
              <img src="/image/trashcan.png" class="tw-h-6 tw-w-6 can" alt="" />
            </button>
          </div>
        </li>
      </ul>
      <div v-else class="tw-text-sm tw-font-semibold tw-text-gray-900 tw-text-center">
        선택된 위시가 없습니다
      </div>
      <!-- 페이지네이션 UI -->
      <nav v-if="wishNS.length > 4" aria-label="..." class="tw-my-1">
        <ul class="pagination tw-flex tw-justify-center">
          <li class="page-item" :class="{ disabled: currentPageNS === 1 }">
            <a
              class="page-link"
              @click="changePage(currentPageNS - 1, 0)"
              tabindex="-1"
              href="#"
              aria-label="Previous"
            >
              <span aria-hidden="true">&lt;</span>
            </a>
          </li>
          <li
            v-for="page in pagesToShowNS"
            :key="page"
            class="page-item"
            :class="{ active: currentPageNS === page }"
          >
            <a class="page-link" @click="changePage(page, 2)">{{ page }}</a>
          </li>
          <li class="page-item" :class="{ disabled: currentPageNS === totalPagesNS }">
            <a
              class="page-link"
              @click="changePage(currentPageNS + 1, 0)"
              href="#"
              aria-label="Next"
            >
              <span aria-hidden="true">&gt;</span>
            </a>
          </li>
        </ul>
      </nav>
    </div>
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
import { useRouter } from 'vue-router'
import StarMenu from '@/components/StarMenu/StarMenu.vue'

const router = useRouter()

// 스타 id 정보
const props = defineProps(['id'])
const id = ref(props.id)
const myid = store.fanState?.id

// 위시 리스트
const wishNS = ref<WishInfo[]>([])
const wishPS = ref<WishInfo[]>([])
const wishDS = ref<WishInfo[]>([])
const likesList = ref<LikesInfo[]>([])

// 페이지네이션 관련 변수
const itemsPerPageDS = 5 // 페이지당 아이템 수
const currentPageDS = ref(1) // 현재 페이지
const totalPagesDS = computed(() => Math.ceil(wishDS.value.length / itemsPerPageDS)) // 전체 페이지 수

const itemsPerPagePS = 5 // 페이지당 아이템 수
const currentPagePS = ref(1) // 현재 페이지
const totalPagesPS = computed(() => Math.ceil(wishPS.value.length / itemsPerPagePS)) // 전체 페이지 수

const itemsPerPageNS = 5 // 페이지당 아이템 수
const currentPageNS = ref(1) // 현재 페이지
const totalPagesNS = computed(() => Math.ceil(wishNS.value.length / itemsPerPageNS)) // 전체 페이지 수

// 페이지네이션 일부 표시를 위한 함수
const pagesToShowDS = computed(() => {
  const pages: Array<number | string> = []
  const startPage = Math.max(currentPageDS.value - 2, 1)
  const endPage = Math.min(startPage + 4, totalPagesDS.value)

  if (startPage > 1) {
    pages.push(1)
    if (startPage > 2) pages.push('...')
  }

  for (let page = startPage; page <= endPage; page++) {
    pages.push(page)
  }

  if (endPage < totalPagesDS.value) {
    if (endPage < totalPagesDS.value - 1) pages.push('...')
    pages.push(totalPagesDS.value)
  }

  return pages
})

const pagesToShowPS = computed(() => {
  const pages: Array<number | string> = []
  const startPage = Math.max(currentPagePS.value - 2, 1)
  const endPage = Math.min(startPage + 4, totalPagesPS.value)

  if (startPage > 1) {
    pages.push(1)
    if (startPage > 2) pages.push('...')
  }

  for (let page = startPage; page <= endPage; page++) {
    pages.push(page)
  }

  if (endPage < totalPagesPS.value) {
    if (endPage < totalPagesPS.value - 1) pages.push('...')
    pages.push(totalPagesPS.value)
  }

  return pages
})

const pagesToShowNS = computed(() => {
  const pages: Array<number | string> = []
  const startPage = Math.max(currentPageNS.value - 2, 1)
  const endPage = Math.min(startPage + 4, totalPagesNS.value)

  if (startPage > 1) {
    pages.push(1)
    if (startPage > 2) pages.push('...')
  }

  for (let page = startPage; page <= endPage; page++) {
    pages.push(page)
  }

  if (endPage < totalPagesNS.value) {
    if (endPage < totalPagesNS.value - 1) pages.push('...')
    pages.push(totalPagesNS.value)
  }

  return pages
})

// 페이지 변경 함수
const changePage = (page: number | string, status: number) => {
  if (page === '...') {
    return
  }
  let targetPage = Number(page) // 'page'를 숫자 타입으로 변환

  if (status === 0) {
    if (targetPage >= 1 && targetPage <= totalPagesDS.value) {
      currentPageDS.value = targetPage
    }
  } else if (status === 1) {
    if (targetPage >= 1 && targetPage <= totalPagesPS.value) {
      currentPagePS.value = targetPage
    }
  } else {
    if (targetPage >= 1 && targetPage <= totalPagesNS.value) {
      currentPageNS.value = targetPage
    }
  }
}

// 현재 페이지의 아이템 가져오기 (DS)
const getCurrentPageItemsDS = computed(() => {
  const startIndex = (currentPageDS.value - 1) * itemsPerPageDS
  const endIndex = startIndex + itemsPerPageDS
  return wishDS.value.slice(startIndex, endIndex)
})

// 현재 페이지의 아이템 가져오기 (PS)
const getCurrentPageItemsPS = computed(() => {
  const startIndex = (currentPagePS.value - 1) * itemsPerPagePS
  const endIndex = startIndex + itemsPerPagePS
  return wishPS.value.slice(startIndex, endIndex)
})

// 현재 페이지의 아이템 가져오기 (NS)
const getCurrentPageItemsNS = computed(() => {
  const startIndex = (currentPageNS.value - 1) * itemsPerPageNS
  const endIndex = startIndex + itemsPerPageNS
  return wishNS.value.slice(startIndex, endIndex)
})

// 데이터 가져오기 함수
const fetchData = async (status: number, sorting: number): Promise<void> => {
  try {
    const res = await axios.get(`${store.API_URL}/wish/read/${id.value}/${sorting}/${status}`)

    if (status === 0) {
      wishNS.value = res.data.data
      currentPageNS.value = 1
    } else if (status === 1) {
      wishPS.value = res.data.data
      currentPagePS.value = 1
    } else {
      wishDS.value = res.data.data
      currentPageDS.value = 1
    }
  } catch (error) {
    console.error(`Error fetching wishes (${status}): `, error)
  }
}

// 포인트 표시
const formatNumber = (number: number) => {
  return number.toLocaleString()
}

// 최신순/추천순/포인트순 정렬
const selectedSort = ref(0)

const getSortingWish = function (sorting: number): void {
  fetchData(0, sorting)
  fetchData(1, sorting)
  fetchData(2, sorting)
  getLikesList()
  selectedSort.value = sorting
}

// 해당 위시에 포인트 추가
const addPoints = async function (
  wish: WishInfo,
  index: number,
  data: number,
  status: string
): Promise<void> {
  try {
    await axios.post(`${store.API_URL}/wish/add/points/${wish.id}/${myid}/${data}`)

    if (status === 'PS') {
      wishPS.value[index].points += data
    } else if (status === 'NS') {
      wishNS.value[index].points += data
    }
  } catch (error) {
    console.error('Error fetching addPoints: ', error)
  }
}

// 해당 위시를 완료 상태로 변경
const updateWishStatus = async (wishId: number): Promise<void> => {
  try {
    const res = await axios.post(`${store.API_URL}/wish/update/status/star/${myid}/${wishId}/2`)

    console.log('Wish status updated:', res)

    // 상태를 업데이트한 후, 다시 위시 목록을 불러올 수 있도록
    getSortingWish(0)
  } catch (error) {
    console.error('Error updating wish status:', error)
  }
}

// 내 추천 리스트 조회
const getLikesList = async function (): Promise<void> {
  try {
    const res = await axios.get(`${store.API_URL}/likes/read/mylist/${id.value}/${myid}`)
    likesList.value = res.data.data
    console.log(likesList.value, '========================')
  } catch (error) {
    console.error('Error fetching likeList: ', error)
  }
}

// 해당 위시 삭제
const deleteWish = async function (wishId: number): Promise<void> {
  try {
    await axios.delete(`${store.API_URL}/wish/delete/${wishId}/${id.value}/${myid}`)
    router.go(0)
  } catch (error) {
    console.error('Error fetching deleteWish: ', error)
  }
}

const updateWishLiked = async function (
  wishId: number,
  index: number,
  data: number,
  status: String
): Promise<void> {
  try {
    await axios.post(`${store.API_URL}/likes/update/toggle/${wishId}/${myid}`)

    if (status === 'DS') {
      wishDS.value[index].likedCount += data
    } else if (status === 'PS') {
      wishPS.value[index].likedCount += data
    } else if (status === 'NS') {
      wishNS.value[index].likedCount += data
    }
    console.log('likesList:', likesList.value)
    getLikesList()
  } catch (error) {
    console.error('Error updating wish liked status:', error)
  }
}

const checkIfWishHasLike = (wishId: number) => {
  if (likesList.value.length > 0) {
    const foundLike = likesList.value.find((like) => like.wishId === wishId)
    if (foundLike) {
      // 해당 wishId를 가진 항목이 있다면, 해당 항목의 값을 반환
      return foundLike.liked
    }
    // 해당 wishId를 가진 항목이 없다면, 원하는 값을 반환
    return 0 // 또는 다른 값으로 대체 가능
  }
}

// 시작될 때 초기화 및 데이터 가져오기
onMounted(() => {
  getSortingWish(0)
  getLikesList()
})
</script>

<style scoped>
.like,
.dislike,
.point,
.delete,
.complete,
.menu {
  border: none;
  outline: none;
  transition: background-color 0.3s;
}

.like:hover,
.dislike:hover,
.point:hover,
.delete:hover,
.complete:hover {
  background-color: white;
  color: black;
}

.like:hover .heart {
  fill: red;
  transform: scale(1.5);
}

.dislike:hover .heart {
  fill: none;
  opacity: 0.2;
  transition:
    opacity 0.3s ease,
    fill 0.3s ease;
}

.point:hover .coin {
  transform: scale(1.5);
}

.delete:hover .can {
  transform: scale(1.5);
}

.complete:hover .fin {
  transform: scale(1.5);
}

.heart,
.coin,
.can,
.fin {
  transition:
    fill 0.3s ease,
    stroke 0.3s ease,
    transform 0.3s ease;
}

.tw-btn-success:hover {
  color: white;
}

.tw-btn-error:hover {
  color: white;
}

.page-item {
  margin: 0 2px;
  border-radius: 10px;
}

.page-link {
  display: block;
  padding: 8px 16px;
  color: #030e20;
  border: none;
  border-radius: 10px;
  text-decoration: none;
  transition:
    background-color 0.2s,
    color 0.2s;
}

.page-link:hover {
  background-color: #80a4e4;
  color: white;
  cursor: pointer;
}

.page-item.disabled .page-link {
  color: #9ca3af;
  cursor: not-allowed;
}

.page-item.active .page-link {
  background-color: #10b981;
  color: white;
  cursor: default;
}

.title {
  display: block;
  text-align: center;
  font-size: 1.5em;
  font-weight: bold;
  margin: 20px 0;
  color: black;
}

.menu:hover {
  transform: scale(1.1);
  transition: transform 0.3s ease-in-out;
}
</style>
