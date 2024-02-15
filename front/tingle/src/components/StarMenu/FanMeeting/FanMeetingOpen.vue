<template>
    <div>
        <div class="row" style="object-fit: cover; width: 100%; height: 500px;">
            <div class="col-md-8 p-0" >
                <div class="poster-first" :style="{ backgroundImage: `linear-gradient(to right, rgba(255, 255, 255, 0), rgba(0, 0, 0, 1)), url(${fanMeetingInfo.imgURL1})` }"></div>
            </div>
            <div class="col-md-4 d-flex flex-column justify-content-center p-4 poster-content-first" >
                <div>
                    <h2 style="color: white; font-weight: bold;">{{ fanMeetingInfo.title}}</h2>
                </div>
                
                <div class="text-center mt-4">
                    <div class="d-grid gap-2 col-6 mx-auto">
                        <button @click="enterWaitingRoom" class="btn btn-success btn-lg">
                            팬미팅 입장
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div class="d-flex flex-column justify-content-center align-items-center " style="height: 300px;">
            <p style="font-size: 3rem; font-weight: bold;">{{ store.formatDateTime(fanMeetingInfo.fanMeetingStartAt) }} ~</p>
            
        </div>
        <div class="row" style="object-fit: cover; width: 100%; height: 500px;">
            <div class="col-md-4 poster-content-second" >
                <div class="mb-5">
                    <h2 style="color: white;">{{ fanMeetingInfo.description }}</h2>
                </div>
                <button @click="enterWaitingRoom" class="btn btn-success btn-lg">
                    팬미팅 입장
                </button>
            </div>
            <div class="col-md-8 p-0">
                <div class="poster-second"  :style="{ backgroundImage: `linear-gradient(to left, rgba(255, 255, 255, 0), rgba(0, 0, 0, 1)), url(${fanMeetingInfo.imgURL2})` }"></div>
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
.poster-first{
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    width: 100%;
    height: 500px;
}

.poster-content-first{
    background: linear-gradient(to right, rgba(0,0,0,1), rgba(255, 255, 255, 0.3)), #444444;
    color: white;
    justify-content: flex-end;
    display: flex;
}
.poster-second{
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    width: 100%;
    height: 500px;
}

.poster-content-second{
    background: linear-gradient(to left, rgba(0,0,0,1), rgba(255, 255, 255, 0.3)), #444444;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-end;
    color: white;

}
.btn-success {
  background: linear-gradient(to right, #ffffff, #afafaf);
  border: none;
  color: black;
  font-weight: bold;
  padding: 15px 30px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  font-size: 1.2rem;
  transition: all 0.3s;
}

.btn-success:hover {
  background: linear-gradient(to right, #367C2B, #6A9F50);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3);
  transform: translateY(-2px); 
}

.btn-success:active {
  transform: translateY(1px);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); 
}
</style>
