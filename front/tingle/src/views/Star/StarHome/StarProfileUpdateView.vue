<template>
  <main class="container">
    <StarMenu :id="starId" />
  1 {{ starId}}
    <div class="tw-p-8">
      <h1 class="tw-text-2xl tw-font-bold tw-mb-8">스타 프로필 수정</h1>
      <img :src="starProfile?.profileImage" alt="사진" class="w-75 h-75">
      <div>

        <!-- 상품 정보 입력 폼 -->
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

      </div>
        <TiptapTest />


      <!-- 상품 생성 버튼 -->
<!--      <button @click="submitForm" class="tw-btn tw-btn-primary tw-mt-4">프로필 수정</button>-->
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

const store = useUserStore();

const props = defineProps({
  starId: string
})

const starId= props.starId;

import { onMounted, watch } from 'vue'
import type { StarProfile } from '@/common/types'

const router = useRouter()
const starProfile = ref<StarProfile>();

const getstarProfile = async () => {
  const response = await axios.get(`${store.API_URL}/home/profile/${starId}`);
  starProfile.value = response.data.data;
  console.log(starProfile.value);
}

const files = ref<File[]>([])
const previewFiles = ref<string>()
const dragArea = ref<HTMLElement | null>(null)
const fileInput = ref<HTMLInputElement | null>(null)


//  업데이트
onMounted(() => {
  getstarProfile();

  if (dragArea.value) {
    dragArea.value.addEventListener('dragover', handleDragOver)
  }
})


// watch(files, (newFiles) => {
//   previewFiles.value = newFiles.map((file) => URL.createObjectURL(file))
// })

const handleDragOver = (event: DragEvent) => {
  event.preventDefault()
}


// const submitForm = () => {
//   if (files.value.length > 0 && previewFiles.value.length > 0) {
//     console.log('첫번째 이프')
//     //updateProduct(product.value, files.value, previewFiles.value)
//   } else if (previewFiles.value.length > 0 && files.value.length === 0) {
//     console.log('두번째 이프')
//     //updateProductWithOutFile(product.value, previewFiles.value)
//   } else if (previewFiles.value.length === 0 && files.value.length === 0) {
//     console.log('세번째 이프')
//     //updateProductWithPreview(product.value)
//   }
// }




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
</style>
