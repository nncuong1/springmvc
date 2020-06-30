package com.nnc.util;

public class Paging {
	// tong so ban ghi
	private long totalRows;
	// tong so trang
	private int totalPages;
	// trang hien tai
	private int indexPage;
	// so ban ghi tren 1 trang
	private int recordPerPage = 3;
	// so thu tu danh sach ban ghi : 0, 10-19, 
	private int offset;
	
	public Paging(int recordPerPage) {
		this.recordPerPage = recordPerPage;
	}

	public long getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalPages() {
		if(totalRows >0 ) {
			totalPages = (int) Math.ceil(totalRows/(double)recordPerPage);
		}
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getIndexPage() {
		return indexPage;
	}

	public void setIndexPage(int indexPage) {
		this.indexPage = indexPage;
	}

	public int getRecordPerPage() {
		return recordPerPage;
	}

	public void setRecordPerPage(int recordPerPage) {
		this.recordPerPage = recordPerPage;
	}

	public int getOffset() {
		if(indexPage>0) {
			offset = indexPage*recordPerPage - recordPerPage;
		}
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
}
