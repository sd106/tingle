<!-- <template>
  <main class="container">
    <StarMenu :id="id" />
    <h1>스냅샷 생성</h1>
  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'

import StarMenu from '@/components/StarMenu/StarMenu.vue';


const props = defineProps(['id']);
const id = ref(props.id);

</script> -->


<template>
  <div class="container mt-5">
    <!-- 반응형으로 가로 길이를 조정하는 컨테이너 -->
    <div class="row justify-content-center align-items-center">
      <h1 class="text-center mb-5">스냅샷 작성</h1>
      <div class="col-12 col-md-8 col-lg-6">
        <!-- 최대 가로 길이가 화면의 60%를 차지하도록 설정 -->
        <div class="input-group mb-3">
          <input type="text" v-model="content" class="form-control" id="floatingInput" placeholder="간단한 내용을 입력해주세요.">
        </div>

        <!-- 유저 이름 입력 -->
        <div class="input-group mb-3">
          <input type="text" v-model="username" class="form-control" id="floatingInput" placeholder="유저이름">
        </div>
        <!-- 스타 이름 입력 -->
        <div class="input-group mb-3">
          <input type="text" v-model="starname" class="form-control" id="floatingInput" placeholder="스타">
        </div>
        <!-- 파일 업로드 -->
        <div class="input-group mb-3">
          <input type="file" @change="onFileChange" class="form-control" id="inputGroupFile02">
          <label class="input-group-text" for="inputGroupFile02">Upload</label>
        </div>
        <div v-if="imagePreview" class="mb-3">
          <img :src="imagePreview" class="img-thumbnail">
        </div>

        <div class="input-group mb-3">
          <input type="text" v-model="tagInput" @keyup.enter="addTag" class="form-control" placeholder="태그 입력 후 엔터를 눌러주세요." id="floatingInput">
        </div>
        <!-- 태그 목록 -->
        <ul>
          <button v-for="tag in tags" :key="tag" class="btn btn-secondary m-1" disabled>{{ tag }}</button>
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
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const file = ref<File | null>(null);
const content = ref('');
const tags = ref<string[]>([]); // 태그 입력 방식에 따라 수정이 필요할 수 있습니다.
const tagInput = ref<string>('');
const username = ref('');
const starname = ref('');
const router = useRouter();

const addTag = () => {
  const newTag = tagInput.value.trim()
  if (newTag !== '') {
    tags.value.push(newTag);
    tagInput.value = ''; // 입력 필드 초기화
  }
};

// imagePreview를 반응형 참조로 선언하고, 초기값은 null로 설정합니다.
const imagePreview = ref<string | null>(null);

// onFileChange 함수는 Event 타입의 이벤트 객체를 매개변수로 받습니다.
const onFileChange = (event: Event) => {
  const target = event.target as HTMLInputElement;
  // 파일이 있고, 이미지 파일이면 FileReader로 읽어서 미리보기를 생성합니다.
  if (target.files && target.files[0] && target.files[0].type.startsWith('image/')) {
    const file = target.files[0];
    const reader = new FileReader();
    reader.onload = (e: ProgressEvent<FileReader>) => {
      // e.target!.result에는 이미지의 데이터 URL이 담겨있습니다.
      // Non-null assertion operator(!)를 사용하여 result가 null이 아님을 확신합니다.
      imagePreview.value = e.target!.result as string;
    };
    reader.readAsDataURL(file);
  } else {
    // 이미지 파일이 아니라면 imagePreview를 null로 리셋합니다.
    imagePreview.value = null;
  }
};

const createSnapshot = async () => {
  const formData = new FormData();
  if (file.value) {
    formData.append('file', file.value);
  } 
  formData.append('content', content.value);
  formData.append('tags', tags.value.join(',')); // 태그 형식에 따라 조정 필요
  formData.append('username', username.value);
  formData.append('starname', starname.value);
  
  for (let [key, value] of formData.entries()) {
    console.log(`${key}: ${value}`);
  }

  try {
    await axios.post('http://localhost:8080/snapshot/new', formData);
    router.push('/'); // 메인 뷰로 이동
  } catch (error) {
    console.error(error);
    console.log("실패!")
  }
};


</script>

<style>

  .img-thumbnail {
    max-width: 100%;
    height: auto;
  }

</style>