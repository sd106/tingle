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
            <VueDatePicker v-model="meeting.ticketingStartAt" :min-date="ticketingStartMinDate" locale="ko" :time-picker-inline="true" :is-24="false">
            </VueDatePicker>
          </div>
        </div>
        <div class="row mb-2">
          <div class="col-md-4">
            <label class="form-label">입장권 구매 종료</label>
          </div>
          <div class="col-md-8 d-flex">
            <VueDatePicker v-model="meeting.ticketingEndAt" :min-date="ticketingEndMinDate" locale="ko" :time-picker-inline="true" :is-24="false">
            </VueDatePicker>
          </div>
        </div>
        <div class="row">
          <div class="col-md-4 mb-4">
            <label class="form-label">팬미팅 시작</label>
          </div>
          <div class="col-md-8 d-flex mb-4">
            <VueDatePicker v-model="meeting.fanMeetingStartAt" :min-date="fanMeetingStartMinDate" locale="ko" :time-picker-inline="true" :is-24="false">
            </VueDatePicker>
          </div>
        </div>
      </div>
    </div>

    <hr />

    <div class="text-center">컨텐츠 선택</div>
    <hr />
    <div class="d-inline" v-for="(content, index) in allContents" :key="index">
      <button @click="toggleContent(content)" class="btn btn-outline-primary m-1"
        :class="{ 'bg-primary text-white': isSelected(content) }">
        {{ content.name }}
      </button>
    </div>

    <div class="row">
      <div class="col-md-6">
        <div ref="dragArea1"
          class="tw-border-dashed tw-border-2 tw-border-primary tw-p-4 tw-text-center tw-cursor-pointer tw-mb-4"
          @dragover.prevent="handleDragOver" @drop.prevent="handleDrop($event, 'file1')" @click="fileInput1!.click()">
          팬미팅의 첫번째 배너 이미지를 업로드 해주세요
          <input type="file" ref="fileInput1" @change="handleFileUpload($event, 'file1')" style="display: none" />
        </div>
        <div v-if="previewFile1" class="tw-grid tw-grid-cols-3 tw-gap-4">
          <div class="tw-relative tw-mb-4">
            <img :src="previewFile1" class="tw-rounded tw-shadow-md" />
          </div>
        </div>
      </div>
      <div class="col-md-6">
        <div ref="dragArea2"
          class="tw-border-dashed tw-border-2 tw-border-primary tw-p-4 tw-text-center tw-cursor-pointer tw-mb-4"
          @dragover.prevent="handleDragOver" @drop.prevent="handleDrop($event, 'file2')" @click="fileInput2!.click()">
          팬미팅의 두번째 배너 이미지를 업로드 해주세요
          <input type="file" ref="fileInput2" @change="handleFileUpload($event, 'file2')" style="display: none" />
        </div>
        <div v-if="previewFile2" class="tw-grid tw-grid-cols-3 tw-gap-4">
          <div class="tw-relative tw-mb-4">
            <img :src="previewFile2" class="tw-rounded tw-shadow-md" />
          </div>
        </div>
      </div>
    </div>
  
    <div class="col-12 text-center mt-4">
      <button @click="createFanMeeting" class="btn btn-primary btn-lg">팬미팅 만들기</button>
    </div>
  </main>

  <button @click="temp1">임시로 팬미팅 만들기 이름: 황찬준이다이 접속</button>



</template>


<script setup lang="ts">
import { ref, onMounted, watch, computed } from 'vue'
import VueDatePicker from '@vuepic/vue-datepicker' // https://vuepic.github.io/vue-datepicker/
import '@vuepic/vue-datepicker/dist/main.css' // https://vue3datepicker.com/
import axios from 'axios'
import type { FanMeetingType, CreateFanMeetingForm } from '@/common/types/index'
import { useUserStore } from '@/stores/user'

const store = useUserStore()
const temp1 = async () => {
    try {
        const { data } = await axios.post('http://localhost:8080/fanMeetingRoom/create', 
                            {
                                roomName: '환영환영',
                                starName: store.starState?.username,
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

const now = new Date()

const ticketingStartMinDate = now

const ticketingEndMinDate = computed(() => meeting.value.ticketingStartAt)

const fanMeetingStartMinDate = computed(() => meeting.value.ticketingEndAt)
    
const toggleContent = (content: FanMeetingType) => {
  const indexInMeeting = meeting.value.availableFanMeetingTypes.findIndex((c) => c.name === content.name)

  if (indexInMeeting !== -1) {
    meeting.value.availableFanMeetingTypes.splice(indexInMeeting, 1)
  } else {
    meeting.value.availableFanMeetingTypes.push(content)
  }
}

const allContents = ref<FanMeetingType[]>([])

const isSelected = (content: FanMeetingType) => {
  return meeting.value.availableFanMeetingTypes.includes(content)
}

const createFanMeeting = async () => {
  try {
    console.log("??")
    const formData = new FormData()
    formData.append('requestJson', JSON.stringify(meeting.value))
    console.log("11", formData)

    if (file1.value !== null) {
      formData.append('file1', file1.value);
    }
    console.log("22", formData)

    if (file2.value !== null) {
      formData.append('file2', file2.value);
    }
    for (let [key, value] of formData.entries()) {
      console.log(key, value);
    }
    const { data } = await axios.post('http://localhost:8080/fanMeeting/', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })

    console.log(data)
  } catch (error) {
    console.log(error)
  }
}

const loadContents = async () => {
  // Load contents from server
  const { data } = await axios.get('http://localhost:8080/fanMeeting/types')

  console.log(data)
  allContents.value = data
}



const file1 = ref<File | null>(null)
const file2 = ref<File | null>(null)
const previewFile1 = ref<string | null>(null)
const previewFile2 = ref<string | null>(null)
const dragArea = ref<HTMLElement | null>(null)
const fileInput1 = ref<HTMLInputElement | null>(null)
const fileInput2 = ref<HTMLInputElement | null>(null)

// 파일이 변경될 때 미리보기 URL 업데이트
watch([file1, file2], ([newFile1, newFile2]) => {
  previewFile1.value = newFile1 ? URL.createObjectURL(newFile1) : null
  previewFile2.value = newFile2 ? URL.createObjectURL(newFile2) : null
})


const handleDragOver = (event: DragEvent) => {
  event.preventDefault()
}

const handleDrop = (event: DragEvent, fileKey: 'file1' | 'file2') => {
  event.preventDefault()
  if (event.dataTransfer?.files.length) {
    const file = event.dataTransfer.files[0]
    if (fileKey === 'file1') file1.value = file
    else if (fileKey === 'file2') file2.value = file
  }
}

const handleFileUpload = (event: Event, fileKey: 'file1' | 'file2') => {
  const target = event.target as HTMLInputElement
  if (target.files?.length) {
    const file = target.files[0]
    if (fileKey === 'file1') file1.value = file
    else if (fileKey === 'file2') file2.value = file
  }
}


onMounted(() => {
  if (dragArea.value) {
    dragArea.value.addEventListener('dragover', handleDragOver)
  }
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
