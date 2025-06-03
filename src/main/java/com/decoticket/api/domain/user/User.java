package com.decoticket.api.domain.user;

import com.decoticket.api.domain.ticket.Ticket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "event")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String email;
    private String fullName;
    private String password;
    private String identification;
    private String role = "User";
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
}
