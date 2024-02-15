<template>
  <main>
    <div class="my-5 text-center">
      <h2> {{ starid }} 티켓 박스</h2>
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
              <p>가격: {{ meetingStore.selectMeetingPrice }} 원</p>
            </div>
            <div v-else>
              <p>티켓을 선택해주세요.</p>
            </div>
            <div class="d-flex justify-content-center">
              <button :class="{'btn': true ,'btn-on':selectedProduct , 'btn-off':!selectedProduct}" @click="payRequest()">구매하기</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import type { FanMeetingTicket } from '@/common/types/index'
import { useRoute } from 'vue-router' 
import axios from 'axios'
import TicketFormat from '@/components/StarMenu/FanMeeting/TicketFormat.vue'
import { useUserStore } from '@/stores/user'
import { useFanMeetingStore } from '@/stores/fanMeeting'
import router from '@/router'

const route = useRoute();
const userStore = useUserStore();
const meetingStore = useFanMeetingStore();
const products = ref<FanMeetingTicket[]>([])

const starid = Number(route.params.starid);
const meetingid = Number(route.params.meetingid)


// products.value = [
//   {
//     id: 1,
//     image: '/image/fan-meeting-img.webp',
//     name: '생일 축하 노래!',
//     description: '생일 축하 노래!',
//     price: 3000
//   },
//   {
//     id: 2,
//     image: '/image/fan-meeting-img.webp',
//     name: '인생 네컷',
//     description: '60초 안에 인생 네컷을 찍어보세요!',
//     price: 4000
//   },
//   {
//     id: 3,
//     image: '/image/fan-meeting-img.webp',
//     name: '테트리스',
//     description: '같이 테트리스 해봅시다!',
//     price: 5000
//   },
//   {
//     id: 4,
//     image: '/image/fan-meeting-img.webp',
//     name: '자유',
//     description: '스타와 자유로운 시간을 보내세요!',
//     price: 10000
//   },
//   {
//     id: 2,
//     image: '/image/fan-meeting-img.webp',
//     name: '인생 네컷',
//     description: '60초 안에 인생 네컷을 찍어보세요!',
//     price: 4000
//   },
//   {
//     id: 3,
//     image: '/image/fan-meeting-img.webp',
//     name: '테트리스',
//     description: '같이 테트리스 해봅시다!',
//     price: 5000
//   },
//   {
//     id: 4,
//     image: '/image/fan-meeting-img.webp',
//     name: '자유',
//     description: '스타와 자유로운 시간을 보내세요!',
//     price: 10000
//   }
// ]

const selectedProduct = ref<FanMeetingTicket>()

const select = (product: FanMeetingTicket) => {
  selectedProduct.value = product
  console.log(fanId.value)
  console.log(selectedProduct.value.id)
  console.log(starid)
  console.log(meetingStore.selectMeetingPrice)
}

const getProducts = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/fanMeeting/info/${starid}`)
    products.value = response.data.availableTypes
    console.log(products.value)
  } catch (error) {
    console.log(error)
  }
}

const fanId = ref(userStore.fanState!.id)

const payRequest = async () => {

  if (selectedProduct!.value) {

    try {
      console.log("post 직전, 유저정보 :", userStore.fanState!.username)
      console.log("팬미팅 id :", meetingid, "팬 id :", fanId.value, "스타 id :", starid, "상품 타입 id :", selectedProduct.value.id)

      await axios.post(`http://localhost:8080/fanMeeting/${meetingid}/reservation/new/${starid}/${selectedProduct.value.id}`,
       { username: userStore.fanState!.username }, { withCredentials: true })
      
      console.log("post 끝남")
      
    } catch (error) {
      console.log(error)
    }
    router.push({ name: 'fanmeeting'})
  } else {
    window.alert('상품을 선택해주세요')
  }
  
}


onMounted(() => {
  getProducts()
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
  