<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="bookingForm">
	<div class="span-5">
		<h3>${booking.hotel.name}</h3>
		
		<address>
		<spring:bind path="booking.hotel.address">${status.value}</spring:bind>
			<br/>
			<spring:bind path="booking.hotel.city">${status.value}</spring:bind>, <spring:bind path="booking.hotel.state">${status.value}</spring:bind>, <spring:bind path="booking.hotel.zip">${status.value}</spring:bind>
			<br/>
			${booking.hotel.country}
		</address>
	</div>
	<div class="span-12 last">
		<c:url var="bookingConfirmDetailsURL" value="/bookingConfirmDetails"/>
		<form:form id="confirm" modelAttribute="booking" action="${bookingConfirmDetailsURL}" method="get">
		<fieldset>
			<form:input type="hidden" id="user.name" path="user.name" name="user.name" value="${booking.user.name}" />
			<form:input type="hidden" id="user.username" path="user.username" name="user.username" value="${booking.user.username}" />
			<form:input type="hidden" id="hotel.id" path="hotel.id" name="hotel.id" value="${booking.hotel.id}" />
			<form:input type="hidden" id="hotel.name" path="hotel.name" name="hotel.name" value="${booking.hotel.name}" />
			<form:input type="hidden" id="hotel.price" path="hotel.price" name="hotel.price" value="${booking.hotel.price}" />
			<form:input type="hidden" id="hotel.address" path="hotel.address" name="hotel.address" value="${booking.hotel.address}" />
			<form:input type="hidden" id="hotel.city" path="hotel.city" name="hotel.city" value="${booking.hotel.city}" />
			<form:input type="hidden" id="hotel.state" path="hotel.state" name="hotel.state" value="${booking.hotel.state}" />
			<form:input type="hidden" id="hotel.zip" path="hotel.zip" name="hotel.zip" value="${booking.hotel.zip}" />
			<form:input type="hidden" id="hotel.country" path="hotel.country" name="hotel.country" value="${booking.hotel.country}" />
			<legend>Confirm Booking Details</legend>
			<div>
				<div class="span-3">Check In:</div>
				<div class="span-8 last">
					<p><spring:bind path="checkinDate">${status.value}</spring:bind></p>
				</div>
			</div>
			<div>
				<div class="span-3">Checkout:</div>
				<div class="span-8 last">
					<p><spring:bind path="checkoutDate">${status.value}</spring:bind></p>
				</div>
			</div>
	        <div>
	            <div class="span-3">Number of Nights:</div>
	            <div class="span-8 last">
	            	<p><spring:bind path="nights">${status.value}</spring:bind></p>
	            </div>
	        </div>
	        <div>
	            <div class="span-3">Total Payment:</div>
	            <div class="span-8 last">
	            	<p><spring:bind path="total">${status.value}</spring:bind></p>
	            </div>
	        </div>
			<div>
				<div class="span-3">Credit Card #:</div>
				<div class="span-8 last">
				<p><spring:bind path="creditCard">${status.value}</spring:bind></p>
				</div>
			</div>
			<div>
				<button type="submit" name="_eventId_confirm">Confirm</button>
				<button type="submit" name="_eventId_revise">Revise</button>
				<button type="submit" name="_eventId_cancel">Cancel</button>
			</div>
		</fieldset>
		</form:form>
	</div>

</div>
