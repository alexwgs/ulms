/**axios封装
 * 请求拦截、相应拦截、错误统一处理
 */
import axios from 'axios'
import QS from 'qs'
import router from '../router/index'
import axiosRetry from 'axios-retry'
import { MessagePlugin } from 'tdesign-vue-next'

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
      config.data = JSON.stringify(config.data)
      config.headers['Content-Type'] = 'application/json'
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
    return Promise.error(error)
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
      if (res.code >= 500) MessagePlugin.error(res.msg)
      else if (res.code >= 400) MessagePlugin.warning(res.msg)
      else if (res.code >= 300) MessagePlugin.info(res.msg)
      return Promise.resolve(res)
    } else {
      return Promise.reject(response) //失败
    }
  },
  // 服务器状态码不是200的情况
  (error) => {
    console.error(error)
    if (error.response.status) {
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
        // 其他错误，直接抛出错误提示
        default:
      }
      return Promise.reject(error.response)
    }
  }
)
/**
 * get方法，对应get请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
const $get = (url, params) => {
  return httpInstance
    .get(url, { params })
    .then((res) => res)
    .catch((err) => Promise.reject(err))
}
/**
 * post方法，对应post请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
const $post = (url, params) => {
  return httpInstance
    .post(url, QS.stringify(params))
    .then((res) => res)
    .catch((err) => Promise.reject(err))
}

//q:下面是vue3必须加的，vue2不需要，只需要暴露出去get，post方法就可以

const downloadAxios = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 5000, //响应时间
  retry: 3, //设置全局重试请求次数（最多重试几次请求）
  withCredentials: true, // default
  retryDelay: 1000 //设置全局请求间隔
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
    return Promise.error(error)
  }
)
const downloadExcel = (url, params, fileName) => {
  downloadAxios.defaults.headers.get['Content-Type'] =
    'application/x-www-form-urlencoded'
  downloadAxios.defaults.responseType = 'blob'
  downloadAxios
    .get(url, {
      params: params
    })
    .then((res) => {
      const blob = new Blob([res.data], { type: 'application/vnd.ms-excel' })
      let contentDisposition = ''
      if (res.headers['content-disposition'])
        contentDisposition = res.headers['content-disposition']
      if (res.headers['Content-Disposition'])
        contentDisposition = res.headers['Content-Disposition']
      if (fileName == undefined || fileName == '') fileName = '文件下载.xlsx'
      if (contentDisposition == undefined || contentDisposition == '') {
      } else {
        fileName = decodeURI(
          contentDisposition.substring(
            contentDisposition.indexOf('filename=') + 9
          )
        )
      }
      if ('download' in document.createElement('a')) {
        // 非IE下载
        const elink = document.createElement('a')
        elink.download = fileName
        elink.style.display = 'none'
        elink.href = URL.createObjectURL(blob)
        document.body.appendChild(elink)
        elink.click()
        URL.revokeObjectURL(elink.href) // 释放URL 对象
        document.body.removeChild(elink)
      } else {
        // IE10+下载
        navigator.msSaveBlob(blob, fileName)
      }
    })
    .catch((err) => {
      console.error(err)
    })
}

// 请求工具函数
export { httpInstance, $get, $post, downloadExcel }
