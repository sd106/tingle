import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import type { selectedSnapshotType } from '@/common/types/index'
import { useUserStore } from '@/stores/user';



export const useWishStore = defineStore('wish', () => {

    const userStore = useUserStore()

    const API_URL = 'http://i10d106.p.ssafy.io:8080'

    const selectedSnapshot = ref<selectedSnapshotType | null>(null);

    const selectSnapshot = async (id: number) => {
      console.log("스냅샷 선택됨")
      try {
        
        const response = await axios.post(`${API_URL}/snapshot/${id}`, { username: userStore.fanState!.username}, { withCredentials: true });
        console.log(response.data)
        selectedSnapshot.value = response.data;
        console.log(selectedSnapshot.value?.isLiked)
      } catch (error) {
        console.error(error);
        window.alert("로그인이 필요합니다.");
      }
      scrollTop();
    };

    const scrollTop = () => {
      window.scrollTo(0,0);
    }
  return {
    API_URL, selectedSnapshot, selectSnapshot
  }
})
