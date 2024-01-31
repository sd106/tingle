import { ref } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import axios from 'axios'

import type { SignUp, LogIn, Starinfo } from '@/common/types/index'

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

  const categories: Record<string, Starinfo[]> = {
    '유형민': yoo,
    '황찬준': jun,
    '정수빈': bin,
    '이성모': mo,
    '윤정영': yoon,
    '오은아': oh,
  };
  // 

  const isSidebarOpen = ref(true)

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
            const name = data.username
            const role = data.role
            const mail = data.email
            console.log(name)
            console.log(role)
            console.log(mail)
          }
        }

        )
      console.log(response);
      console.log(payload);
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
    hotstarinfo, getStarInfo,
    allstarinfo, isLogin, categories,
    //
    isSidebarOpen,
  }
})
