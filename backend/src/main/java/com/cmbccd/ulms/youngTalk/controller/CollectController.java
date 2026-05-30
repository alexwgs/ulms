package com.cmbccd.ulms.youngTalk.controller;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.youngTalk.domain.Artical;
import com.cmbccd.ulms.youngTalk.domain.Collect;
import com.cmbccd.ulms.youngTalk.service.ArticalService;
import com.cmbccd.ulms.youngTalk.service.CollectService;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("cyt")
public class CollectController {

	@Resource
	private CollectService collectService;
	@Resource
	private ArticalService articalService;

	@PostMapping(value = "collect/{id}/{status}")
	public Msg collectArtical(@PathVariable("id") int id, @PathVariable("status") int status) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}
		// 判断ID是否正确
		Artical artical = articalService.getArticalById(id);
		if (Util.isNullorEmpty(artical)) {
			return Msg.error("操作失败！没有获取到文章信息！");
		}
		Collect collect = new Collect();
		collect.setId(collectService.newId());
		collect.setUserId(userId);
		collect.setArticalId(id);
		collect.setDateTime(Util.currentDateTime());
		if (status == 0) {
			collect.setStatus(1);
			int count = collectService.insertCollect(collect);
			if (count == 0) {
				return Msg.error("收藏失败！请联系管理员！");
			}
			articalService.increaseCollectNum(id);
			return Msg.success("收藏成功！您可以在我的收藏查看记录！");
		} else {
			collect.setStatus(0);
			int count = collectService.updateStatusByUserAndArtical(collect);
			if (count == 0) {
				return Msg.error("取消收藏失败！请联系管理员！");
			}
			articalService.decreaseCollectNum(id);
			return Msg.success("取消收藏成功！我的收藏中会删除此记录！");
		}
	}

	/**
	 * 获取当前收藏到列表
	 */
	@GetMapping(value = "collect/list")
	public Msg getCollectList(@RequestParam Map<String, String> params) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}

		Map<String, Integer> pageParams = Util.innitTablePages(params);
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<Collect> collects = collectService.getCollectListByUserId(userId);

		for (Collect collect : collects) {
			collect.setArtical(articalService.getArticalByIdWithNoContent(collect.getArticalId()));
		}
		return Msg.success( new DataPage<Collect>(collects));
	}
}
