<template>
  <AppHeader />
  <AppSidebar />
  <div class="content" :class="{ 'is-sidebar-open': store.isSidebarOpen }">
    <RouterView />
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue';
import { RouterView } from 'vue-router'
import { useUserStore } from '@/stores/user';

import AppHeader from '@/components/Layouts/AppHeader.vue';
import AppSidebar from '@/components/Layouts/AppSidebar.vue';

const store = useUserStore()


onMounted(() => {
  store.getStarInfo()
  if (store.isSidebarOpen) {
    document.body.classList.add('is-sidebar-open');
  }
})
</script>



<style>
.star-card {
  display: flex;
  height: 50px;
  align-items: center;
  margin: 1px;
  border-radius: 10px;
}

.star-card:hover {
  background-color: rgb(230, 230, 230);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0.3);
}

.star-image {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  overflow: hidden;
  margin: 5px;
}

.star-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.arrow-image {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  overflow: hidden;
  margin-left: 5px;
  margin-right: 5px;
  margin-top: 7px;
  margin-bottom: 3px;
}

.arrow-image img {
  width: 55%;
  height: 55%;
  object-fit: cover;
}

.offcanvas {
  transition: none;
  /* 애니메이션을 없애는 스타일 */
}

main {
  margin-top: 71px;
}

.content {
  margin-left: 64px;
  margin-right: 64px;
  transition: none;
}

.is-sidebar-open .content {
  margin-left: 240px;
  margin-right: 64px;
}
</style>
