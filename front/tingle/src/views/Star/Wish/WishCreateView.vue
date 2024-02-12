<template>
  <main class="container">
    <StarMenu :id="id" />

    <!-- 입력 Form -->
    <div>
      <h1>Review 폼</h1> <br>
      <form @submit.prevent="submitForm">
        <input type="number" id="userId" class="hidden-input"> <!-- :value 바인딩 추가 -->
        <input type="number" id="starId" class="hidden-input"> <!-- :value 바인딩 추가 -->
        <input type="number" id="status" class="hidden-input"> <!-- value 속성으로 숫자값 지정 -->
        <input type="number" id="likedCount" class="hidden-input"> <!-- value 속성으로 숫자값 지정 -->

        <label for="points">포인트:</label>
        <input type="number" id="points" v-model="formData.points" required> <br>

        <label for="contents">내용:</label>
        <textarea id="contents" v-model="formData.contents" required></textarea> <br>

        <button type="submit">전송</button>
      </form>
    </div>


  </main>
</template>

<script setup lang="ts">
import { ref, defineProps } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';
import type { WishInfo } from '@/common/types';
import axios from 'axios';
import StarMenu from '@/components/StarMenu/StarMenu.vue';

const store = useUserStore();

const props = defineProps(['id']);
const id = ref(props.id);

const router = useRouter();

const formData = ref<WishInfo>({
  id: 0,
  userId: id.value,
  starId: id.value,
  status: 0,
  points: 0,
  likedCount: 0,
  contents: '',
  createTime: new Date(),
  deleteTime: new Date(),
});

const isModalOpen = ref(false);

// 위시 등록
const submitForm = () => {
  // 위시 등록 함수 호출
  createWish(formData.value);

  console.log('폼이 제출되었습니다.');
  isModalOpen.value = true;
};

// 위시 등록
const createWish = async function (payload: WishInfo): Promise<void> {

  try {
    const res = await axios.post(`${store.API_URL}/wish/save`, payload);
    console.log(res);
    router.push(`/${id.value}/wish`);
  } catch (error) {
    console.error('Error saving wish:', error);
  }
};

</script>

<style scoped>
.hidden-input {
  display: none;
}
</style>