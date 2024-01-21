<template>
  <div class="d-flex justify-content-center">
    <main class="container border rounded-4 m-5 col-6">
      <div class="mt-5 mb-4 mx-3">
        <form @submit.prevent="signUp">
          <h2 class="fw-bold text-center mb-4">Sign Up</h2>

          <div class="d-flex justify-content-between mb-4">
            <button class="signin__btn mt-3 me-3"> <span>팬이에요!</span> </button>
            <button class="signin__btn2 mt-3 ms-3"> <span>스타에요!</span> </button>
          </div>

          <label for="email" class="form-label">이메일 : </label>
          <input class="form-control mb-4" type="text" id="email" v-model.trim="email" autocomplete="off">

          <label for="password" class="form-label">비밀번호 : </label>
          <input class="form-control" type="password" id="password" v-model.trim="password"
            aria-describedby="passwordHelpBlock">
          <div id="passwordHelpBlock" class="form-text">
            패스워드 길이는 최소 8글자 이상, 영문자와 숫자가 각각 하나 이상 포함되어야 합니다.
          </div>
          <br>

          <label for="password" class="form-label">비밀번호 확인 : </label>
          <input class="form-control" type="password" id="password" v-model.trim="chk_password"><br>

          <label for="username" class="form-label">닉네임 : </label>
          <input class="form-control" type="text" id="username" v-model.trim="username" autocomplete="off"><br>

          <button type="submit" class="btn btn-outline-secondary mt-3 w-100">Signup</button>
        </form>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">

import { ref } from 'vue'
import type { SignUp } from '@/common/types/index';
import { useUserStore } from '@/stores/user';



const store = useUserStore()

const username = ref<string>('')
const password = ref<string>('')
const chk_password = ref<string>('')
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
}
</script>

<style>
.signin__btn {
  background: #b71cd2;
  color: white;
  display: block;
  width: 92.5%;
  height: 50px;
  border-radius: 10px;
  margin: 0 auto;
  border: none;
  cursor: pointer;
  font-size: 20px;
  font-family: 'Montserrat', sans-serif;
  box-shadow: 0 15px 30px rgba(#e91e63, .36);
  transition: .2s linear;

  &:hover {
    box-shadow: 0 0 0 rgba(#e91e63, .0);
  }
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
  width: 92.5%;
  height: 50px;
  border-radius: 10px;
  margin: 0 auto;
  border: none;
  cursor: pointer;
  font-size: 20px;
  font-family: 'Montserrat', sans-serif;
  box-shadow: 0 15px 30px rgba(#e91e63, .36);
  transition: .2s linear;

  &:hover {
    box-shadow: 0 0 0 rgba(#e91e63, .0);
  }
}
</style>
