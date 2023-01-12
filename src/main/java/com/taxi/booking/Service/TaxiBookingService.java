package com.taxi.booking.Service;

import com.taxi.booking.Dto.TaxiBookedEventDto;
import com.taxi.booking.Model.TaxiBooking;
import com.taxi.booking.Model.TaxiBookingStatus;
import com.taxi.booking.Repository.TaxiBookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaxiBookingService implements ITaxiBookingService {
    private final TaxiBookingRepository taxiBookingRepository;
//    private final TaxiService taxiService;

    public TaxiBookingService(TaxiBookingRepository taxiBookingRepository) {
        this.taxiBookingRepository = taxiBookingRepository;
    }

    @Override
    public TaxiBooking book(TaxiBookedEventDto taxiBookedEventDto) {
        TaxiBooking taxiBooking = new TaxiBooking();
        taxiBooking.setCustomerId(taxiBookedEventDto.getCustomerId());
        taxiBooking.setTaxiId(taxiBookedEventDto.getTaxiId());
        taxiBooking.setTaxiBookingStatus(taxiBookedEventDto.getTaxiBookingStatus());

        TaxiBooking savedTaxiBooking =
                taxiBookingRepository.save(taxiBooking);
        return savedTaxiBooking;
    }


    @Override
    public TaxiBooking cancel(Long taxiBookingId){ //, TaxiBookingCanceledEventDTO canceledEventDTO) {
        TaxiBooking taxiBooking = taxiBookingRepository.findById((Long) taxiBookingId).get();
        taxiBooking.setTaxiBookingStatus(TaxiBookingStatus.Cancelled);
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        taxiBooking.setCancelTime(formattedDate);
//        taxiBookingRepository.delete(taxiBooking);
        return taxiBooking;
    }


    @Override
    public TaxiBooking accept(TaxiBooking taxiBooking) {
        taxiBooking.setTaxiBookingStatus(TaxiBookingStatus.Accepted);
        return taxiBooking;

    }

    @Override
    public List<TaxiBooking> getAllBookings() {
        List<TaxiBooking> TaxiBookingEntities
                = taxiBookingRepository.findAll();

        List<TaxiBooking> taxiBookiing = TaxiBookingEntities
                .stream()
                .map(taxi -> new TaxiBooking(
                        taxi.getTaxiBookingId(),
                        taxi.getTaxiBookingStatus(),
                        taxi.getTaxiId(),
                        taxi.getCustomerId(),
                        taxi.getTaxiStatus(),
                        taxi.getBookedTime(),
                        taxi.getCancelTime()
                )).collect(Collectors.toList());
        return taxiBookiing;
    }


    @Override
    public TaxiBooking updateBookingStatus(String taxiBookingId, TaxiBookingStatus taxiBookingStatus) {
        return null;
    }

//    @Override
//    public TaxiBooking updateBookingStatus(String taxiBookingId, TaxiBookingStatus taxiBookingStatus) {
//        return null;
//    }


}
