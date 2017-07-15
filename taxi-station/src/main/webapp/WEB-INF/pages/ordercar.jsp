<%@ include file="/WEB-INF/pages/addon/header.jsp"%>
<div class="container-fluid" align="center">

	<div class="row-fluid pg-title">
		<h3>
			<fmt:message key="taxistation.ordercar.title" bundle="${bundle}" />
		</h3>
	</div>

	<div class="row">
		<div class="col-sm-6 col-sm-offset-3 ">
			<form action="./ordercar" method="POST" role="form">

				<c:if test="${not empty error}">
					<div class="alert alert-danger" role="alert">
						<span class="glyphicon glyphicon-exclamation-sign"
							aria-hidden="true"></span>
						<p>${error}</p>
					</div>
				</c:if>

				<c:if test="${not empty success}">
					<div class="alert alert-success" role="alert">
						<span class="glyphicons glyphicons-tick" aria-hidden="true"></span>
						<p>${success}</p>
					</div>
				</c:if>


				<div class="form-group">
					<label for="pickup"><fmt:message key="taxistation.pickup"
							bundle="${bundle}" />*</label> <input type="text" class="form-control"
						id="pickup" name="pickup"
						placeholder="<fmt:message key="taxistation.pickup" bundle="${bundle}"/>" />
				</div>

				<div class="form-group">
					<label for="destination"><fmt:message
							key="taxistation.destination" bundle="${bundle}" />*</label> <input
						type="text" class="form-control" id="destination"
						name="destination"
						placeholder="<fmt:message key="taxistation.destination" bundle="${bundle}"/>" />
				</div>

				<div class="form-group">
					<p>
						<input type="checkbox" name="carchar_minibus" value="MINIBUS" />
						<fmt:message key="taxistation.carchar.minibus" bundle="${bundle}" />
					
					
						<input type="checkbox" name="carchar_animal"
							value="ANIMAL_TRANSPORTATION" />
						<fmt:message key="taxistation.carchar.animal" bundle="${bundle}" />
					
					
						<input type="checkbox" name="carchar_receipt" value="RECEIPT" />
						<fmt:message key="taxistation.carchar.receipt" bundle="${bundle}" />
					</p>
					<p>
						<input type="checkbox" name="carchar_creditcard"
							value="CREDITCARD_PAYMENT" />
						<fmt:message key="taxistation.carchar.creditcard"
							bundle="${bundle}" />
					
					
						<input type="checkbox" name="carchar_wifi" value="WIFI" />
						<fmt:message key="taxistation.carchar.wifi" bundle="${bundle}" />
					
					
						<input type="checkbox" name="carchar_premium" value="PREMIUM" />
						<fmt:message key="taxistation.carchar.premium" bundle="${bundle}" />
					</p>	
				</div>
				<button type="submit" class="btn btn-default" id="submitButton">
					<fmt:message key="taxistation.submit" bundle="${bundle}" />
				</button>
			</form>
		</div>
	</div>
</div>
</body>
</html>