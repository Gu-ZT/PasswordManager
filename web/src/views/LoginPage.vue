<script setup lang="ts">
import {ref} from "vue";
import {Request} from "../request";
import {goToHome} from "../router";

const loginData = ref({
  username: undefined,
  password: undefined
});

function login() {
  Request.post("/login/login", loginData.value, ctx => {
    localStorage.setItem("id", ctx.data.id);
    localStorage.setItem("nickname", ctx.data.nickname);
    localStorage.setItem("token", ctx.data.token);
    goToHome();
  });
}
</script>

<template>
  <div class="container">
    <a-card class="card">
      <template #title>
        登录
      </template>
      <a-input v-model:value="loginData.username" class="input" placeholder="用户名"/>
      <a-input-password v-model:value="loginData.password" class="input" placeholder="密码"/>
      <a-button type="primary" size="large" @click="login">登录</a-button>
      <br/>
      <a href="/#/register">没有账号，前往注册</a>
    </a-card>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  height: 400px;
}

.input {
  margin: 5px;
}

.card {
  display: inline-block;
  width: 40%;
  margin: auto;
  text-align: center;
}
</style>