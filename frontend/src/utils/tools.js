import DOMPurify from 'dompurify'

/**
 * Sanitize HTML string to prevent XSS.
 * Allows safe formatting tags while stripping scripts and event handlers.
 */
export function sanitizeHtml(dirty) {
  if (!dirty) return ''
  return DOMPurify.sanitize(dirty)
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
