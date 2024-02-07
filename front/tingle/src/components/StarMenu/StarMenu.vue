<template>
    <ul class="d-flex justify-content-around nav nav-underline">
        <li class="nav-item">
            <RouterLink :to="`/${props.id}/home`" class="nav-link router-link-custom"
                :class="{ 'active': isActive('/home') }">Home</RouterLink>
        </li>
        <li class="nav-item">
            <RouterLink :to="`/${props.id}/snapshot`" class="nav-link router-link-custom"
                :class="{ 'active': isActive('/snapshot') }">Snapshot</RouterLink>
        </li>
        <li class="nav-item">
            <RouterLink :to="`/${props.id}/wish`" class="nav-link router-link-custom"
                :class="{ 'active': isActive('/wish') }">Wish</RouterLink>
        </li>
        <li class="nav-item">
            <RouterLink :to="`/${props.id}/store`" class="nav-link router-link-custom"
                :class="{ 'active': isActive('/store') }">Store</RouterLink>
        </li>
        <li class="nav-item">
            <RouterLink :to="`/${props.id}/fanmeeting`" class="nav-link router-link-custom"
                :class="{ 'active': isActive('/fanmeeting') }">Fanmeeting</RouterLink>
        </li>
        <li class="nav-item">
            <RouterLink :to="`/profile/${props.id}/teststore`" class="nav-link router-link-custom"
                :class="{ 'active': isActive('/teststore') }">Test스토어Product</RouterLink>
        </li>
        <li class="nav-item">
            <RouterLink :to="`/profile/${props.id}/teststore2`" class="nav-link router-link-custom"
                :class="{ 'active': isActive('/teststore2') }">Test스토어Order</RouterLink>
        </li>
    </ul>
</template>

<script setup lang="ts">
import { defineProps, watch } from 'vue'
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user';

const route = useRoute()

const isActive = (path: string) => {
    return route.path.includes(path);
};

const props = defineProps(['id']);
const store = useUserStore();

// props.id가 변경될 때마다 실행될 함수
watch(() => props.id, (newId, oldId) => {
  // Store의 starId 값을 업데이트
  store.starId = newId;
  // 필요한 경우 추가적인 로직을 여기에 구현할 수 있습니다.
});

</script>

<style scoped></style>