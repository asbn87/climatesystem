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
				<h3>User Login</h3>
			</div>
			
			<c:if test="${param.error != null}">
				<div class="login-error">
					Incorrect username or password.
				</div>
			</c:if>
			
			<div class="card-body">
				<form class="form-signin" method="post" action="${loginUrl}">
				
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				
					<div class="input-group">
						<input class="form-control m-1" type="text" name="username"
							placeholder="Username" />
					</div>
					<div class="input-group">
						<input class="form-control m-1" type="password" name="password"
							placeholder="Password" />
					</div>
					<div class="input-group">
						<button type="submit" class="btn btn-lg btn-primary btn-block m-1">Sign in</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>