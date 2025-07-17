package com.decoticket.api.services;

import com.decoticket.api.domain.flight.Flight;
import com.decoticket.api.domain.flight.FlightRequestDTO;
import com.decoticket.api.domain.flight.FlightResponseDTO;
import com.decoticket.api.domain.seat.SeatResponseDTO;
import com.decoticket.api.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private SeatService seatService;

    public List<FlightResponseDTO> listAll(){
        return flightRepository.findAll()
                .stream()
                .map(flight -> new FlightResponseDTO(
                        flight.getId(),
                        flight.getDeparture(),
                        flight.getDestination(),
                        flight.getDepartureDate(),
                        flight.getArrivalDate(),
                        flight.getSeats().stream()
                                .map(seat -> new SeatResponseDTO(
                                        seat.getSeatNumber(),
                                        seat.getSeatClass(),
                                        seat.isOccupied(),
                                        seat.getPrice(),
                                        seat.getUser()
                                ))
                                .toList()
                ))
                .toList();
    }

    public Flight createFlight(FlightRequestDTO data){
        Flight newFlight = new Flight();
        newFlight.setDeparture(data.departure());
        newFlight.setDestination(data.destination());
        newFlight.setDepartureDate(data.departureDate());
        newFlight.setArrivalDate(data.arrivalDate());
        newFlight.setSeatCount(data.seatCount());
        flightRepository.save(newFlight);

        seatService.generateSeats(newFlight, data.seatCount());

        return newFlight;
    }
}
