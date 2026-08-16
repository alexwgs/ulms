<!-- IconSelect.vue -->
<template>
  <t-popup
    v-model:visible="popoverVisible"
    placement="bottom"
    width="400px"
    trigger="click"
  >
    <t-input
      :model-value="modelValue"
      placeholder="点击选择图标"
      class="icon-select-input"
      readonly
    >
      <template #prefix>
        <DynamicIcon v-if="modelValue" :name="modelValue" />
      </template>
    </t-input>

    <!-- 新增图标列表容器 -->
    <template #content>
      <div class="icon-grid">
        <div
          v-for="(icon, index) in icons"
          :key="index"
          class="icon-item"
          @click="selectIcon(icon)"
        >
          <t-icon class="icon">
          <component :is="icon.component" />
        </t-icon>
        <span class="icon-name">{{ icon.name }}</span>
      </div>
    </div>
    </template>
  </t-popup>
</template>

<script setup>
import { ref } from 'vue'
// 审计优化（F-M2）：原 import * as TDesignIcons 全量引入 1000+ 图标导致
// 5.7MB chunk。改为按需引入常用菜单图标（与 DynamicIcon 清单保持一致）。
import {
  HomeIcon, SettingIcon, UserIcon, UserCircleIcon, ViewModuleIcon,
  MenuIcon, NotificationIcon, SwapIcon,
  EditIcon, DeleteIcon, SearchIcon, AddIcon, MinusIcon,
  CloseIcon, CheckIcon, CheckCircleIcon, CheckCircleFilledIcon,
  ErrorCircleIcon, ErrorCircleFilledIcon, InfoCircleFilledIcon,
  HelpCircleFilledIcon, CloseCircleFilledIcon,
  ChevronDownIcon, ChevronLeftIcon, ChevronRightIcon, ChevronUpIcon,
  ArrowUpIcon, ArrowDownIcon, RollbackIcon,
  UploadIcon, DownloadIcon, RefreshIcon,
  ShareIcon, StarIcon, StarFilledIcon,
  ImageIcon, FileIcon, FolderIcon, FolderOpenIcon,
  LinkIcon, FileCopyIcon, PrintIcon,
  LocationIcon, LockOnIcon, LockOffIcon,
  CallIcon, ChatIcon, ChatBubbleIcon, MailIcon,
  BrowseIcon, BrowseOffIcon,
  DiscountIcon, TicketIcon, ShopIcon, MoneyIcon, CartIcon, GiftIcon,
  ChartIcon, DashboardIcon, ChartBarIcon, ChartPieIcon, ChartLineIcon,
  TimeIcon, AlarmIcon, CalendarIcon,
  ViewListIcon, ViewAgendaIcon, UnfoldMoreIcon, UnfoldLessIcon,
  GestureClickIcon, FilterIcon, OrderDescendingIcon, OrderAscendingIcon,
  VideoIcon, PlayCircleIcon, PauseCircleIcon,
  EarphoneIcon, MicrophoneIcon, PoweroffIcon,
  DesktopIcon, MobileIcon, CpuIcon,
  CompassIcon, BookOpenIcon, BookIcon,
  HighlightIcon, CutIcon,
  FullscreenIcon, EllipsisIcon, ToolsIcon,
  BuildingIcon, EducationIcon, BookmarkIcon
} from 'tdesign-icons-vue-next'

const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  }
})

const emit = defineEmits(['update:modelValue'])
// 名称转换方法
const toKebabCase = (str) => {
  return str.replace(/([a-z])([A-Z])/g, '$1-$2').toLowerCase()
}

// 按需图标清单（kebab-case 名称 → 组件）
const iconMap = {
  'home': HomeIcon,
  'setting': SettingIcon,
  'user': UserIcon,
  'user-circle': UserCircleIcon,
  'view-module': ViewModuleIcon,
  'menu': MenuIcon,
  'notification': NotificationIcon,
  'swap': SwapIcon,
  'edit': EditIcon,
  'delete': DeleteIcon,
  'search': SearchIcon,
  'add': AddIcon,
  'minus': MinusIcon,
  'close': CloseIcon,
  'check': CheckIcon,
  'check-circle': CheckCircleIcon,
  'check-circle-filled': CheckCircleFilledIcon,
  'error-circle': ErrorCircleIcon,
  'error-circle-filled': ErrorCircleFilledIcon,
  'info-circle-filled': InfoCircleFilledIcon,
  'help-circle-filled': HelpCircleFilledIcon,
  'close-circle-filled': CloseCircleFilledIcon,
  'chevron-down': ChevronDownIcon,
  'chevron-left': ChevronLeftIcon,
  'chevron-right': ChevronRightIcon,
  'chevron-up': ChevronUpIcon,
  'arrow-up': ArrowUpIcon,
  'arrow-down': ArrowDownIcon,
  'rollback': RollbackIcon,
  'upload': UploadIcon,
  'download': DownloadIcon,
  'refresh': RefreshIcon,
  'share': ShareIcon,
  'star': StarIcon,
  'star-filled': StarFilledIcon,
  'image': ImageIcon,
  'file': FileIcon,
  'folder': FolderIcon,
  'folder-open': FolderOpenIcon,
  'link': LinkIcon,
  'file-copy': FileCopyIcon,
  'print': PrintIcon,
  'location': LocationIcon,
  'lock-on': LockOnIcon,
  'lock-off': LockOffIcon,
  'call': CallIcon,
  'chat': ChatIcon,
  'chat-bubble': ChatBubbleIcon,
  'mail': MailIcon,
  'browse': BrowseIcon,
  'browse-off': BrowseOffIcon,
  'discount': DiscountIcon,
  'ticket': TicketIcon,
  'shop': ShopIcon,
  'money': MoneyIcon,
  'cart': CartIcon,
  'gift': GiftIcon,
  'chart': ChartIcon,
  'dashboard': DashboardIcon,
  'chart-bar': ChartBarIcon,
  'chart-pie': ChartPieIcon,
  'chart-line': ChartLineIcon,
  'time': TimeIcon,
  'alarm': AlarmIcon,
  'calendar': CalendarIcon,
  'view-list': ViewListIcon,
  'view-agenda': ViewAgendaIcon,
  'unfold-more': UnfoldMoreIcon,
  'unfold-less': UnfoldLessIcon,
  'gesture-click': GestureClickIcon,
  'filter': FilterIcon,
  'order-descending': OrderDescendingIcon,
  'order-ascending': OrderAscendingIcon,
  'video': VideoIcon,
  'play-circle': PlayCircleIcon,
  'pause-circle': PauseCircleIcon,
  'earphone': EarphoneIcon,
  'microphone': MicrophoneIcon,
  'poweroff': PoweroffIcon,
  'desktop': DesktopIcon,
  'mobile': MobileIcon,
  'cpu': CpuIcon,
  'compass': CompassIcon,
  'book-open': BookOpenIcon,
  'book': BookIcon,
  'highlight': HighlightIcon,
  'cut': CutIcon,
  'fullscreen': FullscreenIcon,
  'ellipsis': EllipsisIcon,
  'tools': ToolsIcon,
  'building': BuildingIcon,
  'education': EducationIcon,
  'bookmark': BookmarkIcon
}

// 图标列表数据生成
const icons = Object.entries(iconMap).map(([name, component]) => ({
  name,
  component
}))

// 状态控制
const popoverVisible = ref(false)

// 图标选择处理
const selectIcon = (icon) => {
  emit('update:modelValue', icon.name)
  popoverVisible.value = false
}
</script>

<style scoped>
.icon-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 12px;
  max-height: 400px;
  overflow-y: auto;
}

.icon-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 12px;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.2s;
}

.icon-item:hover {
  background-color: var(--td-brand-color-light);
  transform: scale(1.05);
}

.icon {
  font-size: 24px;
  margin-bottom: 8px;
}

.icon-name {
  font-size: 12px;
  text-align: center;
  word-break: break-all;
}
</style>
