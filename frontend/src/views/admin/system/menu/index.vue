<template>
  <t-alert
    title="操作说明"
    theme="info"
    :closable="false"
    message="请正确使用菜单信息：1.菜单名称为前端菜单显示名称。2.排序越小，显示越靠前。3.最后一集为资源点配置。"
  />
  <t-card class="box-card">
    <t-row :gutter="20">
      <t-col :span="3">
        <t-select
          size="small"
          v-model="queryInfo.system"
          placeholder="选择菜单系统"
          @change="getMenuList"
        >
          <t-option
            v-for="item in dictStore.dictList.sys_menu_system"
            :key="item.code"
            :label="item.codeval"
            :value="item.code"
          />
        </t-select>
      </t-col>
      <t-col :span="2">
        <t-radio-group
          size="small"
          v-model="queryInfo.useage"
          @change="getMenuList"
        >
          <t-radio-button
            v-for="item in dictStore.dictList.sys_menu_usage"
            :key="item.code"
            :value="parseInt(item.code)"
          >
            {{ item.codeval }}
          </t-radio-button>
        </t-radio-group>
      </t-col>
      <t-col :span="5">
        <div class="buttons">
          <t-button size="small" @click="setAllExpand(true)"
            >展开所有节点</t-button
          >
          <t-button size="small" @click="setAllExpand(false)"
            >收起所有节点</t-button
          >
          <t-button size="small" theme="primary" @click="add"
            >添加菜单</t-button
          >
        </div>
      </t-col>
    </t-row>
    <CustomTable
      ref="menuTreeRef"
      size="small"
      :data="list"
      height="calc(100vh - 300px)"
      style="margin-top: 20px"
      row-key="id"
      expand-all
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <TableColumn colKey="name" label="名称" width="180">
        <template #default="{ row }">
          <i :class="row.icon"></i> {{ row.name }}
        </template>
      </TableColumn>
      <TableColumn colKey="sort" label="序号" width="80"></TableColumn>
      <TableColumn
        prop="resourse"
        label="资源"
        width="200"
        ellipsis></TableColumn>
      <TableColumn colKey="status" label="状态" width="80">
        <template #default="{ row }">
          <t-tag
            v-for="item in dictStore.dictList.sys_menu_status"
            v-show="row.status == item.code"
            :key="item.code"
            size="small"
            :theme="item.code == 0 ? 'danger' : 'success'"
          >
            {{ item.codeval }}
          </t-tag>
        </template>
      </TableColumn>
      <TableColumn
        prop="intro"
        label="简介"
        ellipsis></TableColumn>
      <TableColumn label="操作" width="180">
        <template #default="{ row }">
          <t-button
            v-if="row.menuType == 0 && row.pid != 0"
            theme="primary" size="small"
            @click="() => addResourse(row)"
            circle
          ><template #icon><DynamicIcon name="plus" /></template></t-button>
          <t-button
            theme="warning"
            size="small" @click="() => update(row)"
            circle
          ><template #icon><DynamicIcon name="edit" /></template></t-button>
          <t-button
            v-if="row.menuType == 0"
            theme="danger"
            size="small" @click="() => remove(row)"
            circle
          ><template #icon><DynamicIcon name="delete" /></template></t-button>
        </template>
      </TableColumn>
    </CustomTable>

    <t-dialog
      :header="dialogTitle"
      :close-on-overlay-click="false"
      v-model:visible="dialogFormVisible"
    >
      <t-form :data="formdata" :rules="formRules" ref="menuDataFormRef">
        <t-form-item label="系统" :label-width="formLabelWidth" name="system">
          <t-select
            size="small"
            v-model="formdata.system"
            placeholder="选择菜单系统"
          >
            <t-option
              v-for="item in dictStore.dictList.sys_menu_system"
              :key="item.code"
              :label="item.codeval"
              :value="item.code"
            />
          </t-select>
        </t-form-item>
        <t-form-item
          label="菜单名称"
          :label-width="formLabelWidth"
          prop="name"
        >
          <t-input
            size="small"
            v-model="formdata.name"
            autocomplete="off"
          ></t-input>
        </t-form-item>
        <t-form-item
          label="菜单描述"
          :label-width="formLabelWidth"
          prop="intro"
        >
          <t-textarea size="small"
            :autosize="{ minRows: 2, maxRows: 4 }"
            placeholder="菜单描述"
            v-model="formdata.intro" />
        </t-form-item>
        <t-form-item
          label="菜单地址"
          :label-width="formLabelWidth"
          prop="path"
        >
          <t-input
            size="small"
            v-model="formdata.path"
            autocomplete="off"
          ></t-input>
        </t-form-item>
        <t-form-item
          label="菜单排序"
          :label-width="formLabelWidth"
          prop="sort"
        >
          <t-input
            size="small"
            v-model="formdata.sort"
            type="number"
            autocomplete="off"
          ></t-input>
        </t-form-item>
        <t-form-item label="上级菜单" :label-width="formLabelWidth" name="pid">
          <t-select
            size="small"
            v-model="formdata.pid"
            placeholder="选择上级菜单"
          >
            <t-option label="一级菜单" :value="0"></t-option>
            <t-option
              v-for="item in list"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </t-select>
        </t-form-item>
        <t-form-item
          label="图标样式"
          :label-width="formLabelWidth"
          prop="icon"
        >
          <icon-select v-model="formdata.icon" />
        </t-form-item>
        <t-form-item
          label="菜单状态"
          :label-width="formLabelWidth"
          prop="status"
        >
          <t-select
            size="small"
            v-model="formdata.status"
            placeholder="选择菜单是否展示"
          >
            <t-option
              v-for="item in statusOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </t-select>
        </t-form-item>
        <t-form-item
          label="前台后台"
          :label-width="formLabelWidth"
          prop="useage"
        >
          <t-select
            size="small"
            v-model="formdata.useage"
            placeholder="选择菜单类型"
          >
            <t-option
              v-for="item in dictStore.dictList.sys_menu_usage"
              :key="item.code"
              :label="item.codeval"
              :value="parseInt(item.code)"
            />
          </t-select>
        </t-form-item>
        <t-form-item
          label="菜单类型"
          :label-width="formLabelWidth"
          prop="menuType"
        >
          <t-select
            size="small"
            v-model="formdata.menuType"
            placeholder="选择菜单类型"
          >
            <t-option
              v-for="item in dictStore.dictList.sys_menu_type"
              :key="item.code"
              :label="item.codeval"
              :value="parseInt(item.code)"
            />
          </t-select>
        </t-form-item>
        <t-form-item
          label="资源名称"
          :label-width="formLabelWidth"
          prop="resourse"
        >
          <t-input
            size="small"
            v-model="formdata.resourse"
            autocomplete="off"
            :disabled="formdata.menuType == 0"
          ></t-input>
        </t-form-item>
      </t-form>
      <template #footer>
        <t-button size="small" @click="dialogFormVisible = false"
          >取 消</t-button
        >
        <t-button size="small" theme="primary" @click="dialogSubmit"
          >确 定</t-button
        >
      </template>
    </t-dialog>

    <t-dialog
      header="资源管理"
      :close-on-overlay-click="false"
      v-model:visible="resourseFormVisible"
    >
      <t-form
        :data="resourseFormData"
        :rules="formRules"
        ref="resourseFormRef"
      >
        <t-form-item
          label="资源名称"
          :label-width="formLabelWidth"
          prop="name"
        >
          <t-input
            size="small"
            v-model="resourseFormData.name"
            autocomplete="off"
          ></t-input>
        </t-form-item>
        <t-form-item
          label="资源代码"
          :label-width="formLabelWidth"
          prop="resourse"
        >
          <t-input
            size="small"
            v-model="resourseFormData.resourse"
            autocomplete="off"
          ></t-input>
        </t-form-item>
        <t-form-item
          label="资源描述"
          :label-width="formLabelWidth"
          prop="intro"
        >
          <t-textarea size="small"
            :autosize="{ minRows: 2, maxRows: 4 }"
            placeholder="资源描述"
            v-model="resourseFormData.intro" />
        </t-form-item>
        <t-form-item label="排序" :label-width="formLabelWidth" name="sort">
          <t-input
            size="small"
            v-model="resourseFormData.sort"
            type="number"
            autocomplete="off"
          ></t-input>
        </t-form-item>
        <t-form-item label="上级菜单" :label-width="formLabelWidth" name="pid">
          <t-input
            size="small"
            v-model="resourseFormData.pid"
            disabled
          ></t-input>
        </t-form-item>
        <t-form-item
          label="资源状态"
          :label-width="formLabelWidth"
          prop="status"
        >
          <t-select
            size="small"
            v-model="resourseFormData.status"
            placeholder="选择菜单是否展示"
          >
            <t-option
              v-for="item in statusOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </t-select>
        </t-form-item>
        <t-form-item
          label="前台后台"
          :label-width="formLabelWidth"
          prop="useage"
        >
          <t-input
            size="small"
            v-model="resourseFormData.useage"
            readonly
          ></t-input>
        </t-form-item>
        <t-form-item
          label="菜单类型"
          :label-width="formLabelWidth"
          prop="menuType"
        >
          <t-select
            size="small"
            v-model="resourseFormData.menuType"
            placeholder="选择菜单类型"
            disabled
          >
            <t-option
              v-for="item in dictStore.dictList.sys_menu_type"
              :key="item.code"
              :label="item.codeval"
              :value="parseInt(item.code)"
            />
          </t-select>
        </t-form-item>
      </t-form>
      <template #footer>
        <t-button size="small" @click="resourseFormVisible = false"
          >取 消</t-button
        >
        <t-button size="small" theme="primary" @click="resourseSubmit"
          >确 定</t-button
        >
      </template>
    </t-dialog>
  </t-card>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { MessagePlugin, DialogPlugin } from 'tdesign-vue-next'
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
    MessagePlugin.error(error.message)
  }
}

const remove = (data) => {
  DialogPlugin.confirm(
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
        MessagePlugin.success(res.msg)
        getMenuList()
      } catch (error) {
        MessagePlugin.error(error.message)
      }
    })
    .catch(() => {
      MessagePlugin.info('取消删除')
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
  const valid = await menuDataFormRef.value.validate()
  if (valid !== true) return

  try {
    let res = null
    if (dialogTitle.value === '新增菜单') {
      res = await menuApi.addMenu(formdata.value)
    } else {
      res = await menuApi.updateMenuById(formdata.value.id, formdata.value)
    }

    MessagePlugin.success(res.msg)
    dialogFormVisible.value = false
    getMenuList()
  } catch (error) {
    if (error.message) {
      MessagePlugin.error(error.message)
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
  const valid = await resourseFormRef.value.validate()
  if (valid !== true) return

  try {
    const res = await menuApi.addMenu(resourseFormData.value)
    MessagePlugin.success(res.msg)
    resourseFormVisible.value = false
    getMenuList()
  } catch (error) {
    MessagePlugin.error(error.message)
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
.t-tree {
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
