<%@ include file="/WEB-INF/pages/addon/header.jsp"%>
<div class="container-fluid" align="center">

	<div class="row-fluid pg-title">
		<h3>
			<fmt:message key="taxistation.allcars.title" bundle="${bundle}" />
		</h3>
	</div>

	<div class="row">	
		<history:all-cars carList="${carList}"></history:all-cars>
	</div>
</div>
</body>
</html>