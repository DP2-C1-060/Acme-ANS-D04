
package acme.features.customer.bookingRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.booking.Booking;
import acme.entities.booking.BookingRecord;
import acme.entities.booking.Passenger;
import acme.realms.Customer;

@GuiService
public class CustomerBookingRecordCreateService extends AbstractGuiService<Customer, BookingRecord> {

	@Autowired
	private CustomerBookingRecordRepository customerBookingRecordRepository;


	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(Customer.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		BookingRecord bookingRecord = new BookingRecord();

		super.getBuffer().addData(bookingRecord);

	}

	@Override
	public void bind(final BookingRecord bookingRecord) {
		super.bindObject(bookingRecord, "passenger", "booking");
	}

	@Override
	public void validate(final BookingRecord br) {

		if (super.getBuffer().getErrors().hasErrors("booking") || super.getBuffer().getErrors().hasErrors("passenger"))
			return;
		long duplicates = this.customerBookingRecordRepository.countByBookingAndPassenger(br.getBooking().getId(), br.getPassenger().getId());

		super.state(duplicates == 0, "*", "customer.booking-record.form.error.duplicate");
	}

	@Override
	public void perform(final BookingRecord bookingRecord) {
		this.customerBookingRecordRepository.save(bookingRecord);
	}

	@Override
	public void unbind(final BookingRecord bookingRecord) {

		Integer customerId = super.getRequest().getPrincipal().getActiveRealm().getId();

		Collection<Passenger> passengers = this.customerBookingRecordRepository.getAllPassengersByCustomer(customerId);
		Collection<Booking> bookings = this.customerBookingRecordRepository.getBookingsByCustomerId(customerId);
		SelectChoices passengerChoices = SelectChoices.from(passengers, "fullName", bookingRecord.getPassenger());
		SelectChoices bookingChoices = SelectChoices.from(bookings, "locatorCode", bookingRecord.getBooking());
		Dataset dataset = super.unbindObject(bookingRecord, "passenger", "booking");
		dataset.put("passengers", passengerChoices);
		dataset.put("bookings", bookingChoices);

		super.getResponse().addData(dataset);

	}

}
