<template>
  <main class="container my-5 mx-5">
    <h1 class="text-center mb-5">팬미팅 만들기</h1>
    <div class="row">
      <div class="col-md-5">
        <div class="row">
          <div class="col-md-3 mb-4">
            <label for="title" class="form-label">팬미팅 제목</label>
          </div>
          <div class="col-md-9 mb-4">
            <input type="text" id="title" class="form-control" v-model="meeting.title" />
          </div>

          <div class="col-md-3 mb-4">
            <label for="description" class="form-label">설명</label>
          </div>
          <div class="col-md-9 mb-4">
            <textarea id="description" class="form-control" v-model="meeting.description"></textarea>
          </div>

          <div class="row mb-4">
            <div class="col-md-4">
              <label for="capacity" class="form-label">참여 인원</label>
            </div>
            <div class="col-md-6 d-flex">
              <input type="number" id="capacity" class="form-control me-3" v-model="meeting.capacity" />
              <span>명</span>
            </div>
          </div>
          <div class="row mb-4">
            <div class="col-md-4">
              <label for="price" class="form-label">가격</label>
            </div>
            <div class="col-md-6 d-flex">
              <input type="number" id="price" class="form-control me-3" v-model="meeting.price" />
              <span>원</span>
            </div>
          </div>
        </div>
      </div>
      <div class="col-md-5">
        <div class="row mb-5">
          <div class="col-md-4">
            <label class="form-label">입장권 구매 시작</label>
          </div>
          <div class="col-md-8 d-flex">
            <VueDatePicker
              v-model="meeting.ticketingStartAt"
              locale="ko"
              :time-picker-inline="true"
              :is-24="false"
            ></VueDatePicker>
          </div>
        </div>
        <div class="row mb-2">
          <div class="col-md-4">
            <label class="form-label">입장권 구매 종료</label>
          </div>
          <div class="col-md-8 d-flex">
            <VueDatePicker
              v-model="meeting.ticketingEndAt"
              locale="ko"
              :time-picker-inline="true"
              :is-24="false"
            ></VueDatePicker>
          </div>
        </div>
        <div class="row">
          <div class="col-md-4 mb-4">
            <label class="form-label">팬미팅 시작</label>
          </div>
          <div class="col-md-8 d-flex mb-4">
            <VueDatePicker
              v-model="meeting.fanMeetingStartAt"
              locale="ko"
              :time-picker-inline="true"
              :is-24="false"
            >
            </VueDatePicker>
          </div>
        </div>
      </div>
    </div>

    <hr />

    <div class="text-center">컨텐츠 선택</div>
    <hr />
    <div class="d-inline" v-for="(content, index) in allContents" :key="index">
      <button
        @click="toggleContent(content)"
        class="btn btn-outline-primary m-1"
        :class="{ 'bg-primary text-white': isSelected(content) }"
      >
        {{ content.name }}
      </button>
    </div>

    <div class="col-12 text-center mt-4">
      <button @click="submit" class="btn btn-primary btn-lg">Submit</button>
    </div>
  </main>
</template>


<script setup lang="ts">
import { ref, onMounted } from 'vue'
import VueDatePicker from '@vuepic/vue-datepicker' // https://vuepic.github.io/vue-datepicker/
import '@vuepic/vue-datepicker/dist/main.css' // https://vue3datepicker.com/
import axios from 'axios'
import type { FanMeeting, FanMeetingContent, CreateFanMeetingForm } from '@/common/types/index'
import { useUserStore } from '@/stores/user'

const store = useUserStore()
const temp1 = async () => {
    try {
        const { data } = await axios.post('https://i10d106.p.ssafy.io/fanMeetingRoom/create', 
                            {
                                roomName: '환영환영',
                                starName: '황찬준이다이',
                                maxUserCnt: 8,
                            })
        console.log(data)
    } catch (e) {
        console.log(e)
    }
}

const props = defineProps(['username'])
const name = ref('')
name.value = props.username

let meeting = ref<CreateFanMeetingForm>({
  title: '',
  description: '',
  fanMeetingStartAt: '',
  ticketingStartAt: '',
  ticketingEndAt: '',
  price: 0,
  capacity: 0,
  availableFanMeetingTypes: [],
  starName: store.starState?.username || '',
})


    
const toggleContent = (content: FanMeetingContent) => {
  const indexInMeeting = meeting.value.contents.findIndex((c) => c === content.name)
  const indexInAllContents = allContents.value.findIndex((c) => c.name === content.name)

  if (indexInMeeting !== -1) {
    meeting.value.contents.splice(indexInMeeting, 1)
  } else if (indexInAllContents !== -1) {
    meeting.value.contents.push(content.name)
  }
}

const allContents = ref<FanMeeting[]>([])

const isSelected = (content: FanMeetingContent) => {
  return meeting.value.contents.includes(content.name)
}

const submit = () => {
    // Submit the meeting
    axios.post('https://i10d106.p.ssafy.io/fanMeeting', meeting.value)
    console.log(meeting.value)
}

const loadContents = async () => {
    // Load contents from server
    const { data }  = await axios.get('https://i10d106.p.ssafy.io/fanMeeting/types')
    
    console.log(data)
    allContents.value = data
}

onMounted(() => {
  loadContents()
})
</script>

<style scoped>
.fixed-size {
  min-height: 100px;
}

.is-selected {
  background-color: rgb(231, 165, 165);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0.3);
}
</style>
