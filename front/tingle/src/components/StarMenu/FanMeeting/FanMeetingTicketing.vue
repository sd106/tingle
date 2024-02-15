<template>
    <div>
      <div class="row" style="height: 500px;">
        <div class="col-md-8 p-0">
            <div class="img-fluid h-100 opacity-75" :style="{ backgroundImage: `url(${fanMeetingInfo.imgURL1})` }" style="background-size: cover;" ></div>
        </div>
        <div v-if="!props.haveTicket && remainTicket > 0" class="col-md-4 rounded-3 d-flex flex-column justify-content-center bg-light p-4">
            <div>
                <h2>{{ fanMeetingInfo.title }}</h2>
                <p>{{ fanMeetingInfo.description }}</p>
            </div>
            <p> {{ store.formatDateTime(fanMeetingInfo.ticketingStartAt) }} ~ {{ store.formatDateTime(fanMeetingInfo.ticketingEndAt) }}</p>

            <div class="text-center mt-4">
                <div class="d-grid gap-2 col-6 mx-auto">
                    <RouterLink :to="`/profile/${starId}/fanmeeting/${fanMeetingInfo.id}/ticketbox`" @click="changePrice">
                        <button class="btn btn-secondary btn-lg">
                            팬미팅 입장권 구매 {{ remainTicket }}
                        </button>
                    </RouterLink>
                </div>
            </div>
        </div>
        <div v-else class="col-md-4 rounded-3 d-flex flex-column justify-content-center bg-light p-4">
          <div>
            <h2>{{ fanMeetingInfo.title }}</h2>
            <p>{{ fanMeetingInfo.description }}</p>
          </div>
          <hr>
          <p>티켓 예매 기간 :</p>
          <p> {{ store.formatDateTime(fanMeetingInfo.ticketingStartAt) }} 부터</p>
          <p>{{ store.formatDateTime(fanMeetingInfo.ticketingEndAt) }} 까지</p>
          <hr>
          <p>{{ store.formatDateTime(fanMeetingInfo.fanMeetingStartAt) }}에 미팅이 열립니다!</p>
        </div>
      </div>
    </div>
</template>

<script setup lang="ts">
import { ref, defineProps, onMounted, computed } from 'vue'
import type { PropType } from 'vue';
import axios from 'axios'
import { useRoute } from 'vue-router' 
import type { FanMeetingInfo } from '@/common/types/index'
import { useFanMeetingStore } from '@/stores/fanMeeting'

const props = defineProps({
    fanMeetingInfo: {
        type: Object as PropType<FanMeetingInfo>,
        required: true
    },
    starId:String,
    haveTicket:Boolean

})

const store = useFanMeetingStore()
const route = useRoute()

const starId = Number(props.starId)

const changePrice = () => {
    store.selectMeetingPrice = props.fanMeetingInfo!.price
}

const sellingTicket = ref<number>(0);
const remainTicket = ref(props.fanMeetingInfo.capacity);
const getTicketNumber =async () => {
    
    try {
        console.log("팬미팅 id", props.fanMeetingInfo.id)
        const response = await axios.get(`http://localhost:8080/fanMeeting/${props.fanMeetingInfo.id}/reservation`)
        sellingTicket.value = response.data
        console.log("판매된 티켓", sellingTicket.value)
    } catch (error) {
        console.log(error)
    }
}

const changeRemainTicket =async () => {
    try {
        console.log(remainTicket.value)
        console.log(sellingTicket.value)
        if (sellingTicket.value > 0 && sellingTicket.value < remainTicket.value) {
            remainTicket.value = remainTicket.value - sellingTicket.value
        } 
    } catch (error) {
        console.log(error)
    }
}

onMounted(() => {
    getTicketNumber()
    console.log(sellingTicket.value)
    console.log(remainTicket.value)
    changeRemainTicket()
    
})

</script>

<style scoped></style>
