package com.cmbccd.ulms.rpa.service;

import com.cmbccd.ulms.rpa.domain.ToolTemplete;

import java.util.List;

public interface ToolTempleteService {

    List<ToolTemplete> getToolTemplete(String templeteId);

    int updateToolTemplete(ToolTemplete toolTemplete);

    int addToolTemplete(ToolTemplete toolTemplete);

    int deleteToolTemplete(String id);

}
