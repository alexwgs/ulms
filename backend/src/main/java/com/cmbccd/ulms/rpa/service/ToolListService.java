package com.cmbccd.ulms.rpa.service;

import com.cmbccd.ulms.rpa.domain.ToolList;

import java.util.List;
import java.util.Map;

public interface ToolListService {

    List<ToolList> getAllToolList(Map<String, String> params);

    int addToolList(ToolList toolList);

    int updateToolList(ToolList toolList);

    int deleteToolList(String id);

    List<ToolList> getUserToolList(Map<String, String> params);

    List<String> searchToolListName(String name);

}
