<template>
  <main class="container">
    <StarMenu :id="id" />

    <!-- 입력 Form -->
    <div class="tw-h-24 tw-w-24">
      🌠
    </div>
    <div class="tw-p-4 tw-max-w-md tw-mx-auto">
      <form @submit.prevent="submitForm" class="tw-flex tw-flex-col tw-gap-4">
        <input type="number" id="userId" class="tw-hidden-input"> <!-- :value 바인딩 추가 -->
        <input type="number" id="starId" class="tw-hidden-input"> <!-- :value 바인딩 추가 -->
        <input type="number" id="status" class="tw-hidden-input"> <!-- value 속성으로 숫자값 지정 -->
        <input type="number" id="likedCount" class="tw-hidden-input"> <!-- value 속성으로 숫자값 지정 -->

        <div class="tw-flex tw-flex-col tw-gap-2">
          <label for="points" class="tw-block tw-text-lg tw-font-bold tw-text-gray-700 tw-text-center">포인트</label>
          <input type="number" id="points" v-model="formData.points"
            class="tw-mt-1 tw-p-2 tw-border tw-border-gray-300 tw-rounded-md tw-shadow-sm" @focus="handleFocus"
            @blur="handleBlur">
        </div>

        <div class="tw-flex tw-flex-col tw-gap-2">
          <label for="contents" class="tw-block tw-text-lg tw-font-bold tw-text-gray-700 tw-text-center">내용</label>
          <textarea id="contents" v-model="formData.contents" required
            class="tw-mt-1 tw-p-2 tw-border tw-border-gray-300 tw-rounded-md tw-shadow-sm tw-h-32"></textarea>
        </div>

        <button type="submit"
          class="tw-mt-4 tw-py-2 tw-px-4 tw-bg-blue-500 tw-text-white tw-font-bold tw-rounded-md hover:tw-bg-blue-700">등록</button>
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

// input창 0남는거 거슬려서
const handleFocus = (event: FocusEvent) => {
  const target = event.target as HTMLInputElement;
  if (target.value == '0') {
    target.value = ''; // 값이 0이면 입력 필드를 비웁니다.
  }
};

const handleBlur = (event: FocusEvent) => {
  const target = event.target as HTMLInputElement;
  if (target.value === '') {
    formData.value.points = 0; // 사용자가 아무것도 입력하지 않았다면 값을 0으로 재설정합니다.
  }
};

</script>

<style scoped>
.hidden-input {
  display: none;
}
</style>