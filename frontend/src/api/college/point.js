import { httpInstance } from '@/utils/request'

export const pointApi = {
  getUserPoint: () =>
    httpInstance({
      url: 'college/point',
      method: 'get'
    })
}