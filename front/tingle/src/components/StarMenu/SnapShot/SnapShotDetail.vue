<template>
  <div class="off-canvas mx-0 ">

    <!-- 이미지 컨테이너 -->
    <div class="image-container">
      <img :src="props.selectedSnapshot?.imageUrl" alt="Snapshot Image" class="snapshot-image">
    </div>
    <div class="content-section">
      <!-- 본문 내용 -->
      <div class="content mx-0">
        <h3>{{ props.selectedSnapshot?.content }}</h3>
        <p>{{ props.selectedSnapshot?.username }}</p>
      </div>

      <!-- 태그 리스트 -->
      <ul class="tags-list">
        <li v-for="(tag, index) in props.selectedSnapshot?.tags" :key="index">{{ tag }}</li>
      </ul>

      <!-- 댓글 작성 폼 -->
      <form @submit.prevent="postComment" class="comment-form">
        <input type="text" v-model="newCommentContent" placeholder="Write a comment...">
        <button type="submit">Post</button>
      </form>

      <!-- 댓글 목록 -->
      <div class="comments-list">
        <div v-for="comment in props.selectedSnapshot?.comments" :key="comment.id" class="comment">
          <!-- 수정 중인 댓글의 UI 변경 -->
          <div v-if="editingCommentId === comment.id">
            <input type="text" v-model="editingCommentContent" />
            <button @click="submitCommentEdit(comment.id)">수정하기</button>
            <button @click="cancelEdit">취소</button>
          </div>
          <!-- 일반 댓글 표시 -->
          <div v-else>
            <p>{{ comment.context }} | {{ comment.username }}</p>
            <button @click="startEditComment(comment)">수정</button>
            <button @click="deleteComment(comment.id)">삭제</button>
          </div>
        </div>
      </div>

      <!-- 스냅샷 관리 버튼 -->
      <div class="snapshot-actions">
        <button @click="goToUpdate(props.selectedSnapshot?.snapshotId)">스냅샷 수정</button>
        <button @click="deleteSnapshot(props.selectedSnapshot?.snapshotId)">스냅샷 삭제</button>
      </div>
    </div>
  </div>
</template>
  
<script lang="ts" setup>
  import { ref, onMounted, defineProps } from 'vue';
  import { useUserStore } from '@/stores/user';
  import axios from 'axios';
  import { useRouter, useRoute } from 'vue-router';
  

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

  


  // const snapshot = ref<SnapshotType | null>(null);
  const router = useRouter();
  const route = useRoute();
  
  const store = useUserStore();
  const username = store.starInfo?.username;
  

  const starid = store.starInfo?.starId;
  
  const props = defineProps({
    selectedSnapshot: Object as () => selectedSnapshotType | null
  });

  const snapshotId = props.selectedSnapshot?.snapshotId;


  const goToUpdate = (id: number | undefined) => {
    if (id && props.selectedSnapshot) {
      router.push({
        name: 'snapshotupdate',
        params: { starid: starid, snapshotid: id },
        query: {
          file: props.selectedSnapshot.imageUrl,
          content: props.selectedSnapshot.content,
          tags: props.selectedSnapshot.tags
        }
      });
    } else {
      console.error('No snapshot selected or invalid ID');
    }
  };


  const deleteSnapshot = async (id: number | undefined) => {
    console.log("삭제 시작할게요")
    if (id && props.selectedSnapshot) {
      
        const response = await axios.delete(`http://localhost:8080/snapshot/${id}/delete`);
        console.log(response.data); // 성공 응답 로그
        console.log("삭제 성공!");
        // 성공적으로 삭제 후 필요한 추가 작업을 여기에 작성하세요.
        router.go(0)
      } else {
      console.error('No snapshot selected or invalid ID');
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
      console.log(props.selectedSnapshot?.snapshotId)
      await axios.post(`http://localhost:8080/snapshot/${props.selectedSnapshot?.snapshotId}/comment/new`, {
        context: newCommentContent.value,
        username: username,
        snapshotId: props.selectedSnapshot?.snapshotId
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
      const response = await axios.post(`http://localhost:8080/snapshot/${props.selectedSnapshot?.snapshotId}/comment/${commentId}/update`, {
        context: editingCommentContent.value,
        username: username,
        snapshotId: props.selectedSnapshot?.snapshotId
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
      await axios.post(`http://localhost:8080/snapshot/${props.selectedSnapshot?.snapshotId}/comment/${id}/delete`);
      // 댓글 목록을 다시 불러오는 로직 필요
      router.go(0)
    } catch (error) {
      console.error(error);
    }
  };
</script>

<style>
.off-canvas {
  display: flex; /* Flex 컨테이너 설정 */
  flex-direction: row; /* 자식 요소들을 세로로 나열 */
  align-items: center;
  width: 100%; /* 컨테이너 너비 설정 */
  max-width: 100vh; /* 최대 너비 설정 */
  margin: auto; /* 페이지 중앙 정렬 */
  background: white; /* 배경색 */
  overflow-y: auto; /* 내용이 길어지면 스크롤바 생성 */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
}

.image-container img {
  width: 100%; /* 이미지 너비 설정 */
  height: auto; /* 이미지 높이 자동 설정 */
  object-fit: cover; /* 이미지 비율을 유지하면서 컨테이너를 채움 */
  max-height: 50vh; /* 이미지 최대 높이를 화면 높이의 반으로 설정 */
}

/* 본문, 태그, 댓글 작성창, 댓글 목록 스타일링 */
.content,
.tags-list,
.comment-form,
.comments-list {
  padding: 16px; /* 패딩 설정 */
  border-top: 1px solid #eee; /* 상단에 경계선 설정 */
}

.content {
  font-size: 1rem; /* 본문 폰트 크기 */
}

.content-section {
  flex-basis: 50%; /* 나머지 50% 공간 차지 */
  padding: 1em; /* 내용과 테두리 사이의 여백 */
  overflow-y: auto; /* 필요한 경우 스크롤바 표시 */
}

.tags-list li {
  display: inline-block; /* 태그를 인라인 블록으로 표시 */
  margin-right: 8px; /* 태그 사이의 여백 설정 */
  padding: 5px 10px; /* 태그 패딩 설정 */
  background-color: #f0f0f0; /* 태그 배경색 */
  border-radius: 16px; /* 태그 둥근 모서리 */
}

.comment-form {
  display: flex; /* Flex 컨테이너 설정 */
  align-items: center; /* 요소들을 세로 중앙 정렬 */
}

.comment-form input[type="text"] {
  flex-grow: 1; /* 입력 필드가 가능한 모든 공간을 차지하도록 설정 */
  margin-right: 8px; /* 버튼과의 여백 설정 */
}

.comments-list {
  flex-direction: column; /* 댓글을 세로로 나열 */
}

.comment {
  padding: 8px 0; /* 댓글의 상하 패딩 설정 */
  border-bottom: 1px solid #eee; /* 댓글 하단에 경계선 설정 */
}

.comment:last-child {
  border-bottom: none; /* 마지막 댓글 하단 경계선 제거 */
}

.snapshot-actions {
  padding: 16px;
  text-align: center; /* 버튼을 가운데 정렬 */
}

.snapshot-actions button {
  margin: 0 8px; /* 버튼 사이의 여백 */
}

.snapshot-image {
  width: 100%; /* 이미지가 섹션 너비를 꽉 채우도록 함 */
  height: auto; /* 이미지 높이 자동 조절 */
  object-fit: cover; /* 이미지 비율 유지 */
}
</style>