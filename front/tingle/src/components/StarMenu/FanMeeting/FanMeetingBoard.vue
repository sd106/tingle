<template>
    <div>
        <div>
            <button @click="finishFan">팬 내보내기</button>
        </div>
        <!-- <div>
            <div v-for="fan in fans">
                <div @click="openDetailModal">
                    {{ fan.username }}
                </div>
                <div v-if="selectedFanName == fan.username">
                    <button>
                        초대하기
                    </button>
                </div>
            </div>
        </div> -->
        <div>
            <button @click="finishMeeting">팬미팅 끝내기</button>
        </div>
    </div>
</template>

<script lang="ts" setup>
// import { ref } from 'vue'
// import type { Fan } from '@/common/types/index.ts';
// const fans = ref<Fan[]>([])

const emit = defineEmits(['finish'])

const finishFan = async () => {
    
}

const finishMeeting = async () => {
    
}

</script>

<style scoped>
</style>
