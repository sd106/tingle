<template>
    <main class="container my-5 mx-5">
        <h1 class="text-center mb-5">팬미팅 만들기</h1>
        <div class="row ">
            <div class="col-md-5">
                <div class="row">
                    <div class="col-md-3 mb-4">
                        <label for="meeting-name" class="form-label">팬미팅 제목</label>
                    </div>
                    <div class="col-md-9 mb-4">
                        <input type="text" id="meeting-name" class="form-control" v-model="meeting.name">
                    </div>

                    <div class="row mb-4">
                        <div class="col-md-4">
                            <label for="participants" class="form-label">참여 인원</label>
                        </div>
                        <div class="col-md-6 d-flex">
                            <input type="number" id="participants" class="form-control me-3" v-model="meeting.participants"> 
                            <span>명</span>
                        </div>
                    </div>
                    <div class="row mb-4">
                        <div class="col-md-4">
                            <label for="price" class="form-label">가격</label>
                        </div>
                        <div class="col-md-6 d-flex">
                            <input id="price" class="form-control me-3" v-model="meeting.price">
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
                        <VueDatePicker v-model="meeting.ticketStartDate" :locale="locale" :time-picker-inline="true" :is-24="false"></VueDatePicker>
                    </div>
                </div>
                <div class="row mb-2">
                    <div class="col-md-4">
                        <label class="form-label">입장권 구매 종료</label>
                    </div>
                    <div class="col-md-8 d-flex">
                        <VueDatePicker v-model="meeting.ticketEndDate" :locale="locale" :time-picker-inline="true" :is-24="false"></VueDatePicker>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4 mb-4">
                        <label class="form-label">팬미팅 시작</label>
                    </div>
                    <div class="col-md-8 d-flex mb-4">
                        <VueDatePicker v-model="meeting.startDate" :locale="locale" :time-picker-inline="true" :is-24="false"></VueDatePicker>
                    </div>
                </div>
            </div>

        </div>
        
            
        
        <hr>
        
        <div class="text-center">컨텐츠 선택</div>
        <hr>
        <div class="d-inline" v-for="(content, index) in allContents" :key="index">
            <button
                @click="toggleContent(content)" 
                class="btn btn-outline-primary m-1" 
                :class="{ 'bg-primary text-white': isSelected(content) }" >
                {{ content.name }}
            </button>
        </div>

        <div class="col-12 text-center mt-4">
            <button @click="submit" class="btn btn-primary btn-lg">Submit</button>
        </div>
    </main>

</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import VueDatePicker from '@vuepic/vue-datepicker'  // https://vuepic.github.io/vue-datepicker/
import '@vuepic/vue-datepicker/dist/main.css'  // https://vue3datepicker.com/
import { ko } from 'date-fns/locale'


interface Content {
    name: string
}   

interface Meeting {
    name: string;
    ticketStartDate: Date | null 
    ticketEndDate: Date | null
    startDate: Date | null
    participants: number
    price: number
    contents: string[]
}

const props = defineProps(['username']);
const name = ref('')
const locale = reactive(ko)
name.value = props.username

let meeting = ref<Meeting>({
    name: '',
    ticketStartDate: null,
    ticketEndDate: null,
    startDate: null,
    participants: 0,
    price: 0,
    contents: []
})



const toggleContent = (content: Content) => {
    const indexInMeeting = meeting.value.contents.findIndex(c => c === content.name)
    const indexInAllContents = allContents.value.findIndex(c => c.name === content.name)

    if (indexInMeeting !== -1) {
        meeting.value.contents.splice(indexInMeeting, 1)
        
    } else if (indexInAllContents !== -1) {
        meeting.value.contents.push(content.name)

    }
}


const allContents = ref([
    { name: '인생네컷' },
    { name: '테트리스' },
    { name: '자유대화' },
    { name: '컨텐츠4' },
    { name: '컨텐츠5' }
])

const isSelected = (content: Content) => {
        return meeting.value.contents.includes(content.name);
    };

const submit = () => {
    // Submit the meeting
    console.log(meeting.value)
}
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