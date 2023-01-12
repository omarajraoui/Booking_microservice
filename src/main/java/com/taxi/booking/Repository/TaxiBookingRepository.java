package com.taxi.booking.Repository;

import com.taxi.booking.Model.TaxiBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiBookingRepository extends JpaRepository<TaxiBooking,Long> {

}
