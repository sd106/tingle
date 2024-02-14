<template>
  <div :class="{ ticket: true, 'ticket-selected': isSelected, 'ticket-not-selected': !isSelected }" @click="select">
    <div class="row">
      <div class="col-3 ">
        <img :src="product.image" :alt="'팬 미팅 티켓 이미지' " class="ticket-image">
      </div>
      <div class="col-9 text-start">
        <h4> {{ product.name }} </h4>
        <h5>{{ product.description }}</h5>

      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { defineProps, defineEmits } from 'vue'
import type { PropType } from 'vue'
import type { FanMeetingTicket } from '@/common/types/index'

const props = defineProps({
  product: {
    type: Object as PropType<FanMeetingTicket>,
    required: true
  },
  isSelected: {
    type: Boolean,
    required: true
  }
})

const emit = defineEmits(['select'])

const select = () => {
  emit('select', props.product)
}
</script>

<style scoped>
.ticket {
  border: 1px solid #e2e2e2;
  border-radius: 10px;
  padding: 10px;
  
}

.ticket-not-selected {
  cursor: pointer;
}
.ticket-selected {
  background-color: rgb(230, 230, 230);
  border: 3px solid rgb(93, 93, 250);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0.3);
}

.ticket-not-selected:hover {
  background-color: rgb(240, 240, 240);
  box-shadow: 0 0 3px rgba(0, 0, 0, 0.5);
}

.ticket-image {
  width: 100%;
  height: auto;
  object-fit: contain; 
}

</style>
