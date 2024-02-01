<template>
  <main class="row">
    <StarMenu :id="id" />
    <div class="container border col-12">
      <button>정렬1</button>
      <button>정렬2</button>
    </div>

    <div v-if="selectedSnapshot" class="off-canvas image-container">
      <img :src="selectedSnapshot.imageUrl" alt="Snapshot Image" class="snapshot-image">
      <h3>{{ selectedSnapshot.username }}</h3>
      <p>{{ selectedSnapshot.content }}</p>
      <ul>
        <li v-for="(tag, index) in selectedSnapshot.tags" :key="index">{{ tag }}</li>
      </ul>
      <button @click="goToUpdate(selectedSnapshot.snapshotId)">스냅샷 수정</button>
      <button @click="deleteSnapshot(selectedSnapshot.snapshotId)">스냅샷 삭제</button>

      <!-- 댓글 목록 -->
      <div v-for="comment in selectedSnapshot.comments" :key="comment.id" class="comment">
      <!-- 수정 중인 댓글의 UI 변경 -->
        <div v-if="editingCommentId === comment.id">
          <input type="text" v-model="editingCommentContent" />
          <button @click="submitCommentEdit(comment.id)">수정하기</button>
          <button @click="cancelEdit">취소</button>
        </div>
        <div v-else>
          <p>{{ comment.context }} | {{ comment.username }}</p>
          <button @click="startEditComment(comment)">댓글 수정</button>
          <button @click="deleteComment(comment.id)">댓글 삭제</button>
        </div>
      </div>

      <!-- 댓글 작성 폼 -->
      <form @submit.prevent="postComment">
        <input type="text" v-model="newCommentContent" placeholder="Write a comment...">
        <button type="submit">Post</button>
      </form>

    </div>

    <div class="col-12 container d-flex flex-wrap" ref="containerRef" @scroll="handleScroll"
         style="height: 480px; overflow-y: auto;">
      <div v-for="snapshot in snapshots" :key="snapshot.id" @click="selectSnapshot(snapshot.id)"
           class="p-2 d-flex flex-column align-items-center image-container" style="width: 20%;">
        <img :src="snapshot.imageUrl" alt="Snapshot Image" class="snapshot-image">
        <div class="star-name">{{ snapshot.username }}</div>
      </div>
    </div>

    <RouterLink :to="`/${store.starInfo?.starId}/snapshot/create`">글쓰기</RouterLink>
  </main>
</template>

<script setup lang="ts">
  import { ref, onMounted } from 'vue';
  import axios from 'axios';
  import { useRouter } from 'vue-router';
  import { useUserStore } from '@/stores/user';
  import StarMenu from '@/components/StarMenu/StarMenu.vue';
  import type { Starinfo } from '@/common/types';

  const store = useUserStore();
  const router = useRouter();
  const props = defineProps(['id']);
  const id = ref(props.id);
  const username = store.starInfo?.username;
  const starId = store.starInfo?.starId;

  type SnapshotType = {
    id: number;
    imageUrl: string;
    username: string;
  };

  type selectedSnapshotType = {
    snapshotId : number;
    imageUrl: string;
    username: string;
    starname: string;
    content: string;
    tags: string[];
    comments: CommentType[];
    likes: number;
    createdAt: string;
    updatedAt: string;
  }

  type CommentType = {
    id: number;
    context: string;
    username: string;
    snapshotId: number;
  };

  const snapshots = ref<SnapshotType[]>([]);
  const display = ref<Starinfo[]>([]);
  const containerRef = ref<HTMLElement | null>(null);

  const selectedSnapshot = ref<selectedSnapshotType | null>(null);

  const selectSnapshot = async (id: number) => {
    console.log("스냅샷 선택됨")
    try {
      const response = await axios.get(`http://localhost:8080/snapshot/${id}`);
      console.log(response.data)
      selectedSnapshot.value = response.data;
      console.log(selectedSnapshot.value?.snapshotId)
    
    } catch (error) {
      console.error(error);
    }
  };



  const loadSnapshots = async (): Promise<void> => {
    try {
      const response = await axios.get('http://localhost:8080/snapshot/');
      snapshots.value = response.data.AllSnapShot;
      console.log(snapshots.value);
    } catch (error) {
      console.error(error);
    }
  };

  const loadMore = function (): void {
    // 스크롤 관련 로딩 로직
    const newData = store.allstarinfo.slice(display.value.length, display.value.length + 10);
    display.value = [...display.value, ...newData];
  };

  const handleScroll = function (): void {
    // 스크롤 이벤트 처리 로직
    const container = containerRef.value;
    if (container) {
      // 스크롤이 하단에 도달했을 때 추가 데이터 로딩
      if (container.scrollTop + container.clientHeight >= container.scrollHeight) {
        loadMore();
      }
    }
  };

  onMounted(() => {
    loadSnapshots();
  });


  const goToUpdate = (id: number) => {
    if (selectedSnapshot.value) {
      // `router.push` 메소드에 객체를 전달하여 라우트 상태를 설정
      router.push({
        name: 'snapshotupdate', // 라우트 이름
        params: { starid: starId, snapshotid: id, file: selectedSnapshot.value.imageUrl, content: selectedSnapshot.value.content, tags: selectedSnapshot.value.tags }, // URL 파라미터
      });
    } else {
      console.error('No snapshot selected');
    }
  };


  const deleteSnapshot = async (id: number) => {
    console.log("삭제 시작할게요")
    try {
      const response = await axios.delete(`http://localhost:8080/snapshot/${id}/delete`);
      console.log(response.data); // 성공 응답 로그
      console.log("삭제 성공!");
      // 성공적으로 삭제 후 필요한 추가 작업을 여기에 작성하세요.
      router.go(0)
    } catch (error) {
      console.log("실패!");
    }
  };

  // 댓글 관련 코드

  const newCommentContent = ref(''); // 새 댓글 내용을 위한 반응형 변수

  // 수정 중인 댓글의 상태
  const editingCommentId = ref<number | null>(null);
  const editingCommentContent = ref('');


  // 새 댓글 작성
  const postComment = async () => {
    try {
      console.log(selectedSnapshot.value?.snapshotId)
      await axios.post(`http://localhost:8080/snapshot/${selectedSnapshot.value?.snapshotId}/comment/new`, {
        context: newCommentContent.value,
        username: username,
        snapshotId: selectedSnapshot.value?.snapshotId
        // 필요하다면 여기에 더 많은 필드 추가
      });
      newCommentContent.value = ''; // 입력 필드 초기화
      router.go(0)
      // 댓글 목록을 다시 불러오는 로직 필요
    } catch (error) {
      console.error(error);
    }
  };

  // 댓글 수정 시작
  const startEditComment = (comment: CommentType) => {
    editingCommentId.value = comment.id;
    editingCommentContent.value = comment.context;
  };

  // 수정 취소
  const cancelEdit = () => {
    editingCommentId.value = null;
    editingCommentContent.value = '';
  };

  // 수정된 댓글 전송
  const submitCommentEdit = async (commentId: number) => {
    try {
      const response = await axios.post(`http://localhost:8080/snapshot/${selectedSnapshot.value?.snapshotId}/comment/${commentId}/update`, {
        context: editingCommentContent.value,
        username: username,
        snapshotId: selectedSnapshot.value?.snapshotId
        // 기타 필요한 데이터
      });
      console.log(response.data);
      // 여기서 댓글 목록 갱신 로직 필요
      cancelEdit();
      router.go(0)
    } catch (error) {
      console.error(error);
    }
  };

  // 댓글 삭제
  const deleteComment = async (id : number) => {
    try {
      await axios.post(`http://localhost:8080/snapshot/${selectedSnapshot.value?.snapshotId}/comment/${id}/delete`);
      // 댓글 목록을 다시 불러오는 로직 필요
      router.go(0)
    } catch (error) {
      console.error(error);
    }
  };


</script>

<style>
.container::-webkit-scrollbar {
  display: none; /* 스크롤바 숨기기 */
}

.image-container img {
  width: 100%;       /* 이미지 컨테이너의 너비에 맞춥니다 */
  height: auto;     /* 모든 이미지의 높이를 150px로 설정합니다 */
  object-fit: cover; /* 이미지 비율을 유지하면서 컨테이너를 채웁니다 */
}

.off-canvas {
  /* 오프캔버스 스타일 */

  width: 300px; /* 상세 페이지의 너비 */
  height: 100%; /* 전체 높이 */
  background: white; /* 배경색 */
  z-index: 1000; /* 다른 요소 위에 표시 */
  /* 여기에 애니메이션 효과 등을 추가할 수 있습니다 */
}
</style>