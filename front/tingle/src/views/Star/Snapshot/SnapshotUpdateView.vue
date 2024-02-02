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
    <input type="file" @change="onFileChange">
    <input type="text" v-model="content" placeholder="Content">
    <!-- 태그, 사용자 이름, 스타 이름 등 필요한 필드 추가 -->
    <button @click="updateSnapshot">Update</button>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter, useRoute } from 'vue-router';

const file = ref<File | null>(null);
const content = ref('');
const tags = ref([]); // 태그 입력 방식에 따라 수정 필요
const snapshotId = useRoute().params.snapshotId;
const router = useRouter();

onMounted(async () => {
  try {
    const response = await axios.get(`/snapshot/${snapshotId}`);
    const data = response.data;
    content.value = data.content;
    // 태그, 사용자 이름 등 필요한 필드를 여기에서 설정
  } catch (error) {
    console.error(error);
  }
});

const onFileChange = (e: Event) => {
  const target = e.target as HTMLInputElement;
  if (target.files) {
    file.value = target.files[0];
  }
};

const updateSnapshot = async () => {
  const formData = new FormData();
  if (file.value) {
    formData.append('file', file.value);
  }
  formData.append('content', content.value);
  formData.append('tags', tags.value.join(',')); // 태그 형식에 따라 조정 필요

  try {
    await axios.post(`/snapshot/${snapshotId}/update`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
    router.push(`/snapshot/${snapshotId}`);
  } catch (error) {
    console.error(error);
  }
};
</script>