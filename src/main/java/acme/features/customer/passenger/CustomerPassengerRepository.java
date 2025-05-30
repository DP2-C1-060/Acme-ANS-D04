
package acme.features.customer.passenger;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.booking.Booking;
import acme.entities.booking.BookingRecord;
import acme.entities.booking.Passenger;
import acme.realms.Customer;

@Repository
public interface CustomerPassengerRepository extends AbstractRepository {

	@Query("SELECT c FROM Customer c WHERE c.id = :customerId")
	Customer findCustomerById(Integer customerId);

	@Query("SELECT p FROM Passenger p WHERE p.id=:passengerId")
	Passenger getPassengerById(int passengerId);

	@Query("SELECT b FROM Booking b WHERE b.id=:bookingId")
	Booking getBookingById(int bookingId);

	@Query("SELECT br.passenger FROM BookingRecord br WHERE br.booking.id =:bookingId")
	List<Passenger> findPassengerByBookingId(Integer bookingId);

	@Query("SELECT p FROM Passenger p WHERE p.customer.id=:customerId")
	Collection<Passenger> getPassengersByCustomer(int customerId);

	@Query("SELECT p FROM Passenger p WHERE p.id=:passengerId")
	Passenger findPassengerById(int passengerId);

	@Query("SELECT br FROM BookingRecord br WHERE br.passenger.id = :passengerId")
	Collection<BookingRecord> findAllBookingRecordsByPassengerId(int passengerId);

	@Query("SELECT p FROM Passenger p WHERE p.passportNumber = :passportNumber")
	List<Passenger> findAllByPassportNumber(@Param("passportNumber") String passportNumber);

}
