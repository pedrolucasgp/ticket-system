package com.decoticket.api.domain.flight;

import com.decoticket.api.domain.seat.Seat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "flight")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    @Id
    @GeneratedValue
    private UUID id;

    private String departure;
    private String destination;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private int seatCount;
    @OneToMany(mappedBy = "flight")
    private List<Seat> seats = new ArrayList<>();

}
