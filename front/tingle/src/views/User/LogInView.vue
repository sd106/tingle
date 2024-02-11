<template>
    <main class="container d-flex flex-column align-items-center justify-content-center">
        <h1 class="my-5">로그인</h1>
      
        <div class="d-flex">
          <a @click="redirectToGoogle" class="btn btn-secondary active" role="button">Google Login</a>
          <a @click="redirectToNaver" class="btn  active" role="button">Naver Login</a>
        </div>
    </main>
</template>

<script setup lang="ts">
const redirectToOAuthProvider = async (provider:string) => {
    try {
      window.location.href = `http://i10d106.p.ssafy.io:8080/api/oauth2/authorization/${provider}`;
      
    } catch (error) {
        console.log('error', error)
    }
}

const redirectToGoogle = () => {
    redirectToOAuthProvider('google');
}

const redirectToNaver = () => {
    redirectToOAuthProvider('naver');
}


</script>
  
<style scoped>
</style>