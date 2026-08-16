<template>
  <div class="emoji-board">
    <!-- 分组切换（最近 / 笑脸 / 手势 / 符号 / 动物 / 食物 / 物品） -->
    <div class="emoji-tabs">
      <button
        v-for="(group, key) in GROUPS"
        :key="key"
        class="emoji-tab"
        :class="{ active: activeGroup === key }"
        type="button"
        :title="group.name"
        @click="activeGroup = key"
      >
        {{ group.icon }}
      </button>
    </div>

    <div class="emoji-body">
      <!-- 最近使用（无则提示） -->
      <div v-if="activeGroup === 'recent'" class="emoji-grid">
        <button
          v-for="(e, i) in recentEmojis"
          :key="'r' + i"
          type="button"
          class="emoji-cell"
          @click="select(e)"
        >
          {{ e }}
        </button>
        <div v-if="!recentEmojis.length" class="recent-empty">还没有常用表情，点一个试试</div>
      </div>
      <div v-else class="emoji-grid">
        <button
          v-for="e in GROUPS[activeGroup].emojis"
          :key="e"
          type="button"
          class="emoji-cell"
          @click="select(e)"
        >
          {{ e }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

// 离线 emoji 面板：全部为本地 Unicode 字符，不依赖任何网络/CDN 资源，
// 内网、离线环境下同样可用（由系统 emoji 字体渲染）。
const RECENT_KEY = 'oht-emoji-recent'
const RECENT_MAX = 12

const GROUPS = {
  recent: { name: '最近使用', icon: '🕘', emojis: [] },
  smileys: {
    name: '笑脸',
    icon: '😀',
    emojis: ['😀', '😁', '😂', '🤣', '😊', '😇', '🙂', '😉', '😍', '🥰', '😘', '😜', '🤪', '🤔', '🤨', '😐', '😴', '🥱', '😭', '😤', '😡', '🤯', '😱', '😨']
  },
  hands: {
    name: '手势',
    icon: '👍',
    emojis: ['👍', '👎', '👌', '✌️', '🤞', '🤟', '🤘', '👏', '🙌', '🙏', '🤝', '💪', '✊', '👊', '🤙', '🫶']
  },
  symbols: {
    name: '符号',
    icon: '❤️',
    emojis: ['❤️', '🧡', '💛', '💚', '💙', '💜', '🖤', '🤍', '💯', '💢', '💥', '💫', '✨', '⭐', '🌟', '🔥', '⚡', '🌈', '☀️', '🌙', '❄️', '💧', '✅', '❌']
  },
  animals: {
    name: '动物',
    icon: '🐱',
    emojis: ['🐶', '🐱', '🐭', '🐹', '🐰', '🦊', '🐻', '🐼', '🐨', '🐯', '🦁', '🐮', '🐷', '🐸', '🐵', '🙈', '🙉', '🙊', '🐔', '🐧', '🐦', '🦄']
  },
  food: {
    name: '食物',
    icon: '🍜',
    emojis: ['🍎', '🍊', '🍋', '🍌', '🍉', '🍇', '🍓', '🍑', '🍒', '🥝', '🍅', '🥥', '🍞', '🧀', '🍔', '🍟', '🍕', '🌭', '🥪', '🌮', '🍜', '🍣', '🍦', '☕']
  },
  items: {
    name: '物品',
    icon: '📌',
    emojis: ['⚽', '🏀', '🎯', '🎮', '🎲', '🎸', '🎹', '🎤', '🎬', '📱', '💻', '⌚', '📷', '🎁', '🎉', '🎊', '📌', '📢', '🔔', '⏰', '📅', '🗂️', '🔒', '🔑']
  }
}

const activeGroup = ref('smileys')
const recentEmojis = ref([])

const readRecent = () => {
  try {
    const cache = localStorage.getItem(RECENT_KEY)
    recentEmojis.value = cache ? JSON.parse(cache) : []
  } catch {
    recentEmojis.value = []
  }
}
readRecent()

const emit = defineEmits(['select'])

const select = (emoji) => {
  // 记录最近使用（去重、置顶、限量）
  const next = [emoji, ...recentEmojis.value.filter((e) => e !== emoji)].slice(0, RECENT_MAX)
  recentEmojis.value = next
  try {
    localStorage.setItem(RECENT_KEY, JSON.stringify(next))
  } catch {
    // localStorage 不可用时静默降级（只影响最近使用记忆）
  }
  emit('select', emoji)
}
</script>

<style lang="less" scoped>
.emoji-board {
  width: 320px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 16px rgba(23, 35, 59, 0.12);
  border: 1px solid #e8edf5;
  overflow: hidden;
}

.emoji-tabs {
  display: flex;
  align-items: center;
  gap: 2px;
  padding: 8px 8px 6px;
  border-bottom: 1px solid #eef1f6;
  background: #f8fafd;

  .emoji-tab {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 30px;
    font-size: 17px;
    line-height: 1;
    background: transparent;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    transition: background 0.15s ease;

    &:hover {
      background: #eef3fb;
    }

    &.active {
      background: #e2ecff;
    }
  }
}

.emoji-body {
  height: 216px;
  overflow-y: auto;
  padding: 8px;
}

.emoji-grid {
  display: grid;
  grid-template-columns: repeat(8, 1fr);
  gap: 2px;

  .emoji-cell {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 34px;
    height: 34px;
    font-size: 21px;
    line-height: 1;
    background: transparent;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    transition: background 0.12s ease, transform 0.12s ease;

    &:hover {
      background: #eef3fb;
      transform: scale(1.12);
    }

    &:focus-visible {
      outline: 2px solid var(--td-brand-color);
      outline-offset: -1px;
    }
  }
}

.recent-empty {
  grid-column: 1 / -1;
  padding: 40px 0;
  text-align: center;
  font-size: 13px;
  color: var(--td-text-color-placeholder);
}
</style>
