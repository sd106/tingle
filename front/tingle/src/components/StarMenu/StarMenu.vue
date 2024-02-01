<template>
    <ul class="d-flex justify-content-around nav nav-underline">
        <li class="nav-item">
            <RouterLink :to="`/${props.id}/home`" class="nav-link router-link-custom"
                :class="{ 'active': isActive('/home') }">Home</RouterLink>
        </li>
        <li class="nav-item">
            <RouterLink :to="`/${props.id}/snapshot`" class="nav-link router-link-custom"
                :class="{ 'active': isActive('/snapshot') }">Snapshot</RouterLink>
        </li>
        <li class="nav-item">
            <RouterLink :to="`/${props.id}/wish`" class="nav-link router-link-custom"
                :class="{ 'active': isActive('/wish') }">Wish</RouterLink>
        </li>
        <li class="nav-item">
            <RouterLink :to="`/${props.id}/store`" class="nav-link router-link-custom"
                :class="{ 'active': isActive('/store') }">Store</RouterLink>
        </li>
        <li class="nav-item">
            <RouterLink :to="`/${props.id}/fanmeeting`" class="nav-link router-link-custom"
                :class="{ 'active': isActive('/fanmeeting') }">Fanmeeting</RouterLink>
        </li>
        <li class="nav-item">
            <RouterLink :to="`/profile/${props.name}/teststore`" class="nav-link router-link-custom"
                :class="{ 'active': isActive('/teststore') }">Test스토어Product</RouterLink>
        </li>
        <li class="nav-item">
            <RouterLink :to="`/profile/${props.name}/teststore2`" class="nav-link router-link-custom"
                :class="{ 'active': isActive('/teststore2') }">Test스토어Order</RouterLink>
        </li>
    </ul>
</template>

<script setup lang="ts">
import { defineProps } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const isActive = (path: string) => {
    return route.path.includes(path);
};

const props = defineProps(['id']);
</script>

<style scoped></style>