import { ref } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import axios from 'axios'

import type { SignUp, LogIn, Star } from '@/common/types/index'

import hotstar from '@/static/data/hotstar.json'

export const useUserStore = defineStore('user', () => {

  const API_URL = 'http://localhost:9000'

  const starinfo = ref<Star[]>([])

  const router = useRouter()

  const getStarInfo = function (): void {
    starinfo.value = hotstar
    console.log(starinfo)
  }


  const signUp = async function (payload: SignUp): Promise<void> {
    const { username, password, email } = payload;

    try {
      const response = await axios.post(`${API_URL}/users/new`, {
        username,
        password,
        email
      });
      console.log(response);
      console.log(payload);
      window.alert('회원가입완료');
      // username, password 받아서 바로 로그인 하기
    } catch (error) {
      console.error(error);
    }
  };


  const logIn = async function (payload: LogIn): Promise<void> {
    const { username, password } = payload;

    try {
      const response = await axios.post(`${API_URL}/users/login`, {
        username,
        password
      });
      console.log(response);
      console.log(payload);
      window.alert('로그인성공!');
      router.push({ name: 'home' }) // 무조건 홈으로 돌아가는 게 아니라 이전 창으로 돌아가기?
      // 토큰이라든가 뭔가 받아서 뭘 해야 하지 않을까
    } catch (error) {
      console.error(error);
    }


  }
  return {
    API_URL,
    signUp, logIn,
    starinfo, getStarInfo,
  }
})
