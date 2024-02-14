<template>
  <main class="container">
    <!-- <StarMenu :id="id" />
    <div class="tw-p-8">
      <h1 class="tw-text-2xl tw-font-bold tw-mb-8">스타 프로필 수정</h1>

      <div class="d-flex justify-content-between">
        드래그 앤 드롭을 통한 파일 업로드 영역
        <div
          ref="dragArea"
          class="tw-border-dashed tw-border-2 tw-border-primary tw-p-4 tw-text-center tw-cursor-pointer tw-mb-4"
          @dragover.prevent="handleDragOver"
          @drop="handleDrop"
          @click="fileInput!.click()"
        >
          프로필 사진을 드래그 앤 드롭하거나 클릭하여 선택하세요.
          <input
            type="file"
            ref="fileInput"
            @change="handleFileUpload"
            style="display: none"
          />
        </div>

        드래그 앤 드롭으로 업로드된 파일의 미리보기
        <div class="tw-grid tw-grid-cols-3 tw-gap-4">
          <div v-for="(file, index) in previewFiles" :key="index" class="tw-relative tw-mb-4">
            <img :src="file" class="tw-rounded tw-shadow-md" />
            <button
              @click="removeFile(index)"
              class="tw-btn tw-btn-error tw-btn-sm tw-absolute tw-right-0 tw-top-0"
            >
              삭제
            </button>
          </div>
        </div>
      </div>
      <div>
        상품 정보 입력 폼
        <div class="tw-flex tw-gap-4 tw-me-4 tw-mb-4">

          <input
            type="text"
            :placeholder= "starProfile?.username"
            class="tw-input tw-input-bordered tw-w-full"
          />

          <select class="select textarea-bordered tw-input tw-input-bordered select-bordered w-full max-w-xs">
            <option disabled selected>카테고리</option>
            <option value="0">일상/토크</option>
            <option value="1" >동물</option>
            <option value="2">게임/스포츠</option>
            <option value="3">미술/음악</option>
            <option value="4">뷰티/패션</option>
            <option value="5">기타</option>
          </select>

        </div>

        <textarea class="textarea textarea-bordered tw-input tw-input-bordered" placeholder="SNS">

        </textarea>

      </div>
        <TiptapTest />


      상품 생성 버튼
      <button @click="submitForm" class="tw-btn tw-btn-primary tw-mt-4">상품 생성</button>
    </div>
    //-----------상품 생성----------------------------// -->
  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import TiptapTest from '@/components/StarMenu/Store/TiptabTest.vue'
import StarMenu from '@/components/StarMenu/StarMenu.vue'
const router = useRouter()
const starProfile = ref<StarProfile>();

// const getstarProfile = async () => {
//   const response = await axios.get(`${store.API_URL}/home/profile/${starId}`);
//   starProfile.value = response.data.data;
//   console.log(starProfile.value);
// }

const submitForm = () => {
  if (files.value.length > 0) {
    //createProduct(productcreate.value, files.value)
  } else {
   // createProductNoFile(productcreate.value)
  }
}

import { onMounted, watch } from 'vue'
import type { StarProfile } from '@/common/types'


const files = ref<File[]>([])
const previewFiles = ref<string[]>([])
const dragArea = ref<HTMLElement | null>(null)
const fileInput = ref<HTMLInputElement | null>(null)


const removeFile = (index: number) => {
  // 미리보기 URL 배열에서 해당 항목 제거
  previewFiles.value.splice(index, 1)

  // 실제 파일 배열에서도 해당 항목 제거
  files.value.splice(index, 1)
}

onMounted(() => {
  // getstarProfile();
});


</script>

<style lang="scss">
h1 {
  font-size: 2em;
  /* 예시 */
  color: #333;
}

h2 {
  font-size: 1.7em;
  color: #333;
}

h2 {
  font-size: 1.5em;
  color: #333;
}

/* h3 등에 대해서도 필요한 스타일을 추가 */

/* Basic editor styles */
.drag-area {
  border: 2px dashed #ccc;
  padding: 20px;
  text-align: center;
  cursor: pointer;
}

.tiptap {
  > * + * {
    margin-top: 0.75em;
  }

  code {
    background-color: rgba(#616161, 0.1);
    color: #616161;
  }
}

.content {
  padding: 1rem 0 0;

  pre {
    border-radius: 5px;
    color: #333;
  }

  code {
    display: block;
    white-space: pre-wrap;
    font-size: 0.8rem;
    padding: 0.75rem 1rem;
    background-color: #e9ecef;
    color: #495057;
  }
}


</style>
