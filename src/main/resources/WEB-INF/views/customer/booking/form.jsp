<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:input-select code="customer.booking.form.label.flight" path="flight" choices="${flights}" readonly="${isPublished}"/>
	<acme:input-textarea code="customer.booking.form.label.locatorCode" path="locatorCode" readonly="${isPublished}"/>
	<acme:input-textbox code="customer.booking.form.label.purchaseMoment" path="purchaseMoment" readonly="true"/>
	<acme:input-select code="customer.booking.form.label.travelClass" path="travelClass" choices="${travelClass}" readonly="${isPublished}"/>	
	<acme:input-textarea code="customer.booking.form.label.price" path="price" readonly="true"/>
	<acme:input-textarea code="customer.booking.form.label.lastNibble" path="lastNibble" readonly="${isPublished}"/>

	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|publish|delete') && isPublished == false}">
			<acme:submit code="customer.booking.form.button.update" action="/customer/booking/update"/>
			<acme:submit code="customer.booking.form.button.publish" action="/customer/booking/publish"/>
			<acme:submit code="customer.booking.form.button.delete" action="/customer/booking/delete?bookingId=${id}"/>
			<jstl:if test="${ hasPassengers == true}">
					<acme:button code="customer.booking.form.button.passengers" action="/customer/passenger/list?bookingId=${id}"/>
				</jstl:if>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|publish|delete') && isPublished == true}">
			<acme:button code="customer.booking.form.button.passengers" action="/customer/passenger/list?bookingId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="customer.booking.form.button.create" action="/customer/booking/create"/>
		</jstl:when>		
	</jstl:choose>
			
	
</acme:form>