package org.eps.common.vo;

import java.io.Serializable;
import java.util.List;

public class DataGrid implements Serializable {

	private static final long serialVersionUID = 5926683603371901741L;

	private long total;

	private List<? extends Object> rows;
	
	public DataGrid() {
		
	}
	
	public DataGrid(long total, List<? extends Object> rows) {
		this.total = total;
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<? extends Object> getRows() {
		return rows;
	}

	public void setRows(List<? extends Object> rows) {
		this.rows = rows;
	}

}
