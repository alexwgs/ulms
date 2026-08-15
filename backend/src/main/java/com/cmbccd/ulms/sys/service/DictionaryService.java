/**
 * 
 */
package com.cmbccd.ulms.sys.service;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.sys.domain.Dictionary;
import com.cmbccd.ulms.sys.domain.DictionaryExample;

import java.util.List;
import java.util.Map;

/** 
* @Author WeiGenSheng
* @Time 2020年5月8日 上午11:48:50 
* @Version 1.0
* @Description:
*/
/** 
* @Description: （对类进行功能描述）
* @date: datedate{time} （日期）
*/
public interface DictionaryService {
	//通过字典的名字获取字典结果集合
	public List <Dictionary> getDictionaryByName(String name);
	
	//获取所有的Name分组
	public List<String> getGroupByName();
	
	//获取Dictionary列表
	public List<Dictionary> getDictionaryList(DictionaryExample example);
	//分页获取Dictionary列表（管理端查询）
	public DataPage<Dictionary> getDictionaryListByAdmin(Map<String, String> params);
	//通过字典名获取字典Map（key为code）
	public Map<String, Dictionary> getDictionaryMapByName(String name);
	//修改
	public int updateDictionary(Dictionary record);
	//删除
	public int deleteDictionaryByPrimaryKey(Integer id);
	//添加
	public int addDictionary(Dictionary record);
	//新Id
	public int selectNewId();
}
