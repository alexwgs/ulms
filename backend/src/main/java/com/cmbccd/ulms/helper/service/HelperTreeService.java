package com.cmbccd.ulms.helper.service;

import com.cmbccd.ulms.helper.domain.HelperTree;

import java.util.List;

public interface HelperTreeService {
    List<HelperTree> helperTree(short status);

    List<Integer> getChiledIds(Integer id);

    int update(HelperTree record);

    int delete(Integer id);

    int deleteByIdWithChildren(Integer id);

    int insert(HelperTree record);
}
