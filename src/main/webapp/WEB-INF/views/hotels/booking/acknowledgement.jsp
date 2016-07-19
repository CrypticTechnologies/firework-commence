<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<c:url var="returnToHomeUrl" value="/returnToHome" />
<form:form modelAttribute="booking" action="${returnToHomeUrl}" method="get" cssClass="inline">
	<fieldset>
		<h1>
			Thank you
			<spring:bind path="user.name">${status.value}</spring:bind>
			for booking in
			<spring:bind path="hotel.name">${status.value}</spring:bind>.
		</h1>
		<button type="submit">Return to Home Page</button>
	</fieldset>
</form:form>