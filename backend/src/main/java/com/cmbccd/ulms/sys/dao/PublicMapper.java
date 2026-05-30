package com.cmbccd.ulms.sys.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PublicMapper {
	
    String selectNewJourno();
}
