<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
	<!-- Include libraries(jQuery, bootstrap) -->
	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<!-- Include summernote css/js -->
	<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote.css" rel="stylesheet">
	<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote.js"></script>
	<!-- Title -->
	<title><spring:message code="voteboards.title"/></title>
</head>
<body>
	<div class="container" style="padding-top: 100px;">
		<h2 class="page.headder">${voteBoardRead.voteBoardTitle}</h2>
			<table class="table table-bordered">
				<tr>
					<td class="align-middle"><image src="${voteBoardRead.voteBoardImageURL}"></td>
				</tr>
				<tr>
					<td>${voteBoardRead.voteBoardContent}</td>
				</tr>
				<tr>
					<td class="align-middle">
						<c:choose>
							<c:when test="${(voteBoardRead.voteAgreeCnt+voteBoardRead.voteDisagreeCnt)>0}"> <%-- 투표자가 0명 이상일 때 --%>
								<div class="progress">
									<div class="progress-bar progress-bar-success" role="progressbar" style="width:${voteBoardRead.voteAgreePercent}%">
										<spring:message code="boards.agree"/> (${voteBoardRead.voteAgreePercent}%)
									</div>
									<div class="progress-bar progress-bar-danger" role="progressbar" style="width:${100-voteBoardRead.voteAgreePercent}%">
										<spring:message code="boards.disagree"/> (${100-voteBoardRead.voteAgreePercent}%)
									</div>
								</div>	
							</c:when>
							<c:otherwise> <%-- 투표자가 없을 때 --%>
								<div class="progress">
									<div class="progress-bar progress-bar-warning" role="progressbar" style="width:100%">
										<spring:message code="boards.zeroVoter"/>	
									</div>
								</div>
							</c:otherwise>
						</c:choose>
						<c:set var="voteProgressesURL" value="../../voteprogresses/${voteBoardRead.voteBoardId}"/>
						<button id="voteAgreeBtn" type="button" class="btn btn-success"><spring:message code="boards.agree"/> (${voteBoardRead.voteAgreeCnt})</button>
						<button id="voteDisagreeBtn" type="button" class="btn btn-danger"><spring:message code="boards.disagree"/> (${voteBoardRead.voteDisagreeCnt})</button>

						<input type="hidden" id="boardId" value="${voteBoardRead.voteBoardId}"/>
						<input type="hidden" id="alreadyVote" value='<spring:message code="voteboards.alreadyVoted"/>'/>
						<input type="hidden" id="needLogin" value='<spring:message code="members.needLogin"/>'/>
						<input type="hidden" id="memberGradeError" value='<spring:message code="members.gradeError"/>'/>
					</td>
				</tr>
				<tr>
					<!-- 덧글... -->
				</tr>
			</table>
			
			<script>
				function getContextPath() {
					var hostIndex = location.href.indexOf( location.host ) + location.host.length;
					return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
				};
				
				function refreshVote() {
					// ... 새로고침 하는 함수를 만들어보자 [여기부터 시작]
				}

				$("#voteAgreeBtn").bind("click",function(){
					$.ajax({
						url : getContextPath() + "/voteprogresses/" + $("#boardId").val() + "/agree",
						type: "POST",
						data : {},
						success : function(result){
							if(result == 'alreadyVote'){
								alert($("#alreadyVote").val());
								return false;
							} else if (result == 'needLogin'){
								alert($("#needLogin").val());
								return false;
							} else if (result == 'memberGradeError') {
								alert($("#memberGradeError").val());
								return false;
							} else {
								refreshVote();
								return true;
							}
						}
					});
				});

				$("#voteDisagreeBtn").bind("click",function(){
					$.ajax({
						url : getContextPath() + "/voteprogresses/" +$("#boardId").val() + "/disagree",
						type: "POST",
						data : {},
						success : function(result){
							if(result == 'alreadyVote'){
								alert($("#alreadyVote").val());
								return false;
							} else if ( result == 'needLogin'){
								alert($("#needLogin").val());
								return false;
							} else if (result == 'memberGradeError') {
								alert($("#memberGradeError").val());
								return false;
							} else {
								refreshVote();
								return true;
							}
						}
					});
				});
			</script>

		<%-- 게시글 수정을 위한 주석
		<form:form action="./" method="PUT" modelAttribute="voteBoardWriteCmd" enctype="multipart/form-data">
			<table class="table table-bordered">
				<tr>
					<th>글 제목</th>
					<td><form:input path="voteBoardTitle" class="form-control"/></td>
				</tr>
				<tr>
					<th>마감 기준</th>
					<td>
						<p>투표 인원 수 : <form:input type="number" path="voteEndCnt" value="100"/></p>
						<p>투표 마감일 : <input type="date" id="voteEndDate" value="2018-10-10"/></p>
					</td>
				</tr>
				<tr>
					<th>파일</th>
					<td>
						<div class="fileForm">
							<input type="file" name="voteBoardThumbnailImage" accept=".jpg, .jpeg, .png, .bmp"/>
						</div>
					</td>
				</tr>
				<tr>
					<th>글 내용</th>
					<form:hidden id="voteBoardContent" path="voteBoardContent" value="testDefault"/>
					<td><textarea class="form-control" id="summernote" name="content" placeholder="content" maxlength="140" rows="7"></textarea></td>
				</tr>
			</table>
			<input type="submit" onclick="boardWrite()" class="btn btn-primary" value="작성하기"/>
		</form:form> --%>
	</div>
</body>
<script>
	$('#summernote').summernote({
		width: 800,
		height: 300,
		minHeight: 300,
		maxHeight: 1000,
		focus: true,
		toolbar: [
			['style', ['bold', 'italic', 'underline', 'clear']],
			['font', ['strikethrough', 'superscript', 'subscript']],
            ['fontsize', ['fontsize']],
            ['color', ['color']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['height', ['height']],
            ['insert', ['link', 'table']] ]
	});
	
	function boardWrite() {
        var boardContent = $('#summernote').summernote('code');
		$('#voteBoardContent').val(boardContent);
	}
</script>
</html>