<template>
    <div class="invite-card-wrapper">
        <div class="invite-card-container">
            <div>
              <p>
                {{ starName }} 으로부터 팬미팅 초대장이 왔습니다.
              </p>
            </div>
            <br>
            <div class="d-flex justify-content-center">
                <button @click="store.goPage('MeetingRoomView')">입장하기</button>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { defineProps } from 'vue'
import { useFanMeetingStore } from '@/stores/fanMeeting'

const store = useFanMeetingStore()


const props = defineProps({
    starName: {
        type: String,
        required: true
    }
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
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5); 
  transition: opacity 0.3s ease; 
}

.invite-card-container {
  padding: 20px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
</style>