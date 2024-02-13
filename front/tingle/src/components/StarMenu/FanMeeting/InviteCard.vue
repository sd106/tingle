<template>
    <div class="invite-card-wrapper">
        <div class="invite-card-container">
            <div>
              <p>
                {{ star?.username }} 으로부터 팬미팅 초대장이 왔습니다.
              </p>
            </div>
            <br>
            <div class="d-flex justify-content-center">
                <button class="enter-btn" @click="store.goPage('FanMeetingRoomView')">입장하기</button>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { defineProps, onMounted } from 'vue'
import { useFanMeetingStore } from '@/stores/fanMeeting'
import { ref } from 'vue'
import type { Star } from '@/common/types'
import axios from 'axios';

const store = useFanMeetingStore()

const star = ref<Star>()
const loadStar = async () => {
  star.value = await axios.get(`${store.API_URL}/star/${props.starid}`)
}

const props = defineProps({
    starid: {
        type: String,
        required: true
    }
})

onMounted(() => {
  loadStar()
})
</script>

<style scoped>
.invite-card-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed; 
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5); 
  transition: opacity 0.3s ease;
  z-index: 9999;
}


.invite-card-container {
  padding: 20px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.enter-btn {
  background-color: #4CAF50;
  color: white;
  padding: 10px 20px;
  border: none; 
  border-radius: 5px;
  font-size: 16px; 
  cursor: pointer; 
  transition: background-color 0.3s; 
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); 
  text-transform: uppercase; 
}

.enter-btn:hover {
  background-color: #45a049; 
}
</style>