<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="Andreas Bengtsson">
<meta name="theme-color" content="#563d7c">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title><tiles:insertAttribute name="title" /></title>

<c:set var="contextRoot" value="${pageContext.request.contextPath}" />

<!-- BootstrapCSS -->
<link rel="stylesheet" href="${contextRoot}/css/bootstrap.min.css">

<!-- Custom styles -->
<link href="${contextRoot}/css/main.css" rel="stylesheet">

<!--  JQuery -->
<script src="${contextRoot}/js/jquery-3.4.1.slim.min.js" type="text/javascript"></script>

<!--  BootstrapJS -->
<script src="${contextRoot}/js/bootstrap.bundle.min.js" type="text/javascript"></script>

</head>

<body>
	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
		<a class="navbar-brand" href="${contextRoot}/">Office Climate
			System</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarCollapse" aria-controls="navbarCollapse"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarCollapse">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><a class="nav-link" href="${contextRoot}/">Home</a></li>
				<li class="nav-item"><a class="nav-link"
					href="${contextRoot}/about">About</a></li>
			</ul>
			<ul class="navbar-nav ml-auto">

				<sec:authorize access="!isAuthenticated()">
					<li class="nav-item">
						<a class="nav-link" href="${contextRoot}/login">Login</a>
					</li>
				</sec:authorize>
				
				<sec:authorize access="isAuthenticated()">

					<sec:authorize access="hasRole('ROLE_USER')">
						<li class="nav-item">
							<a class="nav-link" href="${contextRoot}/dashboard">Dashboard</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="javascript:$('#logoutForm').submit();">Logout</a>
						</li>				
					</sec:authorize>
									
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<li class="nav-item">
							<a class="nav-link" href="${contextRoot}/dashboard">Dashboard</a>
						</li>
						<li class="nav-item dropdown">
							<button type="button"
								class="btn btn-dark dropdown-toggle dropdown-toggle-split"
								data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								Settings <span class="sr-only">Toggle dropdown</span>
							</button>
							<div class="dropdown-menu dropdown-menu-right">
								<a class="dropdown-item" href="${contextRoot}/register">Register user</a>
								<div class="dropdown-divider"></div>
								<a class="nav-link" href="javascript:$('#logoutForm').submit();">Logout</a>
							</div>
						</li>
					</sec:authorize>

				</sec:authorize>
			

			</ul>
		</div>
	</nav>

	<c:url var="logoutLink" value="/logout" />
	<form id="logoutForm" method="post" action="${logoutLink}">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>

	<div class="container-fluid main">
		<tiles:insertAttribute name="content" />
	</div>
</body>
</html>