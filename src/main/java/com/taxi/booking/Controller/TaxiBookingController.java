package com.taxi.booking.Controller;

import com.taxi.booking.Dto.TaxiBookedEventDto;
import com.taxi.booking.Model.TaxiBooking;
import com.taxi.booking.Model.TaxiBookingStatus;
import com.taxi.booking.Model.TaxiStatus;
import com.taxi.booking.Repository.TaxiBookingRepository;
import com.taxi.booking.Service.TaxiBookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("/api/v1")
@RestController
public class TaxiBookingController {

    private final TaxiBookingService taxiBookingService;
    private final TaxiBookingRepository taxiBookingRepository;


    public TaxiBookingController(TaxiBookingService taxiBookingService, TaxiBookingRepository taxiBookingRepository) {
        this.taxiBookingService = taxiBookingService;
        this.taxiBookingRepository = taxiBookingRepository;
    }

    @GetMapping("/taxiBookings")
    public List<TaxiBooking> getTaxiBookings(){
        return taxiBookingService.getAllBookings();

    }

    @PutMapping("/acceptBooking/{id}")
    public ResponseEntity<?> acceptBooking(@PathVariable Long id){
        Map<String,TaxiBooking> response = new HashMap<>();
        TaxiBooking availability = taxiBookingRepository.findById((Long) id).get();
        TaxiStatus taxiStatus = availability.getTaxiStatus();
        if((availability.getTaxiBookingStatus() == TaxiBookingStatus.False || availability.getTaxiBookingStatus() == TaxiBookingStatus.Cancelled ) && taxiStatus ==TaxiStatus.AVAILABLE){
            availability.setTaxiBookingStatus(TaxiBookingStatus.Booked);
            response.put("Booking accepted", availability);
            return ResponseEntity.ok(response);

        }
        else if (availability.getTaxiBookingStatus() == TaxiBookingStatus.Accepted || availability.getTaxiBookingStatus() == TaxiBookingStatus.Booked ){
             //response entity isnt available !
            return new ResponseEntity<>(
                    "Taxi isnt available !!",
                    HttpStatus.BAD_REQUEST);


        }
        return new ResponseEntity<>(
                "Can't accept the booking !",
                HttpStatus.BAD_REQUEST);
    }

    @PostMapping("bookTaxi")
    @Transactional
    public ResponseEntity<?> book(@RequestBody TaxiBookedEventDto taxiBookedEventDto){
        TaxiBooking taxiBooked = taxiBookingService.book(taxiBookedEventDto);
        //need to communicate to the other taxi service to know if its booked
        taxiBooked.setTaxiBookingStatus(TaxiBookingStatus.Accepted);
//        SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        Date date = new Date(System.currentTimeMillis());
//        taxiBooked.setBookedTime(date);
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        taxiBooked.setBookedTime(formattedDate);
        taxiBooked.setTaxiStatus(TaxiStatus.AVAILABLE); // if condition should get it from the other service
        if (taxiBooked.getTaxiStatus() == TaxiStatus.OCCUPIED || taxiBooked.getTaxiStatus() == TaxiStatus.BROKEN )
        {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Taxi not available at the moment");
        }
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new TaxiBookedEventDto(taxiBooked.getTaxiBookingId(),taxiBooked.getTaxiId(),taxiBooked.getCustomerId(),taxiBooked.getBookedTime(),taxiBooked.getTaxiStatus(),taxiBooked.getTaxiBookingStatus()));


    }

    @PutMapping("/cancelBoooking/{id}")
    public ResponseEntity<Map<String,TaxiBooking>> cancelBooking(@PathVariable Long id) {

        TaxiBooking canceled;
        canceled = taxiBookingService.cancel(id);
        Map<String,TaxiBooking> response = new HashMap<>();
        response.put("canceled", canceled);
        return ResponseEntity.ok(response);
    }






}
