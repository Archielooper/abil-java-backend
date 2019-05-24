package com.policy.bazaar.globalresponse;

import org.springframework.stereotype.Component;

@Component
public class GlobalPaginationResponse {

	private Object list;
	private long count;

	public Object getList() {
		return list;
	}

	public void setList(Object list) {
		this.list = list;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}
