<template>
<ul v-if="!store.isStar" class="d-flex justify-content-around nav nav-underline">
    <li class="nav-item">
      <RouterLink
        :to="`/profile/userinfo`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/userInfo') }"
        >회원 정보</RouterLink
      >
    </li>
    <li class="nav-item">
      <RouterLink
        :to="`/profile/storage`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/storage') }"
        >보관함</RouterLink
      >
    </li>
    <li class="nav-item">
      <RouterLink
        :to="`/profile/orders`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/orders') }"
        >주문 목록</RouterLink
      >
    </li>
  </ul>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'
const isActive = (path: string) => {
  return route.path.includes(path)
}

const store = useUserStore()
const route = useRoute()
</script>
