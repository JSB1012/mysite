package com.douzone.mysite.vo;

public class PageVo {
	private int curPage; // 현재 페이지
	private int totalPage; // 총페이지
	private int beginPage; //
	private int endPage; //

	private int pageSet;

	private int nextPage;
	private int prevPage;

	private int cntPerPage = 10; // 페이지당 개수
	private int pageRange = 5; // 밑에 5개
	private int total;

	public PageVo() {
		super();
	}

	public PageVo(int curPage, int total) {

		super();
		// this.curPage = curPage;

		this.total = total; // 60
		// 2 > 0.4[1] * 5 = 5
		// 여기서 구해야 하는 게 시작 페이지 / 끝 페이지

		this.endPage = (int) (Math.ceil(curPage * 0.2) * 5);
		//System.out.println(endPage);
		this.beginPage = endPage - pageRange + 1;
		this.pageSet = (int) ((curPage - 1) * 0.2 + 1); // 1 ~ 5는 1페이지셋 6 ~ 10은 2페이지셋

		if (total % cntPerPage == 0) {
			this.totalPage = (int) (total / cntPerPage);
		} else {
			this.totalPage = (int) (total / cntPerPage + 1);
		}

		if (curPage > totalPage) {
			this.curPage = totalPage;
		} else {
			this.curPage = curPage;
		}
		
		if(curPage % 5 ==0) {
			this.nextPage = curPage + 1;	
			this.prevPage = curPage - 9; 
		} else if(curPage % 5 == 1){
			this.nextPage = curPage + pageRange; 
			this.prevPage = curPage - 5; 
		} else if(curPage % 5 == 2) {
			this.nextPage = curPage + 4;
			this.prevPage = curPage - 6; 
		} else if(curPage % 5 == 3) {
			this.nextPage = curPage + 3;
			this.prevPage = curPage - 7;
		} else if(curPage % 5 == 4) {
			this.nextPage = curPage + 2;
			this.prevPage = curPage - 8;
		}
		
		//this.prevPage = curPage - pageRange; // 이전 페이지로 넘어가기 위해서 5를 더해서 ?cur=현재-5

	}

	public int getNum() {
		// limit x, 5;
		// 페이지가 1이 들어오면 1 / 2가 들어오면 6 / 3이 들어오면 11 / 4 16
		//System.out.println(curPage);
		return curPage - 1 == 0 ? 1 : (curPage - 1) * cntPerPage + 1;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getPageSet() {
		return pageSet;
	}

	public void setPageSet(int pageSet) {
		this.pageSet = pageSet;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getBeginPage() {
		return beginPage;
	}

	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getPrevPage() {
		return prevPage;
	}

	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}

	public int getCntPerPage() {
		return cntPerPage;
	}

	public void setCntPerPage(int cntPerPage) {
		this.cntPerPage = cntPerPage;
	}

	public int getPageRange() {
		return pageRange;
	}

	public void setPageRange(int pageRange) {
		this.pageRange = pageRange;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "PageVo [curPage=" + curPage + ", totalPage=" + totalPage + ", beginPage=" + beginPage + ", endPage="
				+ endPage + ", nextPage=" + nextPage + ", prevPage=" + prevPage + ", cntPerPage=" + cntPerPage
				+ ", pageRange=" + pageRange + ", total=" + total + "]";
	}

}
