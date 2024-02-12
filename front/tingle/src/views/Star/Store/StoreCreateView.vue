<template>
  <main class="container">
    <StarMenu :id="id" />
    <div class="tw-p-8">
      <h1 class="tw-text-2xl tw-font-bold tw-mb-8">상품 생성</h1>

      <!-- 상품 정보 입력 폼 -->
      <div class="tw-grid tw-gap-4 tw-mb-8">
        <!-- <input
          type="text"
          v-model="productcreate.starName"
          placeholder="스타 이름"
          class="tw-input tw-input-bordered tw-w-full"
        /> -->
        <input
          type="text"
          v-model="productcreate.name"
          placeholder="상품 이름"
          class="tw-input tw-input-bordered tw-w-full"
        />
        <input
          type="number"
          v-model.number="productcreate.amount"
          placeholder="수량"
          class="tw-input tw-input-bordered tw-w-full"
        />
        <input
          type="number"
          v-model.number="productcreate.price"
          placeholder="가격"
          class="tw-input tw-input-bordered tw-w-full"
        />

        <TiptapTest v-model="productcreate.content" />
      </div>

      <!-- 드래그 앤 드롭을 통한 파일 업로드 영역 -->
      <div
        ref="dragArea"
        class="tw-border-dashed tw-border-2 tw-border-primary tw-p-4 tw-text-center tw-cursor-pointer tw-mb-4"
        @dragover.prevent="handleDragOver"
        @drop="handleDrop"
        @click="fileInput!.click()"
      >
        여기에 파일을 드래그 앤 드롭하거나 클릭하여 선택하세요.
        <input
          type="file"
          multiple
          ref="fileInput"
          @change="handleFileUpload"
          style="display: none"
        />
      </div>

      <!-- 드래그 앤 드롭으로 업로드된 파일의 미리보기 -->
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
      <!-- 상품 생성 버튼 -->
      <button @click="submitForm" class="tw-btn tw-btn-primary tw-mt-4">상품 생성</button>
    </div>
    <!-- //-----------상품 생성----------------------------// -->
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
const store = useUserStore()

const starName = store.starState!.username

const props = defineProps(['id'])
const id = ref(props.id)

const createProduct = async (productInfo: any, fileInputs: File[]) => {
  try {
    const formData = new FormData()
    formData.append('productDto', JSON.stringify(productInfo))
    fileInputs.forEach((file) => {
      formData.append('files', file)
    })

    const response = await axios.post('https://i10d106.p.ssafy.io/api/product/create', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    if (response.status === 200) {
      alert('상품이 성공적으로 생성되었습니다.')
      router.go(-1)
    } else {
      alert('상품 생성에 실패했습니다.')
    }
  } catch (error) {
    console.error('상품 생성 중 오류 발생', error)
  }
}


const createProductNoFile = async (productInfo: any) => {
  try {
    const formData = new FormData()
    formData.append('productDto', JSON.stringify(productInfo))

    const response = await axios.post('http://localhost:8080/product/create/nofile', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    if (response.status === 200) {
      alert('상품이 성공적으로 생성되었습니다.')
      router.go(-1)
    } else {
      alert('상품 생성에 실패했습니다.')
    }
  } catch (error) {
    console.error('상품 생성 중 오류 발생', error)
  }
}

const submitForm = () => {
  if (files.value.length > 0) {
    createProduct(productcreate.value, files.value)
  }
  else {
    createProductNoFile(productcreate.value)
  }
}

import { onMounted, watch } from 'vue'

const productcreate = ref({
  starName: starName,
  name: '',
  amount: Number,
  price: Number,
  content: ''
})
const files = ref<File[]>([])
const previewFiles = ref<string[]>([])
const dragArea = ref<HTMLElement | null>(null)
const fileInput = ref<HTMLInputElement | null>(null)

watch(files, (newFiles) => {
  previewFiles.value = newFiles.map((file) => URL.createObjectURL(file))
})

const handleDragOver = (event: DragEvent) => {
  event.preventDefault()
}

const handleDrop = (event: DragEvent) => {
  event.preventDefault();
  if (event.dataTransfer) {
    // 기존 파일 배열에 새로운 파일들을 추가
    files.value = files.value.concat(Array.from(event.dataTransfer.files));
  }
};

const handleFileUpload = (event: Event) => {
  const target = event.target as HTMLInputElement;
  if (target.files) {
    // 기존 파일 배열에 새로운 파일들을 추가
    files.value = [...files.value, ...Array.from(target.files)];
  }
}

// 여기에 나머지 createProduct 및 submitForm 로직을 포함합니다.

onMounted(() => {
  if (dragArea.value) {
    dragArea.value.addEventListener('dragover', handleDragOver)
  }
})

const removeFile = (index: number) => {
  // 미리보기 URL 배열에서 해당 항목 제거
  previewFiles.value.splice(index, 1)

  // 실제 파일 배열에서도 해당 항목 제거
  files.value.splice(index, 1)
}
</script>

<style lang="scss">
h1 {
  font-size: 2em; /* 예시 */
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
