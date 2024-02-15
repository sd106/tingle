<template>
  <main class="container">
    <StarMenu :id="starId" />
    <div class="container border">
      <img :src="starProfile?.banner" alt="사진" />
    </div>
    <div class="container border">
      <!-- 프로필 사진 > 누르면 확대-->
      <div>
        <img :src="starProfile?.profileImage" alt="사진" />
        <!-- 프로필 수정, 삭제 컴포넌트 -->
        <div style="display: inline-block">
          <!-- 닉네임 -->
          <p>닉네임 {{ starProfile?.snsUrl }}</p>
          <!-- 구독 여부 창 -->
          <div>
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

          <button @click="checkFollow">{{ buttonText }}</button>
          <!-- sns주소 링크 -->
        </div>
      </div>

      <p>sns 주소 {{ starProfile?.username }}</p>
    </div>

    <draggable v-model="article" class="drag-area" item-key="id">
      <template v-slot:item="{ item }">
        <div class="item-container">
          <div>check</div>
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
  </main>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { RouterLink } from 'vue-router'
import axios from 'axios'

import { useUserStore } from '@/stores/user'
const store = useUserStore()

import StarMenu from '@/components/StarMenu/StarMenu.vue'
import type { StarProfile, HomeArticle } from '@/common/types/index'

const props = defineProps(['id'])
const starId = ref(props.id)

const starProfile = ref<StarProfile>()

let isGood = ref()
const buttonText = ref('')

const article = ref<HomeArticle[]>([])

const checkFollow = async () => {
  //처음에 팔로잉 여부 체크
  if (isGood.value === undefined) {
    isGood.value = await isFollow()
    console.log(isGood.value)
    buttonText.value = isGood.value ? '구독중' : '구독'
  } else {
    if (isGood.value) {
      //팔로잉을 이미 한 상태였다면
      await unFollowing()
      isGood.value = false
      buttonText.value = '구독'
    } else {
      //팔로잉을 안한 상태였다면
      await following()
      isGood.value = true
      buttonText.value = '구독중'
    }
  }
}

const isFollow = async () => {
  try {
    const response = await axios.get(`${store.API_URL}/follow/isFollowing`, {
      params: {
        userId: 1,
        starId: starId.value
      }
    })
    console.log(response)
    isGood.value = response.data.data
    return response.data.data
  } catch (error) {
    console.error(error)
    return false
  }
}

const unFollowing = async () => {
  try {
    const response = await axios.delete(`${store.API_URL}/follow/delete`, {
      data: {
        userId: 1,
        starId: starId.value
      }
    })
    console.log(response)
    isGood.value = response.data.data
    return response.data.data
  } catch (error) {
    console.error(error)
    return false
  }
}

const following = async () => {
  try {
    const response = await axios.post(`${store.API_URL}/follow/create`, {
      userId: 1,
      starId: starId.value
    })
    console.log(response)
    isGood.value = response.data.data
    return response.data.data
  } catch (error) {
    console.error(error)
    return false
  }
}

const getstarProfile = async () => {
  const response = await axios.get(`${store.API_URL}/home/profile/${starId.value}`)
  starProfile.value = response.data.data
  console.log(starProfile.value)
}

const getArticle = async () => {
  try {
    const response = await axios.get(`${store.API_URL}/home/${starId.value}`)
    article.value = response.data.data
  } catch (error) {
    console.error('Failed to fetch articles:', error)
    // 에러 발생 시 기본값 설정 또는 사용자 피드백
    article.value = []
  }
}

watch(article, (newVal, oldVal) => {
  console.log('Article data updated', newVal)
})

onMounted(() => {
  getArticle()
  checkFollow()
  getstarProfile()
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
</style>
