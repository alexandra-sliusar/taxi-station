<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<c:set var="lang" scope="session" value="${empty sessionScope.locale ? 'en_GB' : sessionScope.locale}" />
<fmt:setLocale value="${lang}" scope="session" />
<fmt:setBundle basename="/i18n/messages" var="bundle" scope="session" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Page not found</title>

<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/style.css" />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/bootstrap-3.3.7-dist/css/bootstrap.min.css" />" />
</head>
<body>
	<div class="container-fluid " align="center">
		<div class="row">

			<div class="error alert alert-danger">
				<strong>
					<fmt:message key="taxistation.error.pagenotfound" bundle="${bundle}" />
				</strong>
			</div>

		</div>
		<div class="row-fluid">
			<a href="${pageContext.request.contextPath}/main/"><fmt:message key="taxistation.error.backtohomepage" bundle="${bundle}" /></a>
		</div>

	</div>
</body>
</html>