<template>
  <main>
    <div class="d-flex">
      <button
        @click="selectLoginType('fan')"
        :class="{ signin__btn: true, is_selected: selectedType === 'fan' }"
      >
        <span>팬이에요!</span>
      </button>
      <button
        @click="selectLoginType('star')"
        :class="{ signin__btn2: true, is_selected: selectedType === 'star' }"
      >
        <span>스타에요!</span>
      </button>
    </div>
  </main>
</template>

<script lang="ts" setup>
import axios from 'axios'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const selectedType = ref('')
const router = useRouter()
const store = useUserStore()

const selectLoginType = async (type: string) => {
  try {
    const { data } = await axios.post(
      `http://localhost:8080/auth/login/${type}`,
      {},
      { withCredentials: true }
    )
    // const { data } = await axios.post(`http://localhost:8080/auth/login/${type}`, {}, { withCredentials: true })
    console.log('▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼')
    console.log(data)
    console.log('▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲')
    if (type === 'fan') {
      const { id, username, picture, follwingStars } = data
      store.fanState = { id, username, picture, follwingStars }
      console.log(store.fanState)
    } else if (type === 'star') {
      const { id, username, picture } = data
      store.starState = { id, username, picture }
      store.isStar = true
    }

    router.push('/')
  } catch (error) {
    console.log('error', error)
  }
}
</script>

<style scoped>
.is_selected {
  border: 2px solid #ffffff;
  /* 흰색 테두리로 선택됨을 강조 */
  background-color: #4caf50;
  /* 조금 더 진한 배경색 */
  color: #ffffff;
  /* 텍스트 색상을 흰색으로 */
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
  /* 선택된 버튼에 그림자 효과 추가 */
  transform: scale(1.05);
  /* 버튼을 약간 크게 만들어 선택됨을 강조 */
  transition:
    background-color 0.3s,
    transform 0.3s,
    box-shadow 0.3s;
}

.signin__btn {
  background: #b71cd2;
  color: white;
  display: block;
  width: 100px;
  height: 100px;
  border-radius: 10px;
  margin: 0 auto;
  border: none;
  cursor: pointer;
  font-size: 20px;
  font-family: 'Montserrat', sans-serif;
  box-shadow: 0 15px 30px rgba(#e91e63, 0.36);
  transition: 0.2s linear;
}

.signin__btn:hover {
  box-shadow: 0 0 0 rgba(#e91e63, 0);
}

.signin__btn:hover {
  background: #cb4ae2;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
}

.signin__btn2:hover {
  background: #f32a2a;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
}

.signin__btn2 {
  background: #ef0909;
  color: white;
  display: block;
  width: 100px;
  height: 100px;
  border-radius: 10px;
  margin: 0 auto;
  border: none;
  cursor: pointer;
  font-size: 20px;
  font-family: 'Montserrat', sans-serif;
  box-shadow: 0 15px 30px rgba(#e91e63, 0.36);
  transition: 0.2s linear;
}

.signin__btn2 :hover {
  box-shadow: 0 0 0 rgba(#e91e63, 0);
}
</style>
