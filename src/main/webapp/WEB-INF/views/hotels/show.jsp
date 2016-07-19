<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1>${hotel.name}</h1>
<address>
	${hotel.address}
	<br />
	${hotel.city}, ${hotel.state}, ${hotel.zip}
	<br />
	${hotel.country}
</address>
<c:url var="bookingUrl" value="/bookingDetails"/>
<form:form modelAttribute="hotel" action="${bookingUrl}" method="get" cssClass="inline">
<fieldset>
	<p>
		Nightly Rate:
		<spring:bind path="hotel.price">${status.value}</spring:bind>
	</p>
		<form:input type="hidden" id="id" path="id" name="id" value="${hotel.id}" />
		<div>
		<button type="submit">Book Hotel</button>
	</div>
	</fieldset>
</form:form>