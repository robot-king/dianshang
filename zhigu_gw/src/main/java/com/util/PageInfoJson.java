package com.util;

import java.util.List;

import com.github.pagehelper.PageInfo;

public class PageInfoJson<T> extends PageInfo<T> {

	private static final long serialVersionUID = 1L;

	// 自定义结果集
	private List<T> rows;

	public PageInfoJson(List<T> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	public List<T> getRows() {
		rows = this.getList();
		return rows;
	}

}
