package com.cmbccd.ulms.youngTalk.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cmbccd.ulms.youngTalk.dao.MemberMapper;
import com.cmbccd.ulms.youngTalk.domain.Member;
import com.cmbccd.ulms.youngTalk.domain.MemberExample;
import com.cmbccd.ulms.youngTalk.domain.MemberExample.Criteria;
import com.cmbccd.ulms.youngTalk.service.MemberService;

import jakarta.annotation.Resource;

@Service
public class MemberServiceImpl implements MemberService {

	@Resource
	private MemberMapper memberMapper;
	
	@Override
	public int newId() {
		
		return memberMapper.creatNewId();
	}

	@Override
	public List<Integer> getArticalIdByUserId(String userId) {
		
		return memberMapper.selectArticalIdByUserId(userId);
	}

	@Override
	public int invalidByArticalId(int articalId) {
		return memberMapper.invalidStatusByArticalId(articalId);
	}

	@Override
	public int insertRecord(Member record) {
		record.setId(memberMapper.creatNewId());
		return memberMapper.insert(record);
	}

	@Override
	public List<Member> getMemberListByArticalId(int articalId) {
		MemberExample example = new MemberExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(1);
		criteria.andArticalIdEqualTo(articalId);
		example.setOrderByClause(" Role ASC , ID DESC ");
		return memberMapper.selectByExample(example);
	}

	@Override
	public List<Member> listMember(MemberExample example) {
		return memberMapper.selectByExample(example);
	}


}
