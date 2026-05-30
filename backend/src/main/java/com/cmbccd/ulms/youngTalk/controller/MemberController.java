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

	@PostMapping(value = "member/{articalId}")
	public Msg setMember(@PathVariable("articalId") int articalId, @RequestBody List<Member> members) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}

		memberService.invalidByArticalId(articalId);
		for (Member member : members) {
			member.setArticalId(articalId);
			memberService.insertRecord(member);
		}
		return Msg.success();
	}

	@GetMapping(value = "member/{articalId}")
	public Msg getMember(@PathVariable("articalId") int articalId) {
		// String userId = Util.userIdByShiro();
//		if (userId.equals("0")) {
//			return Msg.error("您无权做此操作！请通过A6广场操作！");
//		}

		List<Member> members = memberService.getMemberListByArticalId(articalId);
		for (Member member : members) {
			member.setUser(DataCache.EMPLOYEE.get(member.getUserId()));
		}
		return Msg.success( members);
	}
}
