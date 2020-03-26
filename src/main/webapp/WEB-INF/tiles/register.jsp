<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:url var="loginUrl" value="/login" />

<div class="row">
	<div class="col-md-4 col-sm-6 ml-auto mr-auto mt-5">
		<div class="card">
			<div class="card-header font-weight-bold text-center">
				<h3>Create an Account</h3>
			</div>
			
			<div class="login-error">
				<form:errors path="user.*" />
			</div>
			
			<div class="card-body">
				<form:form class="form-signin" method="post" modelAttribute="user">

					<div class="input-group">
						<form:input class="form-control m-1" type="text" path="username"
							placeholder="Username" />
					</div>
					<div class="input-group">
						<form:input class="form-control m-1" type="password"
							path="plainPassword" placeholder="Password" />
					</div>
					<div class="input-group">
						<form:input class="form-control m-1" type="password"
							path="repeatPassword" placeholder="Repeat Password" />
					</div>
					<div class="input-group">
						<button type="submit" class="btn btn-lg btn-primary btn-block m-1">Register</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>