<template>
  <main>
    <h1>프로필</h1>

    <!-- 메뉴 버튼 -->
    <div>
      <RouterLink :to="`/profile/${username}/home`">Home</RouterLink> |
      <RouterLink :to="`/profile/${username}/snapshot`">Snapshot</RouterLink> |
      <RouterLink :to="`/profile/${username}/wish`">Wish</RouterLink> |
      <RouterLink :to="`/profile/${username}/store`">Store</RouterLink> |
      <RouterLink :to="`/profile/${username}/fanmeeting`">Fanmeeting</RouterLink>
    </div>
    <div>

    </div>
  </main>
  <RouterView />
</template>

<script setup lang="ts">
import { onMounted } from 'vue';
import { RouterLink, RouterView, useRouter } from 'vue-router'

const router = useRouter()
const props = defineProps(['username']);

onMounted(() => {
  console.log(router.currentRoute.value.params.username)
  console.log(props)
})

</script>
