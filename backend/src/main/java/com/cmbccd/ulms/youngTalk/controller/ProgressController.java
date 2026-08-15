package com.cmbccd.ulms.youngTalk.controller;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.youngTalk.domain.Progress;
import com.cmbccd.ulms.youngTalk.service.ProgressService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 
* <p>Title: ProgressController.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
@RestController
@RequestMapping("/cyt")
public class ProgressController {

	@Resource
	private ProgressService progressService;

	/**
	 * 拉取进度情况
	 * 
	 * @param articleId
	 * @return
	 */
	@GetMapping(value = "progress/{articleId}")
	public Msg getProgressByArticleId(@PathVariable("articleId") int articleId) {
		// String userId = Util.userIdByShiro();

		List<Progress> progresses = progressService.getProgressByArticleId(articleId);
		for (Progress progress : progresses) {
			progress.setUser(DataCache.getEmployees().get(progress.getUserId()));
		}

		return Msg.success(progresses);
	}

	/**
	 * 新增进度信息
	 */
	@PostMapping(value = "progress")
	public Msg newProgress(@RequestBody Progress record) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}

		record.setUserId(userId);
		record.setStatus(1);
		record.setDateTime(Util.currentDateTime());
		int count = progressService.insertNewProgress(record);
		if (count == 0) {
			return Msg.error("更新进度失败！");
		}
		return Msg.success("成功更新一条进度");
	}

	/**
	 * 修改进度信息
	 */
	@PutMapping(value = "progress")
	public Msg updateProgress(@RequestBody Progress record) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}
		if (Util.isNullorEmpty(record.getId())) {
			return Msg.error("没有获取到用户信息！请正确使用A6有声！");
		}

		int count = progressService.updateProgressById(record);
		if (count == 0) {
			return Msg.error("修改进度失败");
		}
		return Msg.success("成功更新一条进度");
	}
}
