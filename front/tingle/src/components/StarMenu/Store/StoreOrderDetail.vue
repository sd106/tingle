<template>
  <main>
    <div class="tw-bg-white tw-shadow-lg tw-rounded-lg tw-p-6 tw-w-full">
      <div class="d-flex">
        <div class="tw-mb-2 d col-6 d-flex flex-column justify-content-center align-items-center">
          <div class="mx-0">
            <h2 class="fw-bold p-0 tw-font-medium">구매자 정보</h2>
            <p>이름: {{ order.fan.username }}</p>
            <p>이메일: {{ order.fan.email }}</p>
          </div>
          <img :src="order.fan.picture" alt="상품 이미지" class="tw-mt-2 tw-rounded tw-shadow" />
        </div>
        <div class="col-6" style="margin-top: 100px">
          <div class="d-felx flex-column"></div>
          <div
            ref="dragArea"
            class="me-5 tw-border-dashed tw-border-2 tw-border-primary tw-p-4 tw-text-center tw-cursor-pointer tw-mb-4 tw-h-72"
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

          <div class="text-end me-5">
            <button class="tw-btn me-3" @click="submitForm">파일 전송</button>
            <button class="tw-btn" @click="deleteOrder(order.orderId)">주문 삭제</button>
          </div>
        </div>
        <!-- 드래그 앤 드롭으로 업로드된 파일의 미리보기 -->

        <!-- 상품 생성 버튼 -->
      </div>
      <div class="tw-grid tw-grid-cols-3 tw-gap-4 d-flex justify-content-center align-items-center">
        <div v-for="(file, index) in previewFiles" :key="index" class="tw-relative tw-mb-4">
          <img :src="file" height="200px;" width="200px;" class="tw-rounded tw-shadow-md" />
          <button
            @click="removeFile(index)"
            class="tw-btn tw-btn-sm tw-absolute tw-right-1 tw-bottom-1"
          >
            삭제
          </button>
        </div>
      </div>
    </div>

    <!-- <form @submit.prevent="submitForm(order.orderId)">
    <input type="file" @change="handleFileUpload" multiple>
    <button type="submit">생성</button>
  </form> -->

    <!-- 드래그 앤 드롭을 통한 파일 업로드 영역 -->
  </main>
</template>

<script setup lang="ts">
import type { OrderResponse } from '@/common/types'
import { ref, onMounted, watch } from 'vue'
import axios from 'axios'
import router from '@/router'

// const files = ref<File[]>([])
const content = ref<string>('')
const title = ref<string>('')

const deleteOrder = async (orderId: number) => {
  try {
    const response = await axios.post(`http://localhost:8080/order/delete/${orderId}`)
    console.log(response.data)
    router.go(0)
  } catch (error) {
    console.log(error)
  }
}

const props = defineProps({
  order: {
    type: Object as () => OrderResponse,
    required: true
  }
})

const order = props.order

// =========================================
const files = ref<File[]>([])
const previewFiles = ref<string[]>([])
const dragArea = ref<HTMLElement | null>(null)
const fileInput = ref<HTMLInputElement | null>(null)

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
    // 기존 파일 배열에 새로운 파일들을 추가
    files.value = [...files.value, ...Array.from(target.files)]
  }
}
const submitForm = () => {
  if (files.value.length > 0) {
    createStorage(order.orderId, files.value)
  } else {
    alert('파일을 선택해주세요.')
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

watch(files, (newFiles) => {
  previewFiles.value = newFiles.map((file) => URL.createObjectURL(file))
})

const createStorage = async (orderId: Number, fileInputs: File[]) => {
  try {
    const formData = new FormData()
    formData.append('orderId', orderId.toString())
    formData.append('content', content.value)
    formData.append('title', title.value)
    for (let i = 0; i < fileInputs.length; i++) {
      formData.append('files', fileInputs[i])
    }

    const response = await axios.post('http://localhost:8080/order/sendTouser', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })

    if (response.status === 200) {
      alert('파일 전송을 성공했습니다.')
      router.go(0)
    } else {
      alert('파일 전송을 실패했습니다.')
    }
  } catch (error) {
    console.error('상품 생성 중 오류 발생', error)
  }
}
</script>

<style>
.order-info {
  font-family: 'Arial', sans-serif;
  border-collapse: collapse;
  width: 100%;
}
.order-info td,
.order-info th {
  border: 1px solid #ddd;
  padding: 8px;
}
.order-info tr:nth-child(even) {
  background-color: #f2f2f2;
}
.order-info th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #4caf50;
  color: white;
}
.order-info img {
  width: 50px;
  height: auto;
}
</style>
