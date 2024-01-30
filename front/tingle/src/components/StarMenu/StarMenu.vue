<template>
    <ul class="d-flex justify-content-around nav nav-underline">
        <li class="nav-item">
            <RouterLink :to="`/profile/${props.name}/home`" class="nav-link router-link-custom"
                :class="{ 'active': isActive('/home') }">Home</RouterLink>
        </li>
        <li class="nav-item">
            <RouterLink :to="`/profile/${props.name}/snapshot`" class="nav-link router-link-custom"
                :class="{ 'active': isActive('/snapshot') }">Snapshot</RouterLink>
        </li>
        <li class="nav-item">
            <RouterLink :to="`/profile/${props.name}/wish`" class="nav-link router-link-custom"
                :class="{ 'active': isActive('/wish') }">Wish</RouterLink>
        </li>
        <li class="nav-item">
            <RouterLink :to="`/profile/${props.name}/store`" class="nav-link router-link-custom"
                :class="{ 'active': isActive('/store') }">Store</RouterLink>
        </li>
        <li class="nav-item">
            <RouterLink :to="`/profile/${props.name}/fanmeeting`" class="nav-link router-link-custom"
                :class="{ 'active': isActive('/fanmeeting') }">Fanmeeting</RouterLink>
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

const props = defineProps(['name']);
</script>

<style scoped></style>