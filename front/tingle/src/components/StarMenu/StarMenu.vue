<template>
  <ul v-if="!store.isStar" class="d-flex justify-content-around nav nav-underline">
    <li class="nav-item">
      <RouterLink
        :to="`/${props.id}/home`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/home') }"
        >Home</RouterLink
      >
    </li>
    <li class="nav-item">
      <RouterLink
        :to="`/${props.id}/snapshot`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/snapshot') }"
        >Snapshot</RouterLink
      >
    </li>
    <li class="nav-item">
      <RouterLink
        :to="`/${props.id}/wish`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/wish') }"
        >Wish</RouterLink
      >
    </li>
    <!--  -->
    <li class="nav-item">
      <RouterLink
        :to="`/${props.id}/store/user`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/store') }"
        >Store</RouterLink
      >
    </li>
    <!--  -->
    <li class="nav-item">
      <RouterLink
        :to="`/${props.id}/fanmeeting`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/fanmeeting') }"
        >Fanmeeting</RouterLink
      >
    </li>
    <li class="nav-item">
      <RouterLink
        :to="`/profile/${props.id}/teststore`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/teststore') }"
        >Test스토어Product</RouterLink
      >
    </li>
    <li class="nav-item">
      <RouterLink
        :to="`/profile/${props.id}/teststore2`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/teststore2') }"
        >Test스토어Order</RouterLink
      >
    </li>
    <li class="nav-item">
      <RouterLink
        :to="`/${store.fanState.id}/profile`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/profileview') }"
        >Profile</RouterLink
      >
    </li>
  </ul>

  <ul v-else class="d-flex justify-content-around nav nav-underline">
    <li class="nav-item">
      <RouterLink
        :to="`/${props.id}/home`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/home') }"
        >Home</RouterLink
      >
    </li>
    <li class="nav-item">
      <RouterLink
        :to="`/${props.id}/snapshot`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/snapshot') }"
        >Snapshot</RouterLink
      >
    </li>
    <li class="nav-item">
      <RouterLink
        :to="`/${props.id}/wish`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/wish') }"
        >Wish</RouterLink
      >
    </li>
    <!--  -->
    <li class="nav-item">
      <RouterLink
        :to="`/${props.id}/store/star`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/store') }"
        >Store</RouterLink
      >
    </li>
    <!--  -->
    <li class="nav-item">
      <RouterLink
        :to="`/${props.id}/fanmeeting`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/fanmeeting') }"
        >Fanmeeting</RouterLink
      >
    </li>
    <li class="nav-item">
      <RouterLink
        :to="`/profile/${props.id}/teststore`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/teststore') }"
        >Test스토어Product</RouterLink
      >
    </li>
    <li class="nav-item">
      <RouterLink
        :to="`/profile/${props.id}/teststore2`"
        class="nav-link router-link-custom"
        :class="{ active: isActive('/teststore2') }"
        >Test스토어Order</RouterLink
      >
    </li>
  </ul>
</template>

<script setup lang="ts">
import { defineProps } from 'vue'
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'

const store = useUserStore()
const route = useRoute()


const isActive = (path: string) => {
  return route.path.includes(path)
}

const props = defineProps(['id'])
</script>

<style scoped></style>
