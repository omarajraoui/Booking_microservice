package com.taxi.booking.Dto;

import com.taxi.booking.Model.TaxiBookingStatus;
import com.taxi.booking.Model.TaxiStatus;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

public class TaxiBookedEventDto {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taxiBookingId;
    private String taxiId;

    //ddepuis ou?
    private long customerId;
    private String bookedTime;
    private TaxiBookingStatus taxiBookingStatus;
    //depuis service
    private TaxiStatus taxiStatus;

    public TaxiBookedEventDto(Long taxiBookingId, String taxiId, long customerId, String bookedTime, TaxiStatus taxiStatus, TaxiBookingStatus taxiBookingStatus) {
        this.taxiBookingId = taxiBookingId;
        this.taxiId =taxiId;
        this.customerId = customerId;
        this.bookedTime = bookedTime;
        this.taxiStatus = taxiStatus;
        this.taxiBookingStatus = taxiBookingStatus;
    }


    public Long getTaxiBookingId() {
        return taxiBookingId;
    }

    public void setTaxiBookingId(Long taxiBookingId) {
        this.taxiBookingId = taxiBookingId;
    }


    public String getBookedTime() {
        return bookedTime;
    }

    public void setBookedTime(String bookedTime) {
        this.bookedTime = bookedTime;

    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }


    public TaxiBookingStatus getTaxiBookingStatus() {
        return taxiBookingStatus;
    }

    public void setTaxiBookingStatus(TaxiBookingStatus taxiStatus) {
        this.taxiBookingStatus = taxiBookingStatus;
    }


    public TaxiStatus getTaxiStatus() {
        return taxiStatus;
    }

    public void setTaxiStatus(TaxiStatus taxiStatus) {
        this.taxiStatus = taxiStatus;
    }

    public String getTaxiId() {
        return taxiId;
    }

    public void setTaxiId(String taxiId) {
        this.taxiId = taxiId;
    }
}
