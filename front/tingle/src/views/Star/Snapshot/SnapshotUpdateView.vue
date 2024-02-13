<!-- <template>
  <main class="container">
    <StarMenu :id="id" />
    <h1>여기까진 아직 못오지~~</h1>
  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'

import StarMenu from '@/components/StarMenu/StarMenu.vue';


const props = defineProps(['id']);
const id = ref(props.id);

</script> -->

<template>
  <div class="row justify-content-center align-items-center">
    <div class="col-12 col-md-8 col-lg-6">
      <h1 class="text-center mb-5">스냅샷 수정</h1>
      <div class="input-group mb-3">
        <input
          type="text"
          v-model="content"
          class="form-control"
          id="floatingInput"
          placeholder="간단한 내용을 입력해주세요."
        />
      </div>
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
        <button @click="updateSnapshot" class="btn btn-primary form-control">수정</button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter, useRoute } from 'vue-router'

// 라우터와 라우트 객체 접근
const router = useRouter()
const route = useRoute()
const snapshotId = route.params.snapshotid as string // params는 반응형 객체이므로 as를 사용하여 타입 단언
const starId = route.params.starid as String
const file = ref<File | null>(null)
const content = ref('')
const tags = ref<string[]>([]) // 태그 상태 초기화
const tagInput = ref<string>('')

const imagePreview = ref<string | null>(null)

// onFileChange 함수는 Event 타입의 이벤트 객체를 매개변수로 받습니다.
const onFileChange = (event: Event) => {
  const target = event.target as HTMLInputElement
  // 파일이 있고, 이미지 파일이면 FileReader로 읽어서 미리보기를 생성합니다.
  if (target.files && target.files[0] && target.files[0].type.startsWith('image/')) {
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

// 업데이트 버튼 클릭 시 호출되는 함수
const updateSnapshot = async () => {
  const formData = new FormData()
  if (file.value) {
    formData.append('file', file.value)
  }
  formData.append('content', content.value)
  formData.append('tags', tags.value.join(',')) // 태그를 콤마로 구분된 문자열로 변환하여 추가
  console.log(file.value)
  console.log(content.value)
  console.log(tags.value)
  console.log(snapshotId)
  // API 호출을 통해 스냅샷 데이터를 업데이트하는 로직
  try {
    console.log('axios 직전')
    await axios.put(`http://localhost:8080/snapshot/${snapshotId}/update`, formData)
    console.log('axios 통과')
    router.push(`/${starId}/snapshot`) // 업데이트 후 상세 페이지로 이동
  } catch (error) {
    console.error(error)
  }
}

const addTag = () => {
  const newTag = tagInput.value.trim()
  if (newTag !== '') {
    tags.value.push(newTag)
    tagInput.value = '' // 입력 필드 초기화
  }
  console.log(tags)
}
</script>
