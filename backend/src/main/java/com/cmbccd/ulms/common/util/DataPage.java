package com.cmbccd.ulms.common.util;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;

/** 
* @Author WeiGenSheng
* @Time 2020年4月30日 下午3:02:32 
* @Version 1.0
* @Description:
*/
public class DataPage<T>  implements Serializable {
	
    private static final long serialVersionUID = -9202109574544652243L;
    private long total;        //总记录数
    private List< T > list;    //结果集
    private int pageNum;    // 第几页
    private int pageSize;    // 每页记录数
    private int pages;        // 总页数
    private int size;        // 当前页的数量 <= pageSize，该属性来自ArrayList的size属性

	
	public DataPage(List<T> list) {
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.total = page.getTotal();
            this.pages = page.getPages();
            this.list = page;
            this.size = page.size();
        }
        
        
	}


	public long getTotal() {
		return total;
	}


	public void setTotal(long total) {
		this.total = total;
	}


	public List<T> getList() {
		return list;
	}


	public void setList(List<T> list) {
		this.list = list;
	}


	public int getPageNum() {
		return pageNum;
	}


	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getPages() {
		return pages;
	}


	public void setPages(int pages) {
		this.pages = pages;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "DataPage [total=" + total + ", list=" + list + ", pageNum=" + pageNum + ", pageSize=" + pageSize
				+ ", pages=" + pages + ", size=" + size + "]";
	}

	
	
}
