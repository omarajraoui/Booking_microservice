package com.taxi.booking.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@ToString
public class TaxiBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    private Long taxiBookingId;
    private TaxiBookingStatus taxiBookingStatus =TaxiBookingStatus.False;
    private String taxiId;//other service
    private long customerId;
    private TaxiStatus taxiStatus;//other service
    private String bookedTime;
    private String cancelTime;


    public TaxiBooking(Long taxiBookingId, TaxiBookingStatus taxiBookingStatus, String taxiId, long customerId, TaxiStatus taxiStatus, String bookedTime, String cancelTime) {
        this.taxiBookingId = taxiBookingId;
        this.taxiBookingStatus = taxiBookingStatus;
        this.taxiId = taxiId;
        this.customerId = customerId;
        this.taxiStatus = taxiStatus;
        this.bookedTime = bookedTime;
        this.cancelTime = cancelTime;
    }
}
