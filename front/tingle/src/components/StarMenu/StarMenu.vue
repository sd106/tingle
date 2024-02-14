<template>
  <!--  유저일 때 -->
  <ul v-if="!store.isStar" class="d-flex justify-content-around nav nav-underline">
    <li class="nav-item">
      <RouterLink
        :to="`/${starId}/home`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/home') }"
        >Home</RouterLink
      >
    </li>
    <li class="nav-item">
      <RouterLink
        :to="`/${starId}/snapshot`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/snapshot') }"
        >Snapshot</RouterLink
      >
    </li>
    <li class="nav-item">
      <RouterLink
        :to="`/${starId}/wish`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/wish') }"
        >Wish</RouterLink
      >
    </li>
    <li class="nav-item">
      <RouterLink
        :to="`/${starId}/store/user`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/store') }"
        >Store</RouterLink
      >
    </li>
    <li class="nav-item">
      <RouterLink
        :to="`/${starId}/fanmeeting`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/fanmeeting') }"
        >Fanmeeting</RouterLink
      >
    </li>
  </ul>
  <!-- 스타일 때 -->
  <ul v-else class="d-flex justify-content-around nav nav-underline">
    <li class="nav-item">
      <RouterLink :to="`/forstar/home/manage`" class="nav-link router-link-custom">Home</RouterLink>
    </li>
    <li class="nav-item">
      <RouterLink
        :to="`/forstar/snapshot`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/snapshot') }"
        >Snapshot</RouterLink
      >
    </li>

    <li class="nav-item">
      <RouterLink
        :to="`/forstar/wish/manage/`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/wish') }"
        >Wish</RouterLink
      >
    </li>
    <li class="nav-item">
      <RouterLink
        :to="`/forstar/store/`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/store') }"
      >
        Store</RouterLink
      >
    </li>
    <li class="nav-item">
      <RouterLink
        :to="`/forstar/fanmeeting`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/fanmeeting') }"
        >Fanmeeting</RouterLink
      >
    </li>
  </ul>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'

const props = defineProps(['id'])
const starId = ref<number>()
starId.value = Number(props.id)

const store = useUserStore()
const route = useRoute()

const isActive = (path: string) => {
  return route.path.includes(path)
}
</script>

<style scoped></style>
