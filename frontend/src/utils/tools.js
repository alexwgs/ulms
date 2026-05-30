/**
 * 下载模板
 * @param {String} fileName 模板文件名带上扩展名
 */
export function downloadTemplate(url, fileName) {
  let a = document.createElement('a')
  a.href = '' + url
  a.download = fileName
  a.style.display = 'none'
  document.body.appendChild(a)
  a.click()
  a.remove()
}

// 下载文件
export const download = (res) => {
  const data = res.data

  // 设置下载文件名称，使用正则取出名称
  const pat = new RegExp('(?<=filename=).*')
  let contentDisposition = ''
  //浏览器问题可能会出现 content-disposition 匹配不到
  if (res.headers['content-disposition'])
    contentDisposition = res.headers['content-disposition']
  if (res.headers['Content-Disposition'])
    contentDisposition = res.headers['Content-Disposition']
  const result = pat.exec(contentDisposition)
  let fileName = result && result[0]
  if (fileName == undefined) {
    fileName = '测试下载.xlsx'
  } else {
    fileName = decodeURIComponent(fileName)
  }

  let url = window.URL.createObjectURL(new Blob([data]))
  let link = document.createElement('a')
  link.style.display = 'none'
  link.href = url
  link.setAttribute('download', fileName)
  document.body.appendChild(link)
  link.click()
}

// 新增获取客户端本地IP地址（通过WebRTC获取内网IP）
export function getLocalIp() {
  return new Promise((resolve, reject) => {
    const ipRegex =
      /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/
    const ips = []

    const pc = new RTCPeerConnection({
      iceServers: []
    })

    pc.createDataChannel('')
    pc.createOffer().then((offer) => pc.setLocalDescription(offer))

    pc.onicecandidate = (ice) => {
      if (!ice || !ice.candidate || !ice.candidate.candidate) {
        pc.close()
        if (ips.length > 0) {
          resolve(ips[0])
        } else {
          resolve('127.0.0.1')
        }
        return
      }

      const line = ice.candidate.candidate
      const match = ipRegex.exec(line)
      if (match) {
        const ip = match[1]
        if (!ips.includes(ip) && ip !== '127.0.0.1') {
          ips.push(ip)
        }
      }
    }

    setTimeout(() => {
      pc.close()
      if (ips.length > 0) {
        resolve(ips[0])
      } else {
        resolve('127.0.0.1')
      }
    }, 2000)
  })
}
