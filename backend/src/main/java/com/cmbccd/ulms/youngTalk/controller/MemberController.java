package com.cmbccd.ulms.youngTalk.controller;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.youngTalk.domain.Member;
import com.cmbccd.ulms.youngTalk.service.MemberService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 
* <p>Title: MemberController.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
@RestController
@RequestMapping("cyt")
public class MemberController {

	@Resource
	private MemberService memberService;

	@PostMapping(value = "member/{articleId}")
	public Msg setMember(@PathVariable("articleId") int articleId, @RequestBody List<Member> members) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}

		memberService.invalidByArticleId(articleId);
		for (Member member : members) {
			member.setArticleId(articleId);
			memberService.insertRecord(member);
		}
		return Msg.success();
	}

	@GetMapping(value = "member/{articleId}")
	public Msg getMember(@PathVariable("articleId") int articleId) {
		// String userId = Util.userIdByShiro();
//		if (userId.equals("0")) {
//			return Msg.error("您无权做此操作！请通过A6广场操作！");
//		}

		List<Member> members = memberService.getMemberListByArticleId(articleId);
		for (Member member : members) {
			member.setUser(DataCache.getEmployees().get(member.getUserId()));
		}
		return Msg.success( members);
	}
}
