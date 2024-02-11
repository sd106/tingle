<template>
  <main>
    <StarMenu :name="name" />
    
    <section v-if="fanMeetingInfo && fanMeetingInfo.status === 'ticketing'" class="pt-5">
      <FanMeetingTicketing :fanMeetingInfo="fanMeetingInfo"></FanMeetingTicketing>
    </section>

    <section v-else-if="fanMeetingInfo && fanMeetingInfo.status === 'open'" class="pt-5">
      <FanMeetingOpen :fanMeetingInfo="fanMeetingInfo"></FanMeetingOpen>
    </section>

    <section v-else class="pt-5 text-center">
      <FanMeetingClosed></FanMeetingClosed>
    </section>

  </main>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'
import type { FanMeetingInfo } from '@/common/types/index'
import FanMeetingOpen from '@/components/StarMenu/FanMeeting/FanMeetingOpen.vue';
import FanMeetingTicketing from '@/components/StarMenu/FanMeeting/FanMeetingTicketing.vue';
import FanMeetingClosed from '@/components/StarMenu/FanMeeting/FanMeetingClosed.vue';
import StarMenu from '@/components/StarMenu/StarMenu.vue';

const props = defineProps(['username'])
const name = ref('')
name.value = props.username

const fanMeetingInfo = ref<FanMeetingInfo>()

// fanMeetingInfo.value = 
// {
//   "id": 1,
//   "status": "open",
//   "name": "제 1회 르브론 팬미팅",
//   "description": "모두 모여라!",
//   "ticketingStartAt": "2024-01-25 14:41:00",
//   "ticketingEndAt": "2024-01-26 14:41:00",
//   "fanMeetingStartAt": "2024-01-27 02:41:00",
//   "price": 10000,
//   "imgURL": '/image/fan-meeting-img.webp',
// }


const getFanMeetingInfo = async () => {
  try {
    const response = await axios.get(`http://i10d106.p.ssafy.io:8080/api/fanMeeting/info/${props.starid}`)
    fanMeetingInfo.value = response.data
  } catch (error) {
    console.log(error)
  }
}


onMounted(() => {
  getFanMeetingInfo()
})
</script>
