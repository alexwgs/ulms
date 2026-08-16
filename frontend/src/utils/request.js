/**axios封装
 * 请求拦截、相应拦截、错误统一处理
 */
import axios from 'axios'
import router from '../router/index'
import axiosRetry from 'axios-retry'
import { MessagePlugin } from 'tdesign-vue-next'
import {
  getBusinessErrorMessage,
  getHttpErrorMessage,
  NETWORK_ERROR_MESSAGE,
  TIMEOUT_MESSAGE
} from './errorCodes'

const httpInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 30000,
  withCredentials: true
})

axiosRetry(httpInstance, {
  retries: 3,
  retryDelay: (retryCount) => retryCount * 1000,
  retryCondition: (error) =>
    axiosRetry.isNetworkOrIdempotentRequestError(error) ||
    error?.response?.status >= 500
})

// 配置请求头
//  axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';

httpInstance.interceptors.request.use(
  //响应拦截
  async (config) => {
    if (config.method === 'post') {
      // FormData 用于文件上传，不需要 JSON 序列化
      if (config.data instanceof FormData) {
        delete config.headers['Content-Type']
      } else if (config.data && typeof config.data === 'object') {
        // 仅在 data 为对象时序列化（幂等）：axios-retry 重试会重新走本拦截器，
        // 若 data 已是字符串（上次序列化结果）再 stringify 会双重编码导致后端 400
        config.data = JSON.stringify(config.data)
        config.headers['Content-Type'] = 'application/json'
      }
    }
    // 每次发送请求之前判断vuex中是否存在token
    // 如果存在，则统一在http请求的header都加上token，这样后台根据token判断你的登录情况
    // 即使本地存在token，也有可能token是过期的，所以在响应拦截器中要对返回状态进行判断
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = token
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)
// 响应拦截器
httpInstance.interceptors.response.use(
  (response) => {
    const res = response.data
    if (response.status === 200) {
      switch (response.data.code) {
        case 301:
        case 401:
          localStorage.clear()
          sessionStorage.clear()
          router.push('/login')
          break
        default:
      }
      // 审计修复（F-H4）：业务错误统一按对照表提示，后端文案优先、对照表兜底
      if (res.code >= 500) MessagePlugin.error(res.msg || getBusinessErrorMessage(res.code))
      else if (res.code >= 400) MessagePlugin.warning(res.msg || getBusinessErrorMessage(res.code))
      else if (res.code >= 300) MessagePlugin.info(res.msg || getBusinessErrorMessage(res.code))
      return Promise.resolve(res)
    } else {
      return Promise.reject(response) //失败
    }
  },
  // 服务器状态码不是200的情况
  (error) => {
    console.error(error)
    if (error.response) {
      switch (error.response.status) {
        // 401: 未登录
        // 未登录则跳转登录页面，并携带当前页面的路径
        // 在登录成功后返回当前页面，这一步需要在登录页操作。
        case 401:
          localStorage.clear()
          sessionStorage.clear()
          router.push('/login')
          break
        // 403 token过期
        // 登录过期对用户进行提示
        // 清除本地token和清空vuex中token对象
        // 跳转登录页面
        case 403:
          localStorage.clear()
          sessionStorage.clear()
          router.push('/login')
          break
        // 404请求不存在
        case 404:
          break
        // 审计修复（F-H4）：HTTP 5xx 统一提示（原来静默失败，用户无感知）
        default:
          if (error.response.status >= 500) {
            MessagePlugin.error(getHttpErrorMessage(error.response.status))
          }
      }
      return Promise.reject(error.response)
    }
    // 审计修复（F-H4）：网络错误/请求超时（无响应对象）统一提示
    const isTimeout =
      error.code === 'ECONNABORTED' || error.code === 'ETIMEDOUT' || error.message?.includes('timeout')
    MessagePlugin.error(isTimeout ? TIMEOUT_MESSAGE : NETWORK_ERROR_MESSAGE)
    return Promise.reject(error)
  }
)
//q:下面是vue3必须加的，vue2不需要，只需要暴露出去get，post方法就可以

const downloadAxios = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 5000,
  withCredentials: true
})
downloadAxios.interceptors.request.use(
  //响应拦截
  async (config) => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = token
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)
const downloadExcel = (url, params, fileName) => {
  downloadAxios.defaults.headers.get['Content-Type'] =
    'application/x-www-form-urlencoded'
  downloadAxios.defaults.responseType = 'blob'
  return downloadAxios
    .get(url, { params })
    .then((res) => {
      const blob = new Blob([res.data], { type: 'application/vnd.ms-excel' })
      const contentDisposition =
        res.headers['content-disposition'] || res.headers['Content-Disposition'] || ''
      // 从响应头解析文件名（正则匹配带保护，避免 indexOf 越界）
      const match = /filename\*=UTF-8''([^;]+)|filename="?([^";]+)"?/i.exec(contentDisposition)
      if (match) {
        const raw = match[1] || match[2]
        try {
          fileName = decodeURIComponent(raw)
        } catch (e) {
          fileName = raw
        }
      }
      if (!fileName) fileName = '文件下载.xlsx'
      if ('download' in document.createElement('a')) {
        const elink = document.createElement('a')
        elink.download = fileName
        elink.style.display = 'none'
        elink.href = URL.createObjectURL(blob)
        document.body.appendChild(elink)
        elink.click()
        URL.revokeObjectURL(elink.href)
        document.body.removeChild(elink)
      } else {
        navigator.msSaveBlob(blob, fileName)
      }
    })
    .catch((err) => {
      console.error('文件下载失败', err)
      throw err
    })
}

// 请求工具函数
export { httpInstance, downloadExcel }
