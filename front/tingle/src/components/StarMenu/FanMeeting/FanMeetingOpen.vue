<template>
    <div>
        <div class="row" style="height: 500px;">
            <div class="col-md-8 p-0">
                <div class="img-fluid h-100 opacity-75" :style="{ backgroundImage: `url(${fanMeetingInfo.imgURL})` }" style="background-size: cover;" ></div>
            </div>
            <div class="col-md-4 rounded-3 d-flex flex-column justify-content-center bg-light p-4">
                <div>
                    <h2>{{ fanMeetingInfo.name }}</h2>
                    <p>{{ fanMeetingInfo.description }}</p>
                </div>
                <p>{{ store.formatDateTime(fanMeetingInfo.fanMeetingStartAt) }} ~</p>
                <div class="text-center mt-4">
                    <div class="d-grid gap-2 col-6 mx-auto">
                        <button @click="enterWaitingRoom" class="btn btn-success btn-lg">
                            팬미팅 입장
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { defineProps } from 'vue'
import type { PropType } from 'vue';
import type { FanMeetingInfo } from '@/common/types/index'
import { useFanMeetingStore } from '@/stores/fanMeeting'

const props = defineProps({
    fanMeetingInfo: {
        type: Object as PropType<FanMeetingInfo>,
        required: true
    }
})

const store = useFanMeetingStore()

const enterWaitingRoom = () => {
    store.goPage('WaitingRoomView')
}
</script>

<style scoped>

</style>
