/**
 * 
 */
package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.dao.DictionaryMapper;
import com.cmbccd.ulms.sys.domain.Dictionary;
import com.cmbccd.ulms.sys.domain.DictionaryExample;
import com.cmbccd.ulms.sys.service.DictionaryService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 
* <p>Title: DictionaryServiceImpl.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

	@Resource
	private DictionaryMapper dictionaryMapper;
	@Override
	public List<Dictionary> getDictionaryByName(String name) {
		return dictionaryMapper.selectDictinaryByName(name);
	}

	@Override
	public List<String> getGroupByName() {
		return dictionaryMapper.selectGroupByName();
	}

	@Override
	public List<Dictionary> getDictionaryList(DictionaryExample example) {
		return dictionaryMapper.selectByExample(example);
	}

	@Override
	public DataPage<Dictionary> getDictionaryListByAdmin(Map<String, String> params) {
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		DictionaryExample example = new DictionaryExample();
		DictionaryExample.Criteria criteria = example.createCriteria();
		if (!Util.isNullorEmpty(params.get("status"))) {
			criteria.andStatusEqualTo(Short.parseShort(params.get("status")));
		}
		if (!Util.isNullorEmpty(params.get("query"))) {
			example.or().andNameLike('%' + params.get("query") + '%');
			example.or().andCodeLike('%' + params.get("query") + '%');
			example.or().andCodevalLike('%' + params.get("query") + '%');
			example.or().andDescriptionLike('%' + params.get("query") + '%');
		}
		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
		}
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<Dictionary> dictionaryList = dictionaryMapper.selectByExample(example);
		return new DataPage<Dictionary>(dictionaryList);
	}

	@Override
	public Map<String, Dictionary> getDictionaryMapByName(String name) {
		return dictionaryMapper.selectDictinaryMapByName(name);
	}

	@Override
	public int updateDictionary(Dictionary record) {
		return dictionaryMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteDictionaryByPrimaryKey(Integer id) {
		return dictionaryMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int addDictionary(Dictionary record) {
		return dictionaryMapper.insert(record);
	}

	@Override
	public int selectNewId() {
		return dictionaryMapper.selectNewId();
	}

}
