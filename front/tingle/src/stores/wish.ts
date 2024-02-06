import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import type { selectedSnapshotType } from '@/common/types/index'



export const useWishStore = defineStore('wish', () => {

    const API_URL = 'http://localhost:8080'

    const selectedSnapshot = ref<selectedSnapshotType | null>(null);

    const selectSnapshot = async (id: number) => {
      console.log("스냅샷 선택됨")
      try {
        const response = await axios.get(`${API_URL}/snapshot/${id}`);
        console.log(response.data)
        selectedSnapshot.value = response.data;
        console.log(selectedSnapshot.value?.snapshotId)
      } catch (error) {
        console.error(error);
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
