<%@ include file="/WEB-INF/pages/addon/header.jsp"%>
<div class="container-fluid" align="center">

	<div class="row-fluid pg-title">
		<h3>
			<fmt:message key="taxistation.login.title" bundle="${bundle}" />
		</h3>
	</div>

	<div class="row-fluid">
		<div class="col-sm-6 col-sm-offset-3 ">
			<form action="./login" method="POST" role="form">


				<c:if test="${not empty error}">
					<div class="alert alert-danger" role="alert">
						<span class="glyphicon glyphicon-exclamation-sign"
							aria-hidden="true"></span>
						<p>${error}</p>
					</div>
				</c:if>

				<div class="form-group">
					<label for="email"><fmt:message key="taxistation.email"
							bundle="${bundle}" /></label> <input type="text" class="form-control"
						id="email" name="email"
						placeholder="<fmt:message key="taxistation.email" bundle="${bundle}"/>"
						value="<c:out value="${requestScope.userEmail}" />" />
				</div>
				<div class="form-group">
					<label for="password"><fmt:message
							key="taxistation.password" bundle="${bundle}" /></label> <input
						type="password" class="form-control" id="password" name="password"
						placeholder="<fmt:message key="taxistation.password" bundle="${bundle}"/>" />
				</div>
				<button type="submit" class="btn btn-default" id="submitButton">
					<fmt:message key="taxistation.login" bundle="${bundle}" />
				</button>
			</form>
		</div>
	</div>
</div>
</body>
</html>