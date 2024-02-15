<template>
  <div class="container">
    <div class="signup-section" style="background-color: #f5f5f5;">
      <h2 class="section-title">추가 정보 입력</h2>
      <form @submit.prevent="starSignup" class="signup-form">
        <div class="form-group">
          <label for="email" class="form-label">이메일</label>
          <input id="email" style="background-color: white;" v-model="signupData.email" type="email" disabled class="form-input" />
        </div>
        <div class="form-group">
          <label for="name" class="form-label">이름</label>
          <input id="name" style="background-color: white;" v-model="signupData.name" type="text" disabled class="form-input" />
        </div>
        <div class="form-group">
          <label for="username" class="form-label">닉네임</label>
          <input
            id="username"
            v-model="signupData.username"
            type="text"
            placeholder="사용자 이름을 입력해주세요"
            class="form-input"
          />
        </div>
        <button type="submit" class="submit-button">회원 가입</button>
      </form>
    </div>

  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive } from 'vue'
import axios from 'axios'
// import { useUserStore } from '@/stores/user'
import { useRouter } from 'vue-router'

const router = useRouter()

const signupData = reactive({
  email: '',
  name: '',
  username: ''
})

const starSignup = async () => {
  try {
    const response = await axios.post('http://localhost:8080/auth/member/signup', signupData, {
      withCredentials: true
    })

    // // 스타 회원가입 시 채팅방도 생성
    // const response2 = await axios.post(`http://localhost:8080/chat/saveTheRoom/${signupData.username}`)

    console.log(response)

    // console.log(response2)
    
    router.push('/')
  } catch (error) {
    console.error('스타 회원가입 실패', error)
  }
}

const loadMemeber = async () => {
  try {
    const response = await axios.get('http://localhost:8080/auth/member/info', {
      withCredentials: true
    })

    console.log(response)

    if (response.data) {
      signupData.email = response.data.email
      signupData.name = response.data.name
    }
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadMemeber()
})
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 80vh;
  /* background-color: #f5f5f5; */
}

.title {
  margin-bottom: 2rem;
  color: #333;
}

.signup-section {
  padding: 2rem;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  max-width: 500px;
  width: 100%;
}

.section-title {
  margin-bottom: 1.5rem;
  color: #666;
}

.signup-form {
  display: flex;
  flex-direction: column;
}

.form-group {
  margin-bottom: 1rem;
}

.form-label {
  display: block;
  margin-bottom: 0.5rem;
  color: #444;
}

.form-input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.submit-button {
  padding: 0.75rem;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.submit-button:hover {
  background-color: #0056b3;
}
</style>