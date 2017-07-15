<table class="table">
	<thead>
		<tr>
			<th><fmt:message key="taxistation.history.id" bundle="${bundle}" /></th>
			<th><fmt:message key="taxistation.pickup" bundle="${bundle}" /></th>
			<th><fmt:message key="taxistation.destination" bundle="${bundle}" /></th>
			<th><fmt:message key="taxistation.history.car.number" bundle="${bundle}" /></th>
			<th><fmt:message key="taxistation.history.model" bundle="${bundle}" /></th>
			<th><fmt:message key="taxistation.history.color" bundle="${bundle}" /></th>
			<th><fmt:message key="taxistation.history.date" bundle="${bundle}" /></th>
			<th><fmt:message key="taxistation.history.orderstatus" bundle="${bundle}" /></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${orderList}" var="order">
			<tr>
				<td>${order.getId()}</td>
				<td>${order.getRequest().getPickup()}</td>
				<td>${order.getRequest().getDestination()}</td>
				<td>${order.getCar().getNumber()}</td>
				<td>${order.getCar().getModel()}</td>
				<td>${order.getCar().getColor()}</td>
				<td>${order.getRequest().getDateOfRequest().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))}</td>
				<td>${order.getStatus()}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>