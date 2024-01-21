<template>
  <main class="container">
    <h1>로그인</h1>
    <form @submit.prevent="logIn">
      <label for="username" class="form-label">아이디 : </label>
      <input class="form-control" type="text" id="username" v-model.trim="username" autocomplete="username"><br>

      <label for="password" class="form-label">비밀번호 : </label>
      <input class="form-control" type="password" id="password" v-model.trim="password"><br>

      <button type="submit" class="btn btn-outline-secondary mt-3 w-100">Login</button>
    </form>
  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { LogIn } from '@/common/types/index';
import { useUserStore } from '@/stores/user';

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
</script>
