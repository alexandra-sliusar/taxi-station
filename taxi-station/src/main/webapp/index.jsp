<%@ include file="/WEB-INF/pages/addon/header.jsp"%>
<div class="container-fluid" align="center">
	<h2>
		<fmt:message key="taxistation.welcome" bundle="${bundle}" />
	</h2>
	<h4>
		<fmt:message key="taxistation.welcome.text" bundle="${bundle}" />
	</h4>
	
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
	</div>
</div>
</body>
</html>
