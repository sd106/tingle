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
import axios from 'axios'
import { useUserStore } from '@/stores/user'

const store = useUserStore()
const emit = defineEmits(['finishFan', 'finishMeeting'])

const finishFan = async () => {
    
}

const finishMeeting = async () => {
    try {
        axios.delete(`https://i10d106.p.ssafy.io/api/fanMeeting/finish/${store.starState?.id}`)
    } catch (error) {
        console.log(error)
    }
}

</script>

<style scoped>
</style>
