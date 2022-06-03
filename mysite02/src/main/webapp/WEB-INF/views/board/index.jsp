<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board?a=list&kwd=${kwd}" method="GET">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach items='${list }' var='vo' varStatus='status'>
						<c:set var='count' value='${fn:length(list) }' />

						<!-- 원글 -->
						<c:choose>
							<c:when test="${vo.depth == 0}">
								<tr>
									<td>${count-status.index }</td>

									<td style="text-align: left; padding-left: 0px">
										<a href="${pageContext.request.contextPath }/board?a=view&no=${vo.no}">${vo.title }</a>
									</td>
									<td>${vo.userName}</td>
									<td>${vo.hit }</td>
									<td>${vo.regDate }</td>

									<c:choose>
										<c:when
											test="${not empty authUser && vo.userNo eq authUser.no}">
											<td>
												<a href="${pageContext.request.contextPath }/board?a=delete&no=${vo.no}" class="del">삭제</a></td>
										</c:when>
										<c:otherwise>
											<td></td>
										</c:otherwise>
									</c:choose>

								</tr>
							</c:when>
						</c:choose>
						
						<!-- 답글 -->
						<c:choose>
							<c:when test="${vo.depth != 0}">
								<tr>
									<td>${count-status.index }</td>
									<td style="text-align:left; padding-left:${20*vo.depth }px">
										<img src='${pageContext.servletContext.contextPath }/assets/images/reply.png' />
										<a href="${pageContext.request.contextPath }/board?a=view&no=${vo.no}">${vo.title }</a>
									</td>
									<td>${vo.userName}</td>
									<td>${vo.hit }</td>
									<td>${vo.regDate }</td>

									<c:choose>
										<c:when
											test="${not empty authUser && vo.userNo eq authUser.no}">
											<td>
												<a href="${pageContext.request.contextPath }/board?a=delete&no=${vo.no}" class="del">삭제</a></td>
										</c:when>
										<c:otherwise>
											<td></td>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:when>
						</c:choose>
					</c:forEach>
				</table>

				<!-- pager 추가 -->
								
				<div class="pager">
					<ul>
						<c:if test="${page.pageSet == 1}">
							<li>◀</li>
						</c:if>
						<c:if test="${page.pageSet != 1}">
							<li><a
								href="${pageContext.request.contextPath }/board?a=list&cur=${page.prevPage}">◀</a></li>
						</c:if>

						<c:forEach begin="${page.beginPage }" end="${page.endPage }"
							step="1" varStatus="status">
							<c:choose>
								<c:when test="${page.totalPage >= status.index }">
									<li>
										<a href="${pageContext.request.contextPath }/board?a=list&cur=${status.index}&kwd=${kwd}" id = "linkpage">
											${status.index}
										</a>
									</li>
								</c:when>
								<c:otherwise>
									<li>${status.index}</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:choose>
						<c:when test="${page.pageSet * 5 >= page.totalPage }">
							<li>
									▶
							</li>
						</c:when>
						<c:otherwise>
							<li>
								<a href="${pageContext.request.contextPath }/board?a=list&cur=${page.nextPage }&&kwd=${kwd}">
									▶
								</a>
							</li>
						</c:otherwise>
						</c:choose>
					</ul>
				</div>
				<!-- pager 추가 -->

				<div class="bottom">
					<a href="${pageContext.request.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>