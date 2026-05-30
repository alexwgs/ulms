package com.cmbccd.ulms.sys.domain;

/**
 * @Author WeiGenSheng
 * @Time 2020年4月29日 下午8:44:43
 * @Version 1.0
 * @Description:
 */
public class QueryInfo {
	private Integer pageSize;
	private Integer pageNum;
	private String query;
	private String queryType;
	private String order;
	private String orderType;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize == null) {
			pageSize = 20;
		}
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		if (pageNum == null) {
			pageNum = 1;
		}
		this.pageNum = pageNum;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Override
	public String toString() {
		return "QueryInfo [pageSize=" + pageSize + ", pageNum=" + pageNum + ", query=" + query + ", queryType="
				+ queryType + ", order=" + order + ", orderType=" + orderType + "]";
	}

}
