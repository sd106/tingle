<template>
  <main>
    <div>
      <button @click="updateImage" class="tw-btn m-2">사진 수정</button>
      <button @click="deleteImage" class="tw-btn m-2">사진 삭제</button>
    </div>
  </main>
</template>

<script setup lang="ts">
import { useUserStore } from '@/stores/user'
import axios from 'axios'
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const { starState } = useUserStore()
const starId = starState!.id


onMounted(() => {
  if (!starState?.picture) {
    starState!.picture = '/image/basic-profile.png'
  }
})

const updateImage = async () => {
  try {
    // 파일 선택하기
    const file = await selectFile2()
    if (!file) return
    // FormData 생성
    const formData = new FormData()
    formData.append('file', file)
    formData.append('starId', String(starId))

    // Axios 요청 보내기
    const response = await axios.post('https://i10d106.p.ssafy.io/api/star/profilePicture', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    if (response.data.resultCode === 'SUCCESS') {
      alert('프로필 사진이 업데이트 되었습니다.')
      starState!.picture = response.data.data
      router.go(0)
    } else {
      console.log(formData)
      alert('프로필 사진 업데이트에 실패했습니다.')
      router.go(0)
    }
  } catch (error) {
    console.error('프로필 사진 업로드 실패:', error)
    alert('프로필 사진 업로드 중 오류가 발생했습니다.')
  }
}

// 파일 선택 함수
const selectFile2 = () => {
  return new Promise<File | null>((resolve) => {
    const input = document.createElement('input')
    input.type = 'file'
    input.onchange = () => {
      resolve(input.files ? input.files[0] : null)
    }
    input.click()
  })
}

const deleteImage = async () => {
  try {
    const response = await axios.post(`https://i10d106.p.ssafy.io/api/star/profilePicture/delete/${starId}`)
    starState!.picture = response.data.data
    if (response.data.resultCode === 'SUCCESS') {
      alert('프로필 사진이 삭제 되었습니다.')
      starState!.picture = response.data.data
      console.log(starState)
      router.go(0)
    } else {
      console.log(starState)
      alert('프로필 사진 삭제에 실패했습니다.')
      router.go(0)
    }
  } catch (error) {
    console.error('이미지 삭제 실패:', error)
  }
}



</script>

<style>

</style>