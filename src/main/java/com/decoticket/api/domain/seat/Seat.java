package com.decoticket.api.domain.seat;

import com.decoticket.api.domain.flight.Flight;
import com.decoticket.api.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "seat")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue
    private UUID id;

    private String seatNumber;
    private boolean occupied = false;
    private String seatClass;
    private float price;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
