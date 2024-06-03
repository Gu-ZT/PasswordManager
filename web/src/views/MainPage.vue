<script setup lang="ts">
import {goToLogin} from "../router";
import {ref} from "vue";
import {Operate, AESUtil} from "../util";
import {Request} from "../request";

let token = localStorage.getItem("token");
if (token == null) goToLogin();
let nick = localStorage.getItem("nickname");
const data = ref([]);

const columns = [
  {
    title: 'URL',
    dataIndex: 'url',
    key: 'url',
  },
  {
    title: '用户名',
    dataIndex: 'username',
    key: 'username',
  },
  {
    title: '密码',
    dataIndex: 'password',
    key: 'password',
  },
  {
    title: '描述',
    dataIndex: 'desc',
    key: 'desc',
  },
  {
    title: '操作',
    dataIndex: 'function',
    key: 'function',
  }
];
getData();

function copyText(value: string) {
  // 创建一个临时的输入框来保存要复制的文本
  var input = document.createElement("input");
  input.value = value;
  // 将输入框添加到页面中
  document.body.appendChild(input);
  // 选中输入框的文本
  input.select();
  // 执行复制操作
  document.execCommand("copy");
  // 移除输入框
  document.body.removeChild(input);
  Operate.success();
}

function getData() {
  Request.get("/pwd/get", ctx => {
    data.value = ctx.data;
    data.value.forEach((item: any) => {
      let token = localStorage.getItem("token");
      if (token) item.password = AESUtil.decrypt(item.password, token);
    })
  })
}

const addData = ref({
  url: undefined,
  username: undefined,
  password: undefined,
  desc: undefined,
});
const addOpen = ref(false);

function openAdd() {
  addData.value = {
    url: undefined,
    username: undefined,
    password: undefined,
    desc: undefined,
  }
  addOpen.value = true;
}

function add() {
  Request.post("/pwd/add", addData.value, _ => {
    addData.value = {
      url: undefined,
      username: undefined,
      password: undefined,
      desc: undefined,
    }
    addOpen.value = false;
    getData();
  })
}

const changeData = ref({
  id: undefined,
  url: undefined,
  username: undefined,
  password: undefined,
  desc: undefined,
});
const changeOpen = ref(false);

function openChange(data: any) {
  changeData.value = data
  changeOpen.value = true;
}

function change() {
  Request.post("/pwd/change", changeData.value, _ => {
    changeData.value = {
      id: undefined,
      url: undefined,
      username: undefined,
      password: undefined,
      desc: undefined,
    }
    changeOpen.value = false;
    getData();
  })
}

function remove(id: any) {
  Request.del(`/pwd/remove/${id}`, _ => {
    getData();
  });
}

function pwdChange(e: any, data: any) {
  data.password = e.target.__vnode.props.value;
}
</script>

<template>
  <a-space>
    你好，{{ nick }}
    <a-button @click="goToLogin" type="primary" danger>退出登录</a-button>
    <a-button @click="openAdd" type="primary">添加密码</a-button>
  </a-space>
  <a-table :dataSource="data" :columns="columns">
    <template #bodyCell="{ column,record }">
      <template v-if="column.key === 'password'">
        <a-space>
          <a-input-password v-model:value="record.password" @change="(e:any)=>pwdChange(e,record)"/>
          <a-button @click="copyText(record.password)">复制</a-button>
        </a-space>
      </template>
      <template v-if="column.key === 'function'">
        <a-button @click="openChange(record)" type="primary">修改</a-button>
        <a-popconfirm
            title="你确定要删除吗？"
            ok-text="Yes"
            cancel-text="No"
            @confirm="remove(record.id)"
        >
          <a-button type="primary" danger>删除</a-button>
        </a-popconfirm>
      </template>
    </template>
  </a-table>
  <a-modal v-model:open="addOpen" title="添加密码" @ok="add">
    <a-input v-model:value="addData.url" placeholder="URL"/>
    <a-input v-model:value="addData.username" placeholder="用户名"/>
    <a-input-password v-model:value="addData.password" placeholder="密码"/>
    <a-input v-model:value="addData.desc" placeholder="描述"/>
  </a-modal>
  <a-modal v-model:open="changeOpen" title="修改" @ok="change">
    <a-input v-model:value="changeData.url" placeholder="URL"/>
    <a-input v-model:value="changeData.username" placeholder="用户名"/>
    <a-input-password v-model:value="changeData.password" placeholder="密码"/>
    <a-input v-model:value="changeData.desc" placeholder="描述"/>
  </a-modal>
</template>

<style scoped>

</style>