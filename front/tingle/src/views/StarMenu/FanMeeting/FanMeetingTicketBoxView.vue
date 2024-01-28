<template>
  <main>
    <div class="my-5 text-center">
      <h2> {{ starName }} 티켓 박스</h2>
    </div>
    <div class="container">
      <div class="row">
        <div class="col-md-8 ">
          <div class="row mb-4" v-for="product in products" :key="product.id">
            <TicketFormat @select="select(product)" :product="product" :isSelected="selectedProduct === product" />
          </div>
        </div>

        <div class="col-md-4">
          <div class="pay-box">
            <div v-if="selectedProduct">
              <h4>{{ selectedProduct.name }}</h4>
              <p>가격: {{ selectedProduct.price }} 원</p>
            </div>
            <div v-else>
              <p>티켓을 선택해주세요.</p>
            </div>
            <div class="d-flex justify-content-center">
              <button :class="{'btn': true ,'btn-on':selectedProduct , 'btn-off':!selectedProduct}" @click="payRequest">구매하기</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>ddd
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import type { FanMeetingTicket } from '@/common/types/index'
import { useRoute } from 'vue-router'
import axios from 'axios'
import TicketFormat from '@/components/StarMenu/FanMeeting/TicketFormat.vue'

const route = useRoute()

const products = ref<FanMeetingTicket[]>([])


const starName = ref<string>()
starName.value = route.params.username.toString();

products.value = [
  {
    id: 1,
    image: '/image/fan-meeting-img.webp',
    name: '생일 축하 노래!',
    description: '생일 축하 노래!',
    price: 3000
  },
  {
    id: 2,
    image: '/image/fan-meeting-img.webp',
    name: '인생 네컷',
    description: '60초 안에 인생 네컷을 찍어보세요!',
    price: 4000
  },
  {
    id: 3,
    image: '/image/fan-meeting-img.webp',
    name: '테트리스',
    description: '같이 테트리스 해봅시다!',
    price: 5000
  },
  {
    id: 4,
    image: '/image/fan-meeting-img.webp',
    name: '자유',
    description: '스타와 자유로운 시간을 보내세요!',
    price: 10000
  },
  {
    id: 2,
    image: '/image/fan-meeting-img.webp',
    name: '인생 네컷',
    description: '60초 안에 인생 네컷을 찍어보세요!',
    price: 4000
  },
  {
    id: 3,
    image: '/image/fan-meeting-img.webp',
    name: '테트리스',
    description: '같이 테트리스 해봅시다!',
    price: 5000
  },
  {
    id: 4,
    image: '/image/fan-meeting-img.webp',
    name: '자유',
    description: '스타와 자유로운 시간을 보내세요!',
    price: 10000
  }
]

const selectedProduct = ref<FanMeetingTicket>()

const select = (product: FanMeetingTicket) => {
  selectedProduct.value = product
}

const getProducts = async () => {
  try {
    const response = await axios.get(``)
    products.value = response.data
  } catch (error) {
    console.log(error)
  }
}

const payRequest = async () => {
  try {
    const response = await axios.post(``)
    products.value = response.data
  } catch (error) {
    console.log(error)
  }
}


onMounted(() => {
//   getProducts()
})
</script>

<style scoped>
.pay-box {
  border: 1px solid #e2e2e2;
  border-radius: 10px;
  padding: 10px;
  position: fixed;
  width: 20%;
}

@media (max-width: 767px) {
  .pay-box {
    margin-top: 0;
    position: relative;
    width: auto;
  }
}

.btn-on {
  width: 80%;
  background-color: rgb(173, 172, 215);
}

.btn-off {
  width: 80%;
  background-color: slategray;
  cursor: not-allowed;
}

</style>
  