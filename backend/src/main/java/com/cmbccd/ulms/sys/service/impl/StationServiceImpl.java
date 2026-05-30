package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.dao.StationMapper;
import com.cmbccd.ulms.sys.domain.Station;
import com.cmbccd.ulms.sys.domain.StationExample;
import com.cmbccd.ulms.sys.service.StationService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author WeiGenSheng
 * @Time 2020年4月29日 下午3:17:31
 * @Version 1.0
 * @Description:
 */
@Service
public class StationServiceImpl implements StationService {

	// 定义每个IP第三段的值对应的楼层号码
	private final static Map<String, String> FLOOR_MAP = new HashMap<>();
	static {
		FLOOR_MAP.put("73", "10");
		FLOOR_MAP.put("74", "4");
		FLOOR_MAP.put("75", "5");
		FLOOR_MAP.put("76", "6");
		FLOOR_MAP.put("77", "7");
		FLOOR_MAP.put("78", "8");
		FLOOR_MAP.put("79", "9");
		FLOOR_MAP.put("81", "11");
	}

	@Resource
	private StationMapper stationMapper;

	@Override
	public List<Station> getStationList(Map<String, String> params) {
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		StationExample example = new StationExample();
		StationExample.Criteria criteria = example.createCriteria();
		String queryType = params.get("queryType");
		if (!Util.isNullorEmpty(params.get("query"))) {
			if ("pcIp".equals(queryType)) criteria.andPcIpLike("%" + params.get("query") + "%");
			else if ("floorNum".equals(queryType)) criteria.andFloorNumLike("%" + params.get("query") + "%");
			else if ("memo".equals(queryType)) criteria.andMemoLike("%" + params.get("query") + "%");
			else if ("ploNum".equals(queryType)) criteria.andPloNumLike("%" + params.get("query") + "%");
			else if ("extnNum".equals(queryType)) criteria.andExtnNumLike("%" + params.get("query") + "%");
		}

		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.camel4underline(params.get("order")) + " " + params.get("orderType"));
		}
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		return stationMapper.selectByExample(example);
	}

	@Override
	public int updateStationByPcIp(Station record) {
		record.setUpdateTime(Util.currentDateTime());
		StationExample example = new StationExample();
		StationExample.Criteria criteria = example.createCriteria();
		criteria.andPcIpEqualTo(record.getPcIp());
		record.setFloorNum(getFloorByIp(record.getPcIp()));
		return stationMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int deleteStationByIp(String pcIp) {
		StationExample example = new StationExample();
		StationExample.Criteria criteria = example.createCriteria();
		criteria.andPcIpEqualTo(pcIp);
		return stationMapper.deleteByExample(example);
	}

	@Override
	public int insertStation(Station record) {
		return stationMapper.insert(record);
	}

	@Override
	public int insertOrUpdate(Station record) {
		record.setFloorNum(getFloorByIp(record.getPcIp()));
		record.setUpdateTime(Util.currentDateTime());
		StationExample example = new StationExample();
		StationExample.Criteria criteria = example.createCriteria();
		criteria.andPcIpEqualTo(record.getPcIp());
		List<Station> list = stationMapper.selectByExample(example);
		if (list.size() > 0) return stationMapper.updateByExampleSelective(record, example);
		else return stationMapper.insert(record);
	}

	@Override
	public Station getStationByIp(String pcIp) {
		return stationMapper.selectByIp(pcIp);
	}

	@Override
	public Station selectByIpNoMemo(String pcIp) {
		return stationMapper.selectByIpNoMemo(pcIp);
	}

	private String getFloorByIp(String pcIp) {
		// 判断IP格式是否符合要求
		if (Util.isNullorEmpty(pcIp) || pcIp.split("\\.").length != 4) {
			return "0";
		}
		String ipThirdPart = pcIp.split("\\.")[2];
		String floor = FLOOR_MAP.get(ipThirdPart);
		if (Util.isNullorEmpty(floor)) return "0";
		return floor;
	}
}
