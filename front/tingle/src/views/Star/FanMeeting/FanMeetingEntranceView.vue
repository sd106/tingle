<template>
  <main class="container">
    <StarMenu :id="id" />

    <section v-if="fanMeetingInfo && fanMeetingInfo.status === 'ticketing'" class="pt-5">
      <FanMeetingTicketing :fanMeetingInfo="fanMeetingInfo" :starId="props.id" :haveTicket="haveTicket"></FanMeetingTicketing>
    </section>

    <section v-else-if="fanMeetingInfo && fanMeetingInfo.status === 'open'" class="pt-5">
      <FanMeetingOpen :fanMeetingInfo="fanMeetingInfo" :haveTicket="haveTicket"></FanMeetingOpen>
    </section>

    <section v-else class="pt-5 text-center">
      <FanMeetingClosed></FanMeetingClosed>
    </section>
  </main>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useUserStore } from '@/stores/user'
import type { FanMeetingInfo } from '@/common/types/index'
import FanMeetingOpen from '@/components/StarMenu/FanMeeting/FanMeetingOpen.vue'
import FanMeetingTicketing from '@/components/StarMenu/FanMeeting/FanMeetingTicketing.vue'
import FanMeetingClosed from '@/components/StarMenu/FanMeeting/FanMeetingClosed.vue'
import StarMenu from '@/components/StarMenu/StarMenu.vue'

const store = useUserStore()
const props = defineProps(['id'])
const name = ref(store.fanState?.username)

const fanMeetingInfo = ref<FanMeetingInfo>()

const haveTicket = ref<boolean>(false);
const starId = ref<number>();
starId.value = Number(props.id)


const getFanMeetingInfo = async () => {
  try {
    console.log(starId.value)
    const response = await axios.get(`http://localhost:8080/fanMeeting/info/${starId.value}`)
    fanMeetingInfo.value = response.data
    console.log(response)
    console.log("팬미팅 정보 불러왔다.")
  } catch (error) {
    console.log(error)
  }
}

const getUserTicket =async () => {
  try {
    console.log(fanMeetingInfo.value?.id)
    console.log(fanMeetingInfo.value?.status)
    const response = await axios.get(`http://localhost:8080/fanMeeting/${starId.value}/reservation/${store.fanState!.id}/${fanMeetingInfo.value!.id}`)
    console.log(response.data)
    haveTicket.value = response.data
    console.log(haveTicket.value)
  } catch (error) {
    console.log(error)
  }
}

onMounted( async() => {
  await getFanMeetingInfo()
  await getUserTicket()
})
</script>
