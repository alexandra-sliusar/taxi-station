<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="lang" scope="session"
	value="${empty sessionScope.locale ? 'en_GB' : sessionScope.locale}" />
<fmt:setLocale value="${lang}" scope="session" />
<fmt:setBundle basename="/i18n/messages" var="bundle" scope="session" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/bootstrap-3.3.7-dist/css/bootstrap.min.css" />">

<meta name="viewport" content="width=device-width, initial-scale=1">
<title><fmt:message key="taxistation.title" bundle="${bundle}" /></title>
</head>
<body>
	<div class="menu">
		<div class="container-fluid">
			<ul class="nav navbar-left">
				<img src="${pageContext.request.contextPath}/resources/img/logo.png"
					width=50px />
			</ul>
			<ul class="nav navbar-nav">
				<li><a href="${pageContext.request.contextPath}/main/"><fmt:message
							key="taxistation.brand" bundle="${bundle}" /></a></li>


				<c:if test="${not empty user}">
					<c:if test="${user.getRole().name().toLowerCase() eq 'client' }">
						<li><a href="${pageContext.request.contextPath}/main/order">
								<fmt:message key="taxistation.order.car" bundle="${bundle}" />
						</a></li>

						<li><a
							href="${pageContext.request.contextPath}/main/
							${user.getRole().name().toLowerCase()}/history">
								<fmt:message key="taxistation.history" bundle="${bundle}" />
						</a></li>

					</c:if>
					<c:if test="${user.getRole().name().toLowerCase() eq 'driver' }">
						<li><a href="${pageContext.request.contextPath}/main/profile"><fmt:message
									key="taxistation.car.profile" bundle="${bundle}" /></a></li>
						<li><a
							href="${pageContext.request.contextPath}/main/
							${user.getRole().name().toLowerCase()}/history">
								<fmt:message key="taxistation.history" bundle="${bundle}" />
						</a></li>
					</c:if>
					<c:if
						test="${user.getRole().name().toLowerCase() eq 'dispatcher' }">
						<li><a
							href="${pageContext.request.contextPath}/main/requests"><fmt:message
									key="taxistation.requests" bundle="${bundle}" /></a></li>
						<li><a href="${pageContext.request.contextPath}/main/cars"><fmt:message
									key="taxistation.cars" bundle="${bundle}" /></a></li>
					</c:if>
				</c:if>
			</ul>


			<ul class="nav navbar-nav navbar-right">
				<li><p class="navbar-text lang">
						<a href="${pageContext.request.contextPath}/main/locale?lang=EN">EN</a>
						| <a href="${pageContext.request.contextPath}/main/locale?lang=RU">RU</a>
					</p></li>
				<c:choose>
					<c:when test="${not empty user}">
						<li><a href="${pageContext.request.contextPath}/main/logout"><span
								class="glyphicon glyphicon-log-out"
								title="<fmt:message
								key="taxistation.logout" bundle="${bundle}" />"></span>
						</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath}/main/login"><span
								class="glyphicon glyphicon-log-in"
								title="<fmt:message
								key="taxistation.login" bundle="${bundle}" />"></span>
						</a></li>

						<li><a href="${pageContext.request.contextPath}/main/signup"><span
								class="glyphicon glyphicon-cloud-upload"
								title="<fmt:message
								key="taxistation.signup" bundle="${bundle}" />"></span>
						</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>