package com.cmbccd.ulms.helper.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.helper.dao.HelperTreeMapper;
import com.cmbccd.ulms.helper.domain.HelperTree;
import com.cmbccd.ulms.helper.domain.HelperTreeExample;
import com.cmbccd.ulms.helper.service.HelperTreeService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HelperTreeServiceImpl implements HelperTreeService {
    @Resource
    private HelperTreeMapper helperTreeMapper;

    @Override
    public List<HelperTree> helperTree(short status) {
        List<HelperTree> list = returnList(status);
        // 获取多级数据
        Map<Short, List<HelperTree>> listMap = list.stream().collect(Collectors.groupingBy(HelperTree::getGrade));
        List<HelperTree> level1List = new ArrayList<>();
        treeChildSet(listMap ,level1List, 0, (short)1);
        return level1List;
    }

    @Override
    public List<Integer> getChiledIds(Integer id) {
        List<HelperTree> resultList = new ArrayList<>();
        // 获取该ID下的所有子ID
        List<HelperTree> list = returnList(null);
        Map<Short, List<HelperTree>> listMap = list.stream().collect(Collectors.groupingBy(HelperTree::getGrade));
        treeChildSet(listMap ,resultList, id, (short)1);
        List<Integer> ids= resultList.stream().map(HelperTree::getId)
                .collect(Collectors.toList());
        ids.add(id);
        return ids;
    }

    @Override
    public int update(HelperTree record) {
        return helperTreeMapper.updateByPrimaryKeySelective(record);
    }

    /*
    删除此id及级别以下所有的项目
     */
    @Override
    public int delete(Integer id) {
        return helperTreeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByIdWithChildren(Integer id) {
        List<Integer> ids = getChiledIds(id);
        if (Util.isNullorEmpty(ids)) {
            return 0;
        }
        HelperTreeExample example = new HelperTreeExample();
        HelperTreeExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(getChiledIds(id));
        return helperTreeMapper.deleteByExample(example);
    }


    @Override
    public int insert(HelperTree record) {
        record.setId(helperTreeMapper.getNewId());
        return helperTreeMapper.insertSelective(record);
    }


    public void treeChildSet(Map<Short, List<HelperTree>> listMap, List<HelperTree> level1List, Integer pid, short level) {
        if(Util.isNullorEmpty(listMap.get(level)) ||listMap.get(level).size()<1) return;
        for(HelperTree item:listMap.get(level)){
            if(item.getPid().equals(pid)){
                List<HelperTree> temp=new ArrayList<>();
                treeChildSet(listMap ,temp,item.getId(),  (short)(item.getGrade()+1));
                if(temp.size()>0){
                    item.setChildren(temp);
                }
                level1List.add(item);
            }
        }
    }

    public List<HelperTree> returnList(Short status) {
        HelperTreeExample example = new HelperTreeExample();
        HelperTreeExample.Criteria criteria = example.createCriteria();
        if(!Util.isNullorEmpty(status)){
            criteria.andStatusEqualTo(status);
        }
        example.setOrderByClause(" grade, sort ");
        List<HelperTree> list = helperTreeMapper.selectByExample(example);
        return list;
    }
}
