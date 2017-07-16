<%@ include file="/WEB-INF/pages/addon/header.jsp"%>
<div class="container-fluid" align="center">

	<div class="row-fluid pg-title">
		<h3>
			<fmt:message key="taxistation.signup.title" bundle="${bundle}" />
		</h3>
	</div>

	<div class="row-fluid">
		<div class="col-sm-6 col-sm-offset-3 ">
			<form action="./signup" method="POST" role="form">

				<c:if test="${not empty requestScope.errors}">
					<div class="alert alert-danger" role="alert">
						<span class="glyphicon glyphicon-exclamation-sign"
							aria-hidden="true"></span>
						<c:forEach items="${requestScope.errors}" var="error">
							<p>
								<fmt:message key="${error}" bundle="${bundle}" />
							</p>
						</c:forEach>
					</div>
				</c:if>

				<div class="form-group">
					<label for="email"><fmt:message key="taxistation.email"
							bundle="${bundle}" />*</label> <input type="text" class="form-control"
						id="email" name="email"
						placeholder="<fmt:message key="taxistation.email" bundle="${bundle}"/>"
						value="<c:out value="${requestScope.userEmail}" />" />
				</div>
				<div class="form-group">
					<label for="password"><fmt:message
							key="taxistation.password" bundle="${bundle}" />*</label> <input
						type="password" class="form-control" id="password" name="password"
						placeholder="<fmt:message key="taxistation.password" bundle="${bundle}"/>" />
				</div>
				<div class="form-group">
					<label for="confirmPassword"><fmt:message
							key="taxistation.confirm.password" bundle="${bundle}" />*</label> <input
						type="password" class="form-control" id="confirmPassword"
						name="confirmPassword"
						placeholder="<fmt:message key="taxistation.confirm.password" bundle="${bundle}"/>" />
				</div>
				<div class="form-group">
					<label for="phonenumber"><fmt:message
							key="taxistation.phonenumber" bundle="${bundle}" />*</label> <input
						type="text" class="form-control" id="phonenumber"
						name="phonenumber"
						placeholder="<fmt:message key="taxistation.phonenumber" bundle="${bundle}"/>"
						value="<c:out value="${requestScope.userPhonenumber}" />" />
				</div>
				<button type="submit" class="btn btn-default" id="submitButton">
					<fmt:message key="taxistation.signup" bundle="${bundle}" />
				</button>
			</form>
		</div>
	</div>
</div>
</body>
</html>