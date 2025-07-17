package com.decoticket.api.services;

import com.decoticket.api.domain.flight.Flight;
import com.decoticket.api.domain.seat.Seat;
import com.decoticket.api.repositories.SeatRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public void generateSeats(Flight flight, int seatCount){
        List<Seat> seats = new ArrayList<>();

        char[] seatRows = {'A', 'B', 'C', 'D', 'E', 'F'};

        int fullRows = seatCount / 6;

        for(int row = 1; row <= fullRows; row++){
            for(char seatRow : seatRows){
                Seat seat = new Seat();
                seat.setFlight(flight);
                seat.setOccupied(false);
                seat.setSeatNumber(seatRow + String.valueOf(row));
                if(row <= 4){
                    seat.setSeatClass("EXECUTIVE");
                    seat.setPrice(4250.0f);
                }else if(row <= 8) {
                    seat.setSeatClass("BUSINESS");
                    seat.setPrice(2750.0f);
                }else{
                    seat.setSeatClass("ECONOMY");
                    seat.setPrice(1500.0f);
                }
                seats.add(seat);
            }
        }
        seatRepository.saveAll(seats);
    }
}
