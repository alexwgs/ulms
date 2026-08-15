<template>
  <PageTips
    title="操作说明"
    theme="info"
    :closable="false"
    message="请正确使用菜单信息：1.菜单名称为前端菜单显示名称。2.排序越小，显示越靠前。3.最后一集为资源点配置。"
  />
  <t-card class="management-card">
    <t-form :data="queryInfo" label-width="80px" colon class="filter-form">
      <t-row :gutter="[24, 24]">
        <t-col :span="3">
          <t-form-item label="菜单系统" name="system">
            <t-select
              size="small"
              v-model="queryInfo.system"
              placeholder="选择菜单系统"
              @change="getMenuList"
            >
              <t-option
                v-for="item in (dictStore.dictList?.sys_menu_system || [])"
                :key="item.code"
                :label="item.codeval"
                :value="item.code"
              />
            </t-select>
          </t-form-item>
        </t-col>
        <t-col :span="6" class="operation-container">
          <t-button variant="outline" size="small" @click="setAllExpand(true)"
            >展开所有节点</t-button
          >
          <t-button variant="outline" size="small" @click="setAllExpand(false)"
            >收起所有节点</t-button
          >
          <t-button variant="outline" size="small" theme="primary" @click="add"
            >添加菜单</t-button
          >
        </t-col>
      </t-row>
    </t-form>
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
            v-for="item in (dictStore.dictList?.sys_menu_status || [])"
            v-show="row.status == item.code"
            :key="item.code"
            size="small"
            :theme="item.code == 0 ? 'danger' : 'success'"
           variant="light">
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
          <t-button variant="outline"
            v-if="row.menuType == 0 && row.pid != 0"
            theme="primary" size="small"
            @click="() => addResourse(row)"
           
          >新增</t-button>
          <t-button variant="outline"
            theme="default"
            size="small" @click="() => update(row)"
           
          >编辑</t-button>
          <t-button variant="outline"
            v-if="row.menuType == 0"
            theme="danger"
            size="small" @click="() => remove(row)"
           
          >删除</t-button>
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
              v-for="item in (dictStore.dictList?.sys_menu_system || [])"
              :key="item.code"
              :label="item.codeval"
              :value="item.code"
            />
          </t-select>
        </t-form-item>
        <t-form-item
          label="菜单名称"
          :label-width="formLabelWidth"
          name="name"
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
          name="intro"
        >
          <t-textarea size="small"
            :autosize="{ minRows: 2, maxRows: 4 }"
            placeholder="菜单描述"
            v-model="formdata.intro" />
        </t-form-item>
        <t-form-item
          label="菜单地址"
          :label-width="formLabelWidth"
          name="path"
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
          name="sort"
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
          name="icon"
        >
          <icon-select v-model="formdata.icon" />
        </t-form-item>
        <t-form-item
          label="菜单状态"
          :label-width="formLabelWidth"
          name="status"
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
          name="useage"
        >
          <t-select
            size="small"
            v-model="formdata.useage"
            placeholder="选择菜单类型"
          >
            <t-option
              v-for="item in (dictStore.dictList?.sys_menu_usage || [])"
              :key="item.code"
              :label="item.codeval"
              :value="parseInt(item.code)"
            />
          </t-select>
        </t-form-item>
        <t-form-item
          label="菜单类型"
          :label-width="formLabelWidth"
          name="menuType"
        >
          <t-select
            size="small"
            v-model="formdata.menuType"
            placeholder="选择菜单类型"
          >
            <t-option
              v-for="item in (dictStore.dictList?.sys_menu_type || [])"
              :key="item.code"
              :label="item.codeval"
              :value="parseInt(item.code)"
            />
          </t-select>
        </t-form-item>
        <t-form-item
          label="资源名称"
          :label-width="formLabelWidth"
          name="resourse"
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
        <t-button variant="outline" size="small" @click="dialogFormVisible = false"
          >取 消</t-button
        >
        <t-button variant="outline" size="small" theme="primary" @click="dialogSubmit"
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
          name="name"
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
          name="resourse"
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
          name="intro"
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
          name="status"
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
          name="useage"
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
          name="menuType"
        >
          <t-select
            size="small"
            v-model="resourseFormData.menuType"
            placeholder="选择菜单类型"
            disabled
          >
            <t-option
              v-for="item in (dictStore.dictList?.sys_menu_type || [])"
              :key="item.code"
              :label="item.codeval"
              :value="parseInt(item.code)"
            />
          </t-select>
        </t-form-item>
      </t-form>
      <template #footer>
        <t-button variant="outline" size="small" @click="resourseFormVisible = false"
          >取 消</t-button
        >
        <t-button variant="outline" size="small" theme="primary" @click="resourseSubmit"
          >确 定</t-button
        >
      </template>
    </t-dialog>
  </t-card>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import IconSelect from './components/IconSelect.vue'
import { menuApi } from '@/api/system/menu'
import { useDictStore } from '@/stores'
import { useConfirm } from '@/hooks/useConfirm'

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
  system: 'a6squre'
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

const remove = async (data) => {
  const { confirm: confirmDialog } = useConfirm()
  const ok = await confirmDialog(
    '此操作将永久删除该记录,同时会删除子菜单, 是否继续?',
    { title: '提示' }
  )
  if (!ok) {
    MessagePlugin.info('取消删除')
    return
  }
  try {
    const res = await menuApi.deleteMenu(data.id, data.pid)
    MessagePlugin.success(res.msg)
    getMenuList()
  } catch (error) {
    MessagePlugin.error(error.message)
  }
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
    system: queryInfo.value.system
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
    system: queryInfo.value.system
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

const setAllExpand = (state) => {
  menuTreeRef.value?.setAllExpanded(state)
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
.management-card {
  height: calc(100vh - 190px);
  overflow: auto;
}
</style>
