package com.admin.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 */
public class QueryParam extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	// 当前页
	private int page;
	// 每页条数
	private int limit;

	public QueryParam(Map<String, Object> params) {
		this.putAll(params);
		// 分页参数
		if (params.get("page") != null && params.get("limit") != null) {
			this.page = Integer.parseInt(params.get("page").toString());
			this.limit = Integer.parseInt(params.get("limit").toString());
			this.put("limit", limit);
			this.put("offset", (page -1) * limit);
		}
		
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
}
