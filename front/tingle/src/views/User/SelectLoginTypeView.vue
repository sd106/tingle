<template>
  <main class="main-container">
    <div class="buttons-container">
      <button
        v-if="!isStarExpanding"
        @click="selectLoginType('fan')"
        :class="{
          'signin-btn': true,
          'is-selected': selectedType === 'fan',
          'expand-animation': isFanExpanding
        }"
        style="display: flex; flex-direction: column; align-items: center; justify-content: center"
      >
        <span>팬이에요!</span>
        <!-- <img
          src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/Purple%20Heart.png"
          alt="Purple Heart"
          width="300"
          height="25"
        /> -->
        <!-- <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/Red%20Heart.png" alt="Red Heart" width="300" height="25" /> -->
        <img
          src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/Pink%20Heart.png"
          alt="Pink Heart"
          width="250"
          height="25"
        />
      </button>
      <button
        v-if="!isFanExpanding"
        @click="selectLoginType('star')"
        :class="{
          'signin-btn2': true,
          'is-selected2': selectedType === 'star',
          'expand-animation': isStarExpanding
        }"
        style="display: flex; flex-direction: column; align-items: center; justify-content: center"
      >
        <span>스타에요!</span>
        <img
          src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Travel%20and%20places/Star.png"
          alt="Star"
          width="300"
          height="25"
        />
      </button>
    </div>
    <div v-if="showWelcomeMessage" class="welcome-message">
      <h1 style="z-index: 19999">환영합니다</h1>
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
const isFanExpanding = ref(false)
const isStarExpanding = ref(false)
const showWelcomeMessage = ref(false)

const selectLoginType = async (type: string) => {
  try {
    const { data } = await axios.post(
      `http://localhost:8080/auth/login/${type}`,
      {},
      { withCredentials: true }
    )
    if (type === 'fan') {
      const { id, username, picture, follwingStars } = data
      store.fanState = { id, username, picture, follwingStars }
      await expandAndRefresh('fan')
      router.push('/')
    } else if (type === 'star') {
      const { id, username, picture } = data
      store.starState = { id, username, picture }
      store.isStar = true
      await expandAndRefresh('star')
      router.push('/')
    }
  } catch (error) {
    console.log('error', error)
  }
}

const expandAndRefresh = (type: string) => {
  return new Promise<void>((resolve) => {
    if (type === 'fan') {
      isFanExpanding.value = true
    } else {
      isStarExpanding.value = true
    }
    setTimeout(() => {
      showWelcomeMessage.value = true
      setTimeout(() => {
        resolve() // 애니메이션 완료 후 Promise를 해결하여 router.push가 호출될 수 있게 함
      }, 1500) // "환영합니다" 메시지 표시 후 애니메이션 지속 시간
    }, 1000) // 버튼 확장 애니메이션 지속 시간
  })
}
</script>

<style scoped>
.main-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.buttons-container {
  display: flex;
  gap: 20px;
}

.signin-btn,
.signin-btn2 {
  display: block;
  width: 400px;
  height: 500px;
  border-radius: 10px;
  margin: 0 auto;
  border: none;
  cursor: pointer;
  font-size: 20px;
  font-family: 'Montserrat', sans-serif;
  transition:
    transform 0.3s ease,
    box-shadow 0.3s ease;
}

.signin-btn {
  background: #aa96da;
  color: white;
  box-shadow: 0 15px 30px rgba(233, 30, 99, 0.36);
}

.signin-btn2 {
  background: #e85454;
  color: white;
  box-shadow: 0 15px 30px rgba(233, 30, 99, 0.36);
}

.signin-btn:hover {
  transform: scale(1.5);
  background: #aa96da;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
  z-index: 100;
}

.signin-btn2:hover {
  transform: scale(1.5);
  background: #e85454;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
  z-index: 100;
}

.is_selected {
  border: 2px solid #ffffff;
  background-color: #4caf50;
  color: #ffffff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
  transform: scale(1.05);
}

.expand-animation {
  animation: expand 1s forwards;
}

@keyframes expand {
  from {
    transform: scale(1);
  }
  to {
    transform: scale(20); /* 화면을 충분히 덮을 정도로 크기 조정 */
    opacity: 0; /* 최종적으로 사라지게 만듦 */
    z-index: 10000;
  }
}

/* 기존 CSS 코드 유지 및 추가 */

.welcome-message {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 2rem;
  color: white; /* 배경색과 대비되는 색상 설정 */
  opacity: 0;
  animation: fadeIn 2s ease forwards;
  z-index: 19999;
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
</style>
