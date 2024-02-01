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
  <div>
    {{ file }}  | {{ content }}  | {{ tags }}
    <input type="file" @change="onFileChange">
    <input type="text" v-model="content" placeholder="Content">
    <input type="text" v-model="tagInput" @keyup.enter="addTag" placeholder="태그 입력 후 엔터">
    <ul>
      <li v-for="tag in tags" :key="tag">{{ tag }}</li>
    </ul>
    <button @click="updateSnapshot">Update</button>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter, useRoute } from 'vue-router';


// 라우터와 라우트 객체 접근
const router = useRouter();
const route = useRoute();
const snapshotId = route.params.snapshotid as string; // params는 반응형 객체이므로 as를 사용하여 타입 단언
const starId = route.params.starid as String;
const file = ref<File | null>(null);
const content = ref('');
const tags = ref<string[]>([]); // 태그 상태 초기화
const tagInput = ref<string>('');


// 파일 선택 시 호출되는 함수
const onFileChange = (e: Event) => {
  const target = e.target as HTMLInputElement;
  if (target.files) {
    file.value = target.files[0];
  }
};

// 업데이트 버튼 클릭 시 호출되는 함수
const updateSnapshot = async () => {
  const formData = new FormData();
  if (file.value) {
    formData.append('file', file.value);
  }
  formData.append('content', content.value);
  formData.append('tags', tags.value.join(',')); // 태그를 콤마로 구분된 문자열로 변환하여 추가
  console.log(file.value)
  console.log(content.value)
  console.log(tags.value)
  console.log(snapshotId)
  // API 호출을 통해 스냅샷 데이터를 업데이트하는 로직
  try {
    console.log("axios 직전")
    await axios.put(`http://localhost:8080/snapshot/${snapshotId}/update`, formData);
    console.log("axios 통과")
    router.push(`/${starId}/snapshot`); // 업데이트 후 상세 페이지로 이동
  } catch (error) {
    console.error(error);
  }
};

const addTag = () => {
  const newTag = tagInput.value.trim()
  if (newTag !== '') {
    tags.value.push(newTag);
    tagInput.value = ''; // 입력 필드 초기화
  }
  console.log(tags)
};
</script>