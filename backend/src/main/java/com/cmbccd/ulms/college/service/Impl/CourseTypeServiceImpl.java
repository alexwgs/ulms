package com.cmbccd.ulms.college.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cmbccd.ulms.college.dao.CourseTypeMapper;
import com.cmbccd.ulms.college.domain.CourseTypeExample;
import com.cmbccd.ulms.common.util.Util;
import org.springframework.stereotype.Service;

import com.cmbccd.ulms.college.domain.CourseType;
import com.cmbccd.ulms.college.service.CourseTypeService;

import jakarta.annotation.Resource;

@Service
public class CourseTypeServiceImpl implements CourseTypeService {

	private CourseTypeMapper courseTypeMapper;

	@Resource
	public void setCourseTypeMapper (CourseTypeMapper courseTypeMapper) {
		this.courseTypeMapper = courseTypeMapper;
	}

	public List<CourseType> courseTypeTree(short status) {
		List<CourseType> list = returnList(status);
		// 获取多级数据
		Map<Short, List<CourseType>> listMap = list.stream().collect(Collectors.groupingBy(CourseType::getGrade));
		List<CourseType> level1List = new ArrayList<>();
		treeChildSet(listMap ,level1List, 0, (short)1);
		return level1List;
	}

	@Override
	public List<Integer> getChiledIds(Integer id) {
		List<CourseType> list = returnList((short)1);
		Map<Short, List<CourseType>> listMap = list.stream().collect(Collectors.groupingBy(CourseType::getGrade));
		List<Integer> ids = new ArrayList<>();
		ids.add(id);
		// 获取多级数据
		for (short i = 1; i<listMap.size()+1; i++ ) {
			if(Util.isNullorEmpty(listMap.get(i))) continue;
			List<CourseType> idList = listMap.get(i).stream().filter(e-> ids.contains(e.getPid())).collect(Collectors.toList());
			if(idList.size()<1) continue;
			ids.addAll(idList.stream().map(CourseType::getId).collect(Collectors.toList()));
		}

		return ids;
	}

	public List<CourseType> returnList(Short status) {
		CourseTypeExample example = new CourseTypeExample();
		CourseTypeExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(status);
		example.setOrderByClause(" grade, sort ");
		List<CourseType> list = courseTypeMapper.selectByExample(example);
		return list;
	}
//	public void treeChildSet(List<CourseType> list, List<CourseType> level1List, Integer pid) {
//		for(CourseType item:list){
//			if(item.getPid().equals(pid)){
//				List<CourseType> temp=new ArrayList<>();
//				//这里的areaMenuVo.getWRRCD()就是字段id,id要和pid进行对应，二级菜单的pid就是一级菜单的id
//				treeChildSet(list,temp,item.getId());
//				if(temp.size()>0){
//					item.setChildren(temp);
//				}
//				level1List.add(item);
//			}
//		}


	public void treeChildSet(Map<Short, List<CourseType>> listMap, List<CourseType> level1List, Integer pid, short level) {
		if(Util.isNullorEmpty(listMap.get(level)) ||listMap.get(level).size()<1) return;
		for(CourseType item:listMap.get(level)){
			if(item.getPid().equals(pid)){
				List<CourseType> temp=new ArrayList<>();
				treeChildSet(listMap ,temp,item.getId(),  (short)(item.getGrade()+1));
				if(temp.size()>0){
					item.setChildren(temp);
				}
				level1List.add(item);
			}
		}
	}


	@Override
	public int update(CourseType courseType) {
		return courseTypeMapper.updateByPrimaryKeySelective(courseType);
	}

	@Override
	public int delete(int id) {
		return courseTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CourseType courseType) {
		courseType.setId(courseTypeMapper.getNewId());
		return courseTypeMapper.insertSelective(courseType);
	}

}
