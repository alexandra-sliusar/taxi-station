<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale
	value="${empty sessionScope.locale ? 'en_US' : sessionScope.locale}"
	scope="session" />
<fmt:setBundle basename="/i18n/messages" var="bundle" scope="session" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<div class="container" align="center" style="width: 300px;">
	<h3>Hello from inside</h3>
</div>
</body>
</html>
