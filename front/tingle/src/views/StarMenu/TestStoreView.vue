<template>
  <main class="container">

    <StarOrderComponent/>
    <UserOrderComponent/>


    <!-- //-----------스타의 상품 상세 조회----------------------------// -->
    <div>
      <h1>상품 상세 조회</h1>
      <form @submit.prevent="submitFormGetProductById">
        <input type="number" v-model="inputProductId" placeholder="상품 ID 입력" />
        <button class="tw-btn" type="submit">상품 상세 조회</button>
      </form>

      <div v-if="productgetById">
        <p>상품 이름: {{ productgetById.name }}</p>
        <p>스타 이름: {{ productgetById.starName }}</p>
        <p>수량: {{ productgetById.amount }}</p>
        <p>가격: {{ productgetById.price }}</p>
        <div v-html="productgetById.content"></div>
        <p>사용 가능: {{ productgetById.available ? '예' : '아니오' }}</p>
        <div v-if="productgetById.imageUrl && productgetById.imageUrl.length">
          <h3>이미지:</h3>
          <div v-for="url in productgetById.imageUrl" :key="url.id">
            <img :src="url.url" alt="Product Image" style="max-width: 200px; margin: 5px" />
            <button @click="updateImage(productgetById.productId, url.id)">수정하기</button>
            <button @click="deleteImage(url.id, productgetById.productId)">삭제하기</button>
          </div>
        </div>
        <button @click="uploadImage(productgetById.productId)">업로드</button>
      </div>
    </div>
    <!-- //-----------스타의 상품 상세 조회----------------------------// -->

    <!-- //-----------스타의 상품 조회----------------------------// -->
    <hr />
    <br />
    <hr />
    <h1>스타의 상품 조회</h1>
    <div>
      <form @submit.prevent="submitFormgetByStarName">
        <input type="text" v-model="inputStarName" placeholder="스타 이름 입력" />
        <button type="submit">스타 상품 목록 조회</button>
      </form>
      <div v-if="productgetByStarName">
        {{ productgetByStarName }}
        <div v-for="product in productgetByStarName" :key="product.productId">
          <p>{{ product.name }}</p>
          <!-- 기타 상품 정보 표시 -->
        </div>
        <!-- <h3>{{ productgetByStarName }}</h3> -->
      </div>
      <div v-else>스타의 상품 목록이 없습니다.</div>
    </div>
    <!-- //-----------스타의 상품 조회----------------------------// -->

    <!-- //-----------상품 삭제----------------------------// -->
    <hr />
    <br />
    <hr />
    <div>
      <h1>상품 삭제</h1>
      <form @submit.prevent="submitFormgetdelete">
        <input type="text" v-model="inputProductIdDelete" placeholder="삭제할 상품ID 입력" />
        <button type="submit">삭제하기</button>
      </form>
      <p>상품 상세 조회랑 같이 사용시 확인 가능</p>
    </div>
    <!-- //-----------상품 삭제----------------------------// -->

    <!-- //-----------상품 생성----------------------------// -->

    <hr />
    <br />
    <hr />
    <h1>상품 생성</h1>
    <div class="tw-p-8">
      <h1 class="tw-text-2xl tw-font-bold tw-mb-8">상품 생성</h1>

      <!-- 상품 정보 입력 폼 -->
      <div class="tw-grid tw-gap-4 tw-mb-8">
        <input
          type="text"
          v-model="productcreate.starName"
          placeholder="스타 이름"
          class="tw-input tw-input-bordered tw-w-full"
        />
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

    <!-- //-----------상품 수정 이미지제외----------------------------// -->

    <hr />
    <br />
    <hr />

    <h1>상품 정보 업데이트</h1>
    <form @submit.prevent="submitFormupdate">
      <div class="d-flex">
        <p>상품Id</p>
        <input type="number" v-model="productIdupdate" placeholder="상품 ID" />
      </div>
      <input type="text" v-model="productupdate.name" placeholder="상품 이름" />
      <div class="d-flex">
        <p>수량</p>
        <input type="number" v-model.number="productupdate.amount" placeholder="수량" />
      </div>
      <div class="d-flex">
        <p>가격</p>
        <input type="number" v-model.number="productupdate.price" placeholder="가격" />
      </div>
      <input type="text" v-model="productupdate.imageUrl" placeholder="don't touch this holder" />
      <textarea v-model="productupdate.content" placeholder="상품 설명"></textarea>
      <label>
        사용 가능:
        <input type="checkbox" v-model="productupdate.isAvailable" />
      </label>
      <button type="submit">업데이트</button>
    </form>

    <!-- //-----------상품 수정 이미지제외----------------------------// -->

    <!-- //-----------상품 이미지 수정----------------------------// -->
    <hr />
    <br />
    <hr />

    <h1>상품 이미지 업데이트</h1>
    <form @submit.prevent="submitFormimageupdate">
      <input type="number" v-model="productIdimageupdate" placeholder="상품 ID" />
      <input type="text" v-model="existingImageUrl" placeholder="기존 이미지 URL" />
      <input type="file" ref="fileInputimageupdate" />
      <button type="submit">업데이트</button>
    </form>

    <!-- //-----------상품 이미지 수정----------------------------// -->
  </main>
</template>

<script setup lang="ts">


// ==============================유저 사진 수정=============================



import { ref } from 'vue'
import axios from 'axios'
import type { Goods } from '@/common/types/index'
// ----------상품 상세조회----------------------------//

const productgetById = ref<Goods>()
const inputProductId = ref(0)

const getProductById = async (productId: number) => {
  try {
    const response = await axios.get(`http://i10d106.p.ssafy.io:8080/product/getById/${productId}`)
    if (response.data.resultCode === 'SUCCESS') {
      productgetById.value = response.data.data
    } else {
      console.error(response.data.message)
      console.log(response.data)
      productgetById.value = response.data.data
    }
  } catch (error) {
    console.error('상품 조회 중 오류 발생', error)
  }
}

const submitFormGetProductById = () => {
  getProductById(inputProductId.value)
}
// ----------상품 상세 조회----------------------------//

//-----------전체 상품 조회----------------------------//

const productgetByStarName = ref<Goods[]>([])
const inputStarName = ref('')

const getProductsByStarName = async (starName: string) => {
  try {
    const response = await axios.get(`http://i10d106.p.ssafy.io:8080/product/getByStarName/${starName}`)
    if (response.data.resultCode === 'SUCCESS') {
      productgetByStarName.value = response.data.data
    } else {
      console.error(response.data.message)
      productgetByStarName.value = response.data.data
    }
  } catch (error) {
    console.error('스타의 상품 목록 조회 중 오류 발생', error)
  }
}

const submitFormgetByStarName = () => {
  getProductsByStarName(inputStarName.value)
}

  //-----------전체 상품 조회----------------------------//

//-----------상품 삭제----------------------------//

const productdelete = ref(null)
const inputProductIdDelete = ref(1)

const getProductdelete = async (productId: number) => {
  try {
    const response = await axios.post(`http://i10d106.p.ssafy.io:8080/product/delete/${productId}`)
    if (response.data.resultCode === 'SUCCESS') {
      productdelete.value = response.data.data
    } else {
      console.error(response.data.message)
      productdelete.value = response.data.data
    }
  } catch (error) {
    console.error('스타의 상품 목록 조회 중 오류 발생', error)
  }
}

const submitFormgetdelete = () => {
  getProductdelete(inputProductIdDelete.value)
}
//-----------상품 삭제----------------------------//

//-----------상품 생성----------------------------//

import TiptapTest from '@/components/StarMenu/Store/TiptabTest.vue'

const createProduct = async (productInfo: any, fileInputs: File[]) => {
  try {
    const formData = new FormData()
    formData.append('productDto', JSON.stringify(productInfo))
    fileInputs.forEach((file) => {
      formData.append('files', file)
    })

    const response = await axios.post('http://i10d106.p.ssafy.io:8080/product/create', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    if (response.status === 200) {
      alert('상품이 성공적으로 생성되었습니다.')
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
  } else {
    alert('파일을 선택해주세요.')
  }
}

import { onMounted, watch } from 'vue'

const productcreate = ref({
  starName: '',
  name: '',
  amount: 0,
  price: 0,
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
  event.preventDefault()
  if (event.dataTransfer) {
    files.value = Array.from(event.dataTransfer.files)
  }
}

const handleFileUpload = (event: Event) => {
  const target = event.target as HTMLInputElement
  if (target.files) {
    files.value = Array.from(target.files)
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
//-----------상품 생성----------------------------//

//-----------상품 수정 이미지제외----------------------------//

const productIdupdate = ref(0)
const productupdate = ref({
  starId: 99,
  starName: 'nono',
  name: '',
  amount: 0,
  price: 0,
  imageUrl: [],
  content: '',
  isAvailable: false
})

const updateProduct = async (productIdupdate: number, updatedProductDto: any) => {
  try {
    const response = await axios.post(
      `http://i10d106.p.ssafy.io:8080/product/update/${productIdupdate}`,
      updatedProductDto
    )
    if (response.data === 'SUCCESS') {
      alert('상품이 성공적으로 업데이트되었습니다.')
    } else {
      alert('상품 업데이트에 실패했습니다.')
      console.log(response.data)
    }
  } catch (error) {
    console.error('상품 업데이트 중 오류 발생', error)
  }
}

const submitFormupdate = () => {
  updateProduct(productIdupdate.value, productupdate.value)
}

//-----------상품 수정 이미지제외----------------------------//

//-----------상품 이미지 수정----------------------------//

const productIdimageupdate = ref(0)
const existingImageUrl = ref('')
const fileInputimageupdate = ref()

const updateProductImage = async (
  productIdimageupdate: number,
  existingImageUrl: string,
  file: File
) => {
  try {
    const formData = new FormData()
    formData.append('existingImageUrl', existingImageUrl)
    formData.append('file', file)

    const response = await axios.post(
      `http://i10d106.p.ssafy.io:8080/product/updateImg/${productIdimageupdate}`,
      formData,
      {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
    )
    if (response.status === 200) {
      alert('상품 이미지가 성공적으로 업데이트되었습니다.')
    } else {
      alert('상품 이미지 업데이트에 실패했습니다.')
    }
  } catch (error) {
    console.error('상품 이미지 업데이트 중 오류 발생', error)
  }
}

const submitFormimageupdate = () => {
  if (fileInputimageupdate.value && fileInputimageupdate.value.files.length > 0) {
    updateProductImage(
      productIdimageupdate.value,
      existingImageUrl.value,
      fileInputimageupdate.value.files[0]
    )
  } else {
    alert('이미지 파일을 선택해주세요.')
  }
}


  //-----------상품 생성----------------------------//

//-----------상품 개별 이미지 수정, 삭제, 추가 ----------------------------//
const uploadImage = async (productId: number) => {
  try {
    // 파일 선택하기
    const file = await selectFile2()
    if (!file) return

    // FormData 생성
    const formData = new FormData()
    formData.append('file', file)

    // Axios 요청 보내기
    const response = await axios.post(`http://i10d106.p.ssafy.io:8080/s3/upload/${productId}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })

    // 업로드 후 결과 처리 (예: 새로고침, 상태 업데이트 등)
    console.log('업로드 성공:', response.data)
    getProductById(productId)
  } catch (error) {
    console.error('업로드 실패:', error)
  }
}

// 파일 선택 함수
const selectFile2 = () => {
  return new Promise<File | null>((resolve) => {
    const input = document.createElement('input')
    input.type = 'file'
    input.onchange = () => {
      resolve(input.files ? input.files[0] : null)
    }
    input.click()
  })
}

const updateImage = async (productId: number, imageId: number) => {
  try {
    // 파일 선택하기
    const file = await selectFile()
    if (!file) return

    // FormData 생성
    const formData = new FormData()
    formData.append('file', file)

    // Axios 요청 보내기
    await axios.post(`http://i10d106.p.ssafy.io:8080/s3/update/${productId}/${imageId}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })

    getProductById(productId)
  } catch (error) {
    console.error('업데이트 실패:', error)
  }
}

const deleteImage = async (imageId: number, productId: number) => {
  try {
    await axios.post(`http://i10d106.p.ssafy.io:8080/s3/delete/${imageId}`)
    getProductById(productId)
  } catch (error) {
    console.error('이미지 삭제 실패:', error)
  }
}

const selectFile = () => {
  return new Promise<File | null>((resolve) => {
    const input = document.createElement('input')
    input.type = 'file'
    input.onchange = () => {
      resolve(input.files ? input.files[0] : null)
    }
    input.click()
  })
}

//-----------상품 개별 이미지 수정 및 삭제----------------------------//
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
