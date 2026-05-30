<template>
  <el-alert
    title="操作说明"
    type="info"
    :closable="false"
    description="请正确使用菜单信息：1.菜单名称为前端菜单显示名称。2.排序越小，显示越靠前。3.最后一集为资源点配置。"
  />
  <el-card class="box-card">
    <el-row :gutter="20">
      <el-col :span="5">
        <el-select
          size="small"
          v-model="queryInfo.system"
          placeholder="选择菜单系统"
          @change="getMenuList"
        >
          <el-option
            v-for="item in dictStore.dictList.sys_menu_system"
            :key="item.code"
            :label="item.codeval"
            :value="item.code"
          />
        </el-select>
      </el-col>
      <el-col :span="4">
        <el-radio-group
          size="small"
          v-model="queryInfo.useage"
          @change="getMenuList"
        >
          <el-radio-button
            v-for="item in dictStore.dictList.sys_menu_usage"
            :key="item.code"
            :value="parseInt(item.code)"
          >
            {{ item.codeval }}
          </el-radio-button>
        </el-radio-group>
      </el-col>
      <el-col :span="10">
        <div class="buttons">
          <el-button size="small" @click="setAllExpand(true)"
            >展开所有节点</el-button
          >
          <el-button size="small" @click="setAllExpand(false)"
            >收起所有节点</el-button
          >
          <el-button size="small" type="primary" @click="add"
            >添加菜单</el-button
          >
        </div>
      </el-col>
    </el-row>
    <el-table
      ref="menuTreeRef"
      size="small"
      :data="list"
      height="calc(100vh - 300px)"
      style="margin-top: 20px"
      row-key="id"
      default-expand-all
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column prop="name" label="名称" width="180">
        <template #default="{ row }">
          <i :class="row.icon"></i> {{ row.name }}
        </template>
      </el-table-column>
      <el-table-column prop="sort" label="序号" width="80"></el-table-column>
      <el-table-column
        prop="resourse"
        label="资源"
        width="200"
        show-overflow-tooltip
      ></el-table-column>
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }">
          <el-tag
            v-for="item in dictStore.dictList.sys_menu_status"
            v-show="row.status == item.code"
            :key="item.code"
            size="small"
            :type="item.code == 0 ? 'danger' : 'success'"
          >
            {{ item.codeval }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="intro"
        label="简介"
        show-overflow-tooltip
      ></el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button
            v-if="row.menuType == 0 && row.pid != 0"
            type="primary"
            icon="plus"
            size="small"
            @click="() => addResourse(row)"
            circle
          />
          <el-button
            type="warning"
            size="small"
            icon="edit"
            @click="() => update(row)"
            circle
          />
          <el-button
            v-if="row.menuType == 0"
            type="danger"
            size="small"
            icon="delete"
            @click="() => remove(row)"
            circle
          />
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      :title="dialogTitle"
      :close-on-click-modal="false"
      v-model="dialogFormVisible"
    >
      <el-form :model="formdata" :rules="formRules" ref="menuDataFormRef">
        <el-form-item label="系统" :label-width="formLabelWidth" prop="system">
          <el-select
            size="small"
            v-model="formdata.system"
            placeholder="选择菜单系统"
          >
            <el-option
              v-for="item in dictStore.dictList.sys_menu_system"
              :key="item.code"
              :label="item.codeval"
              :value="item.code"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          label="菜单名称"
          :label-width="formLabelWidth"
          prop="name"
        >
          <el-input
            size="small"
            v-model="formdata.name"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="菜单描述"
          :label-width="formLabelWidth"
          prop="intro"
        >
          <el-input
            size="small"
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4 }"
            placeholder="菜单描述"
            v-model="formdata.intro"
          >
          </el-input>
        </el-form-item>
        <el-form-item
          label="菜单地址"
          :label-width="formLabelWidth"
          prop="path"
        >
          <el-input
            size="small"
            v-model="formdata.path"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="菜单排序"
          :label-width="formLabelWidth"
          prop="sort"
        >
          <el-input
            size="small"
            v-model="formdata.sort"
            type="number"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="上级菜单" :label-width="formLabelWidth" prop="pid">
          <el-select
            size="small"
            v-model="formdata.pid"
            placeholder="选择上级菜单"
          >
            <el-option label="一级菜单" :value="0"></el-option>
            <el-option
              v-for="item in list"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          label="图标样式"
          :label-width="formLabelWidth"
          prop="icon"
        >
          <icon-select v-model="formdata.icon" />
        </el-form-item>
        <el-form-item
          label="菜单状态"
          :label-width="formLabelWidth"
          prop="status"
        >
          <el-select
            size="small"
            v-model="formdata.status"
            placeholder="选择菜单是否展示"
          >
            <el-option
              v-for="item in statusOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          label="前台后台"
          :label-width="formLabelWidth"
          prop="useage"
        >
          <el-select
            size="small"
            v-model="formdata.useage"
            placeholder="选择菜单类型"
          >
            <el-option
              v-for="item in dictStore.dictList.sys_menu_usage"
              :key="item.code"
              :label="item.codeval"
              :value="parseInt(item.code)"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          label="菜单类型"
          :label-width="formLabelWidth"
          prop="menuType"
        >
          <el-select
            size="small"
            v-model="formdata.menuType"
            placeholder="选择菜单类型"
          >
            <el-option
              v-for="item in dictStore.dictList.sys_menu_type"
              :key="item.code"
              :label="item.codeval"
              :value="parseInt(item.code)"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          label="资源名称"
          :label-width="formLabelWidth"
          prop="resourse"
        >
          <el-input
            size="small"
            v-model="formdata.resourse"
            autocomplete="off"
            :disabled="formdata.menuType == 0"
          ></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button size="small" @click="dialogFormVisible = false"
          >取 消</el-button
        >
        <el-button size="small" type="primary" @click="dialogSubmit"
          >确 定</el-button
        >
      </template>
    </el-dialog>

    <el-dialog
      title="资源管理"
      :close-on-click-modal="false"
      v-model="resourseFormVisible"
    >
      <el-form
        :model="resourseFormData"
        :rules="formRules"
        ref="resourseFormRef"
      >
        <el-form-item
          label="资源名称"
          :label-width="formLabelWidth"
          prop="name"
        >
          <el-input
            size="small"
            v-model="resourseFormData.name"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="资源代码"
          :label-width="formLabelWidth"
          prop="resourse"
        >
          <el-input
            size="small"
            v-model="resourseFormData.resourse"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="资源描述"
          :label-width="formLabelWidth"
          prop="intro"
        >
          <el-input
            size="small"
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4 }"
            placeholder="资源描述"
            v-model="resourseFormData.intro"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="排序" :label-width="formLabelWidth" prop="sort">
          <el-input
            size="small"
            v-model="resourseFormData.sort"
            type="number"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="上级菜单" :label-width="formLabelWidth" prop="pid">
          <el-input
            size="small"
            v-model="resourseFormData.pid"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item
          label="资源状态"
          :label-width="formLabelWidth"
          prop="status"
        >
          <el-select
            size="small"
            v-model="resourseFormData.status"
            placeholder="选择菜单是否展示"
          >
            <el-option
              v-for="item in statusOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          label="前台后台"
          :label-width="formLabelWidth"
          prop="useage"
        >
          <el-input
            size="small"
            v-model="resourseFormData.useage"
            readonly
          ></el-input>
        </el-form-item>
        <el-form-item
          label="菜单类型"
          :label-width="formLabelWidth"
          prop="menuType"
        >
          <el-select
            size="small"
            v-model="resourseFormData.menuType"
            placeholder="选择菜单类型"
            disabled
          >
            <el-option
              v-for="item in dictStore.dictList.sys_menu_type"
              :key="item.code"
              :label="item.codeval"
              :value="parseInt(item.code)"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button size="small" @click="resourseFormVisible = false"
          >取 消</el-button
        >
        <el-button size="small" type="primary" @click="resourseSubmit"
          >确 定</el-button
        >
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import IconSelect from './components/IconSelect.vue'
import { menuApi } from '@/api/system/menu'
import { useDictStore } from '@/stores'

const dictStore = useDictStore()
// Refs
const menuTreeRef = ref(null)
const menuDataFormRef = ref(null)
const resourseFormRef = ref(null)

const statusOptions = ref([
  { id: 0, name: '失效', type: 'danger' },
  { id: 1, name: '生效', type: 'success' }
])

const queryInfo = ref({
  system: 'a6squre',
  useage: 1
})
const dialogTitle = ref('')
const list = ref([])
const dialogFormVisible = ref(false)
const resourseFormVisible = ref(false)
const formLabelWidth = '120px'

const formdata = ref({
  name: '',
  intro: '',
  path: '',
  sort: '',
  pid: '',
  icon: '',
  status: 1,
  useage: 0,
  menuType: 0,
  resourse: '',
  system: 'a6squre'
})

const resourseFormData = ref({
  name: '',
  intro: '',
  path: '',
  sort: '',
  pid: null,
  icon: '',
  status: 1,
  useage: 0,
  menuType: 0,
  resourse: '',
  system: 'a6squre'
})

const formRules = ref({
  name: [
    { required: true, message: '请输入菜单名称', trigger: 'blur' },
    { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
  ],
  sort: [
    {
      required: true,
      message: '请输入展示顺序整数（越小越靠前）',
      trigger: 'blur'
    }
  ],
  pid: [{ required: true, message: '请选择上级菜单', trigger: 'blur' }],
  status: [{ required: true, message: '请选择菜单状态', trigger: 'blur' }]
})

// Methods
const getMenuList = async () => {
  try {
    const res = await menuApi.listMenu(queryInfo.value)
    list.value = res.data
  } catch (error) {
    ElMessage.error(error.message)
  }
}

const remove = (data) => {
  ElMessageBox.confirm(
    '此操作将永久删除该记录,同时会删除子菜单, 是否继续?',
    '提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  )
    .then(async () => {
      try {
        const res = await menuApi.deleteMenu(data.id, data.pid)
        ElMessage.success(res.msg)
        getMenuList()
      } catch (error) {
        ElMessage.error(error.message)
      }
    })
    .catch(() => {
      ElMessage.info('取消删除')
    })
}

const update = (data) => {
  dialogTitle.value = '修改菜单'
  formdata.value = { ...data }
  dialogFormVisible.value = true
}

const add = () => {
  dialogTitle.value = '新增菜单'
  if (menuDataFormRef.value) menuDataFormRef.value.resetFields()
  formdata.value = {
    name: '',
    intro: '',
    path: '',
    sort: '',
    pid: '',
    icon: '',
    status: 1,
    useage: 0,
    menuType: 0,
    resourse: '',
    system: queryInfo.system
  }
  dialogFormVisible.value = true
}

const dialogSubmit = async () => {
  try {
    await menuDataFormRef.value.validate()

    let res = null
    if (dialogTitle.value === '新增菜单') {
      res = await menuApi.addMenu(formdata.value)
    } else {
      res = await menuApi.updateMenuById(formdata.value.id, formdata.value)
    }

    ElMessage.success(res.msg)
    dialogFormVisible.value = false
    getMenuList()
  } catch (error) {
    if (error.message) {
      ElMessage.error(error.message)
    }
  }
}

const addResourse = (data) => {
  resourseFormData.value = {
    ...resourseFormData.value,
    pid: data.id,
    useage: data.useage,
    menuType: 1,
    system: queryInfo.system
  }
  resourseFormVisible.value = true
}

const resourseSubmit = async () => {
  try {
    await resourseFormRef.value.validate()
    const res = await menuApi.addMenu(resourseFormData.value)
    ElMessage.success(res.msg)
    resourseFormVisible.value = false
    getMenuList()
  } catch (error) {
    ElMessage.error(error.message)
  }
}

const expandTable = (arr, state) => {
  arr.forEach((i) => {
    menuTreeRef.value.toggleRowExpansion(i, state)
    if (i.children) {
      expandTable(i.children, state)
    }
  })
}

const setAllExpand = (state) => {
  expandTable(list.value, state)
}

// Lifecycle
onMounted(() => {
  getMenuList()
})
</script>

<style lang="less" scoped>
.el-tree {
  padding-top: 15px;
  font-size: 14px;
}
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
.box-card {
  height: calc(100vh - 190px);
  overflow: auto;
}
</style>
