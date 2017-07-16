<%@ include file="/WEB-INF/pages/addon/header.jsp"%>
<div class="container-fluid" align="center">

	<div class="row-fluid pg-title">
		<h3>
			<fmt:message key="taxistation.request.title" bundle="${bundle}" />
			id = ${req_id}
		</h3>
	</div>

	<div class="row">
		<div class="col-sm-6 col-sm-offset-3 ">
			<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert">
					<span class="glyphicon glyphicon-exclamation-sign"
						aria-hidden="true"></span>
					<p>${error}</p>
				</div>
			</c:if>
			<c:choose>
				<c:when test="${cars.size() ne 0}">
					<form action="./request/submit?req_id=${req_id}"
						method="POST" role="form">
						<select name="car_available">
							<c:forEach var="car" items="${cars}">
								<option value="${car.getId()}">${car.getModel()} ${car.getColor()}</option>
							</c:forEach>
						</select>

						<button type="submit" class="btn btn-default" id="submitButton">
							<fmt:message key="taxistation.submit" bundle="${bundle}" />
						</button>
					</form>
				</c:when>
				<c:otherwise>
					<form action="./request/reject?req_id=${req_id}"
						method="POST" role="form">
						<p>
							<fmt:message key="taxistation.nocaravailable" bundle="${bundle}" />
						</p>
						<button type="submit" class="btn btn-default" id="submitButton">
							<fmt:message key="taxistation.reject" bundle="${bundle}" />
						</button>
					</form>
				</c:otherwise>
			</c:choose>
			</form>
		</div>
	</div>

</div>
</body>
</html>