import { createPinia } from 'pinia'
import useUserStore from './modules/user'
import useAppStore from './modules/app'
import useMenuStore from './modules/menu'
import useDictStore from './modules/dict'
import useOhtStore from './modules/oht'
import useWsStore from './modules/ws'

const pinia = createPinia()

export {
  useUserStore,
  useAppStore,
  useMenuStore,
  useDictStore,
  useOhtStore,
  useWsStore
}
export default pinia
