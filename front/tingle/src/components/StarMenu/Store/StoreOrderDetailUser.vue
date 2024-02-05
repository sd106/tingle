<template>
    <main>
        {{ order }}
        <img :src="order.fan.picture" alt="">
        <p>username : {{ order.fan.username }}</p>
        <p>email : {{ order.fan.email }}</p>
        <p>상품 정보 : {{ order.goods.name }}</p>
        <p>상품 설명 : {{ order.goods.content }}</p>
        <p>상품 가격 : {{ order.goods.price }}</p>

        <hr>
        <div>
        <h2>주문 삭제</h2>
      </div>
    </main>
</template>

<script setup lang="ts">
import type { OrderResponse2 } from '@/common/types';


const props = defineProps({
    order: {
        type: Object as () => OrderResponse2,
        required: true
    }
});


const order = props.order

</script>