<%@ attribute name="requestList" required="true" %>
<table class="table">
	<thead>
		<tr>
			<th><fmt:message key="taxistation.history.id" bundle="${bundle}" /></th>
			<th><fmt:message key="taxistation.pickup" bundle="${bundle}" /></th>
			<th><fmt:message key="taxistation.destination"
					bundle="${bundle}" /></th>
			<th><fmt:message key="taxistation.history.date"
					bundle="${bundle}" /></th>
			<th><fmt:message key="taxistation.carcharacteristics"
					bundle="${bundle}" /></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${requestList}" var="request">
			<tr>
				<td>${request.getId()}</td>
				<td>${request.getPickup()}</td>
				<td>${request.getDestination()}</td>
				<td>${request.getDateOfRequest()}</td>
				<td><c:forEach items="${request.getCarCharacteristics()}"
						var="carchar">
						<fmt:message key="${carchar.getLocaleKey()}" bundle="${bundle}" />
						<br />
					</c:forEach></td>
			</tr>
		</c:forEach>
	</tbody>
</table>