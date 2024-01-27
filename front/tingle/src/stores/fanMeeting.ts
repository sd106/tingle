import { ref } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import axios from 'axios'


export const useFanMeetingStore = defineStore('fanMeeting', () => {

    const router = useRouter()

    const API_URL = 'http://localhost:8080'

    const formatDateTime = (dateTimeStr: string) => {
        const options: Intl.DateTimeFormatOptions = {
            year: 'numeric', month: '2-digit', day: '2-digit', 
            weekday: 'short', hour: '2-digit', minute: '2-digit', 
            hour12: false
        }
        return new Date(dateTimeStr).toLocaleString('ko-KR', options)
    }
    
    const goPage = (pageName: string) => {
        router.push({ name: pageName })
    }
  return {
    formatDateTime,
    goPage
  }
})
