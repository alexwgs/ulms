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
		FileInputStream fis = null;
		String fullPath = ulmsConfig.getUploadPath() + path;

		UserFiles record = new UserFiles();
		try {
			fis = (FileInputStream) Objects.requireNonNull(multiReq.getFile("file")).getInputStream();
			File file = new File(fullPath + "/" + uploadFileName + "." + uploadFileSuffix);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdir();
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
		// 获取上传文件的路径
		String uploadFilePath = Objects.requireNonNull(multiReq.getFile("file")).getOriginalFilename();
		// 截取上传文件的文件名
		String uploadFileName = Util.getCurrentTimestamp() + ((new Random().nextInt(1000)) + 1000);

		if(Util.isNullorEmpty(uploadFilePath)){
			return Msg.error("文件上传失败，未获取到文件路径！");
		}
		// 截取上传文件的后缀
		String uploadFileSuffix = "";
		if (uploadFilePath != null && uploadFilePath.contains(".")) {
			uploadFileSuffix = uploadFilePath.substring(uploadFilePath.lastIndexOf('.') + 1);
		}

		FileOutputStream fos = null;
		FileInputStream fis = null;
		String fullPath = ulmsConfig.getUploadPath() + path;
		try {
			fis = (FileInputStream) Objects.requireNonNull(multiReq.getFile("file")).getInputStream();
			File file = new File(fullPath + "/" + uploadFileName + "." + uploadFileSuffix);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdir();
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
		return Msg.success("文件上传成功").put("path",
				"upload/getFile/" + path + "/" + uploadFileName + "." + uploadFileSuffix);
	}

	@RequestMapping(value = "/getFile/{filePath}/{fileName}", method = RequestMethod.GET)
	public void testDownload(HttpServletResponse res, @PathVariable("filePath") String filePath,
			@PathVariable("fileName") String fileName) {

		res.setHeader("content-type", "application/octet-stream");
		res.setContentType("application/octet-stream");
		res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
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
