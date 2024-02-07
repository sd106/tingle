import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import axios from 'axios'

import type { StarState, FanState } from '@/common/types/index'


// 세트로 id 이름 사진 그정도 저장 해두기?


export const useUserStore = defineStore('user', () => {

  const router = useRouter()

  const API_URL = 'http://i10d106.p.ssafy.io:8080'

  const fanState = ref<FanState | null>(null)
  const starState = ref<StarState | null>(null)
  const isStar = ref(false)
  const isLogin = computed(() => {
    if (fanState.value === null && starState.value === null) {
      return false
    } else {
      return true
    }
  })

  const isSidebarOpen = ref(true)

  const starId = ref<number>();

  const logOut = async function (): Promise<void> {
    try {
      const response = await axios.post(`${API_URL}/users/logout`, {
      });
      console.log(response);
      window.alert('로그아웃!');
      router.push({ name: 'home' })
    } catch (error) {
      console.error(error);
    }
  };
  return {
    API_URL,
    logOut, starState, fanState,
    //
    isLogin, isStar,
    //
    isSidebarOpen, starId
  }
}, { persist: true })