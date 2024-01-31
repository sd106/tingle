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
  <div>
    <input type="file" @change="onFileChange">
    <input type="text" v-model="content" placeholder="Content">
    <input type="text" v-model="tagInput" @keyup.enter="addTag" placeholder="태그 입력 후 엔터">
    <ul>
      <li v-for="tag in tags" :key="tag">{{ tag }}</li>
    </ul>
    <input type="text" v-model="username" placeholder="Username">
    <input type="text" v-model="starname" placeholder="Starname">
    <button @click="createSnapshot">Create</button>
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

const onFileChange = (e: Event) => {
  const target = e.target as HTMLInputElement;
  if (target.files) {
    file.value = target.files[0];
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
