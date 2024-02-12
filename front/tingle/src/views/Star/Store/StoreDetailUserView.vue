<template>
  <main>
    <StarMenu :id="starid" />
    <div v-if="product" class="d-flex row tw-space-x-4 tw-mt-2">
      <!-- 왼쪽 -->
      <!-- <div class="col-1">
        <button @click="goBack" class="tw-btn tw-btn-circle tw-glass">돌아가기</button>
      </div> -->
      <div class="col-6">
        <div class="tw-carousel tw-w-full">
          <div
            v-for="(image, index) in product.imageUrl"
            :key="image.id"
            class="tw-carousel-item tw-relative tw-w-full"
            :class="{ 'tw-hidden': index !== activeIndex }"
          >
            <img :src="image.url" alt="" class="tw-w-full" />
            <div
              v-if="product.imageUrl.length > 1"
              class="tw-absolute tw-flex tw-justify-between tw-transform tw--translate-y-1/2 tw-left-5 tw-right-5 tw-top-1/2"
            >
              <button @click="prevSlide" class="tw-btn tw-btn-circle tw-glass">❮</button>
              <button @click="nextSlide" class="tw-btn tw-btn-circle tw-glass">❯</button>
            </div>
          </div>
        </div>
      </div>
      <!-- 오른쪽 -->
      <div class="col-4 tw-ps-6 tw-flex tw-flex-col tw-justify-between">
        <div>
          <p class="tw-text-3xl tw-font-bold tw-text-gray-900 tw-px-2 tw-py-2">
            {{ product.name }}
          </p>
          <p class="tw-text-xl tw-font-semibold tw-text-gray-900 tw-pt-2 tw-px-2">
            {{ formattedPrice }}<span class="tw-text-sm">원</span>
          </p>
          <div class="tw-flex tw-items-center tw-mb-4 tw-px-1">
            <p class="tw-text-xs tw-text-gray-500 tw-border tw-px-2 tw-py-1 tw-rounded">
              {{ product.amount }} 개 남음
            </p>
          </div>
          <hr class="tw-my-4" />
          <div v-html="product.content" class="tw-py-4 tw-px-2"></div>
          <hr class="tw-my-4" />
        </div>
        <div>
          <!-- ////// 유저일때 -->
          <button v-if="product.available === true && product.amount > 0"
            class="tw-btn tw-btn-active tw-btn-block tw-bg-black tw-text-white tw-py-3 tw-rounded-md tw-mb-2"
            @click="createOrder(fanState!.id, product.productId)"
          >
            구매하기
          </button>
          <button v-else class="tw-btn tw-btn-active tw-btn-block tw-bg-black tw-text-white tw-py-3 tw-rounded-md tw-mb-2">
            구매할 수 없는 상품입니다.
          </button>
        </div>
      </div>
    </div>
  </main>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import type { Goods } from '@/common/types'
import axios from 'axios'
import StarMenu from '@/components/StarMenu/StarMenu.vue'
import { useUserStore } from '@/stores/user'

const { fanState } = useUserStore()
const props = defineProps({
  starid: String,
  productId: String // URL에서 받은 값은 문자열이므로 String으로 받음
})

const starid = ref(props.starid)
const productIdNumber = computed(() => Number(props.productId)) // 숫자로 변환
const product = ref<Goods>()
const router = useRouter()

const getProduct = async (productId: number) => {
  try {
    const response = await axios.get(`https://i10d106.p.ssafy.io/api/product/getById/${productId}`)
    if (response.data.resultCode === 'SUCCESS') {
      product.value = response.data.data
    } else {
      console.error(response.data.message)
      console.log(response.data.data)
      product.value = response.data.data
    }
  } catch (error) {
    console.error('상품 조회 중 오류 발생', error)
  }
}

onMounted(() => {
  if (productIdNumber.value !== undefined) {
    getProduct(productIdNumber.value)
  }
})

const createOrder = async (fanId:number, productId:number) => {
    try {
      const response = await axios.post(`http://localhost:8080/order/create/${fanId}/${productId}`);
      alert("상품 구매에 성공 하였습니다.")
      router.go(-1)
    } catch (error) {
      alert("상품 구매에 실패 하였습니다.")
      console.error(error);
    }
  };

// 사진들 카로셀
const activeIndex = ref(0)

const prevSlide = () => {
  if (activeIndex.value > 0) activeIndex.value -= 1
}

// 다음 슬라이드로 이동
const nextSlide = () => {
  if (activeIndex.value < product.value!.imageUrl.length - 1) activeIndex.value += 1
}

const formattedPrice = computed(() => {
  return new Intl.NumberFormat('ko-KR', { style: 'decimal' }).format(product.value!.price)
})

// 뒤로가기
function goBack() {
  router.go(-1) // 또는 router.back()
}

// 상품 삭제

const getProductdelete = async (productId: number) => {
  try {
    const response = await axios.post(`http://localhost:8080/product/delete/${productId}`)
    if (response.data === 'SUCCESS') {
      alert('상품 삭제에 성공 하였습니다.')
      router.go(-1)
    } else {
      console.error(response.data.message)
      alert('상품 삭제에 실패 하였습니다.')
    }
  } catch (error) {
    console.error('스타의 상품 목록 조회 중 오류 발생', error)
  }
}
</script>

<style scoped>
.close-button {
  float: right;
}

.goods-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
/* 가장 외부 컨테이너 중앙 정렬을 위한 스타일 */
.d-flex.row.tw-space-x-4.tw-mt-2 {
  display: flex;
  justify-content: center; /* 가로 중앙 정렬 */
}

/* 이미지 컨테이너 스타일 */
.tw-carousel {
  display: flex;
  justify-content: center; /* 이미지를 가로 방향으로 중앙에 배치 */
  align-items: center; /* 이미지를 세로 방향으로 중앙에 배치 */
  height: 80vh; /* 예시 높이, 실제 사용 조건에 맞게 조정 필요 */
}

/* 이미지 스타일 */
.tw-carousel-item img {
  object-fit: contain; /* 비율을 유지하면서 최대한 컨테이너에 맞춤 */
}
</style>
