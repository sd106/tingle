<template>
  <div>
    <ul>
      <li v-for="snapshot in snapshots" :key="snapshot.id" @click="goToDetail(snapshot.id)">
        <img :src="snapshot.imageUrl" alt="Snapshot Image">
        <p>{{ snapshot.username }}</p>
      </li>
    </ul>
    <button @click="goToCreate">Create Snapshot</button>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';

type SnapshotType = {
  id: number;
  imageUrl: string;
  username: string;
};

const store = useUserStore();

const snapshots = ref<SnapshotType[]>([]);
const router = useRouter();

const loadSnapshots = async (): Promise<void> => {
  try {
    await axios.get('http://localhost:8080/snapshot/')
    .then((res) => {
    
        snapshots.value = res.data.AllSnapShot;
        console.log(snapshots.value);
      
    });
  } catch (error) {
    console.error(error);
  }
};

onMounted(() => {
  loadSnapshots();
});

const goToCreate = () => {
  router.push(`/${store.starInfo?.starId}/snapshot/create`);
};

const goToDetail = (id: number) => {
  router.push(`/snapshot/${id}`);
};
</script>