import { ref } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'

export const useFanMeetingStore = defineStore('fanMeeting', () => {
  const API_URL = 'http://localhost:8080'
  
  const router = useRouter()


  const formatDateTime = (dateTimeStr: string) => {
    const options: Intl.DateTimeFormatOptions = {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      weekday: 'short',
      hour: '2-digit',
      minute: '2-digit',
      hour12: false
    }
    return new Date(dateTimeStr).toLocaleString('ko-KR', options)
  }

  const goPage = (pageName: string) => {
    router.push({ name: pageName })
  }

  return {
    API_URL,
    formatDateTime,
    goPage
  }
})
