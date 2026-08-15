package com.cmbccd.ulms.edu.controller;

import com.cmbccd.ulms.edu.domain.QuesCollect;
import com.cmbccd.ulms.edu.service.QuesCollectService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("edu/ques/collect")
public class QuesCollectController {

	@Resource
	private QuesCollectService quesCollectService;
	
	
//	@GetMapping("/list")
//	public Msg listQuesCollect(@RequestParam Map<String, String> params) {
//		String userId = Util.userIdByShiro();
//		QuesCollectExample example = new QuesCollectExample();
//		Criteria criteria = example.createCriteria();
//		criteria.andPloNumEqualTo(userId);
//		List<QuesCollect> list = quesCollectService.list(example);
//		return Msg.success();
//	}
//	
	@GetMapping("/my")
	public Msg listQuesCollect(@RequestParam Map<String, String> params) {
		List<QuesCollect> list = quesCollectService.listMyCollect();
		return Msg.success(list);
	}
	
	@PostMapping("/{quesCode}")
	public Msg collectQuestion(@PathVariable ("quesCode") String quesCode) {
		int collectNumber = quesCollectService.collect(quesCode);
		if(collectNumber ==0 ) return Msg.error("您的收藏已达上线！");
		else if(collectNumber == -1 )  return Msg.error("该题已经被收藏过了！");
		return Msg.success().put("collectNumber", collectNumber);
	}
	
	@PutMapping("/{journo}")
	public Msg uncollectQuestion(@PathVariable ("journo") String journo) {
		int count = quesCollectService.uncollect(journo);
		if(count<1) return Msg.error();
		return Msg.success();
	}
}
