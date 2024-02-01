import { ref } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import axios from 'axios'

import type { SignUp, LogIn, Starinfo, StarLogininfo } from '@/common/types/index'

// 
import hotstar from '@/static/data/hotstar.json'
import allstar from '@/static/data/allstar.json'
import yoo from '@/static/data/category/yoo.json'
import bin from '@/static/data/category/bin.json'
import jun from '@/static/data/category/jun.json'
import mo from '@/static/data/category/mo.json'
import yoon from '@/static/data/category/yoon.json'
import oh from '@/static/data/category/oh.json'
// 


// 세트로 id 이름 사진 그정도 저장 해두기?
// const usernameState = ref(null)

// const isLogin = computed(() => {
//   if (usernameState.value === null) {
//     return false
//   } else {
//     return true
//   }
// })

const isLogin = ref(false)



export const useUserStore = defineStore('user', () => {

  const router = useRouter()

  const API_URL = 'http://localhost:8080'


  // 바꿔야할 것
  const hotstarinfo = ref<Starinfo[]>([])
  const allstarinfo = ref<Starinfo[]>([])

  const getStarInfo = function (): void {
    hotstarinfo.value = hotstar
    allstarinfo.value = allstar
  }

  const isSidebarOpen = ref(true)

  const starInfo = ref<StarLogininfo | null>(null);



  const signUp = async function (payload: SignUp): Promise<void> {
    const { username, password, email } = payload;

    try {
      await axios.post(`${API_URL}/users/new`, {
        username,
        password,
        email
      });
      window.alert('회원가입완료');
      // username, password 받아서 바로 로그인 하기
    } catch (error) {
      console.error(error);
    }
  };


  const logIn = async function (payload: LogIn): Promise<void> {
    const { username, password } = payload;

    try {
      const response = await axios

        .post(`${API_URL}/users/login`, {
          username,
          password
        }, {
          withCredentials: true,
          headers: {
            'Content-Type': 'application/json', // 'Context-Type'을 'Content-Type'으로 수정
          }
        })
        .then((res) => {
          const { data } = res
          console.log(res.status)
          if (res.status === 200) {

            isLogin.value = true;
            // 여기에서 로그인한 사용자의 정보를 갱신하기
            starInfo.value = data;
            console.log(starInfo.value?.starId)
            console.log(starInfo.value?.username)
            console.log(starInfo.value?.email)
            console.log(starInfo.value?.picture)
            console.log(starInfo.value?.role)
            console.log(starInfo.value?.provider)
          }
        }
      )
      window.alert('로그인성공!');
      router.push({ name: 'home' }) // 무조건 홈으로 돌아가는 게 아니라 이전 창으로 돌아가기?
      // 토큰이라든가 뭔가 받아서 뭘 해야 하지 않을까
    } catch (error) {
      console.error(error);
    }
  };


  const logOut = async function (): Promise<void> {
    try {
      const response = await axios.post(`${API_URL}/users/logout`, {
      });
      console.log(response);
      window.alert('로그아웃!');
      router.push({ name: 'home' })
    } catch (error) {
      console.error(error);
    }
  };
  return {
    API_URL,
    signUp, logIn, logOut,
    //
    hotstarinfo, getStarInfo, starInfo,
    allstarinfo, isLogin,
    //
    isSidebarOpen,
  }
})
