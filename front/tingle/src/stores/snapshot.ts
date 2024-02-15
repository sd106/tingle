import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import type { selectedSnapshotType } from '@/common/types/index'
import { useUserStore } from '@/stores/user'


export const useSnapshotStore = defineStore('snapshot', () => {


  const userStore = useUserStore()


  const API_URL = 'https://i10d106.p.ssafy.io/api'

  const selectedSnapshot = ref<selectedSnapshotType | null>(null);

  const username = ref<string>('');

  const isStarLogin = ref<boolean>(false);
  

  const selectSnapshot = async (id: number) => {

    console.log("스냅샷 선택됨");
    console.log(id);

    try {
      if (userStore.fanState) {
        console.log("현재 유저는 팬")
        username.value = userStore.fanState.username
      } else if (!userStore.fanState) {
        console.log("현재 유저는 스타")
        username.value = userStore.starState!.username
        isStarLogin.value = true
        console.log(isStarLogin.value)
      } else {
        console.log("유저가 대체 누구야?")
      }
      console.log(username.value)
      const response = await axios.post(`${API_URL}/snapshot/${id}`, { username: username.value, isStarLogin: isStarLogin.value }, { withCredentials: true });
      console.log(response.data)
      selectedSnapshot.value = response.data;
      console.log(selectedSnapshot.value?.isLiked)
    } catch (error) {
      console.error(error);
    }
    scrollTop();
  };

  const scrollTop = () => {
    window.scrollTo(0,0);
  }
  
  return {
    API_URL,
    selectedSnapshot,
    selectSnapshot,
  }
})
