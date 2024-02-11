<template>
    <ul class="d-flex justify-content-around nav nav-underline">
      <li class="nav-item">
        <RouterLink
          :to="`/profile/userinfo`"
          class="nav-link router-link-custom"
          :class="{ active: isActive('/userInfo') }"
          >회원 정보</RouterLink
        >
      </li>
      <li class="nav-item">
        <RouterLink
          :to="`/profile/storage`"
          class="nav-link router-link-custom"
          :class="{ active: isActive('/storage') }"
          >보관함</RouterLink
        >
      </li>
      <li class="nav-item">
        <RouterLink
          :to="`/profile/orders`"
          class="nav-link router-link-custom"
          :class="{ active: isActive('/orders') }"
          >주문 목록</RouterLink
        >
      </li>
    </ul>
  <main class="container d-flex flex-column align-items-center">
    <h1>프로필 사진 업데이트</h1>
    <form @submit.prevent="uploadProfilePicture">
      <input type="file" ref="profileImageInput" />
      <button type="submit">업로드</button>
    </form>
    <div v-if="fanState.picture">
      <img :src="fanState.picture" alt="" />
    </div>
  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useUserStore } from '@/stores/user'
import axios from 'axios'
import { useRoute } from 'vue-router'

const route = useRoute()
const { fanState } = useUserStore()
const profileImageInput = ref<HTMLInputElement | null>(null)
const fanId = fanState.id

const uploadProfilePicture = async () => {
  if (
    !profileImageInput.value ||
    !profileImageInput.value.files ||
    profileImageInput.value.files.length === 0
  ) {
    alert('파일을 선택해주세요.')
    return
  }
  const file = profileImageInput.value.files[0]

  try {
    const formData = new FormData()
    formData.append('fanId', String(fanId))
    formData.append('file', file)

    const response = await axios.post('http://i10d106.p.ssafy.io/api/api/user/profilePicture', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })

    if (response.data.resultCode === 'SUCCESS') {
      alert('프로필 사진이 업데이트 되었습니다.')
      fanState.picture = response.data.data
    } else {
      alert('프로필 사진 업데이트에 실패했습니다.')
    }
  } catch (error) {
    console.error('프로필 사진 업로드 실패:', error)
    alert('프로필 사진 업로드 중 오류가 발생했습니다.')
  }
}

const isActive = (path: string) => {
  return route.path.includes(path)
}
</script>

<style scoped></style>
