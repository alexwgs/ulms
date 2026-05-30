/**
 * 
 */
package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.sys.dao.DictionaryMapper;
import com.cmbccd.ulms.sys.domain.Dictionary;
import com.cmbccd.ulms.sys.domain.DictionaryExample;
import com.cmbccd.ulms.sys.service.DictionaryService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

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
