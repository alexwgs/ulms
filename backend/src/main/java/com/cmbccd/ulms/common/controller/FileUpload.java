package com.cmbccd.ulms.common.controller;

import com.cmbccd.ulms.common.config.UlmsConfig;
import com.cmbccd.ulms.common.domain.FileObject;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.domain.UserFiles;
import com.cmbccd.ulms.sys.service.UserFilesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Random;

@RequestMapping("/upload")
@Controller
public class FileUpload {
	Logger log = LoggerFactory.getLogger(FileUpload.class);

	@Resource
	private UlmsConfig ulmsConfig;

	@Resource
	private UserFilesService userFilesService;

	@RequestMapping(value = "/file/{path}", method = RequestMethod.POST)
	@ResponseBody
	public Msg uploadFile(HttpServletRequest req, MultipartHttpServletRequest multiReq,
			@PathVariable("path") String path) {
		if (path.contains("..") || path.contains("/") || path.contains("\\")) {
			return Msg.error("非法的上传路径");
		}
		if (!path.matches("[a-zA-Z0-9_\\-]+")) {
			return Msg.error("上传路径包含非法字符");
		}
		FileObject fileObject = new FileObject();
		String uploadFilePath = Objects.requireNonNull(multiReq.getFile("file")).getOriginalFilename();

		if(Util.isNullorEmpty(uploadFilePath)){
			return Msg.error("文件上传失败，未获取到文件路径！");
		}
		Path filePath = Paths.get(uploadFilePath);
		String fileName = filePath.getFileName().toString();
		String realFileName = fileName.substring(0, fileName.lastIndexOf('.'));

		String uploadFileName = Util.getCurrentTimestamp() + ((new Random().nextInt(1000)) + 1000);
		String uploadFileSuffix = "";
		if (uploadFilePath != null && uploadFilePath.contains(".")) {
			uploadFileSuffix = uploadFilePath.substring(uploadFilePath.lastIndexOf('.') + 1);
		}

		FileOutputStream fos = null;
		InputStream fis = null;
		String fullPath = ulmsConfig.getUploadPath() + path;

		UserFiles record = new UserFiles();
		try {
			fis = Objects.requireNonNull(multiReq.getFile("file")).getInputStream();
			File file = new File(fullPath + "/" + uploadFileName + "." + uploadFileSuffix);
			if (!file.getParentFile().exists()) {
				if (!file.getParentFile().mkdirs()) {
					log.error("无法创建上传目录: {}", file.getParentFile().getAbsolutePath());
					return Msg.error("服务器上传目录配置异常，请联系管理员");
				}
			}
			fos = new FileOutputStream(file);
			byte[] temp = new byte[1024];
			int i = fis.read(temp);
			while (i != -1) {
				fos.write(temp, 0, i);
				fos.flush();
				i = fis.read(temp);
			}
		} catch (IOException e) {
			log.error("发生错误！", e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					log.error("发生错误！", e);
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					log.error("发生错误！", e);
				}
			}
		}
		record.setFileId(uploadFileName);
		record.setFileName(realFileName);
		record.setFilePath(path);
		record.setFileSuffix(uploadFileSuffix);
		userFilesService.insertNewUserFiles(record);
		fileObject.setPath("upload/getFile/" + path + "/" + uploadFileName + "." + uploadFileSuffix);
		fileObject.setFileId(uploadFileName);
		fileObject.setFileSuffix(uploadFileSuffix);
		return Msg.success("文件上传成功").put("file", fileObject);
//	    return Msg.success("文件上传成功").add("path","upload/getFile/"+ path+ "/" + uploadFileName + "." + uploadFileSuffix);
	}

	@RequestMapping(value = "/{path}", method = RequestMethod.POST)
	@ResponseBody
	public Msg uploadAvatar(HttpServletRequest req, MultipartHttpServletRequest multiReq,
			@PathVariable("path") String path) {
		// 路径安全校验：防止路径穿越
		if (path.contains("..") || path.contains("/") || path.contains("\\")) {
			return Msg.error("非法的上传路径");
		}
		if (!path.matches("[a-zA-Z0-9_\\-]+")) {
			return Msg.error("上传路径包含非法字符");
		}

		// 获取上传文件的原始文件名
		String uploadFilePath = Objects.requireNonNull(multiReq.getFile("file")).getOriginalFilename();
		// 生成安全的文件名（时间戳+随机数，避免原始文件名泄露）
		String uploadFileName = Util.getCurrentTimestamp() + ((new Random().nextInt(1000)) + 1000);

		if(Util.isNullorEmpty(uploadFilePath)){
			return Msg.error("文件上传失败，未获取到文件路径！");
		}
		// 截取上传文件的后缀并校验
		String uploadFileSuffix = "";
		if (uploadFilePath != null && uploadFilePath.contains(".")) {
			uploadFileSuffix = uploadFilePath.substring(uploadFilePath.lastIndexOf('.') + 1).toLowerCase();
		}
		// 服务端校验：仅允许图片格式（客户端校验仅作为UX提示，此处为安全边界）
		if (!isAllowedImageSuffix(uploadFileSuffix)) {
			return Msg.error("不支持的文件格式，仅允许上传 jpg/jpeg/png/gif/bmp/webp 格式的图片");
		}

		FileOutputStream fos = null;
		InputStream fis = null;
		String fullPath = ulmsConfig.getUploadPath() + path;
		try {
			fis = Objects.requireNonNull(multiReq.getFile("file")).getInputStream();

			// 读取文件头部魔数进行真实类型校验（防止伪造 Content-Type）
			byte[] header = new byte[8];
			int headerLen = fis.read(header);
			if (headerLen < 4 || !isValidImageMagicBytes(header, headerLen)) {
				return Msg.error("文件内容与图片格式不符，上传被拒绝");
			}

			File file = new File(fullPath + "/" + uploadFileName + "." + uploadFileSuffix);
			if (!file.getParentFile().exists()) {
				if (!file.getParentFile().mkdirs()) {
					log.error("无法创建上传目录: {}", file.getParentFile().getAbsolutePath());
					return Msg.error("服务器上传目录配置异常，请联系管理员");
				}
			}
			fos = new FileOutputStream(file);
			// 先写入已读取的头部字节，再继续写入剩余内容
			fos.write(header, 0, headerLen);
			byte[] temp = new byte[1024];
			int i = fis.read(temp);
			while (i != -1) {
				fos.write(temp, 0, i);
				fos.flush();
				i = fis.read(temp);
			}
		} catch (IOException e) {
			log.error("文件上传发生IO错误！", e);
			return Msg.error("文件上传失败，请重试");
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					log.error("关闭输入流时发生错误！", e);
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					log.error("关闭输出流时发生错误！", e);
				}
			}
		}
		return Msg.success("文件上传成功").put("path",
				"upload/getFile/" + path + "/" + uploadFileName + "." + uploadFileSuffix);
	}

	/** 允许的图片后缀（小写） */
	private static final java.util.Set<String> ALLOWED_IMAGE_SUFFIXES =
			java.util.Set.of("jpg", "jpeg", "png", "gif", "bmp", "webp");

	private boolean isAllowedImageSuffix(String suffix) {
		return suffix != null && ALLOWED_IMAGE_SUFFIXES.contains(suffix);
	}

	/**
	 * 通过文件头部魔数校验是否为真实图片（防止伪造 Content-Type 绕过客户端校验）
	 */
	private boolean isValidImageMagicBytes(byte[] header, int len) {
		if (len < 4) return false;
		int b0 = header[0] & 0xFF;
		int b1 = header[1] & 0xFF;
		int b2 = header[2] & 0xFF;
		int b3 = header[3] & 0xFF;

		// JPEG: FF D8 FF (后跟 E0/E1/DB 等)
		if (b0 == 0xFF && b1 == 0xD8 && b2 == 0xFF) return true;
		// PNG: 89 50 4E 47
		if (b0 == 0x89 && b1 == 0x50 && b2 == 0x4E && b3 == 0x47) return true;
		// GIF: 47 49 46 38 (GIF8)
		if (b0 == 0x47 && b1 == 0x49 && b2 == 0x46 && b3 == 0x38) return true;
		// BMP: 42 4D
		if (b0 == 0x42 && b1 == 0x4D) return true;
		// WebP: 52 49 46 46 (RIFF) + 57 45 42 50 (WEBP) at offset 8
		if (b0 == 0x52 && b1 == 0x49 && b2 == 0x46 && b3 == 0x46 && len >= 12) {
			int b8 = header[8] & 0xFF;
			int b9 = header[9] & 0xFF;
			int b10 = header[10] & 0xFF;
			int b11 = header[11] & 0xFF;
			if (b8 == 0x57 && b9 == 0x45 && b10 == 0x42 && b11 == 0x50) return true;
		}
		return false;
	}

	@RequestMapping(value = "/getFile/{filePath}/{fileName}", method = RequestMethod.GET)
	public void testDownload(HttpServletResponse res, @PathVariable("filePath") String filePath,
			@PathVariable("fileName") String fileName) {

		// 路径安全校验：防止路径穿越读取任意文件
		if (filePath.contains("..") || fileName.contains("..")
				|| filePath.contains("\\") || fileName.contains("\\")) {
			res.setStatus(403);
			return;
		}

		res.setHeader("content-type", "application/octet-stream");
		res.setContentType("application/octet-stream");
		res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		// 防止 MIME 类型嗅探攻击
		res.setHeader("X-Content-Type-Options", "nosniff");
		byte[] buff = new byte[1024];
		BufferedInputStream bis = null;
		OutputStream os = null;
		String path = ulmsConfig.getUploadPath() + filePath + "/";
		try {
			os = res.getOutputStream();
			bis = new BufferedInputStream(Files.newInputStream(new File(path + fileName).toPath()));
			int i = bis.read(buff);
			while (i != -1) {
				os.write(buff, 0, buff.length);
				os.flush();
				i = bis.read(buff);
			}
		} catch (IOException e) {
			log.error("发生错误！", e);
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					log.error("发生错误！", e);
				}
			}
		}
//	    return Msg.success("成功获取文件");
	}

	@RequestMapping(value = "/getFile/{filePath}/{fileName}", method = RequestMethod.DELETE)
	public Msg deleteFile(HttpServletRequest req, MultipartHttpServletRequest multiReq,
			@PathVariable("path") String path) {
		return Msg.success();
	}

}
