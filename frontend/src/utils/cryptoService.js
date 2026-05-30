import CryptoJS from 'crypto-js'

const secretKey = 'cmbccdWH-wei6130' // 这里是你选择的密钥，需要足够复杂且保密
const iv = '1234567890123456'

// 加密函数
export function encryptData(data) {
  const encrypted = CryptoJS.AES.encrypt(data, CryptoJS.enc.Utf8.parse(secretKey), {
    iv: CryptoJS.enc.Utf8.parse(iv),
    mode: CryptoJS.mode.CBC,
    padding: CryptoJS.pad.Pkcs7,
  })
  return encrypted.toString()
}
// 解密函数

export function decryptData(ciphertext) {
  const decrypted = CryptoJS.AES.decrypt(ciphertext, secretKey, {
    iv: CryptoJS.enc.Utf8.parse(iv),
    mode: CryptoJS.mode.CBC,
    padding: CryptoJS.pad.Pkcs7,
  })
  return decrypted.toString(CryptoJS.enc.Utf8)
}
