<template>
  <div class="container mt-2 mb-3">
    <div class="row">
      <!-- 이미지 컨테이너 -->
      <div class="col-md-6 tw-flex tw-flex-row">
        <img :src="props.selectedSnapshot!.imageUrl" alt="Snapshot Image" class="rounded snapshot-image">
      </div>
      <div class="col-md-6">
        <!-- 스냅샷 관리 버튼 -->
        <div class="snapshot-actions d-flex justify-content-between p-1">
          <!-- <button class="btn btn-danger" v-if="isLike" @click="preDislike(props.selectedSnapshot!.snapshotId)">
            좋아요 취소 {{ props.selectedSnapshot?.likes }}
          </button>
          <button class="btn btn-outline-danger" v-else @click="preLike(props.selectedSnapshot!.snapshotId)">
            좋아요 {{ props.selectedSnapshot?.likes }}
          </button> -->
          <div class="tw-flex tw-gap-x-2 tw-items-center">
            <button v-if="isLike" @click="preDislike(props.selectedSnapshot!.snapshotId)"
              class="tw-btn tw-btn-outline dislike">
              <svg xmlns="http://www.w3.org/2000/svg" class="tw-h-8 tw-w-8 heart" fill="red" viewBox="0 0 24 24"
                stroke="black">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
              </svg>
              {{ props.selectedSnapshot?.likes }}
            </button>
            <button v-else @click="preLike(props.selectedSnapshot!.snapshotId)" class="tw-btn tw-btn-outline like">
              <svg xmlns="http://www.w3.org/2000/svg" class="tw-h-8 tw-w-8 heart" fill="none" viewBox="0 0 24 24"
                stroke="black">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
              </svg>
              {{ props.selectedSnapshot?.likes }}
            </button>
          </div>


          <span>
            <button v-if="!store.isStar && (store.fanState && store.fanState.username === props.selectedSnapshot?.username)" class="btn btn-secondary" @click="goToUpdate(props.selectedSnapshot!.snapshotId)">스냅샷 수정</button>
            <button v-if="store.isStar || (store.fanState && store.fanState.username === props.selectedSnapshot!.username)" class="btn btn-danger" @click="deleteSnapshot(props.selectedSnapshot!.snapshotId)">스냅샷 삭제</button>
          </span>
        </div>
        <!-- 본문 내용 -->
        <div class="content mx-0 my-3 pt-0">
          <p class="d-flex justify-content-between mb-2">
            <span class="text-body-tertiary">{{ props.selectedSnapshot!.username }}</span>
            <span class="text-body-tertiary">{{ createdTime }}</span>
          </p>
          <h3 class="tw-text-xl">{{ props.selectedSnapshot!.content }}</h3>
        </div>

        <!-- 태그 리스트 -->
        <ul class="tags-list border-top border-bottom">
          <li v-for="(tag, index) in props.selectedSnapshot?.tags" :key="index">{{ tag }}</li>
        </ul>

        <!-- 댓글 목록 -->
        <div class="comments-list">
          <p class="mb-2">💬 댓글 {{ props.selectedSnapshot?.comments.length }}</p>

          <div v-for="comment in props.selectedSnapshot!.comments" :key="comment.id" class="mb-2">
            <!-- 수정 중인 댓글의 UI 변경 -->
            <div v-if="editingCommentId === comment.id">
              <p class="d-flex justify-content-between align-items-center">
                <span>
                  <input type="text" class="form-control" v-model="editingCommentContent" />
                </span>
                <span>
                  <button class="btn btn-outline-secondary btn-sm mx-2"
                    @click="submitCommentEdit(comment.id)">수정하기</button>
                  <button class="btn btn-outline-danger btn-sm" @click="cancelEdit">취소</button>
                </span>
              </p>
            </div>
            <!-- 일반 댓글 표시 -->
            <div v-else>
              <div class="tw-flex tw-flex-row tw-items-stretch">
                <div class="tw-self-center tw-basis-4/5 tw-flex tw-flex-row tw-items-stretch">
                  <span v-if="comment.isStar" class="tw-self-center tw-basis-1/3 fw-bold text-danger">{{ comment.username }}</span>
                  <span v-else class="tw-self-center tw-basis-1/3 fw-bold">{{ comment.username }}</span>
                  <span class="tw-self-center tw-basis-1/2">{{ comment.context }}</span>
                  <span class="tw-self-center tw-basis-1/6 text-body-tertiary tw-text-xs">{{ formatDistanceToNowFromLocalDateTime(comment.createdAt) }}</span>
                </div>
                <div class="tw-self-center tw-basis-1/5">
                  <button v-if="store.isStar || (!store.isStar && !comment.isStar && store.fanState && (store.fanState.username === comment.username)) || (store.starState && (store.starState.username === comment.username))" class="btn btn-outline-danger btn-sm" @click="deleteComment(comment.id)">
                    삭제
                  </button>
                  <button v-if="(!store.isStar && !comment.isStar && store.fanState && (store.fanState.username === comment.username)) || (store.isStar && comment.isStar && store.starState!.username === comment.username)" class="btn btn-outline-secondary btn-sm ms-1" @click="startEditComment(comment)">
                    수정
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- 댓글 작성 폼 -->
        <form @submit.prevent="postComment" class="me-1 row rounded comment-form rounded-lg tw-bg-yellow-200">
          <div class="col-sm-10">
            <input class="form-control" type="text" v-model="newCommentContent" placeholder="댓글을 남겨보세요!" />
          </div>
          <button type="submit" class="col-sm-2 tw-bg-yellow-500 hover:tw-bg-blue-700 tw-text-white tw-font-bold tw-py-2 tw-px-4 tw-rounded tw-shadow hover:tw-shadow-lg tw-transition tw-duration-300 tw-ease-in-out">작성</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>

import { ref, watch, computed } from 'vue';
import { formatDistanceToNow } from 'date-fns';
import { ko } from 'date-fns/locale';
import { useUserStore } from '@/stores/user';
import { useSnapshotStore } from '@/stores/snapshot'
import axios from 'axios';
import { useRouter } from 'vue-router';
import type { selectedSnapshotType, CommentType } from '@/common/types/index'
import { isConstructorDeclaration } from 'typescript';

// const snapshot = ref<SnapshotType | null>(null);
const active = ref(0)
const router = useRouter()

const store = useUserStore();
const snapshotStore = useSnapshotStore();
const username = ref<string>('')

watch(
  () => store.fanState,
  (newVal, oldVal) => {
    if (newVal) {
      username.value = newVal!.username;
    } else if (store.starState) {
      username.value = store.starState.username;
    }
  },
  { immediate: true } // 컴포넌트 마운트 시 바로 실행
);

watch(
  () => store.starState,
  (newVal, oldVal) => {
    if (!store.fanState && newVal) {
      username.value = newVal.username;
    }
  },
  { immediate: true } // 컴포넌트 마운트 시 바로 실행
);


const props = defineProps({
  selectedSnapshot: Object as () => selectedSnapshotType,
  starid: String
});

const starid = Number(props.starid);
const time = formatDistanceToNowFromLocalDateTime(props.selectedSnapshot!.updatedAt)
const createdTime = formatDistanceToNowFromLocalDateTime(props.selectedSnapshot!.createdAt)
const isLike = ref(props.selectedSnapshot!.isLiked);

const isStar = ref<boolean>(false)

watch(
  () => store.starState,
  (newVal, oldVal) => {
    if (newVal?.username === props.selectedSnapshot!.starname) {
      isStar.value = true
    }
  },
  { immediate: true }
)

watch(
  () => props.selectedSnapshot!.isLiked,
  (newVal, oldVal) => {
    // isLike 상태 업데이트
    isLike.value = newVal;
  },
  { immediate: true }
);

const goToUpdate = (id: number) => {
  if (id && props.selectedSnapshot) {
    console.log("업데이트로 가자", id, starid)
    router.push({
      name: 'snapshotupdate',
      params: { starid: starid.toString(), snapshotid: id.toString() },
    });
    console.log()
  } else {
    console.error('No snapshot selected or invalid ID');
  }
};

// -------------------------------------------------------------------- 좋아요 코드
const handleClick = async (snapshotId: number) => {
  if (isLike.value) {
    preDislike(snapshotId)
    active.value = 0
  } else {
    preLike(snapshotId)
    active.value = 1
  }
}

const preLike = async (id: number) => {

  if (!store.fanState && store.starState && (store.starState!.username === props.selectedSnapshot!.starname)) {
    console.log(store.starState!.username)
    console.log(props.selectedSnapshot!.starname)
    console.log("스타가 좋아요 누름!")
    like(id, store.starState.username, true)

  } else if (store.fanState && !store.starState) {
    console.log(store.fanState!.username)
    console.log(props.selectedSnapshot!.starname)
    console.log("팬이 좋아요 누름!")
    like(id, store.fanState.username, false)

  } else {
    console.log("스타나 유저 정보가 없어!")
  }
}

const like = async (id: number, username: string, isStar: boolean) => {
  console.log("좋아요 함수 실행")
  console.log(id, username, isStar)
  if (id) {
    try {
      // 좋아요 API 호출
      console.log("좋아요 axios 호출")
      await axios.post(`https://i10d106.p.ssafy.io/api/snapshot/${id}/likes`, { username: username, isStar: isStar }, { withCredentials: true })
        .then(() => {
          snapshotStore.selectSnapshot(id)
        });

    } catch (error) {
      console.error('좋아요 실패:', error);
    }
  }
};

const preDislike = async (id: number) => {
  if (!store.fanState && store.starState && (store.starState.username == props.selectedSnapshot!.starname)) {
    dislike(id, store.starState.username, true)
    console.log("스타가 좋아요 취소!")
  } else if (store.fanState && !store.starState) {
    dislike(id, store.fanState.username, false)
    console.log("팬이 좋아요 취소!")
  } else {
    console.log("스타나 유저 정보가 없어!")
  }
}
const dislike = async (id: number, username: string, isStar: boolean) => {
  console.log("dislike 함수 시작")
  if (id) {
    try {
      // 좋아요 API 호출
      await axios.post(`https://i10d106.p.ssafy.io/api/snapshot/${id}/dislikes`, { username: username, isStar: isStar }, { withCredentials: true });
      console.log("싫어요 실행됨")
      // 스토어에서 선택된 스냅샷을 다시 가져온 후 좋아요 수를 갱신
      snapshotStore.selectSnapshot(id)
    } catch (error) {
      console.error('좋아요 실패:', error);
    }
  }
};

// ---------------------------------------------------------------------------좋아요 기능 끝


const deleteSnapshot = async (id: number) => {
  console.log("삭제 시작할게요")
  if (id && props.selectedSnapshot) {

    const response = await axios.delete(`https://i10d106.p.ssafy.io/api/snapshot/${id}/delete`);
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
    console.log(isStar.value)
    console.log(starid)
    await axios.post(`https://i10d106.p.ssafy.io/api/snapshot/${props.selectedSnapshot?.snapshotId}/comment/new`, {
      context: newCommentContent.value,
      username: username.value,
      snapshotId: props.selectedSnapshot?.snapshotId,
      isStar: isStar.value
      // 필요하다면 여기에 더 많은 필드 추가
    });
    newCommentContent.value = ''; // 입력 필드 초기화
    snapshotStore.selectSnapshot(props.selectedSnapshot!.snapshotId)
    // 댓글 목록을 다시 불러오는 로직 필요
  } catch (error) {
    console.error(error);
  }
};


// 댓글 수정 시작
const startEditComment = (comment: CommentType) => {
  editingCommentId.value = comment.id
  editingCommentContent.value = comment.context
}

// 수정 취소
const cancelEdit = () => {
  editingCommentId.value = null
  editingCommentContent.value = ''
}

// 수정된 댓글 전송
const submitCommentEdit = async (commentId: number) => {
  try {
    const response = await axios.post(
      `https://i10d106.p.ssafy.io/api/snapshot/${props.selectedSnapshot?.snapshotId}/comment/${commentId}/update`,
      {
        context: editingCommentContent.value,
        username: username.value,
        snapshotId: props.selectedSnapshot?.snapshotId,
        isStar: isStar.value
        // 기타 필요한 데이터
      });
    console.log(response.data);
    // 여기서 댓글 목록 갱신 로직 필요
    cancelEdit();
    snapshotStore.selectSnapshot(props.selectedSnapshot!.snapshotId)
  } catch (error) {
    console.error(error);
  }
};

// 댓글 삭제
const deleteComment = async (id: number) => {
  try {
    await axios.post(`https://i10d106.p.ssafy.io/api/snapshot/${props.selectedSnapshot?.snapshotId}/comment/${id}/delete`);
    // 댓글 목록을 다시 불러오는 로직 필요
    snapshotStore.selectSnapshot(props.selectedSnapshot!.snapshotId)
  } catch (error) {
    console.error(error);
  }
};

// 날짜 함수
function formatDistanceToNowFromLocalDateTime(isoString: string) {
  // 배열에서 연, 월, 일, 시, 분, 초를 추출합니다.
  // JavaScript의 Date 월은 0부터 시작하므로 월에서 1을 빼줍니다.
  const date = new Date(isoString)

  // 현재 시간으로부터의 거리 계산
  const distance = formatDistanceToNow(date, { addSuffix: true, locale: ko })

  return distance
}
</script>

<style>
.off-canvas {
  display: flex;
  /* Flex 컨테이너 설정 */
  flex-direction: row;
  /* 자식 요소들을 세로로 나열 */
  flex: 1;
  align-items: center;
  width: 100%;
  /* 컨테이너 너비 설정 */
  max-width: 100vh;
  /* 최대 너비 설정 */
  margin: auto;
  /* 페이지 중앙 정렬 */
  background: white;
  /* 배경색 */
  overflow-y: auto;
  /* 내용이 길어지면 스크롤바 생성 */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  /* 그림자 효과 */
}

.image-container img {
  width: 100%;
  /* 이미지 너비 설정 */
  height: auto;
  /* 이미지 높이 자동 설정 */
  object-fit: cover;
  /* 이미지 비율을 유지하면서 컨테이너를 채움 */
  max-height: 50vh;
  /* 이미지 최대 높이를 화면 높이의 반으로 설정 */
}

/* 본문, 태그, 댓글 작성창, 댓글 목록 스타일링 */
.content,
.tags-list,
.comment-form,
.comments-list {
  padding: 16px;
  /* 패딩 설정 */
}

.content-section {
  flex-basis: 50%;
  /* 나머지 50% 공간 차지 */
  padding: 1em;
  /* 내용과 테두리 사이의 여백 */
  overflow-y: auto;
  /* 필요한 경우 스크롤바 표시 */
}

.tags-list li {
  display: inline-block;
  /* 태그를 인라인 블록으로 표시 */
  margin-right: 8px;
  /* 태그 사이의 여백 설정 */
  padding: 5px 10px;
  /* 태그 패딩 설정 */
  background-color: #f0f0f0;
  /* 태그 배경색 */
  border-radius: 16px;
  /* 태그 둥근 모서리 */
}

.comment-form {
  display: flex;
  /* Flex 컨테이너 설정 */
  align-items: center;
  /* 요소들을 세로 중앙 정렬 */
}

.comment-form input[type='text'] {
  flex-grow: 1;
  /* 입력 필드가 가능한 모든 공간을 차지하도록 설정 */
  margin-right: 8px;
  /* 버튼과의 여백 설정 */
}

.comments-list {
  flex-direction: column;
  /* 댓글을 세로로 나열 */
}

.comment {
  padding: 8px 0;
  /* 댓글의 상하 패딩 설정 */
  border-bottom: 1px solid #eee;
  /* 댓글 하단에 경계선 설정 */
}

.comment:last-child {
  border-bottom: none;
  /* 마지막 댓글 하단 경계선 제거 */
}

.snapshot-actions {
  padding: 12px;
}

.snapshot-actions button {
  margin: 0 8px;
  /* 버튼 사이의 여백 */
}

.snapshot-image {
  width: 100%;
  /* 이미지가 섹션 너비를 꽉 채우도록 함 */
  height: auto;
  /* 이미지 높이 자동 조절 */
  object-fit: cover;
  /* 이미지 비율 유지 */
}

.download-link {
  display: block;
  margin-top: 10px;
  text-align: center;
  background: #007bff;
  color: white;
  padding: 8px 16px;
  border-radius: 4px;
  text-decoration: none;
}

.like,
.dislike {
  border: none;
  outline: none;
  transition: background-color 0.3s;
}

.like:hover,
.dislike:hover {
  background-color: white;
}

.heart {
  transition: fill 0.3s ease, stroke 0.3s ease, transform 0.3s ease;
}

.like:hover .heart {
  fill: red;
  transform: scale(1.5);
}

.dislike:hover .heart {
  fill: none;
  opacity: 0.2;
  transition: opacity 0.3s ease, fill 0.3s ease;
}

</style>
