package com.cmbccd.ulms.edu.service.impl;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.dao.DailyConfigMapper;
import com.cmbccd.ulms.edu.dao.DailyScoreMapper;
import com.cmbccd.ulms.edu.domain.DailyConfig;
import com.cmbccd.ulms.edu.domain.DailyScore;
import com.cmbccd.ulms.edu.domain.DailyScoreExample;
import com.cmbccd.ulms.edu.domain.DailyScoreExample.Criteria;
import com.cmbccd.ulms.edu.domain.report.DailyScoreSummary;
import com.cmbccd.ulms.edu.service.DailyScoreService;
import com.cmbccd.ulms.sys.domain.Department;
import com.cmbccd.ulms.sys.domain.Employee;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import java.math.RoundingMode;
import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DailyScoreServiceImpl implements DailyScoreService {

    @Resource
    private DailyScoreMapper dailyScoreMapper;

    @Resource
    private DailyConfigMapper dailyConfigMapper;
    @Override
    public DailyScore get(String quesDate, String userId) {
        DailyScore dailyScore =  dailyScoreMapper.getDailyScore(quesDate, userId);
        return dailyScore;
    }

    @Override
    public DailyScore init(DailyConfig dailyConfig) {
        String userId = Util.userIdByShiro();
        DailyScore dailyScore = dailyScoreMapper.getDailyScore(dailyConfig.getQuesDate(),userId);
        if(Util.isNullorEmpty(dailyScore)) {
            Employee user = DataCache.getEmployees().get(userId);
            dailyScore = new DailyScore(dailyConfig.getQuesDate(),userId, user.getPloName(),user.getDeptNum(),user.getDeptGroup(),dailyConfig.getQuesCode(),(short)0,(short)0,null, (short)0);
            int count = dailyScoreMapper.insertSelective(dailyScore);
            if( count < 1) return null;
        }
        return dailyScore;
    }

    @Override
    public List<DailyScore> list(DailyScoreExample example) {
        return dailyScoreMapper.selectByExample(example);
    }

    public int getDailyTaskNum(String begDate,String endDate) {
        return dailyConfigMapper.countQuesByDate(begDate, endDate);
    }
    @Override
    public List<DailyScoreSummary> listSumary(DailyScoreExample example, String groupBy, int total) {
        List<DailyScoreSummary> list = dailyScoreMapper.SelectDailyScoreSummay(example, groupBy);
        for(DailyScoreSummary item: list){
            item.setTotalDay(total);
            item.setPunchRate(BigDecimal.valueOf(item.getPunchDays()/(double)total).setScale(4, RoundingMode.HALF_UP));
            item.setSignRate(BigDecimal.valueOf(item.getSignDays()/(double)total).setScale(4, RoundingMode.HALF_UP));
            item.setDeptNum(DataCache.getDepartments().get(item.getDeptNum()).getDeptName());
            if("hum,group".indexOf(groupBy)> -1) {
                String deptGroup = item.getDeptGroup();
                if(!Util.isNullorEmpty(deptGroup)) {
                    item.setDeptGroup(DataCache.getDepartments().get(deptGroup).getDeptName());
                }
            }else{
                item.setDeptGroup("");
            }
        }
        return list;
    }

    @Override
    public DataPage<DailyScoreSummary> listDailyScoreByQuery(Map<String, String> params) {
        String begDate = params.get("begDate");
        String endDate = params.get("endDate");
        String groupBy = params.get("groupBy");
        String query = params.get("query");
        String queryType = params.get("queryType");
        int total = getDailyTaskNum(begDate, endDate);
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        DailyScoreExample example = new DailyScoreExample();
        Criteria criteria = example.createCriteria();
        criteria.andQuesDateBetween(begDate, endDate);
        criteria.andValidEqualTo((short) 1);
        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        if (!Util.isNullorEmpty(query)) {
            if ("ploNum".equals(queryType)) {
                criteria.andPloNumEqualTo(query);
            } else if ("ploName".equals(queryType)) {
                criteria.andPloNameLike("%" + query + "%");
            } else if ("group".equals(queryType)) {
                List<String> condition = DataCache.getDepartments().values().stream()
                        .filter(e -> e.getDeptName().indexOf(query) > -1)
                        .map(Department::getDeptNum).collect(Collectors.toList());
                condition.add("0");
                criteria.andDeptGroupIn(condition);
            } else if ("dept".equals(queryType)) {
                List<String> condition = DataCache.getDepartments().values().stream()
                        .filter(e -> e.getDeptName().indexOf(query) > -1)
                        .map(Department::getDeptNum).collect(Collectors.toList());
                condition.add("0");
                criteria.andDeptNumIn(condition);
            }
        }
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<DailyScoreSummary> list = listSumary(example, groupBy, total);
        return new DataPage<DailyScoreSummary>(list);
    }

    @Override
    public int create(DailyScore record) {
        return dailyScoreMapper.insertSelective(record);
    }

    @Override
    public int update(DailyScore record) {
        DailyScoreExample example = new DailyScoreExample();
        DailyScoreExample.Criteria criteria = example.createCriteria();
        criteria.andPloNumEqualTo(record.getPloNum());
        criteria.andQuesDateEqualTo(record.getQuesDate());
        return dailyScoreMapper.updateByExample(record,example);
    }

    @Override
    public int updateSelective(DailyScore record) {
        DailyScoreExample example = new DailyScoreExample();
        DailyScoreExample.Criteria criteria = example.createCriteria();
        criteria.andPloNumEqualTo(record.getPloNum());
        criteria.andQuesDateEqualTo(record.getQuesDate());
        return dailyScoreMapper.updateByExampleSelective(record,example);
    }

    @Override
    public int delete(String quesDate, String userId) {
        DailyScoreExample example = new DailyScoreExample();
        DailyScoreExample.Criteria criteria = example.createCriteria();
        criteria.andPloNumEqualTo(userId);
        criteria.andQuesDateEqualTo(quesDate);
        return dailyScoreMapper.deleteByExample(example);
    }

    @Override
    public DailyScore submitQuestion(DailyScore dailyScore, int resultFlag, String userAnswer) {

        short valid=0,passFlag=0,mistakes=Util.isNullorEmpty(dailyScore.getMistakes())?0:dailyScore.getMistakes();
        if(resultFlag==0){
            if(mistakes == 0) dailyScore.setFirstAnswer(userAnswer);
            mistakes++;
            valid = passFlag =0;
        }else{
            if(mistakes == 0) {
                passFlag = 1;
            }else{
                passFlag = 0;
            }
            valid = Util.getDateToday().equals(dailyScore.getQuesDate())?(short)1:(short)2;
        }
        dailyScore.setValid(valid);
        dailyScore.setPassFlag(passFlag);
        dailyScore.setMistakes(mistakes);
        this.updateSelective(dailyScore);
        return this.updateSelective(dailyScore)>0?dailyScore:null;
    }

    @Override
    public DailyScore submitMood(DailyScore dailyScore) {
        dailyScore.setMoodDate(Util.currentDateTime());
        return this.updateSelective(dailyScore)>0?dailyScore:null;
    }

    @Override
    public DailyScore submitArticleStudy(DailyScore dailyScore) {
        return this.updateSelective(dailyScore)>0?dailyScore:null;
    }
}
