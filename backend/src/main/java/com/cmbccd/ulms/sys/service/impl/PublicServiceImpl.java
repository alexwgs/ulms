package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.sys.dao.PublicMapper;
import com.cmbccd.ulms.sys.service.PublicService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
public class PublicServiceImpl implements PublicService {
    @Resource
    private PublicMapper publicMapper;


    @Override
    public String getJourno() {
        return publicMapper.selectNewJourno();
    }
}
