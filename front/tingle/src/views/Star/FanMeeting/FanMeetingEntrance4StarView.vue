<template>
    <main>
        <!-- <section v-if="fanMeetingInfo && fanMeetingInfo.status === 'closed'" class="pt-5 text-center">
            <CreateFanMeetingViewVue :fanMeetingInfo="fanMeetingInfo"></CreateFanMeetingViewVue>
        </section>
        <section v-else class="pt-5">
            <MeetingRoomView4Star :fanMeetingInfo="fanMeetingInfo"></MeetingRoomView4Star>
        </section> -->
    </main>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import type { FanMeetingInfo } from '@/common/types/index'
import type CreateFanMeetingViewVue from './CreateFanMeetingView.vue';

const fanMeetingInfo = ref<FanMeetingInfo>()

const getFanMeetingInfo = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/fanMeeting/info/${props.username}`)
    fanMeetingInfo.value = response.data
  } catch (error) {
    console.log(error)
  }
}


onMounted(() => {
  // getFanMeetingInfo()
})
</script>

<style scoped>

</style>