<template>
  <main v-if="!store.isStar && store.isLogin" class="main-color">
    <RouterView />
  </main>
  <main v-if="store.isStar" class="main-color2">
    <RouterView />
  </main>
  <main v-if="!store.isLogin" class="main-color3">
    <RouterView />
  </main>
</template>

<script setup lang="ts">
import { RouterView } from 'vue-router'
import { useUserStore } from './stores/user'

const store = useUserStore()
</script>

<style>
.main-color {
  background-color: #f1eaff;
}

.main-color2 {
  background-color: #ffc7c7;
}

.main-color3 {
  background-color: white;
}

main .container:first-of-type {
  min-height: 90vh;
}

.star-card {
  display: flex;
  height: 50px;
  align-items: center;
  margin: 1px;
  border-radius: 10px;
  /* background-color: blueviolet; */
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

.content {
  margin-top: 71px;
  margin-left: 64px;
  margin-right: 64px;
  transition: none;
}

/* .is-sidebar-open .content {
  margin-left: 240px;
  margin-right: 64px;
} */
</style>
