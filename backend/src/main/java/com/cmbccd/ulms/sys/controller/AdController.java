package com.cmbccd.ulms.sys.controller;

import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.sys.domain.Ad;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.service.AdService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/ad/")
public class AdController {

	@Resource
	private AdService adService;
	
	@GetMapping(value="index")
	public Msg getIndexAd() {
		List<Ad> list = adService.ListIndexAd();
		return Msg.success(list);
	}
	
	@GetMapping(value="list")
	public Msg getAdList(@RequestParam Map<String, String> params) {
		return Msg.success(adService.listAdsByQuery(params));
	} 
	
	@PostMapping(value="record")
	@MyLog(title = "[sys-ad]滚动广告", content = "新增操作")
	public Msg addAd(@RequestBody Ad record) {
		int count = adService.addAd(record);
		return Msg.success("成功新增["+count+"]条数据！");
	}
	
	@PutMapping(value="record")
	@MyLog(title = "[sys-ad]滚动广告", content = "修改操作")
	public Msg updateAd(@RequestBody Ad record) {
		int count = adService.updateAd(record);
		return Msg.success("成功修改["+count+"]条数据！");
	}
	
	@PostMapping(value="delete/{id}")
	@MyLog(title = "[sys-ad]滚动广告", content = "删除操作")
	public Msg deleteAd(@PathVariable ("id") Integer id) {
		int count = adService.deleteAd(id);
		return Msg.success("成功删除["+count+"]条数据！");
	}
}
