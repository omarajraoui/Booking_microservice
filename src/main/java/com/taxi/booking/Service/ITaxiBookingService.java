package com.taxi.booking.Service;

import com.taxi.booking.Dto.TaxiBookingAcceptedEventDTO;
import com.taxi.booking.Dto.TaxiBookingCanceledEventDTO;
import com.taxi.booking.Model.TaxiBooking;
import com.taxi.booking.Dto.TaxiBookedEventDto;
import com.taxi.booking.Model.TaxiBookingStatus;
import com.taxi.booking.Model.TaxiLocation;

import java.util.List;

public interface ITaxiBookingService {
    public TaxiBooking book(TaxiBookedEventDto taxiBookedEventDto);
    public TaxiBooking cancel(Long taxiBookingId);// TaxiBookingCanceledEventDTO canceledEventDTO);
    public TaxiBooking accept(TaxiBooking taxiBooking);

    public List<TaxiBooking> getAllBookings();

    public TaxiBooking updateBookingStatus(String taxiBookingId,
                               TaxiBookingStatus taxiBookingStatus);

}
