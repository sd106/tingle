<template>
  <h1>회원가입</h1>
  <div class="signup-additional-info">
    <h2>추가 정보 입력</h2>
    <form @submit.prevent="starSignup">
      <div>
        <label for="email">이메일</label>
        <input id="email" v-model="signupData.email" type="email" disabled />

      </div>
      <div>
        <label for="name">이름</label>
        <input id="name" v-model="signupData.name" type="text" disabled />
      </div>
      <div>
        <label for="username">닉네임</label>
        <input id="username" v-model="signupData.username" type="text" placeholder="사용자 이름을 입력해주세요" />
      </div>
      <button type="submit">제출</button>
    </form>
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

    console.log(response)
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
