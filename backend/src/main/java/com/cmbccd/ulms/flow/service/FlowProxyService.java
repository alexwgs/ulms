package com.cmbccd.ulms.flow.service;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.flow.domain.FlowProxy;
import com.cmbccd.ulms.sys.domain.Msg;

import java.text.ParseException;
import java.util.Map;

public interface FlowProxyService {

    Msg insert(FlowProxy record);

    int delete(String id);

    int update(FlowProxy record);

    FlowProxy get(String id);

    DataPage<FlowProxy> list(Map<String, String> params);

    // 当前生效的代理人，并返回代理人的id
    String selectByProxyId(String proxyId);

    // 提前终止代理
    Msg stopProxy(String proxyId) throws ParseException;

}
