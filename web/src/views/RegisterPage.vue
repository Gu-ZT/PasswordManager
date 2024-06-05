<script setup lang="ts">
import {Ref, ref} from "vue";
import {Request} from "../request";
import {goToHome} from "../router";
import {RSAUtil} from "../util";

const registerData: Ref<any> = ref({
  username: undefined,
  password: undefined
});

function register() {
  Request.get("/auth/public", (ctx: any) => {
    let serverKey = ctx.msg;
    localStorage.setItem("ServerPublicKey", serverKey);
    Request.post("/login/register", {
      username: registerData.value.username,
      password: RSAUtil.encrypt(registerData.value.password, serverKey),
    }, ctx => {
      localStorage.setItem("id", ctx.data.id);
      localStorage.setItem("nickname", ctx.data.nickname);
      localStorage.setItem("token", ctx.data.token);
      let key = RSAUtil.generateKey();
      localStorage.setItem("PublicKey", key.public);
      localStorage.setItem("PrivateKey", key.private);
      goToHome();
    });
  });
}
</script>

<template>
  <div class="container">
    <a-card class="card">
      <template #title>
        注册
      </template>
      <a-input v-model:value="registerData.username" class="input" placeholder="用户名"/>
      <a-input-password v-model:value="registerData.password" class="input" placeholder="密码"/>
      <a-button type="primary" size="large" @click="register">注册</a-button>
      <br/>
      <a href="/#/login">已有账号，前往登录</a>
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