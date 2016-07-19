package org.springframework.samples.travel;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/root-context.xml")
@Transactional
public class HotelsControllerTest {
	
	private static final Long HOTEL_ID = new Long("2");

	@Autowired
	private BookingService bookingService;

	private HotelsController controller;

	private SearchCriteria searchCriteria;
	@Mock
	private Model model;
	@Mock
	private Hotel hotel;
	@Mock
	private HttpServletRequest request;
	@Mock
	private Booking booking;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		controller = new HotelsController(bookingService);
		searchCriteria = new SearchCriteria();
		when(hotel.getId()).thenReturn(HOTEL_ID);
	}

	@Test
	public void testList() {
		Assert.assertEquals("hotels/list", controller.list(searchCriteria, model));
		verify(model,times(1)).addAttribute(any(List.class));
	}
	
	@Test
	public void testReturnHome() {
		Assert.assertEquals("home", controller.returnToHome(model));
	}
	
	@Test
	public void testShow() {
		Assert.assertEquals("hotels/show", controller.show(HOTEL_ID, model));
	}
	
	@Test
	public void testBook() {
		bookingService.createBasicData();
		Assert.assertEquals("enterBookingDetails", controller.book(hotel, model));
	}
	
	@Test
	public void testConfirmBooking_blankURL() {
		Assert.assertEquals("", controller.confirmBooking(booking , model, request));
	}
	
	@Test
	public void testConfirmBooking_reviewBookingURL() {
		when(request.getParameter("_eventId_proceed")).thenReturn("");
		Booking realBooking = new Booking();
		Assert.assertEquals("reviewBooking", controller.confirmBooking(realBooking , model, request));
	}
	

	@Test
	public void testConfirmBooking_cancelURL() {
		when(request.getParameter("_eventId_cancel")).thenReturn("");
		Assert.assertEquals("hotels/list", controller.confirmBooking(booking , model, request));
	}
	
	
	@Test
	public void testBookingConfirmDetails_blankURL() {
		Assert.assertEquals("", controller.bookingConfirmDetails(booking , model, request));
	}
	
	@Test
	public void testbookingConfirmDetails_acknowledgementURL() {
		when(request.getParameter("_eventId_confirm")).thenReturn("");
		Booking realBooking = new Booking();
		Assert.assertEquals("acknowledgement", controller.bookingConfirmDetails(realBooking , model, request));
	}
	
	
	@Test
	public void testbookingConfirmDetails_enterBookingDetailsURL() {
		when(request.getParameter("_eventId_revise")).thenReturn("");
		Assert.assertEquals("enterBookingDetails", controller.bookingConfirmDetails(booking , model, request));
	}
	
	@Test
	public void testbookingConfirmDetails_cancelURL() {
		when(request.getParameter("_eventId_cancel")).thenReturn("");
		Assert.assertEquals("redirect:../hotels/search", controller.bookingConfirmDetails(booking , model, request));
	}
	

}
