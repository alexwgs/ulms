package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.dao.UserFilesMapper;
import com.cmbccd.ulms.sys.domain.UserFiles;
import com.cmbccd.ulms.sys.domain.UserFilesExample;
import com.cmbccd.ulms.sys.domain.UserFilesExample.Criteria;
import com.cmbccd.ulms.sys.service.UserFilesService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserFilesServiceImpl implements UserFilesService {

	@Resource
	private UserFilesMapper userFilesMapper;

	@Override
	public int selectNewId() {
		return userFilesMapper.selectNewId();
	}

	@Override
	public List<UserFiles> getUserFilesList(UserFilesExample example) {
		return userFilesMapper.selectByExample(example);
	}

	@Override
	public DataPage<UserFiles> getUserFilesListByQuery(Map<String, String> params) {
		UserFilesExample example = new UserFilesExample();
		Criteria criteria = example.createCriteria();
		if (!Util.isNullorEmpty(params.get("fileSuffix"))) {
			criteria.andFileSuffixEqualTo(params.get("fileSuffix"));
		}
		if (!(Util.isNullorEmpty(params.get("begDate")) && Util.isNullorEmpty(params.get("endDate")))) {
			criteria.andDataDateBetween(params.get("begDate") + " 00:00:00", params.get("endDate") + " 23:59:59");
		}
		if (!Util.isNullorEmpty(params.get("fileName"))) {
			criteria.andFileNameLike("%" + params.get("fileName") + "%");
		}
		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
		}
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<UserFiles> list = userFilesMapper.selectByExample(example);
		return new DataPage<UserFiles>(list);
	}

	@Override
	public int insertNewUserFiles(UserFiles record) {
		String userId = "";
		record.setId(userFilesMapper.selectNewId());
		userId = Util.isNullorEmpty(Util.userIdByShiro()) ? ""
				: Util.userIdByShiro();
		record.setUserId(userId);
		record.setDataDate(Util.currentDateTime());
		return userFilesMapper.insert(record);
	}

	@Override
	public int deleteUserFileById(int id) {
		UserFilesExample example = new UserFilesExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		return userFilesMapper.deleteByExample(example);
	}

	@Override
	public List<String> getFileSuffix() {
		return userFilesMapper.getFileSuffix();
	}

}
