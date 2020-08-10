# springboot-vue
采用前后端分离开发的一个小demo，springboot采用集成JPARepository的方式实现数据的增删改查，采用Pageable实现查找数据的分页处理；
vue采用elementui完成前端网页的搭建，采用axios完成前台与后台的数据请求，在springboot中采用CrosConfig配置完成前后台跨域的处理。
## vue前端代码的设计逻辑主要分为以下几个部分
### 1、编写main.js

~~~ 
import Vue from 'vue'
import App from './App'
import router from './router'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.config.productionTip = false
Vue.use(ElementUI)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
~~~ 
### 2、编写APP.vue

~~~<
template>
  <el-container style="height: 800px; border: 1px solid #eee">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
      <el-menu :default-openeds="['1', '3']">
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-menu"></i>城市管理
          </template>
            <el-menu-item v-for="item in $router.options.routes" :key="item.id"
            :class="$route.path==item.path?'is-active':''"><router-link :to="item.path">{{item.name}}</router-link></el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<style>
.el-header {
  background-color: #b3c0d1;
  color: #333;
  line-height: 60px;
}
.el-aside {
  color: #333;
}
</style>
~~~ 
### 3、创建views页面

#### 3.1 创建主页面Main.vue

~~~
<template>
  <div>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column label="ID" width="180">
        <template slot-scope="scope">
          <el-tag size="medium">{{ scope.row.id }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="城市" width="180">
        <template slot-scope="scope">
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.name }}</el-tag>
            </div>
        </template>
      </el-table-column>
    <el-table-column label="城市简介" width="180">
        <template slot-scope="scope">
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.description }}</el-tag>
            </div>
        </template>
      </el-table-column>
      <el-table-column label="城市评分" width="180">
        <template slot-scope="scope">
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.score }}</el-tag>
            </div>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="addCity()">新增</el-button>
          <el-button size="mini" @click="edit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="deleteCity(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      background
      layout="prev, pager, next"
      :page-size="pageSize"
      :total="total"
      @current-change="page"
      style="padding-left:60%;margin-top:1%">
    </el-pagination>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data () {
    return {
      pageSize: 1,
      total: 4,
      tableData: []
    }
  },
  methods: {
    edit (row) {
      this.$router.push({
        path: '/user',
        query: {
          id: row.id
        }
      })
    },
    deleteCity (row) {
      const _this = this
      axios.delete('http://localhost:8088/api/city/deleteById/' + row.id).then(function (resp) {
        _this.$alert('删除成功!', '消息', {
          confirmButtonText: '确定',
          callback: action => {
            window.location.reload()
          }
        })
      })
    },
    addCity () {
      this.$router.push({
        path: '/addUser'
      })
    },
    page (currentPage) {
      const _this = this
      axios.get('http://localhost:8088/api/city/all/find/' + (currentPage - 1) + '/6').then(function (resp) {
        console.log(resp)
        _this.tableData = resp.data.content
        _this.pageSize = resp.data.size
        _this.total = resp.data.totalElements
      })
    }

  },
  created () {
    const _this = this
    axios.get('http://localhost:8088/api/city/all/find/0/6').then(function (resp) {
      console.log(resp)
      _this.tableData = resp.data.content
      console.log('===tableData===', JSON.stringify(_this.tableData))
    })
  }
}
</script>
~~~ 
#### 3.2 创建修改城市数据页面User.vue

~~~
<template>
  <div>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column label="ID" width="180">
        <template slot-scope="scope">
          <el-tag size="medium">{{ scope.row.id }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="城市" width="180">
        <template slot-scope="scope">
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.name }}</el-tag>
            </div>
        </template>
      </el-table-column>
    <el-table-column label="城市简介" width="180">
        <template slot-scope="scope">
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.description }}</el-tag>
            </div>
        </template>
      </el-table-column>
      <el-table-column label="城市评分" width="180">
        <template slot-scope="scope">
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.score }}</el-tag>
            </div>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="addCity()">新增</el-button>
          <el-button size="mini" @click="edit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="deleteCity(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      background
      layout="prev, pager, next"
      :page-size="pageSize"
      :total="total"
      @current-change="page"
      style="padding-left:60%;margin-top:1%">
    </el-pagination>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data () {
    return {
      pageSize: 1,
      total: 4,
      tableData: []
    }
  },
  methods: {
    edit (row) {
      this.$router.push({
        path: '/user',
        query: {
          id: row.id
        }
      })
    },
    deleteCity (row) {
      const _this = this
      axios.delete('http://localhost:8088/api/city/deleteById/' + row.id).then(function (resp) {
        _this.$alert('删除成功!', '消息', {
          confirmButtonText: '确定',
          callback: action => {
            window.location.reload()
          }
        })
      })
    },
    addCity () {
      this.$router.push({
        path: '/addUser'
      })
    },
    page (currentPage) {
      const _this = this
      axios.get('http://localhost:8088/api/city/all/find/' + (currentPage - 1) + '/6').then(function (resp) {
        console.log(resp)
        _this.tableData = resp.data.content
        _this.pageSize = resp.data.size
        _this.total = resp.data.totalElements
      })
    }

  },
  created () {
    const _this = this
    axios.get('http://localhost:8088/api/city/all/find/0/6').then(function (resp) {
      console.log(resp)
      _this.tableData = resp.data.content
      console.log('===tableData===', JSON.stringify(_this.tableData))
    })
  }
}
</script>
~~~ 
#### 3.3 创建添加城市数据页面AddUser.vue

~~~ 
<template>
  <el-form
    :model="ruleForm"
    :rules="rules"
    ref="ruleForm"
    label-width="100px"
    class="demo-ruleForm"
  >
    <el-form-item label="城市名称" prop="name">
      <el-input v-model="ruleForm.name"></el-input>
    </el-form-item>
        <el-form-item label="城市描述" prop="description">
      <el-input v-model="ruleForm.description"></el-input>
    </el-form-item>
        <el-form-item label="城市评分" prop="score">
      <el-input v-model="ruleForm.score"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="addCityForm('ruleForm')">立即添加</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
      <el-button type="default" @click="backIndex()">返回</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
import axios from 'axios'
export default {
  data () {
    return {
      ruleForm: {
        name: '',
        description: '',
        score: ''
      },
      rules: {
        name: [
          {required: true, message: '请输入城市名称', trigger: 'blur'},
          {min: 2, max: 20, message: '长度在 2 到 5 个字符', trigger: 'blur'}
        ],
        description: [
          {required: true, message: '请输入城市描述', trigger: 'blur'},
          {min: 2, max: 40, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],
        score: [
          {required: true, message: '请输入城市评分', trigger: 'blur'},
          {min: 1, max: 2, message: '长度在1 到2个字符', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    addCityForm (formName) {
      const _this = this
      this.$refs[formName].validate(valid => {
        if (valid) {
          axios.post('http://localhost:8088/api/city/add', this.ruleForm).then(function (resp) {
            if (resp.data === 'success') {
              _this.$alert('《' + _this.ruleForm.name + '》添加成功!', '消息', {
                confirmButtonText: '确定',
                callback: action => {
                  _this.$router.push('/')
                }
              })
            }
          })
        } else {
          return false
        }
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    },
    backIndex () {
      this.$router.push({
        path: '/'
      })
    }
  },
  created () {
    const _this = this
    axios.get('http://localhost:8088/api/city/all/findById?id=' + this.$route.query.id).then(function (resp) {
      console.log(resp)
      _this.ruleForm = resp.data[0]
      console.log('===ruleForm===', JSON.stringify(_this.ruleForm))
    })
  }
}
</script>

<style scoped>
  .el-input{
    width:50%
  }
</style>
~~~ 
### 4、配置路由数据

~~~
import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '../views/Main.vue'
import User from '../views/User.vue'
import AddUser from '../views/AddUser.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: '城市查询',
    component: Main
  },
  {
    path: '/user',
    name: '城市维护',
    component: User
  },
  {
    path: '/addUser',
    name: '新增城市',
    component: AddUser
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
~~~ 
