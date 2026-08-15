package com.cmbccd.ulms.college.service.impl;

import com.cmbccd.ulms.college.dao.TeacherMapper;
import com.cmbccd.ulms.college.domain.Teacher;
import com.cmbccd.ulms.college.domain.TeacherExample;
import com.cmbccd.ulms.college.domain.TeacherExample.Criteria;
import com.cmbccd.ulms.college.service.CourseTeacherService;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Employee;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {

    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public int insert(Teacher record) {
        return teacherMapper.insertSelective(record);
    }

    @Override
    public int update(Teacher record) {
        return teacherMapper.updateByPrimaryKey(record);
    }

    @Override
    public int delete(String ploNum) {
        return teacherMapper.deleteByPrimaryKey(ploNum);
    }

    @Override
    public List<Teacher> list(TeacherExample example) {
        List<Teacher> list = teacherMapper.selectByExample(example);
        for (Teacher item : list ) {
            if (Util.isNullorEmpty(item.getPloNum())) continue;
            Employee employee = DataCache.getEmployees().get(item.getPloNum());
            item.setUser(employee);
        }
        return list;
    }

    @Override
    public DataPage<Teacher> listTeacherByQuery(Map<String, String> params) {
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        String status = params.get("status");
        String query = params.get("query");
        String skillType = params.get("skillType");
        String skillName = params.get("skillName");
        TeacherExample example = new TeacherExample();
        Criteria criteria = example.createCriteria();
        if (!Util.isNullorEmpty(skillType)) criteria.andSkillTypeEqualTo(Short.parseShort(skillType));
        if (!Util.isNullorEmpty(skillName)) criteria.andSkillNameEqualTo(Short.parseShort(skillName));
        if (!Util.isNullorEmpty(query)) {
            if (query.length() >= 6) {
                criteria.andPloNumEqualTo(query);
            } else {
                criteria.andPloNameLike("%" + query + "%");
            }
        }
        if (!Util.isNullorEmpty(status)) {
            if ("0".equals(status)) {
                criteria.andStatusEqualTo((short) 0);
            } else {
                criteria.andStatusGreaterThan((short) 0);
            }
        }
        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<Teacher> list = list(example);
        return new DataPage<Teacher>(list);
    }

    @Override
    public Teacher get(String ploNum) {
        Teacher teacher = teacherMapper.selectByPrimaryKey(ploNum);
        if (Util.isNullorEmpty(teacher)) {
            teacher = new Teacher();
        } else {
            Employee employee = Optional.ofNullable(DataCache.getEmployees().get(teacher.getPloNum())).orElse(null);
            teacher.setUser(employee);
        }
        return teacher;
    }

    @Override
    public List<Teacher> listTopTeacher() {
        List<Teacher> list = teacherMapper.selectTopTeacher();
        for (Teacher item : list ) {
            if (Util.isNullorEmpty(item.getPloNum())) continue;
            Employee employee = DataCache.getEmployees().get(item.getPloNum());
            item.setUser(employee);
        }
        return list;
    }
}
