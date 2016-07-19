package org.springframework.samples.travel;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cryptic.propertyreader.PropertyFileLocationException;
import com.cryptic.propertyreader.PropertyFileReader;
import com.cryptic.propertyreader.UserPropertyFileReader;

/**
 * Main controller class which controls the flow of the application.
 * 
 * @author waseem.faruque.syed
 *
 */
@Controller
public class HotelsController {

	private BookingService bookingService;
	private List<Hotel> hotels = new ArrayList<Hotel>();
	static int status = 0;

	@Inject
	public HotelsController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	/**
	 * Displays search hotel page to the user.
	 * 
	 * @param searchCriteria
	 * @param currentUser
	 * @param model
	 */
	@RequestMapping(value = "/hotels/search", method = RequestMethod.GET)
	public void search(SearchCriteria searchCriteria, Principal currentUser,
			Model model) {
		if (status++ == 0) {
			bookingService.createBasicData();
		}
		if (currentUser != null) {
			List<Booking> booking = bookingService.findBookings(currentUser
					.getName());
			model.addAttribute(booking);
		}
	}

	/**
	 * Displays the hotel list to the user.
	 * 
	 * @param criteria
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/hotels", method = RequestMethod.GET)
	public String list(SearchCriteria criteria, Model model) {
		hotels = bookingService.findHotels(criteria);
		model.addAttribute(hotels);
		return "hotels/list";
	}

	/**
	 * Returns the user to the main home page.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/returnToHome", method = RequestMethod.GET)
	public String returnToHome(Model model) {
		return "home";
	}

	/**
	 * Displays hoteld details page to the user.
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/hotels/{id}", method = RequestMethod.GET)
	public String show(@PathVariable Long id, Model model) {
		model.addAttribute(bookingService.findHotelById(id));
		return "hotels/show";
	}

	@RequestMapping(value = "/bookings/{id}", method = RequestMethod.DELETE)
	public String deleteBooking(@PathVariable Long id) {
		bookingService.cancelBooking(id);
		return "redirect:../hotels/search";
	}

	/**
	 * Redirects user to the booking details page.
	 * 
	 * @param hotel
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/bookingDetails", method = RequestMethod.GET)
	public String book(Hotel hotel, Model model) {

		model.addAttribute(bookingService.createBooking(hotel.getId(),
				getValidUserName()));
		return "enterBookingDetails";
	}

	/**
	 * Redirects user to review booking details page or the hotel search result
	 * page depending on the user response on the booking details page.
	 * 
	 * @param booking
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bookingConfirm", method = RequestMethod.GET)
	public String confirmBooking(Booking booking, Model model,
			HttpServletRequest request) {
		String url = "";
		if (request.getParameter("_eventId_proceed") != null) {
			model.addAttribute(bookingService.confirmBooking(booking));
			url = "reviewBooking";
		} else if (request.getParameter("_eventId_cancel") != null) {
			model.addAttribute(hotels);
			url = "hotels/list";
		}

		return url;
	}

	/**
	 * Redirects to acknowledgement page or enter booking details page or hotel
	 * search page depending on the user response on confirmation page.
	 * 
	 * @param booking
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bookingConfirmDetails", method = RequestMethod.GET)
	public String bookingConfirmDetails(Booking booking, Model model,
			HttpServletRequest request) {
		String url = "";
		if (request.getParameter("_eventId_confirm") != null) {
			model.addAttribute(bookingService.confirmBooking(booking));
			url = "acknowledgement";
		} else if (request.getParameter("_eventId_revise") != null) {
			url = "enterBookingDetails";
		} else if (request.getParameter("_eventId_cancel") != null) {
			url = "redirect:../hotels/search";
		}

		return url;
	}

	/**
	 * Gets valid user details from the property file.
	 * 
	 * @return user name
	 */
	private String getValidUserName() {
		String username = null;
		try {
			UserPropertyFileReader reader = new UserPropertyFileReader(
					PropertyFileReader.PROPERTY_FILE_PATH_CLASSPATH,
					"user.properties");
			username = reader.getUsername();
			String password = reader.getPassword();
			System.out
					.println("Using the property file in the classpath :::\nUsername::"
							+ username + ",password::" + password);
		} catch (PropertyFileLocationException e) {
			System.out
					.println(" The property file reader cannot be initialized");
		}
		return username;
	}

}