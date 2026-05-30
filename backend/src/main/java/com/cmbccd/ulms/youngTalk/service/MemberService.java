package com.cmbccd.ulms.youngTalk.service;

import com.cmbccd.ulms.youngTalk.domain.Member;
import com.cmbccd.ulms.youngTalk.domain.MemberExample;

import java.util.List;

public interface MemberService {

	public int newId();
	
	public List<Integer> getArticalIdByUserId(String userId);
	
	public int invalidByArticalId(int articalId);
	
	public int insertRecord(Member record);
	
	public List<Member> getMemberListByArticalId(int articalId);
	
	public List<Member> listMember(MemberExample example);
}
