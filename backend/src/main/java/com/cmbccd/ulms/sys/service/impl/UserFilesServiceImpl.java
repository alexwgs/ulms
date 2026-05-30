package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.dao.UserFilesMapper;
import com.cmbccd.ulms.sys.domain.UserFiles;
import com.cmbccd.ulms.sys.domain.UserFilesExample;
import com.cmbccd.ulms.sys.domain.UserFilesExample.Criteria;
import com.cmbccd.ulms.sys.service.UserFilesService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

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
