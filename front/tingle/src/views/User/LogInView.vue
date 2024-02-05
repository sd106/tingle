<template>
  <main class="container">
    <h1>로그인</h1>
    <form @submit.prevent="logIn">
      <label for="username" class="form-label">아이디 : </label>
      <input class="form-control" type="text" id="username" v-model.trim="username" autocomplete="username"><br>

      <label for="password" class="form-label">비밀번호 : </label>
      <input class="form-control" type="password" id="password" v-model.trim="password"><br>

      <button type="submit" class="btn btn-outline-secondary mt-3 w-100">Login</button>
      <a @click="redirectToGoogle" class="btn btn-success active" role="button">Google Login</a>
      <a @click="redirectToNaver" class="btn btn-secondary active" role="button">Naver Login</a>
      <a @click="redirectToKakao" class="btn btn-secondary active" role="button">Kakao Login</a>
      <a @click="fetchUserData" class="btn btn-secondary active" role="button">정보확인</a>

    </form>
  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { LogIn } from '@/common/types/index';
import { useUserStore } from '@/stores/user';
import type { promiseHooks } from 'v8';
import axios from 'axios';

const store = useUserStore()

const username = ref<string>('')
const password = ref<string>('')

const logIn = function (): void {
  const payload: LogIn = {
    username: username.value,
    password: password.value,
  }
  store.logIn(payload)
}


const fetchUserData = async () => {
  try {
    // JSESSIONID 쿠키는 자동으로 요청에 포함됩니다.
    const response = await axios.get('http://localhost:8080/oauth2/naver');
    console.log('사용자 정보:', response.data);
  } catch (error) {
    console.error('오류 발생:', error);
  }
};



const redirectToGoogle = () => {
  window.location.href = 'http://localhost:8080/oauth2/authorization/google';
  console.log(window.location)
};

const redirectToNaver = () => {
  // http://localhost:8080/oauth2/authorization/google 으로 리다이렉션
  window.location.href = 'http://localhost:8080/oauth2/authorization/naver';
};

const redirectToKakao = () => {
  // http://localhost:8080/oauth2/authorization/google 으로 리다이렉션
  window.location.href = 'http://localhost:8080/oauth2/authorization/kakao';
};
</script>
