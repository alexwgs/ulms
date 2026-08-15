package com.cmbccd.ulms.helper.service;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.helper.domain.HelperArtical;
import com.cmbccd.ulms.helper.domain.HelperArticalExample;

import java.util.List;
import java.util.Map;

public interface HelperArticalService {

    List<HelperArtical> listNoBlob(HelperArticalExample example);

    DataPage<HelperArtical> listArticalByQuery(Map<String, String> params, boolean selfOnly);

    HelperArticalExample listForFont(Map<String, String> params);

    HelperArtical get(String journo);

    int create(HelperArtical record);

    int delete(String journo);

    int update(HelperArtical record);

    int increaseReadCount(String journo);

}
