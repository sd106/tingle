<template>
  <main class="container">
    <h1>회원가입</h1>
    <form @submit.prevent="signUp">
      <label for="username" class="form-label">아이디 : </label>
      <input class="form-control" type="text" id="username" v-model.trim="username" autocomplete="off"><br>

      <label for="password" class="form-label">비밀번호 : </label>
      <input class="form-control" type="text" id="password" v-model.trim="password"><br>

      <label for="email" class="form-label">이메일 : </label>
      <input class="form-control" type="text" id="email" v-model.trim="email" autocomplete="off">

      <button type="submit" class="btn btn-outline-secondary mt-3 w-100">Signup</button>
    </form>

  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { SignUp } from '@/common/types/index';
import { useUserStore } from '@/stores/user';



const store = useUserStore()

const username = ref<string>('')
const password = ref<string>('')
const email = ref<string>('')

const signUp = function () {
  if (!username.value || !password.value || !email.value) {
    window.alert('입력되지 않은 항목이 있습니다.')
    return
  }
  const payload: SignUp = {
    username: username.value,
    password: password.value,
    email: email.value,
  }
  store.signUp(payload)
  console.log(payload)
}
</script>
