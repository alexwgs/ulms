package com.cmbccd.ulms.helper.service;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.helper.domain.HelperArticle;
import com.cmbccd.ulms.helper.domain.HelperArticleExample;

import java.util.List;
import java.util.Map;

public interface HelperArticleService {

    List<HelperArticle> listNoBlob(HelperArticleExample example);

    DataPage<HelperArticle> listArticleByQuery(Map<String, String> params, boolean selfOnly);

    HelperArticleExample listForFont(Map<String, String> params);

    HelperArticle get(String journo);

    int create(HelperArticle record);

    int delete(String journo);

    int update(HelperArticle record);

    int increaseReadCount(String journo);

}
