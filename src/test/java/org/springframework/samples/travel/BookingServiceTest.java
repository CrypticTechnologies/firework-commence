package org.springframework.samples.travel;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/root-context.xml")
@Transactional
public class BookingServiceTest {

	@Autowired
	private BookingService bookingService;

	@Before
	public void setUp() {
		bookingService.createBasicData();
	}

	@Test
	public void testUser() {
		User findUser = bookingService.findUser("waseemsyed");
		if (findUser != null) {
			Assert.assertEquals("waseemsyed", findUser.getUsername());
		}
	}

	@Test
	public void testFindBookings() {
		List<Booking> bookings = bookingService.findBookings("waseemsyed");
		if (bookings != null) {
			Assert.assertTrue(bookings.isEmpty());
		}
	}

	@Test
	public void testFindHotels() {
		SearchCriteria search = new SearchCriteria();
		search.setSearchString("Westin");
		List<Hotel> hotels = bookingService.findHotels(search);
		if (hotels != null) {
			Assert.assertTrue(hotels.isEmpty());
		}
	}

	@Test
	public void testFindHotelById() {
		Long hotelId = new Long("1");
		Hotel hotel = bookingService.findHotelById(hotelId);
		if (hotel != null) {
			Assert.assertEquals("Westin Diplomat", hotel.getName());
		}
	}

	@Test
	public void testCreateBooking() {
		Long hotelId = new Long("3");
		Booking booking = bookingService.createBooking(hotelId, "waseemsyed");
		if (booking != null) {
			Assert.assertEquals("Chilworth Manor", booking.getHotel().getName());
		}
	}

	@Test
	public void testCreateBasicData() {
		Assert.assertEquals("Waseem Syed", bookingService
				.findUser("waseemsyed").getName());
	}

	@Test
	public void testFindUser() {
		Assert.assertEquals("testname", bookingService.findUser("testuser")
				.getName());
	}

	@Test
	public void testConfirmBooking() {
		Booking booking = new Booking(
				bookingService.findHotelById(new Long("2")),
				bookingService.findUser("waseemsyed"));
		Assert.assertEquals(booking, bookingService.confirmBooking(booking));
	}
}
