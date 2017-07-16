<%@ include file="/WEB-INF/pages/addon/header.jsp"%>
<div class="container-fluid" align="center">

	<div class="row-fluid pg-title">
		<h3>
			<fmt:message key="taxistation.history.title" bundle="${bundle}" />
		</h3>
	</div>

	<div class="row">
		<c:if test="${user.getRole().name().toLowerCase() eq 'client' }">
		<history:client-orders orderList="${orderList}"></history:client-orders>
		</c:if>
		<c:if test="${user.getRole().name().toLowerCase() eq 'driver' }">
		<history:driver-orders orderList="${orderList}"></history:driver-orders>
		</c:if>
	</div>
</div>
</body>
</html>