<template>
    <div v-if="snapshot">
      <img :src="snapshot.imageUrl" alt="Snapshot Image">
      <h3>{{ snapshot.username }}</h3>
      <p>{{ snapshot.content }}</p>
      <ul>
        <li v-for="tag in snapshot.tags" :key="tag">{{ tag }}</li>
      </ul>
      <!-- 댓글 관련 내용 -->
      <button @click="goToUpdate(snapshot.snapshotId)">Edit</button>
      <button @click="deleteSnapshot(snapshot.snapshotId)">Delete</button>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue';
  import axios from 'axios';
  import { useRouter, useRoute } from 'vue-router';
  

  type SnapshotType = {
    imageUrl: string;
    username: string;
    content: string;
    tags: string[];
    snapshotId: number;
  };



  const snapshot = ref<SnapshotType | null>(null);
  const router = useRouter();
  const route = useRoute();
  const snapshotId = route.params.snapshotId;
  
  onMounted(async () => {
    try {
      const response = await axios.get(`/snapshot/${snapshotId}`);
      snapshot.value = response.data;
      console.log(snapshot.value)
    } catch (error) {
      console.error(error);
    }
  });
  
  const goToUpdate = (id: number) => {
    router.push(`/snapshot/${id}/update`);
  };

  const deleteSnapshot = async (id: number) => {
    try {
        await axios.post(`/snapshot/${id}/delete`);
        router.push('/');
    } catch (error) {
        console.error(error);
    }
  };
  </script>