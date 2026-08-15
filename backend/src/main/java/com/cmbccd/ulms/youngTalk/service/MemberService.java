package com.cmbccd.ulms.youngTalk.service;

import com.cmbccd.ulms.youngTalk.domain.Member;
import com.cmbccd.ulms.youngTalk.domain.MemberExample;

import java.util.List;

public interface MemberService {

	public int newId();
	
	public List<Integer> getArticleIdByUserId(String userId);
	
	public int invalidByArticleId(int articleId);
	
	public int insertRecord(Member record);
	
	public List<Member> getMemberListByArticleId(int articleId);
	
	public List<Member> listMember(MemberExample example);
}
