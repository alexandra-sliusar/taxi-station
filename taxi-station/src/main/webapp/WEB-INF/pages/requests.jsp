<%@ include file="/WEB-INF/pages/addon/header.jsp"%>
<div class="container-fluid" align="center">

	<div class="row-fluid pg-title">
		<h3>
			<fmt:message key="taxistation.requests.title" bundle="${bundle}" />
		</h3>
	</div>

	<div class="row">
		<c:if test="${not empty success}">
			<div class="alert alert-success" role="alert">
				<span class="glyphicons glyphicons-tick" aria-hidden="true"></span>
				<p>${success}</p>
			</div>
		</c:if>
		<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert">
					<span class="glyphicon glyphicon-exclamation-sign"
						aria-hidden="true"></span>
					<p>${error}</p>
				</div>
			</c:if>
		<history:request-history requestList="${requestList}"></history:request-history>
	</div>
</div>
</body>
</html>