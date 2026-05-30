package com.cmbccd.ulms.youngTalk.dao;

import com.cmbccd.ulms.youngTalk.domain.Artical;
import com.cmbccd.ulms.youngTalk.domain.ArticalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticalMapper {
    long countByExample(ArticalExample example);

    int deleteByExample(ArticalExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Artical record);

    int insertSelective(Artical record);

    List<Artical> selectByExampleWithBLOBs(ArticalExample example);

    List<Artical> selectByExample(ArticalExample example);

    Artical selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Artical record, @Param("example") ArticalExample example);

    int updateByExampleWithBLOBs(@Param("record") Artical record, @Param("example") ArticalExample example);

    int updateByExample(@Param("record") Artical record, @Param("example") ArticalExample example);

    int updateByPrimaryKeySelective(Artical record);

    int updateByPrimaryKeyWithBLOBs(Artical record);

    int updateByPrimaryKey(Artical record);

    //new Id
    int creatNewArticalId();

    //增加查看量
    int viewNumIncrease(Integer id);
    int likeNumIncrease(Integer id);
    int collectNumIncrease(Integer id);
    int collectNumDecrease(Integer id);
    int replyNumIncrease(Integer id);
    int replyNumDecrease(Integer id);

    //使用ID查找文章不返回正文
    Artical selectByPrimaryKeyWithNoContent(Integer id);
    // 获取每周热门数据
    List<Artical> selectWeeklyHotList(String dateTime);
}