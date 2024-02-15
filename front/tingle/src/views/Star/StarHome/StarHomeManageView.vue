<template>
  <main class="container">
    <StarMenu :id="starId" />

    <div v-if="starProfile?.banner">
      <img :src="starProfile.banner" alt="사진" />
    </div>
    <div class="container border d-flex">
      <div class>
        <img :src="starProfile?.profileImage" alt="사진" class="w-75 h-75" />
      </div>
      <div>
        <div>{{ starProfile?.username }}</div>
        <div v-if="!isEdit">
          {{
            starProfile?.category === -1
              ? '카테고리'
              : starProfile?.category === 0
                ? '일상/토크'
                : starProfile?.category === 1
                  ? '동물'
                  : starProfile?.category === 2
                    ? '게임/스포츠'
                    : starProfile?.category === 3
                      ? '미술/음악'
                      : starProfile?.category === 4
                        ? '뷰티/패션'
                        : starProfile?.category === 5
                          ? '기타'
                          : ''
          }}
        </div>
        <div v-else>
          <select v-model="starProfile!.category">
            <option value="-1">카테고리</option>
            <option value="0">일상/토크</option>
            <option value="1">동물</option>
            <option value="2">게임/스포츠</option>
            <option value="3">미술/음악</option>
            <option value="4">뷰티/패션</option>
            <option value="5">기타</option>
          </select>
        </div>

        <div v-if="!isEdit">{{ starProfile?.snsUrl }}</div>
        <div v-else>
          <input v-model="editUsername" />
        </div>
      </div>
      <div class>
        <button @click="startEdit" class="tw-btn m-2">{{ buttonText }}</button>
        <StarProfileUpdateDeleteVue />
      </div>
    </div>

    <div class="container border" v-show="showInputArticle">
      <input type="file" id="image-upload" multiple />
      <input v-model="homeCreateRequest.content" placeholder="입력해주세요" />
      <button @click="insertArticle()">완료</button>
    </div>

    {{ article }}
    <draggable v-if="article.length > 0" v-model="article" class="drag-area" item-key="id">
      <template v-slot:item="{ item }">
        <div class="item-container">
          <div v-if="item.content !== ''">{{ item.content }}</div>
          <div v-if="item.homePictureDtos.length > 0">
            <img
              v-for="picture in item.homePictureDtos"
              :src="picture.image"
              :alt="'Image for article ' + item.id"
              :key="picture.homeId"
              class="article-image"
            />
          </div>
        </div>
      </template>
    </draggable>

    <div v-if="showTextarea">
      <textarea
        id="textarea"
        class="textarea textarea-bordered"
        placeholder="입력해주세요"
        v-model="textContent"
      ></textarea>
      <button @click="insertPencil">완료</button>
    </div>

    <div v-if="showImage">
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
      <button @click="insertPhotos">완료</button>
    </div>
  </main>

  <div class="fixed-button">
    <div style="display: inline-flex">
      <img src="/image/Homephotos.png" @click="showImage = true" />
      <img src="/image/Homepencil.png" @click="showTextarea = true" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import axios from 'axios'
import { useUserStore } from '@/stores/user'
const store = useUserStore()
let showInputArticle = ref(false)

//////////////////////////////////////////////////////////////////////
// drag try
import Draggable from 'vue3-draggable'

//////////////////////////////////////////////////////////////////////

const isEdit = ref(false)
const editUsername = ref('')

import StarMenu from '@/components/StarMenu/StarMenu.vue'
import StarProfileUpdateDeleteVue from '@/components/StarMenu/StarHome/StarProfileUpdateDelete.vue'

import type { StarProfile, HomeArticle } from '@/common/types/index'

const starId = store.starState!.id
const buttonText = ref('수정')
const starProfile = ref<StarProfile>()

const article = ref<HomeArticle[]>([])
let files = ref<File[]>([])

const showTextarea = ref(false)
const showImage = ref(false)

const textContent = ref('')

const dragArea = ref<HTMLElement | null>(null)
const fileInput = ref<HTMLInputElement | null>(null)

const getstarProfile = async () => {
  const response = await axios.get(`${store.API_URL}/home/profile/${starId}`)
  starProfile.value = response.data.data
  console.log(starProfile.value)
}

const getArticle = async () => {
  axios
    .get(`${store.API_URL}/home/${starId}`)
    .then((response) => {
      console.log(response.data.data)
      article.value = response.data.data
    })
    .catch((error) => {
      console.error(error)
    })
}

const handleFileSelection = (event: any) => {
  files.value = event.target.files
}

let homeCreateRequest = {
  starId: starId,
  ordering: 3,
  content: '입력해주세요'
}

const insertArticle = async () => {
  let formData = new FormData()
  formData.append('homeCreateRequest', JSON.stringify(homeCreateRequest)) // JSON 문자열로 변환하여 추가

  // 파일이 있을 경우에만 추가
  if (files && files.value.length > 0) {
    for (let i = 0; i < files.value.length; i++) {
      formData.append('files', files.value[i])
    }
  }

  axios
    .post(`${store.API_URL}/home/post`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    .then((response) => {
      getArticle()
      showInputArticle.value = false
      console.log(response.data)
    })
    .catch((error) => {
      console.error(error)
    })
}

let homeUpdateRequest = {
  homeId: 1,
  ordering: 3,
  content: '입력해주세요'
}

const deleteArticle = async (homeid: number) => {
  axios
    .delete(`${store.API_URL}/home/delete/${homeid}`)
    .then((response) => {
      getArticle()
      console.log(response.data)
    })
    .catch((error) => {
      console.error(error)
    })
}

const IsInputArticle = () => {
  showInputArticle.value = true
}

function startEdit() {
  if (!isEdit.value) {
    isEdit.value = true
    editUsername.value = starProfile.value?.snsUrl || ''
    buttonText.value = '완료'
  } else {
    //수정을 완료함

    insertProfile()
    isEdit.value = false
    buttonText.value = '수정'
  }
}

import { useRouter } from 'vue-router'
const router = useRouter()
const insertProfile = async () => {
  await axios
    .post(`${store.API_URL}/home/profile/${starId}`, {
      category: starProfile.value?.category,
      snsUrl: editUsername.value
    })
    .then((response) => {
      console.log(response.data)
      getstarProfile()
    })
    .catch((error) => {
      console.error(error)
    })
}

let photos = {
  starId: starId,
  ordering: 2,
  content: ''
}

const insertPhotos = async () => {
  let formData = new FormData()
  formData.append('homeCreateRequest', JSON.stringify(photos)) // JSON 문자열로 변환하여 추가

  // 파일이 있을 경우에만 추가
  if (files && files.value.length > 0) {
    for (let i = 0; i < files.value.length; i++) {
      formData.append('files', files.value[i])
    }
  }

  await axios
    .post(`${store.API_URL}/home/post/picture`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    .then((response) => {
      showImage.value = false
      getArticle()
      console.log(response.data)
    })
    .catch((error) => {
      console.error(error)
    })
}

const insertPencil = async () => {
  await axios
    .post(`${store.API_URL}/home/post/pencil`, {
      starId: starId,
      ordering: 1,
      content: textContent!.value
    })
    .then((response) => {
      console.log(response.data)
      showTextarea.value = false
      getArticle()
    })
    .catch((error) => {
      console.error(error)
    })
}

const previewFiles = ref<string[]>([])

watch(files, (newFiles) => {
  previewFiles.value = newFiles.map((file) => URL.createObjectURL(file))
})

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

const removeFile = (index: number) => {
  // 미리보기 URL 배열에서 해당 항목 제거
  previewFiles.value.splice(index, 1)

  // 실제 파일 배열에서도 해당 항목 제거
  files.value.splice(index, 1)
}

onMounted(() => {
  getstarProfile()
  getArticle()
})
</script>

<style>
.item-container {
  border: 1px solid black;
  padding: 10px;
  margin-bottom: 10px;
}

.container {
  margin-bottom: 10px;
}

.border {
  border: 1px solid black;
  padding: 10px;
}

.item-wrapper {
  width: 90%;
  margin: 30px auto;
}

.fixed-button {
  position: fixed;
  bottom: 10px;
  /* 버튼을 화면 밑에서 얼마나 떨어지게 할지 설정 */
  right: 10px;
  /* 버튼을 화면 오른쪽에서 얼마나 떨어지게 할지 설정 */
}

.fixed-button img {
  width: 100px;
  height: auto;
}

.item-container {
  position: relative;
}

.item-header {
  position: absolute;
  top: 0;
  right: 0;
}

.item-button {
  margin: 0 5px;
}
</style>
