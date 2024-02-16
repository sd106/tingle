<template>
  <main class="container">
    <StarMenu :id="starid" />
    <div class="tw-p-8" style="padding-left: 80px; padding-right: 80px;" v-if="product">
      <div class="d-flex align-items-center">
        <h1 class="tw-text-2xl tw-font-bold">상품 수정</h1>
        <div class="tw-p-8 tw-mt-3 d-flex align-items-center justify-content-center">
          <h3>활성화 여부 <input type="checkbox" v-model="product!.available" /></h3>
        </div>
      </div>
      <div class="tw-grid tw-gap-4 tsw-mb-8">
        <input type="text" v-model="product!.name" placeholder="상품 이름" class="tw-input tw-input-bordered tw-w-full" />
        <input type="number" v-model.number="product!.amount" placeholder="수량"
          class="tw-input tw-input-bordered tw-w-full" />
        <input type="number" v-model.number="product!.price" placeholder="가격"
          class="tw-input tw-input-bordered tw-w-full" />

        <TiptapTest v-model="product!.content" />
      </div>

      <div ref="dragArea"
        class="mt-3 tw-border-dashed tw-border-2 tw-border-primary tw-p-4 tw-text-center tw-cursor-pointer tw-mb-4 tw-h-64 d-flex justify-content-center align-items-center"
        @dragover.prevent="handleDragOver" @drop="handleDrop" @click="fileInput!.click()">
        <p>여기에 파일을 드래그 앤 드롭하거나 클릭하여 선택하세요.</p>
        <input type="file" multiple ref="fileInput" @change="handleFileUpload" style="display: none" />
      </div>

      <div class="tw-grid tw-grid-cols-3 tw-gap-4">
        <div v-for="(file, index) in previewFiles" :key="index" class="tw-relative tw-mb-4">
          <img :src="file" class="tw-rounded tw-shadow-md" />
          <button @click="removeFile(index)" class="tw-btn tw-btn-error tw-btn-sm tw-absolute tw-right-0 tw-top-0">
            삭제
          </button>
        </div>
      </div>

      <div style="text-align: right" class="pe-1">
        <h2 @click="submitForm" class="hover-text fw-bold">수정하기</h2>
      </div>
    </div>
  </main>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import TiptapTest from '@/components/StarMenu/Store/TiptabTest.vue'
import type { Goods } from '@/common/types'

const router = useRouter()
import StarMenu from '@/components/StarMenu/StarMenu.vue'

const props = defineProps({
  starid: String,
  productId: String
})

const starid = ref(props.starid)
const productIdNumber = computed(() => Number(props.productId)) // 숫자로 변환
const product = ref<Goods>()
const files = ref<File[]>([])
const previewFiles = ref<string[]>([])
const dragArea = ref<HTMLElement | null>(null)
const fileInput = ref<HTMLInputElement | null>(null)

const getProduct = async (productId: number) => {
  try {
    const response = await axios.get(`https://i10d106.p.ssafy.io/api/product/getById/${productId}`)
    if (response.data.resultCode === 'SUCCESS') {
      product.value = response.data.data
      if (product.value && product.value.imageUrl) {
        // imageUrl 배열을 순회하면서 url.url 값을 previewFiles에 추가
        previewFiles.value = product.value.imageUrl.map((url) => url.url)
      }
    } else {
      product.value = response.data.data
    }
  } catch (error) {
    console.error('상품 조회 중 오류 발생', error)
  }
}

//  업데이트
onMounted(() => {
  getProduct(productIdNumber.value)

  if (dragArea.value) {
    dragArea.value.addEventListener('dragover', handleDragOver)
  }
})

const removeFile = (index: number) => {
  // 미리보기 URL 배열에서 해당 항목 제거
  previewFiles.value.splice(index, 1)

  // 실제 파일 배열에서도 해당 항목 제거
  console.log(files.value, '여긴 지우기 전')
  files.value.splice(index, 1)
  console.log(files.value, '여긴 지우고 난 후')
}

// watch(files, (newFiles) => {
//   previewFiles.value = newFiles.map((file) => URL.createObjectURL(file))
// })

const handleDragOver = (event: DragEvent) => {
  event.preventDefault()
}

const handleDrop = (event: DragEvent) => {
  event.preventDefault()
  if (event.dataTransfer) {
    // 기존 파일 배열에 새로운 파일들을 추가
    files.value = files.value.concat(Array.from(event.dataTransfer.files))
  }
}

const handleFileUpload = (event: Event) => {
  const target = event.target as HTMLInputElement
  if (target.files) {
    const uploadedFiles = Array.from(target.files)

    // 새로운 파일들에 대한 미리보기 URL만 생성
    const newPreviewFiles = uploadedFiles.map((file) => URL.createObjectURL(file))

    // 기존 previewFiles에 새로운 미리보기 URL 추가
    previewFiles.value = [...previewFiles.value, ...newPreviewFiles]

    // files 배열 업데이트
    files.value = [...files.value, ...uploadedFiles]
  }
}

const submitForm = () => {
  if (files.value.length > 0 && previewFiles.value.length > 0) {
    console.log('첫번째 이프')
    updateProduct(product.value, files.value, previewFiles.value)
  } else if (previewFiles.value.length > 0 && files.value.length === 0) {
    console.log('두번째 이프')
    updateProductWithOutFile(product.value, previewFiles.value)
  } else if (previewFiles.value.length === 0 && files.value.length === 0) {
    console.log('세번째 이프')
    updateProductWithPreview(product.value)
  }
}

const updateProductWithOutFile = async (productInfo: any, previewFiles: string[]) => {
  try {
    const formData = new FormData()
    formData.append('productDto', JSON.stringify(productInfo))
    previewFiles.forEach((previewFile) => {
      formData.append('previewFiles', previewFile)
    })
    const response = await axios.post('https://i10d106.p.ssafy.io/api/product/update/nofile', formData)
    if (response.status === 200) {
      alert('상품이 성공적으로 수정되었습니다.')
      router.go(-1)
    } else {
      alert('상품 수정에 실패했습니다.')
    }
  } catch (error) {
    console.error('상품 생성 중 오류 발생', error)
  }
}

const updateProductWithPreview = async (productInfo: any) => {
  try {
    const formData = new FormData()
    formData.append('productDto', JSON.stringify(productInfo))

    const response = await axios.post('https://i10d106.p.ssafy.io/api/product/update/nopre', formData)
    if (response.status === 200) {
      alert('상품이 성공적으로 수정되었습니다.')
      router.go(-1)
    } else {
      alert('상품 수정에 실패했습니다.')
    }
  } catch (error) {
    console.error('상품 생성 중 오류 발생', error)
  }
}

const updateProduct = async (productInfo: any, fileInputs: File[], previewFiles: string[]) => {
  try {
    const formData = new FormData()
    formData.append('productDto', JSON.stringify(productInfo))
    fileInputs.forEach((file) => {
      formData.append('files', file)
    })
    previewFiles.forEach((previewFile) => {
      formData.append('previewFiles', previewFile)
    })

    const response = await axios.post('https://i10d106.p.ssafy.io/api/product/update', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    if (response.status === 200) {
      alert('상품이 성공적으로 수정되었습니다.')
      console.log(formData)
      console.log(fileInputs.values)
      router.go(-1)
    } else {
      alert('상품 수정에 실패했습니다.')
    }
  } catch (error) {
    console.error('상품 생성 중 오류 발생', error)
  }
}
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
  >*+* {
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

.hover-text {
  transition: transform 0.3s ease-in-out; /* transform 속성에 대한 전환 효과 적용 */
}

.hover-text:hover {
  transform: translateX(10px); /* 호버 시 글자를 오른쪽으로 10픽셀 이동 */
  cursor: pointer;
}
</style>
