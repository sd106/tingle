<template>
    <main>
        <section v-if="fanMeetingInfo && fanMeetingInfo.status === 'open'" class="pt-5 text-center">
            <MeetingRoomView4Star :fanMeetingInfo="fanMeetingInfo"></MeetingRoomView4Star>
        </section>
        <section v-else-if="fanMeetingInfo && fanMeetingInfo.status === 'ticketing'">
            <h1>티켓 판매중!</h1>
        </section>
        <section v-else class="pt-5">
            <CreateFanMeetingViewVue :fanMeetingInfo="fanMeetingInfo"></CreateFanMeetingViewVue>
        </section>
    </main>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import type { FanMeetingInfo } from '@/common/types/index'
import { useUserStore } from '@/stores/user';
import CreateFanMeetingViewVue from '@/views/Star/FanMeeting/CreateFanMeetingView.vue'
import MeetingRoomView4Star from '@/views/Star/FanMeeting/MeetingRoom4StarView.vue'


const store = useUserStore()
const fanMeetingInfo = ref<FanMeetingInfo>()

const getFanMeetingInfo = async () => {
    try {
        const response = await axios.get(`http://localhost:8080/fanMeeting/info/${store.starState?.id}`)
        fanMeetingInfo.value = response.data
        console.log("팬미팅 정보  : ", response.data)
        
    } catch (error) {
        console.log(error)
    }
}

onMounted(() => {
    getFanMeetingInfo()
})
</script>

<style scoped>

</style>