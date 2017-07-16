<%@ include file="/WEB-INF/pages/addon/header.jsp"%>
<div class="container-fluid" align="center">
	<div class="row-fluid pg-title">
		<h3>
			<fmt:message key="taxistation.profile.title" bundle="${bundle}" />
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
			<table class="table">
				<thead>
					<tr>
						<th><fmt:message key="taxistation.history.car.number"
								bundle="${bundle}" /></th>
						<th><fmt:message key="taxistation.history.model"
								bundle="${bundle}" /></th>
						<th><fmt:message key="taxistation.history.color"
								bundle="${bundle}" /></th>
						<th><fmt:message key="taxistation.history.orderstatus"
								bundle="${bundle}" /></th>
						<th><fmt:message key="taxistation.carcharacteristics"
								bundle="${bundle}" /></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${car.getNumber()}</td>
						<td>${car.getModel()}</td>
						<td>${car.getColor()}</td>
						<td><fmt:message key="${car.getCarStatus().getLocaleKey()}"
								bundle="${bundle}" /></td>
						<td><c:forEach var="carchar"
								items="${car.getCarCharacteristics()}">
								<fmt:message key="${carchar.getLocaleKey()}" bundle="${bundle}" />
								<br />
							</c:forEach></td>
					</tr>
				</tbody>
			</table>
			<c:choose>
				<c:when
					test="${car.getCarStatus().name().toLowerCase() eq 'unavailable'}">
					<form action="./profile/changeOrderStatus" method="POST"
						role="form">

						<table class="table">
							<thead>
								<tr>
									<th><fmt:message key="taxistation.pickup"
											bundle="${bundle}" /></th>
									<th><fmt:message key="taxistation.destination"
											bundle="${bundle}" /></th>
									<th><fmt:message key="taxistation.history.orderstatus"
											bundle="${bundle}" /></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<c:if test="${not empty order}">
										<td>${order.getRequest().getPickup()}</td>
										<td>${order.getRequest().getDestination()}</td>
										<td>
											<button type="submit" class="btn btn-default"
												id="submitButton">
												<fmt:message key="taxistation.complete.order"
													bundle="${bundle}" />
											</button>
										</td>
									</c:if>
								</tr>
							</tbody>
						</table>
					</form>
				</c:when>
				<c:otherwise>
					<form action="./profile/changeCarStatus" method="POST" role="form">

						<select name="car_status">
							<option value="broken"><fmt:message
									key="enum.carstatus.broken" bundle="${bundle}" /></option>
							<option value="available"><fmt:message
									key="enum.carstatus.available" bundle="${bundle}" /></option>
						</select>
						<button type="submit" class="btn btn-default" id="submitButton">
							<fmt:message key="taxistation.submit" bundle="${bundle}" />
						</button>
					</form>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>
</body>
</html>