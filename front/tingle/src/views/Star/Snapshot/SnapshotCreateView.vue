<template>
  <div class="container mt-5">
    <!-- 반응형으로 가로 길이를 조정하는 컨테이너 -->
    <div class="row justify-content-center align-items-center">
      <h1 class="text-center mb-5">스냅샷 작성</h1>
      <div class="col-12 col-md-8 col-lg-6">
        <!-- 최대 가로 길이가 화면의 60%를 차지하도록 설정 -->
        <div class="input-group mb-3">
          <input
            type="text"
            v-model="content"
            class="form-control"
            id="floatingInput"
            placeholder="간단한 내용을 입력해주세요."
          />
        </div>

        <!-- 유저 이름 입력 -->
        <div class="input-group mb-3">
          <input
            type="text"
            v-model="username"
            class="form-control"
            id="floatingInput"
            placeholder="유저이름"
          />
        </div>
        <!-- 스타 이름 입력 -->
        <div class="input-group mb-3">
          <input
            type="text"
            v-model="starname"
            class="form-control"
            id="floatingInput"
            placeholder="스타"
          />
        </div>
        <!-- 파일 업로드 -->
        <div class="input-group mb-3">
          <input type="file" @change="onFileChange" class="form-control" id="inputGroupFile02" />
        </div>
        <div v-if="imagePreview" class="mb-3">
          <img :src="imagePreview" class="img-thumbnail" />
        </div>

        <div class="input-group mb-3">
          <input
            type="text"
            v-model="tagInput"
            @keyup.enter="addTag"
            class="form-control"
            placeholder="태그 입력 후 엔터를 눌러주세요."
            id="floatingInput"
          />
        </div>
        <!-- 태그 목록 -->
        <ul>
          <button v-for="tag in tags" :key="tag" class="btn btn-secondary m-1" disabled>
            {{ tag }}
          </button>
        </ul>
        <!-- 생성 버튼 -->
        <div class="input-group mb-3">
          <button @click="createSnapshot" class="btn btn-primary form-control">작성하기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const file = ref<File | null>(null)
const content = ref('')
const tags = ref<string[]>([]) // 태그 입력 방식에 따라 수정이 필요할 수 있습니다.
const tagInput = ref<string>('')
const username = ref('')
const starname = ref('')
const router = useRouter()

const addTag = () => {
  const newTag = tagInput.value.trim()
  if (newTag !== '') {
    tags.value.push(newTag)
    tagInput.value = '' // 입력 필드 초기화
  }
}

// imagePreview를 반응형 참조로 선언하고, 초기값은 null로 설정합니다.
const imagePreview = ref<string | null>(null)

// onFileChange 함수는 Event 타입의 이벤트 객체를 매개변수로 받습니다.
const onFileChange = (event: Event) => {
  const target = event.target as HTMLInputElement
  // 파일이 있고, 이미지 파일이면 FileReader로 읽어서 미리보기를 생성합니다.
  if (target.files && target.files[0]) {
    const maxSize = 10 * 1024 * 1024 // 10MB
    if (target.files[0].size > maxSize) {
      alert('파일 크기가 너무 큽니다. 10MB 이하의 파일을 선택해주세요.')
      return
    }
    file.value = target.files[0]
    const reader = new FileReader()
    reader.onload = (e: ProgressEvent<FileReader>) => {
      // e.target!.result에는 이미지의 데이터 URL이 담겨있습니다.
      // Non-null assertion operator(!)를 사용하여 result가 null이 아님을 확신합니다.
      imagePreview.value = e.target!.result as string
    }
    reader.readAsDataURL(file.value)
  } else {
    // 이미지 파일이 아니라면 imagePreview를 null로 리셋합니다.
    imagePreview.value = null
  }
}

const createSnapshot = async () => {
  const formData = new FormData()
  if (file.value) {
    formData.append('file', file.value)
  }
  formData.append('content', content.value)
  formData.append('tags', tags.value.join(',')) // 태그 형식에 따라 조정 필요
  formData.append('username', username.value)
  formData.append('starname', starname.value)

  for (let [key, value] of formData.entries()) {
    console.log(`${key}: ${value}`)
  }

  try {
    console.log('post직전')
    axios.post('https://i10d106.p.ssafy.io/api/snapshot/new', formData, { withCredentials: true })
    console.log('post끝')
    router.go(-1) // 메인 뷰로 이동
  } catch (error) {
    console.error(error)
    console.log('실패!')
  }
}
</script>

<style>
.img-thumbnail {
  max-width: 100%;
  height: auto;
}
</style>
